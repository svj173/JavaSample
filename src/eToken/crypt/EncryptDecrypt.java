package eToken.crypt;

import eToken.EtokenManager;
import iaik.pkcs.pkcs11.Mechanism;
import iaik.pkcs.pkcs11.Module;
import iaik.pkcs.pkcs11.Session;
import iaik.pkcs.pkcs11.Slot;
import iaik.pkcs.pkcs11.Token;
import iaik.pkcs.pkcs11.TokenException;
import iaik.pkcs.pkcs11.objects.DES3SecretKey;
import iaik.pkcs.pkcs11.objects.KeyPair;
import iaik.pkcs.pkcs11.objects.RSAPublicKey;
import iaik.pkcs.pkcs11.parameters.InitializationVectorParameters;
import iaik.pkcs.pkcs11.wrapper.PKCS11Constants;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * This demo program uses a PKCS#11 module to encrypt a given file and test if the data can be decrypted.
 *
 * Генерит DES ключ (с сохранением в брелке?)
 * Шифрует им данные и расшифровывает.
 *
 * @author <a href="mailto:Karl.Scheibelhofer@iaik.at"> Karl Scheibelhofer </a>
 * @version 0.1
 */
public class EncryptDecrypt  extends EtokenManager
{
    public static void main ( String[] args )
    {
        EncryptDecrypt      manager;
        Token               token;
        Module              module;
        String              pin, libName, fileName;
        Session             session;
        int                 slot;
        byte[]              pubKeyId;
        KeyPair             keyPair;
        RSAPublicKey        publicKey;

        module      = null;
        session     = null;

        libName     = "libeTPkcs11.so";
        slot        = 0;   // USB-0
        fileName    = "/home/svj/projects/SVJ/JavaSample/test/etoken/pub_key_509.xpk";

        //pin         = "12345678";
        pin         = "Hbrfitn11";

        //label       = "EltexEMS";

        manager     = new EncryptDecrypt();

        try
        {
            module      = manager.getModule ( libName );
            token       = manager.getToken ( module, slot );
            //System.out.println ( "Token for slot #" + slot + " = " + token.getTokenInfo() );

            session     = manager.createSession ( token, pin );

            Mechanism encryptionMechanism = Mechanism.get ( PKCS11Constants.CKF_DUAL_CRYPTO_OPERATIONS );

            // НЕ реализовано !!!

            /*
            pubKeyId    = manager.createId();

            // Брелок генерит пару ключей, сохраняя их у себя как два обьекта.
            keyPair     = manager.generateKeyPair ( token, session, pubKeyId );

            manager.savePublicKeyToFile ( keyPair, fileName );

            publicKey   = manager.findPublicKey ( session, pubKeyId );
            System.out.println ( "pubKeyId = " + pubKeyId );
            System.out.println ( "find publicKey = " + publicKey );
            */
        } catch ( Exception e )         {
            e.printStackTrace();
        } finally        {
            manager.closeModule ( session, module );
        }
    }

	static PrintWriter output_;

	static {
		try {
			//output_ = new PrintWriter(new FileWriter("Encrypt_output.txt"), true);
			output_ = new PrintWriter(System.out, true);
		} catch (Throwable thr) {
			thr.printStackTrace();
			output_ = new PrintWriter(System.out, true);
		}
	}

	public static void main2 (String[] args)
	    throws TokenException, IOException
	{
		if (args.length != 3) {
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

		Session session = token.openSession(Token.SessionType.SERIAL_SESSION,
		    Token.SessionReadWriteBehavior.RO_SESSION, null, null);

		// login user
		session.login(Session.UserType.USER, args[1].toCharArray());

		output_
		    .println("################################################################################");
		output_.println("generate secret encryption/decryption key");

		Mechanism keyMechanism = Mechanism.get(PKCS11Constants.CKM_DES3_KEY_GEN);
		DES3SecretKey secretEncryptionKeyTemplate = new DES3SecretKey();
		secretEncryptionKeyTemplate.getEncrypt().setBooleanValue(Boolean.TRUE);
		secretEncryptionKeyTemplate.getDecrypt().setBooleanValue(Boolean.TRUE);

		DES3SecretKey encryptionKey = (DES3SecretKey) session.generateKey(keyMechanism,
		    secretEncryptionKeyTemplate);

		output_
		    .println("################################################################################");

		output_
		    .println("################################################################################");
		output_.println("encrypting data from file: " + args[2]);

		InputStream dataInputStream = new FileInputStream(args[2]);

		byte[] dataBuffer = new byte[1024];
		int bytesRead;
		ByteArrayOutputStream streamBuffer = new ByteArrayOutputStream();

		// feed in all data from the input stream
		while ((bytesRead = dataInputStream.read(dataBuffer)) >= 0) {
			streamBuffer.write(dataBuffer, 0, bytesRead);
		}
		Arrays.fill(dataBuffer, (byte) 0); // ensure that no data is left in the memory
		streamBuffer.flush();
		streamBuffer.close();
		byte[] rawData = streamBuffer.toByteArray();

		//be sure that your token can process the specified mechanism
		Mechanism encryptionMechanism = Mechanism.get(PKCS11Constants.CKM_DES3_CBC_PAD);
		byte[] encryptInitializationVector = { 0, 0, 0, 0, 0, 0, 0, 0 };
		InitializationVectorParameters encryptInitializationVectorParameters = new InitializationVectorParameters(
		    encryptInitializationVector);
		encryptionMechanism.setParameters(encryptInitializationVectorParameters);

		// initialize for encryption  -- CKM_DES3_CBC_PAD / DES key
		session.encryptInit(encryptionMechanism, encryptionKey);

		byte[] encryptedData = session.encrypt(rawData);

		output_
		    .println("################################################################################");

		output_
		    .println("################################################################################");
		output_.println("trying to decrypt");

		//Cipher des3Cipher = Cipher.getInstance("DES3/CBC/PKCS5Padding");

		Mechanism decryptionMechanism = Mechanism.get(PKCS11Constants.CKM_DES3_CBC_PAD);
		byte[] decryptInitializationVector = { 0, 0, 0, 0, 0, 0, 0, 0 };
		InitializationVectorParameters decryptInitializationVectorParameters = new InitializationVectorParameters(
		    decryptInitializationVector);
		decryptionMechanism.setParameters(decryptInitializationVectorParameters);

		// initialize for decryption
		session.decryptInit(decryptionMechanism, encryptionKey);

		byte[] decryptedData = session.decrypt(encryptedData);

		// compare initial data and decrypted data
		boolean equal = false;
		if (rawData.length != decryptedData.length) {
			equal = false;
		} else {
			equal = true;
			for (int i = 0; i < rawData.length; i++) {
				if (rawData[i] != decryptedData[i]) {
					equal = false;
					break;
				}
			}
		}

		output_.println((equal) ? "successful" : "ERROR");

		output_
		    .println("################################################################################");

		session.closeSession();
		pkcs11Module.finalize(null);
	}

	public static void printUsage() {
		output_
		    .println("Usage: EncryptDecrypt <PKCS#11 module> <user-PIN> <file to be encrypted>");
		output_.println(" e.g.: EncryptDecrypt pk2priv.dll password data.dat");
		output_.println("The given DLL must be in the search path of the system.");
	}

}
