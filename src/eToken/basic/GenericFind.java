package eToken.basic;

import iaik.pkcs.pkcs11.Module;
import iaik.pkcs.pkcs11.Session;
import iaik.pkcs.pkcs11.Slot;
import iaik.pkcs.pkcs11.Token;
import iaik.pkcs.pkcs11.objects.Attribute;
import iaik.pkcs.pkcs11.objects.BooleanAttribute;
import iaik.pkcs.pkcs11.objects.GenericTemplate;
import iaik.pkcs.pkcs11.objects.Object;
import iaik.pkcs.pkcs11.objects.PrivateKey;
import iaik.pkcs.pkcs11.objects.X509PublicKeyCertificate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * This class demonstrates how to use the GenericSearchTemplate class.
 *
 * @author <a href="mailto:Karl.Scheibelhofer@iaik.at"> Karl Scheibelhofer </a>
 * @version 0.1
 * @invariants
 */
public class GenericFind {

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
	    throws Exception
	{
		if (2 > args.length) {
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

		int slotNr = 0;
		if (1 < args.length) slotNr = Integer.parseInt(args[1]);
		Slot selectedSlot = slots[slotNr];
		Token token = selectedSlot.getToken();

		Session session = token.openSession(Token.SessionType.SERIAL_SESSION,
		    Token.SessionReadWriteBehavior.RO_SESSION, null, null);

		// if we have the user PIN, we login the session, this enables us to find private objects too
		if (2 < args.length) {
			session.login(Session.UserType.USER, args[2].toCharArray());
		}

		// limit output if required
		int limit = 0, counter = 1;
		if (3 < args.length) limit = Integer.parseInt(args[3]);

		output_
		    .println("################################################################################");
		output_.println("Find all signature keys.");
		GenericTemplate signatureKeyTemplate = new GenericTemplate();
		BooleanAttribute signAttribute = new BooleanAttribute(Attribute.SIGN);
		signAttribute.setBooleanValue(Boolean.TRUE);
		signatureKeyTemplate.addAttribute(signAttribute);

		// this find operation will find all objects that posess a CKA_SIGN attribute with value true
		session.findObjectsInit(signatureKeyTemplate);

		Object[] foundSignatureKeyObjects = session.findObjects(1); // find first

		List signatureKeys = null;
		if (foundSignatureKeyObjects.length > 0) {
			signatureKeys = new Vector();
			output_
			    .println("________________________________________________________________________________");
			output_.println(foundSignatureKeyObjects[0]);
			signatureKeys.add(foundSignatureKeyObjects[0]);
			while ((foundSignatureKeyObjects = session.findObjects(1)).length > 0
			    && (0 == limit || counter < limit)) {
				output_
				    .println("________________________________________________________________________________");
				output_.println(foundSignatureKeyObjects[0]);
				signatureKeys.add(foundSignatureKeyObjects[0]);
				counter++;
			}
			output_
			    .println("________________________________________________________________________________");
		} else {
			output_.println("There is no object with a CKA_SIGN attribute set to true.");
			output_.flush();
			throw new Exception("There is no object with a CKA_SIGN attribute set to true.");
		}
		session.findObjectsFinal();

		output_
		    .println("################################################################################");

		output_
		    .println("################################################################################");
		output_.println("Find corresponding certificates for private signature keys.");

		List privateSignatureKeys = new Vector();

		// sort out all signature keys that are private keys
		Iterator signatureKeysIterator = signatureKeys.iterator();
		while (signatureKeysIterator.hasNext()) {
			Object signatureKey = (Object) signatureKeysIterator.next();
			if (signatureKey instanceof PrivateKey) {
				privateSignatureKeys.add(signatureKey);
			}
		}

		// for each private signature key try to find a public key certificate with the same ID
		Iterator privateSignatureKeysIterator = privateSignatureKeys.iterator();
		Hashtable privateKeyToCertificateTable = new Hashtable(privateSignatureKeys.size());
		while (privateSignatureKeysIterator.hasNext()) {
			PrivateKey privateSignatureKey = (PrivateKey) privateSignatureKeysIterator.next();
			byte[] keyID = privateSignatureKey.getId().getByteArrayValue();
			// this is the implementation that uses a concrete object class (X509PublicKeyCertificate) for searching
			X509PublicKeyCertificate certificateSearchTemplate = new X509PublicKeyCertificate();
			certificateSearchTemplate.getId().setByteArrayValue(keyID);
			/*
			 // this is the implementation that uses GenericSearchTemplate class for searching, the same effect as above
			 GenericTemplate certificateSearchTemplate = new GenericTemplate();
			 LongAttribute objectClassAttribute = new LongAttribute(PKCS11Constants.CKA_CLASS);
			 objectClassAttribute.setLongValue(new Long(PKCS11Constants.CKO_CERTIFICATE));
			 certificateSearchTemplate.addAttribute(objectClassAttribute);
			 LongAttribute certificateTypeAttribute = new LongAttribute(PKCS11Constants.CKA_CERTIFICATE_TYPE);
			 certificateTypeAttribute.setLongValue(new Long(PKCS11Constants.CKC_X_509));
			 certificateSearchTemplate.addAttribute(certificateTypeAttribute);
			 ByteArrayAttribute idAttribute = new ByteArrayAttribute(PKCS11Constants.CKA_ID);
			 idAttribute.setByteArrayValue(keyID);
			 certificateSearchTemplate.addAttribute(idAttribute);
			 */

			session.findObjectsInit(certificateSearchTemplate);

			Object[] foundCertificateObjects;
			if ((foundCertificateObjects = session.findObjects(1)).length > 0) {
				privateKeyToCertificateTable.put(privateSignatureKey, foundCertificateObjects[0]);
				output_
				    .println("________________________________________________________________________________");
				output_.println("The certificate for this private signature key");
				output_.println(privateSignatureKey);
				output_
				    .println("--------------------------------------------------------------------------------");
				output_.println("is");
				output_.println(foundCertificateObjects[0]);
				output_
				    .println("________________________________________________________________________________");
			} else {
				output_
				    .println("________________________________________________________________________________");
				output_.println("There is no certificate for this private signature key");
				output_.println(privateSignatureKey);
				output_
				    .println("________________________________________________________________________________");
			}

			session.findObjectsFinal();
		}

		output_
		    .println("################################################################################");
		if (3 < args.length && !"0".equals(args[3])) output_
		    .println("output limited to list a maximum of " + args[3]
		        + " objects. There might be more!");
		else output_.println("found " + counter + " objects on this token");

		output_
		    .println("################################################################################");

		session.closeSession();
		pkcs11Module.finalize(null);
	}

	public static void printUsage() {
		output_
		    .println("Usage: GenericFind <PKCS#11 module> [<slot>] [<userPIN>] [<0...all, >0 limit>]");
		output_.println(" e.g.: GenericFind pk2priv.dll password");
		output_.println("The given DLL must be in the search path of the system.");
	}

}
