package eToken;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 21.09.12 11:56
 */

import iaik.pkcs.pkcs11.Module;
import iaik.pkcs.pkcs11.Info;

public class ModuleInfo
{
    public static void main ( String[] args )
    {
        String libName;

        //libName = "eTpkcs11.dll";
        //libName = "eTpkcs11.so";
        libName = "libeTPkcs11.so";
        System.out.println ( "--- libName = " + libName );

            try
            {
                Module pkcs11Module = Module.getInstance ( libName );
                pkcs11Module.initialize ( null );
                Info info = pkcs11Module.getInfo ();
                System.out.println ( info );
                pkcs11Module.finalize ( null );
            } catch ( Throwable ex )
            {
                ex.printStackTrace ();
            }
    }

    protected static void printUsage ()
    {
        System.out.println ( "ModuleInfo <PKCS#11 module name>" );
        System.out.println ( "e.g.: ModuleInfo cryptoki.dll" );
    }
}