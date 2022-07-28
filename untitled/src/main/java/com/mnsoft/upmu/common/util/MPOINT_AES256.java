package com.mnsoft.upmu.common.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

public class MPOINT_AES256 {
	
	static {
		fixKeyLength(); 
	}
    
    public static String encrypt(String strToEncrypt, String myKey)
    {
        try
        {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");   
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(myKey.getBytes("UTF-8"), "AES"));
            return Base64.encodeBase64String(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        }
        catch (Exception e)
        {
        	StringUtil.printStackTrace(e);
        }
        return null;
    }
    
    public static String decrypt(String strToDecrypt,String myKey)
    {
        try
        {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(myKey.getBytes("UTF-8"), "AES"));
            return new String(cipher.doFinal(Base64.decodeBase64(strToDecrypt)));
        }
        catch (Exception e)
        {
        	StringUtil.printStackTrace(e);
        }
        return null;
    }
    
    @SuppressWarnings("unchecked")
	public static void fixKeyLength() {
	    String errorString = "Failed manually overriding key-length permissions.";
	    int newMaxKeyLength;
	    try {
	        if ((newMaxKeyLength = Cipher.getMaxAllowedKeyLength("AES")) < 256) {
	            Class<?> c = Class.forName("javax.crypto.CryptoAllPermissionCollection");
	            Constructor<?> con = c.getDeclaredConstructor();
	            con.setAccessible(true);
	            Object allPermissionCollection = con.newInstance();
	            Field f = c.getDeclaredField("all_allowed");
	            f.setAccessible(true);
	            f.setBoolean(allPermissionCollection, true);

	            c = Class.forName("javax.crypto.CryptoPermissions");
	            con = c.getDeclaredConstructor();
	            con.setAccessible(true);
	            Object allPermissions = con.newInstance();
	            f = c.getDeclaredField("perms");
	            f.setAccessible(true);
	            ((Map<String, Object>) f.get(allPermissions)).put("*", allPermissionCollection);

	            c = Class.forName("javax.crypto.JceSecurityManager");
	            f = c.getDeclaredField("defaultPolicy");
	            f.setAccessible(true);
	            Field mf = Field.class.getDeclaredField("modifiers");
	            mf.setAccessible(true);
	            mf.setInt(f, f.getModifiers() & ~Modifier.FINAL);
	            f.set(null, allPermissions);

	            newMaxKeyLength = Cipher.getMaxAllowedKeyLength("AES");
	        }
	    } catch (Exception e) {
	        throw new RuntimeException(errorString, e);
	    }
	    if (newMaxKeyLength < 256)
	        throw new RuntimeException(errorString); // hack failed
	}
    
    public static void main(String args[]) throws UnsupportedEncodingException
    {
    	/*******************************************************************
    	*  skmn 제휴사에서 사용되고 있는 암복호화 샘플 결과 
        * - 테스트 평문 : test_sample_한글
        * - AES256 암호화 : GF5GkpRNgQtRMxj%2FEx7rVE80vIEfXBwWqiK3O5s7viQ%3D
        * - AES256 복호화 : test_sample_한글
        * 
        * -	암호화 전 :01011112221
		* -	암호화 후 :6vjSG6msREOiuhsHk0QCtQ==
		* -	암호화 한 값을 UTF-8로 인코딩 : 6vjSG6msREOiuhsHk0QCtQ%3D%3D
        *******************************************************************/
        String key 					= "D63_2018100509_P";
		String plainText 			= "P0211";
		//String plainText 			= "01011112221";
		String encryptedText 		= MPOINT_AES256.encrypt(plainText, key);
		String encryptedTextUenc 	= URLEncoder.encode(encryptedText,"UTF-8");
		String decryptedText 		= MPOINT_AES256.decrypt(encryptedText,key);
		String decryptedTextUdec 	= MPOINT_AES256.decrypt(URLDecoder.decode(encryptedTextUenc,"UTF-8"),key);
         System.out.println("+------------------------------------------+");
		 System.out.println("|                AES256                    |");
		 System.out.println("+------------------------------------------+");
		 System.out.println("| plainText      [" + plainText + "]");
		 System.out.println("| encryptedText  [" + encryptedText + "] URLEncoder [" + encryptedTextUenc + "]");
		 System.out.println("| decryptedText  [" + decryptedText + "] URLDecoder [" + decryptedTextUdec + "]");
		 System.out.println("+------------------------------------------+");
        
    }
}
