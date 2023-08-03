package eToken.SSL;

import iaik.pkcs.pkcs11.Mechanism;
import iaik.pkcs.pkcs11.Module;
import iaik.pkcs.pkcs11.Session;
import iaik.pkcs.pkcs11.Slot;
import iaik.pkcs.pkcs11.Token;
import iaik.pkcs.pkcs11.TokenException;
import iaik.pkcs.pkcs11.TokenInfo;
import iaik.pkcs.pkcs11.objects.GenericSecretKey;
import iaik.pkcs.pkcs11.parameters.SSL3KeyMaterialOutParameters;
import iaik.pkcs.pkcs11.parameters.SSL3KeyMaterialParameters;
import iaik.pkcs.pkcs11.parameters.SSL3MasterKeyDeriveParameters;
import iaik.pkcs.pkcs11.parameters.SSL3RandomDataParameters;
import iaik.pkcs.pkcs11.parameters.VersionParameters;
import iaik.pkcs.pkcs11.wrapper.PKCS11Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

/**
 * This demo program shows how to use the SSL mechanisms. Ensure that your token
 * supports these features.
 *
 * @author <a href="mailto:Karl.Scheibelhofer@iaik.at"> Karl Scheibelhofer </a>
 * @version 0.1
 * @invariants
 */
public class SSLMechanisms {

	static PrintWriter output_;

	static BufferedReader input_;

	static {
		try {
			//output_ = new PrintWriter(new FileWriter("GetInfo_output.txt"), true);
			output_ = new PrintWriter(System.out, true);
			input_ = new BufferedReader(new InputStreamReader(System.in));
		} catch (Throwable thr) {
			thr.printStackTrace();
			output_ = new PrintWriter(System.out, true);
			input_ = new BufferedReader(new InputStreamReader(System.in));
		}
	}

