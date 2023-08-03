package eToken.basic;


import iaik.pkcs.pkcs11.*;
import iaik.pkcs.pkcs11.objects.Object;
import iaik.pkcs.pkcs11.objects.X509AttributeCertificate;
import iaik.pkcs.pkcs11.objects.X509PublicKeyCertificate;

import java.io.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

/**
 * This demo program lists information about a library, the available slots,
 * the available tokens and the objects on them. It takes the name of the module
 * and the absolute path to the shared library of the IAIK PKCS#11 Wrapper
 * and prompts the user PIN. If the user PIN is not available,
 * the program will list only public objects but no private objects; i.e. as
 * defined in PKCS#11 for public read-only sessions.
 *
 * GetInfo <PKCS#11 module name> [<pin>] [<0...all, >0 limit>] [<initialization parameters>]
 *
 * 1) libName
 * 2) pin
 * 3) 0 .. all  -- 0 limit
 * 4) initialization parameters
 *
 * @author <a href="mailto:jce@iaik.tugraz.at"> Birgit Haas </a>
 * @version 0.1
 * @invariants
 */
public class GetInfo
{
    static PrintWriter output_;

    static BufferedReader input_;

    static
    {
        try
        {
            //output_ = new PrintWriter(new FileWriter("GetInfo_output.txt"), true);
            output_ = new PrintWriter ( System.out, true );
            input_ = new BufferedReader ( new InputStreamReader ( System.in ) );
        } catch ( Throwable thr )
        {
            thr.printStackTrace ();
            output_ = new PrintWriter ( System.out, true );
            input_ = new BufferedReader ( new InputStreamReader ( System.in ) );
        }
    }

