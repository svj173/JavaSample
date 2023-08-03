package eToken.adapters;

import iaik.pkcs.pkcs11.objects.Key;
import iaik.pkcs.pkcs11.objects.X509PublicKeyCertificate;

/**
 * This class encapsulates a key and an optional certificate.
 *
 * @author <a href="mailto:Karl.Scheibelhofer@iaik.at"> Karl Scheibelhofer </a>
 * @version 0.1
 * @invariants
 */
public class KeyAndCertificate {

	/**
	 * The key.
	 */
	protected Key key_;

	/**
	 * This optional certificate.
	 */
	protected X509PublicKeyCertificate certificate_;

	/**
	 * Creates a new object that holds the given key and certificate.
	 *
	 * @param key The key.
	 * @param certificate The certificate.
	 * @preconditions
	 * @postconditions
	 */
	public KeyAndCertificate(Key key, X509PublicKeyCertificate certificate) {
		key_ = key;
		certificate_ = certificate;
	}

	/**
	 * Returns the certificate.
	 *
	 * @return The certificate.
	 * @preconditions
	 * @postconditions
	 */
	public X509PublicKeyCertificate getCertificate() {
		return certificate_;
	}

	/**
	 * Returns the key.
	 *
	 * @return The key.
	 * @preconditions
	 * @postconditions
	 */
	public Key getKey() {
		return key_;
	}

	/**
	 * Sets the certificate.
	 *
	 * @param certificate The certificate.
	 * @preconditions
	 * @postconditions
	 */
	public void setCertificate(X509PublicKeyCertificate certificate) {
		certificate_ = certificate;
	}

	/**
	 * Sets the key.
	 *
	 * @param key The key.
	 * @preconditions
	 * @postconditions
	 */
	public void setKey(Key key) {
		key_ = key;
	}

}
