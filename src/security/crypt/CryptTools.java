package security.crypt;


import java.security.Provider;
import java.security.Security;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 28.09.12 13:06
 */
public class CryptTools
{
// This method returns all available services types

    /**
     * This method returns all available services types.
     * Список доступных сервисов.
     * В виде: service-type.service-implementation
     *
     *
     *  ---- SUN ----
     Alg.Alias.Signature.SHA1/DSA
     Alg.Alias.Signature.1.2.840.10040.4.3
     Alg.Alias.Signature.DSS
     SecureRandom.SHA1PRNG ImplementedIn
     KeyStore.JKS
     Alg.Alias.MessageDigest.SHA-1
     MessageDigest.SHA
     KeyStore.CaseExactJKS
     CertStore.com.sun.security.IndexedCollection ImplementedIn
     Alg.Alias.Signature.DSA
     ...

     * @return
     */
    public static String[] getServiceTypes ()
    {
        Provider provider;

        Set result = new HashSet();

        // All all providers
        Provider[] providers = Security.getProviders ();
        for ( int i = 0; i < providers.length; i++ )
        {
            // Get services provided by each provider
            provider = providers[ i ];
            Set keys = provider.keySet ();
            System.out.println ( " ---- " + provider.getName() + " ----" );
            for ( Iterator it = keys.iterator (); it.hasNext (); )
            {
                String key = ( String ) it.next ();
                System.out.println ( key );
                key = key.split ( " " )[ 0 ];

                if ( key.startsWith ( "Alg.Alias." ) )
                {
                    // Strip the alias
                    key = key.substring ( 10 );
                }
                int ix = key.indexOf ( '.' );
                result.add ( key.substring ( 0, ix ) );
            }
        }
        return ( String[] ) result.toArray ( new String[ result.size () ] );
    }

    // This method returns the available implementations for a service type
    /*
  ==  Наиименования сервисов, для которых можно получить спсиок параметров:
AlgorithmParameterGenerator
AlgorithmParameters
CertPathBuilder
CertPathValidator
CertStore
CertificateFactory
Cipher
GssApiMechanism
KeyAgreement
KeyFactory
KeyGenerator
KeyManagerFactory
KeyPairGenerator
KeyStore
Mac
MessageDigest
SSLContext
SecretKeyFactory
SecureRandom
Signature
TrustManagerFactory

 == Параметры сервиса 'Chipher':
OID.1.2.840.113549.1.12.1.6
Blowfish
DESedeWrap
Rijndael
DESede
ARCFOUR
PBEWithSHA1AndDESede
PBEWithSHA1AndRC2_40
RC2
RC4
RSA
AESWrap
PBEWithMD5AndTripleDES
OID.1.2.840.113549.1.12.1.3
DES
AES
1.2.840.113549.1.12.1.6
OID.1.2.840.113549.1.5.3
1.2.840.113549.1.12.1.3
1.2.840.113549.1.5.3
TripleDES
PBEWithMD5AndDES

     */
    public static String[] getCryptoImpls ( String serviceType )
    {
        Set result = new HashSet ();

        // All all providers
        Provider[] providers = Security.getProviders ();
        for ( int i = 0; i < providers.length; i++ )
        {
            // Get services provided by each provider
            Set keys = providers[ i ].keySet ();
            for ( Iterator it = keys.iterator (); it.hasNext (); )
            {
                String key = ( String ) it.next ();
                key = key.split ( " " )[ 0 ];

                if ( key.startsWith ( serviceType + "." ) )
                {
                    result.add ( key.substring ( serviceType.length () + 1 ) );
                }
                else if ( key.startsWith ( "Alg.Alias." + serviceType + "." ) )
                {
                    // This is an alias
                    result.add ( key.substring ( serviceType.length () + 11 ) );
                }
            }
        }
        return ( String[] ) result.toArray ( new String[ result.size () ] );
    }

    public static void main ( String[] args )
    {
        getServiceTypes();

        /*
        String[] names = getCryptoImpls("Cipher");
        for ( String name : names )
            System.out.println ( name );
            //*/
    }

}
