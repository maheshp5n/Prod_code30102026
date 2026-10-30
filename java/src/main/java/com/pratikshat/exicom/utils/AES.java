package com.pratikshat.exicom.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.AlgorithmParameters;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.KeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class AES {
	 public static String encryptAES(String key, String initVector, String value) {

	        try {

	            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes());// initVector.getBytes("UTF-8"));

	            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "AES");

	            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");

	            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

	            byte[] encrypted = cipher.doFinal(value.getBytes());

	            return new String(encrypted);// encrypted.toString();

	        } catch (Exception ex) {

	            ex.printStackTrace();

	        }

	        return null;

	    }

	    public static String decryptAES(String key, String initVector, String encrypted) {

	        try {

	            IvParameterSpec iv = new IvParameterSpec((initVector.getBytes()));

	                //    initVector.getBytes("UTF-8"));

	            SecretKeySpec skeySpec = new SecretKeySpec((key.getBytes()), "AES");

	            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
	            //Cipher cipher=Cipher.getInstance("AES/ECB/PKCS5");

	            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

	            byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));

	            return new String(original);

	        } catch (Exception ex) {

	            ex.printStackTrace();

	        }

	        return null;

	    }
 
}