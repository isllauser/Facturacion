package com.islla.factelect.helper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Encriptador {
	public String encriptarPassword(String contraseniaP){
		String nombreAlgoritmo="SHA-1";
		byte[] buffer=contraseniaP.getBytes();
		byte[] digest=null;
		MessageDigest md;
		try{
			md=MessageDigest.getInstance(nombreAlgoritmo);
			md.reset();
			md.update(buffer);
			digest=md.digest();
   		}catch(NoSuchAlgorithmException e){
			System.out.println("Error: "+e.getMessage());
		}
		return toHexadecimal(digest);
	}
	
	public String toHexadecimal(byte[] digestP){
		String cadena="";
		for(byte i=0;i<digestP.length ;i++){
			byte temp=digestP[i];
			String s=Integer.toHexString(temp);
			while (s.length() < 2) {
                s = "0" + s;
            }
			s = s.substring(s.length() - 2);
            cadena += s;
		}
		return cadena;
		}
	
//	public static void main(String args[])
//	{
//		Encriptar enc = new Encriptar();
//		System.out.println(enc.encriptarPassword("test"));
//	
//	}
	
}