	public static void main(String[] args)
	    throws IOException, TokenException, NoSuchAlgorithmException
	{
		if ((args.length != 1) && (args.length != 2)) {
			printUsage();
			System.exit(1);
		}

		Module pkcs11Module = Module.getInstance(args[0]);
		pkcs11Module.initialize(null);

		Slot[] slots = pkcs11Module.getSlotList(Module.SlotRequirement.TOKEN_PRESENT);

		if (slots.length == 0) {
			output_.println("No slot with present token found!");
			System.exit(0);
		}

		Slot selectedSlot = slots[0];
		Token token = selectedSlot.getToken();
		TokenInfo tokenInfo = token.getTokenInfo();

		output_
		    .println("################################################################################");
		output_.println("Information of Token:");
		output_.println(tokenInfo);
		output_
		    .println("################################################################################");

		List supportedMechanisms = Arrays.asList(token.getMechanismList());

		Session session = token.openSession(Token.SessionType.SERIAL_SESSION,
		    Token.SessionReadWriteBehavior.RW_SESSION, null, null);

		// if we have to user PIN login user
		if (args.length == 2) {
			session.login(Session.UserType.USER, args[1].toCharArray());
		}

		GenericSecretKey premasterSecret = null;
		if (supportedMechanisms.contains(Mechanism
		    .get(PKCS11Constants.CKM_SSL3_PRE_MASTER_KEY_GEN))) {
			output_
			    .println("################################################################################");
			output_.println("Generating premaster secret");

			VersionParameters versionParameters = new VersionParameters((byte) 3, (byte) 0);

			Mechanism sslPremasterKeyGenerationMechanism = Mechanism
			    .get(PKCS11Constants.CKM_SSL3_PRE_MASTER_KEY_GEN);
			sslPremasterKeyGenerationMechanism.setParameters(versionParameters);

			GenericSecretKey premasterSecretTemplate = new GenericSecretKey();

			premasterSecret = (GenericSecretKey) session.generateKey(
			    sslPremasterKeyGenerationMechanism, premasterSecretTemplate);

			output_.println("the premaster secret is");
			output_.println(premasterSecret.toString());

			output_
			    .println("################################################################################");
		}

		GenericSecretKey masterSecret = null;
		SecureRandom randomSource = SecureRandom.getInstance("SHA1PRNG");
		if (supportedMechanisms.contains(Mechanism
		    .get(PKCS11Constants.CKM_SSL3_MASTER_KEY_DERIVE)) && (premasterSecret != null)) {
			output_
			    .println("################################################################################");
			output_.println("Deriving master secret");

			byte[] clientRandom = new byte[28];
			byte[] serverRandom = new byte[28];

			output_.print("generating client random... ");
			output_.flush();
			randomSource.nextBytes(clientRandom);
			output_.println("finished");
			output_.print("generating server random... ");
			output_.flush();
			randomSource.nextBytes(serverRandom);
			output_.println("finished");

			VersionParameters clientVersion = new VersionParameters();
			SSL3RandomDataParameters randomInfo = new SSL3RandomDataParameters(clientRandom,
			    serverRandom);
			SSL3MasterKeyDeriveParameters masterKeyDeriveParameters = new SSL3MasterKeyDeriveParameters(
			    randomInfo, clientVersion);

			Mechanism sslMasterKeyDerivationMechanism = Mechanism
			    .get(PKCS11Constants.CKM_SSL3_MASTER_KEY_DERIVE);
			sslMasterKeyDerivationMechanism.setParameters(masterKeyDeriveParameters);

			GenericSecretKey masterSecretTemplate = new GenericSecretKey();

			masterSecret = (GenericSecretKey) session.deriveKey(
			    sslMasterKeyDerivationMechanism, premasterSecret, masterSecretTemplate);

			output_.println("the client version is");
			output_.println(masterKeyDeriveParameters.getVersion().toString());
			output_.println("the master secret is");
			output_.println(masterSecret.toString());

			output_
			    .println("################################################################################");
		}

		if (supportedMechanisms.contains(Mechanism
		    .get(PKCS11Constants.CKM_SSL3_KEY_AND_MAC_DERIVE)) && (masterSecret != null)) {
			output_
			    .println("################################################################################");
			output_.println("Deriving key material");

			byte[] clientRandom = new byte[28];
			byte[] serverRandom = new byte[28];

			output_.print("generating client random... ");
			output_.flush();
			randomSource.nextBytes(clientRandom);
			output_.println("finished");
			output_.print("generating server random... ");
			output_.flush();
			randomSource.nextBytes(serverRandom);
			output_.println("finished");

			SSL3RandomDataParameters randomInfo = new SSL3RandomDataParameters(clientRandom,
			    serverRandom);

			byte[] clientIVBuffer = new byte[16];
			byte[] serverIVBuffer = new byte[16];
			SSL3KeyMaterialOutParameters returedKeyMaterial = new SSL3KeyMaterialOutParameters(
			    clientIVBuffer, serverIVBuffer);
			SSL3KeyMaterialParameters keyAndMACDeriveParameters = new SSL3KeyMaterialParameters(
			    80, 128, 128, false, randomInfo, returedKeyMaterial);

			Mechanism sslKeyAndMACDerivationMechanism = Mechanism
			    .get(PKCS11Constants.CKM_SSL3_KEY_AND_MAC_DERIVE);
			sslKeyAndMACDerivationMechanism.setParameters(keyAndMACDeriveParameters);

			session.deriveKey(sslKeyAndMACDerivationMechanism, masterSecret, null);

			output_.println("the key material is");
			output_.println(returedKeyMaterial.toString());

			output_
			    .println("################################################################################");
		}

		session.closeSession();
		pkcs11Module.finalize(null);
	}

	public static void printUsage() {
		output_.println("Usage: SSLMechanisms <PKCS#11 module> [<user-PIN>]");
		output_.println(" e.g.: SSLMechanisms cryptoki.dll");
		output_.println("The given DLL must be in the search path of the system.");
	}

}