    public static void main ( String[] args )
            throws IOException, TokenException
    {
        /*
        if ( ( args.length < 1 ) )
        {
            printUsage ();
            System.exit ( 1 );
        }
        */

        String libName;

        //libName = "eTpkcs11.dll";
        //libName = "eTpkcs11.so";
        libName = "libeTPkcs11.so";

        output_.println ( "################################################################################" );
        output_.println ( "load and initialize module: " + libName );
        output_.flush ();

        Module pkcs11Module = Module.getInstance ( libName );

        if ( 3 < args.length )
        {
            DefaultInitializeArgs arguments = new DefaultInitializeArgs ();
            byte[] stringBytes = args[ 3 ].getBytes ();
            byte[] reservedBytes = new byte[ stringBytes.length + 5 ];
            System.arraycopy ( stringBytes, 0, reservedBytes, 0, stringBytes.length );
            arguments.setReserved ( reservedBytes );
            pkcs11Module.initialize ( arguments );
        }
        else
        {
            pkcs11Module.initialize ( null );
        }

        Info info = pkcs11Module.getInfo ();
        output_.println ( info );
        output_
                .println ( "################################################################################" );

        output_
                .println ( "################################################################################" );
        output_.println ( "getting list of all slots" );
        Slot[] slots = pkcs11Module.getSlotList ( Module.SlotRequirement.ALL_SLOTS );

        for ( int i = 0; i < slots.length; i++ )
        {
            output_
                    .println ( "________________________________________________________________________________" );
            SlotInfo slotInfo = slots[ i ].getSlotInfo ();
            output_.print ( "Slot with ID: " );
            output_.println ( slots[ i ].getSlotID () );
            output_
                    .println ( "--------------------------------------------------------------------------------" );
            output_.println ( slotInfo );
            output_
                    .println ( "________________________________________________________________________________" );
        }
        output_
                .println ( "################################################################################" );

        output_
                .println ( "################################################################################" );
        output_.println ( "getting list of all tokens" );
        Slot[] slotsWithToken = pkcs11Module
                .getSlotList ( Module.SlotRequirement.TOKEN_PRESENT );
        Token[] tokens = new Token[ slotsWithToken.length ];

        for ( int i = 0; i < slotsWithToken.length; i++ )
        {
            output_
                    .println ( "________________________________________________________________________________" );
            tokens[ i ] = slotsWithToken[ i ].getToken ();
            TokenInfo tokenInfo = tokens[ i ].getTokenInfo ();
            output_.print ( "Token in slot with ID: " );
            output_.println ( tokens[ i ].getSlot ().getSlotID () );
            output_
                    .println ( "--------------------------------------------------------------------------------" );
            output_.println ( tokenInfo );

            output_.println ( "supported Mechanisms:" );
            Mechanism[] supportedMechanisms = tokens[ i ].getMechanismList ();
            for ( int j = 0; j < supportedMechanisms.length; j++ )
            {
                output_
                        .println ( "--------------------------------------------------------------------------------" );
                output_.println ( "Mechanism Name: " + supportedMechanisms[ j ].getName () );
                MechanismInfo mechanismInfo = tokens[ i ].getMechanismInfo ( supportedMechanisms[ j ] );
                output_.println ( mechanismInfo );
                output_
                        .println ( "--------------------------------------------------------------------------------" );
            }
            output_
                    .println ( "________________________________________________________________________________" );
        }
        output_
                .println ( "################################################################################" );

        output_
                .println ( "################################################################################" );
        output_.println ( "listing objects on tokens" );
        for ( int i = 0; i < tokens.length; i++ )
        {
            output_
                    .println ( "________________________________________________________________________________" );
            output_.println ( "listing objects for token: " );
            TokenInfo tokenInfo = tokens[ i ].getTokenInfo ();

            // tokenInfo - доступна без ПИН.
            output_.println ( tokenInfo );
            Session session = tokens[ i ].openSession ( Token.SessionType.SERIAL_SESSION,
                                                        Token.SessionReadWriteBehavior.RO_SESSION, null, null );

            if ( tokenInfo.isLoginRequired () )
            {
                if ( tokenInfo.isProtectedAuthenticationPath () )
                {
                    session.login ( Session.UserType.USER, null ); // the token prompts the PIN by other means; e.g. PIN-pad
                }
                else
                {
                    output_.print ( "Enter user-PIN or press [return] to list just public objects: " );
                    output_.flush ();
                    String userPINString;
                    if ( 1 < args.length )
                    {
                        userPINString = args[ 1 ];
                        output_.println ( args[ 1 ] );
                    }
                    else userPINString = input_.readLine ();

                    output_.println ();
                    output_.print ( "listing all" + ( ( userPINString.length () > 0 ) ? "" : " public" )
                                            + " objects on token" );
                    if ( userPINString.length () > 0 )
                    {
                        // login user
                        session.login ( Session.UserType.USER, userPINString.toCharArray () );
                    }
                }
            }
            SessionInfo sessionInfo = session.getSessionInfo ();
            output_.println ( " using session:" );
            output_.println ( sessionInfo );

            int limit = 0, counter = 0;
            if ( 2 < args.length ) limit = Integer.parseInt ( args[ 2 ] );

            session.findObjectsInit ( null );
            Object[] objects = session.findObjects ( 1 );
            if ( 0 < objects.length ) counter++;

            CertificateFactory x509CertificateFactory = null;
            while ( objects.length > 0 && ( 0 == limit || counter < limit ) )
            {
                Object object = objects[ 0 ];
                output_
                        .println ( "--------------------------------------------------------------------------------" );
                output_.println ( "Object with handle: " + objects[ 0 ].getObjectHandle () );
                output_.println ( object );
                if ( object instanceof X509PublicKeyCertificate )
                {
                    try
                    {
                        byte[] encodedCertificate = ( ( X509PublicKeyCertificate ) object ).getValue ()
                                .getByteArrayValue ();
                        if ( x509CertificateFactory == null )
                        {
                            x509CertificateFactory = CertificateFactory.getInstance ( "X.509" );
                        }
                        Certificate certificate = x509CertificateFactory
                                .generateCertificate ( new ByteArrayInputStream ( encodedCertificate ) );
                        output_
                                .println ( "................................................................................" );
                        output_.println ( "The decoded X509PublicKeyCertificate is:" );
                        output_.println ( certificate.toString () );
                        output_
                                .println ( "................................................................................" );
                    } catch ( Exception ex )
                    {
                        output_
                                .println ( "Could not decode this X509PublicKeyCertificate. Exception is: "
                                                   + ex.toString () );
                    }
                }
                else if ( object instanceof X509AttributeCertificate )
                {
                    try
                    {
                        byte[] encodedCertificate = ( ( X509AttributeCertificate ) object ).getValue ()
                                .getByteArrayValue ();
                        if ( x509CertificateFactory == null )
                        {
                            x509CertificateFactory = CertificateFactory.getInstance ( "X.509" );
                        }
                        Certificate certificate = x509CertificateFactory
                                .generateCertificate ( new ByteArrayInputStream ( encodedCertificate ) );
                        output_
                                .println ( "................................................................................" );
                        output_.println ( "The decoded X509AttributeCertificate is:" );
                        output_.println ( certificate.toString () );
                        output_
                                .println ( "................................................................................" );
                    } catch ( Exception ex )
                    {
                        output_
                                .println ( "Could not decode this X509AttributeCertificate. Exception is: "
                                                   + ex.toString () );
                    }
                }
                // test the (deep) cloning feature
                // Object clonedObject = (Object) object.clone();
                output_
                        .println ( "--------------------------------------------------------------------------------" );
                objects = session.findObjects ( 1 );
                counter++;
            }
            session.findObjectsFinal ();
            session.closeSession ();

            output_
                    .println ( "________________________________________________________________________________" );
            if ( 2 < args.length && !"0".equals ( args[ 2 ] ) ) output_
                    .println ( "output limited to list a maximum of " + args[ 2 ]
                                       + " objects. There might be more!" );
            else output_.println ( "found " + counter + " objects on this token" );
            output_
                    .println ( "________________________________________________________________________________" );
        }

        output_
                .println ( "################################################################################" );
        pkcs11Module.finalize ( null );
    }

    protected static void printUsage ()
    {
        output_
                .println ( "GetInfo <PKCS#11 module name> [<pin>] [<0...all, >0 limit>] [<initialization parameters>]" );
        output_.println ( "e.g.: GetInfo aetpkss1.dll" );
        output_
                .println ( "      GetInfo aetpkss1.dll C:\\provider\\lib\\win32\\pkcs11wrapper.dll" );
    }

}
