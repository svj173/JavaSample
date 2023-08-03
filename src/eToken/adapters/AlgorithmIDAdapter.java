package eToken.adapters;

import java.security.NoSuchAlgorithmException;
import java.security.Signature;

//import iaik.asn1.structures.AlgorithmID;   -- нет такого класса

/**
 * This class is an adapter to enables an application to use a different
 * implementation than the standard implementation with the IAIK-JCE.
 *
 * @author <a href="mailto:Karl.Scheibelhofer@iaik.at"> Karl Scheibelhofer </a>
 * @version 0.1
 * @invariants
 */
public class AlgorithmIDAdapter //extends AlgorithmID
{

	/**
	 * The delegate object to use, if no concrete implementation is set for a
	 * certain engine class.
	 */
	//protected AlgorithmID delegate_;

	/**
	 * This is the signature engine to use for this object.
	 */
	protected Signature signatureEngine_;

	/**
	 * Creates a new AlgorithmIDAdapter that uses the given delegate object
	 * to get the .
	 *
	 * @param delegate The object to get other implementations from,
	 *                 implementations not provided by this object.
	 * @preconditions
	 * @postconditions
	 */
    /*
	public AlgorithmIDAdapter(AlgorithmID delegate) {
		super(delegate.getAlgorithm());
		delegate_ = delegate;
	}
	*/

	/**
	 * Set the implementation to use as signature instance.
	 *
	 * @param signatureEngine The implementation of the signature class to return
	 *                        upon a call to getSignatureInstance(). If null, the
	 *                        implementation is unset.
	 * @preconditions
	 * @postconditions
	 */
	public void setSignatureInstance(Signature signatureEngine) {
		signatureEngine_ = signatureEngine;
	}

	/**
	 * If a concrete signature implementation was set using
	 * setSignatureInstance(Signature), this method returns this. Otherwise, it
	 * delegates the call to the delegate of this object.
	 *
	 * @return The signature engine to use for this algorthim.
	 * @exception NoSuchAlgorithmException If there is no signature
	 *                                     implementation for this algorithm.
	 * @preconditions
	 * @postconditions
	 */
	public Signature getSignatureInstance()    throws NoSuchAlgorithmException
	{
		//todo return (signatureEngine_ != null) ? signatureEngine_ : super.getSignatureInstance();
        return signatureEngine_;
	}

	/**
	 * If a concrete signature implementation was set using
	 * setSignatureInstance(Signature) and the provider name is null, this method
	 * returns this set signature implementation; otherwise, it delegates the call
	 * to the delegate of this object.
	 *
	 * @return The signature engine to use for this algorthim.
	 * @exception NoSuchAlgorithmException If there is no signature
	 *                                     implementation for this algorithm.
	 * @preconditions
	 * @postconditions
	 */
	public Signature getSignatureInstance(String providerName)
	    throws NoSuchAlgorithmException
	{
		//todo return (providerName == null) ? getSignatureInstance() : super.getSignatureInstance(providerName);
        return getSignatureInstance();
	}

}
