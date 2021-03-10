package com.approval.demo.utils;

import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;


@Component
public class CryptoUtil {
	
	public final static String TRIPLEDES = "TripleDES";
	public final static String DES = "DES";
	public final static String MD5 = "MD5";
	public final static String SHA256 = "SHA-256";
	public final static String aesKey = "com.minwise.aKey";
	public final static String tdesKey = "404142434445464748494A4B4C4D4E4F";
	public final static String commonKey = "com.approval.demo.common.security.key";
	public final static String activeXKey = "f4150d4a1ac5708c29e437749045a39a";
	
	/**    
	 * hex to byte[] : 16진수 문자열을 바이트 배열로 변환한다.
	 * 
	 * @param hex
	 *            hex string
	 * @return
	 */
	public static byte[] hexToByteArray(String hex) {
		if (hex == null || hex.length() == 0) {
			return null;
		}

		byte[] ba = new byte[hex.length() / 2];
		for (int i = 0; i < ba.length; i++) {
			ba[i] = (byte) Integer
					.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
		}
		return ba;
	}

	/**
	 * byte[] to hex : unsigned byte(바이트) 배열을 16진수 문자열로 바꾼다.
	 * 
	 * @param ba
	 *            byte[]
	 * @return
	 */
	public static String byteArrayToHex(byte[] ba) {
		if (ba == null || ba.length == 0) {
			return null;
		}

		StringBuffer sb = new StringBuffer(ba.length * 2);
		String hexNumber;
		for (int x = 0; x < ba.length; x++) {
			hexNumber = "0" + Integer.toHexString(0xff & ba[x]);

			sb.append(hexNumber.substring(hexNumber.length() - 2));
		}
		return sb.toString();
	}

	/**
	 * AES 방식의 암호화
	 * 
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public static String AESencrypt(String message) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(aesKey.getBytes(), "AES");

		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

		byte[] encrypted = cipher.doFinal(message.getBytes());
		return byteArrayToHex(encrypted);
	}

	/**
	 * AES 방식의 복호화
	 * 
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public static String AESdecrypt(String encrypted) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(aesKey.getBytes(), "AES");

		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		byte[] original = cipher.doFinal(hexToByteArray(encrypted));
		String originalString = new String(original);
		return originalString;
	}
	
	/**
	 * AES 방식의 암호화
	 * 
	 * @param message, key
	 * @return
	 * @throws Exception
	 */
	public static String AESencrypt(String message, String key) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(hexToByteArray(key), "AES");

		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

		byte[] encrypted = cipher.doFinal(message.getBytes());
		return byteArrayToHex(encrypted);
	}

	/**
	 * AES 방식의 복호화
	 * 
	 * @param message, key
	 * @return
	 * @throws Exception
	 */
	public static String AESdecrypt(String encrypted, String key) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(hexToByteArray(key), "AES");

		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		byte[] original = cipher.doFinal(hexToByteArray(encrypted));
		String originalString = new String(original);
		return originalString;
	}
	
	   /**
     * 지정된 비밀키를 가지고 오는 메서드
     * @param keyValue : 암호화키값
     * @return
     * @throws Exception
     */
    public static Key getTripleKey(String keyValue) throws Exception {
        DESedeKeySpec desKeySpec = new DESedeKeySpec(keyValue.getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(TRIPLEDES);
        Key key = keyFactory.generateSecret(desKeySpec);
        return key;
    }
    
    /**
     * 지정된 비밀키를 가지고 오는 메서드
     * @param keyValue : 암호화키값
     * @return
     * @throws Exception
     */
    public static Key getDesKey(String keyValue) throws Exception {
    	DESKeySpec desKeySpec = new DESKeySpec(keyValue.getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        Key key = keyFactory.generateSecret(desKeySpec);
        return key;
    }
    
    /**
     * 문자열 TripleDES 암호화
     *
     * @param str 비밀키 암호화를 희망하는 문자열
     * @return String 암호화된 문자열
     * @exception Exception
     */
    public static String encodeTripleDES(String str) throws Exception {
        if (str == null || str.length() == 0)
        {
            return "";
        }
        String instance = "TripleDES/ECB/PKCS5Padding";
        javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(instance);
        cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, getTripleKey(tdesKey));
        String amalgam = str;

        byte[] inputBytes1 = amalgam.getBytes("UTF8");
        byte[] outputBytes1 = cipher.doFinal(inputBytes1);
        StringBuffer sb = new StringBuffer(outputBytes1.length * 2);
		for (int i = 0; i < outputBytes1.length; i++)
		{
			String hex = "0" + Integer.toHexString(0xff & outputBytes1[i]);
			sb.append(hex.substring(hex.length() - 2));
		}
        return sb.toString();
    }

    
    /**
     * 문자열 TripleDES 암호화
     * @param str 비밀키 암호화를 희망하는 문자열
     * @param key 암호화 키
     * @return String 암호화된 문자열
     * @exception Exception
     */
    public static String encodeTripleDES(String str, Key key) throws Exception {
        if (str == null || str.length() == 0)
        {
            return "";
        }
        String instance = "TripleDES/ECB/PKCS5Padding";
        javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(instance);
        cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, key);
        String amalgam = str;

        byte[] inputBytes1 = amalgam.getBytes("UTF8");
        byte[] outputBytes1 = cipher.doFinal(inputBytes1);
        StringBuffer sb = new StringBuffer(outputBytes1.length * 2);
		for (int i = 0; i < outputBytes1.length; i++)
		{
			String hex = "0" + Integer.toHexString(0xff & outputBytes1[i]);
			sb.append(hex.substring(hex.length() - 2));
		}
        return sb.toString();
    }
    

    /**
     * 문자열 TripleDES 복호화
     *
     * @param str  비밀키 복호화를 희망하는 문자열
     * @return String 복호화된 문자열
     * @exception Exception
     */
    public static String decodeTripleDES(String str) throws Exception {
        if (str == null || str.length() == 0)
            return "";
        
        byte[] b = new byte[str.length()/2];
        String instance = "TripleDES/ECB/PKCS5Padding";
        javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(instance);
        cipher.init(javax.crypto.Cipher.DECRYPT_MODE, getTripleKey(tdesKey));

		for (int i = 0; i < b.length; i++) {
			b[i] = (byte) Integer.parseInt(str.substring(2 * i, 2 * i + 2),
					16);
		}
		byte[] decryptedText = cipher.doFinal(b);
		String text = new String(decryptedText, "UTF8");
        
        return text;
    }

    /**
     * 문자열 대칭 복호화
     *
     * @param str  비밀키 복호화를 희망하는 문자열
     * @param key  암호화키
     * @return String 복호화된 문자열
     * @exception Exception
     */
    public static String decodeTripleDES(String str, Key key) throws Exception {
        if (str == null || str.length() == 0)
            return "";
        
        byte[] b = new byte[str.length()/2];
        String instance = "TripleDES/ECB/PKCS5Padding";
        javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(instance);
        cipher.init(javax.crypto.Cipher.DECRYPT_MODE, key);

		for (int i = 0; i < b.length; i++) {
			b[i] = (byte) Integer.parseInt(str.substring(2 * i, 2 * i + 2),
					16);
		}
		byte[] decryptedText = cipher.doFinal(b);
		String text = new String(decryptedText, "UTF8");
        
        return text;
    }
    
    /**
     * 문자열 DES 암호화
     *
     * @param str  비밀키 암호화를 희망하는 문자열
     * @return String 암호화된 문자열
     * @exception Exception
     */
    public static String encodeDES(String str) throws Exception {
        if (str == null || str.length() == 0)
        {
            return "";
        }
        String instance = "DES/ECB/PKCS5Padding";
        javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(instance);
        cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, getDesKey(commonKey));
        String amalgam = str;

        byte[] inputBytes1 = amalgam.getBytes("UTF8");
        byte[] outputBytes1 = cipher.doFinal(inputBytes1);
        StringBuffer sb = new StringBuffer(outputBytes1.length * 2);
		for (int i = 0; i < outputBytes1.length; i++)
		{
			String hex = "0" + Integer.toHexString(0xff & outputBytes1[i]);
			sb.append(hex.substring(hex.length() - 2));
		}
        return sb.toString();
    }
    
    /**
     * 문자열 DES 암호화
     *
     * @param str  비밀키 암호화를 희망하는 문자열
     * @param key 암호화키
     * @return String 암호화된 문자열
     * @exception Exception
     */
    public static String encodeDES(String str, Key key) throws Exception {
        if (str == null || str.length() == 0)
        {
            return "";
        }
        String instance = "DES/ECB/PKCS5Padding";
        javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(instance);
        cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, key);
        String amalgam = str;

        byte[] inputBytes1 = amalgam.getBytes("UTF8");
        byte[] outputBytes1 = cipher.doFinal(inputBytes1);
        StringBuffer sb = new StringBuffer(outputBytes1.length * 2);
		for (int i = 0; i < outputBytes1.length; i++)
		{
			String hex = "0" + Integer.toHexString(0xff & outputBytes1[i]);
			sb.append(hex.substring(hex.length() - 2));
		}
        return sb.toString();
    }

    /**
     * 문자열 대칭 복호화
     * @param str 비밀키 복호화를 희망하는 문자열
     * @return String 복호화된 문자열
     * @exception Exception
     */
    public static String decodeDES(String str) throws Exception {
        if (str == null || str.length() == 0)
            return "";
        
        byte[] b = new byte[str.length()/2];
        String instance = "DES/ECB/PKCS5Padding";
        javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(instance);
        cipher.init(javax.crypto.Cipher.DECRYPT_MODE, getDesKey(commonKey));

		for (int i = 0; i < b.length; i++) {
			b[i] = (byte) Integer.parseInt(str.substring(2 * i, 2 * i + 2),16);
		}
		byte[] decryptedText = cipher.doFinal(b);
		String text = new String(decryptedText, "UTF8");
        
        return text;
    }
    
    /**
     * 문자열 대칭 복호화
     * @param str 비밀키 복호화를 희망하는 문자열
     * @return String 복호화된 문자열
     * @exception Exception
     */
    public static String decodeDES(String str, Key key) throws Exception {
        if (str == null || str.length() == 0)
            return "";
        
        byte[] b = new byte[str.length()/2];
        String instance = "DES/ECB/PKCS5Padding";
        javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(instance);
        cipher.init(javax.crypto.Cipher.DECRYPT_MODE, key);

		for (int i = 0; i < b.length; i++) {
			b[i] = (byte) Integer.parseInt(str.substring(2 * i, 2 * i + 2),16);
		}
		byte[] decryptedText = cipher.doFinal(b);
		String text = new String(decryptedText, "UTF8");
        
        return text;
    }
    
	public static String encodeMD5(String inStr) {
		byte[] bpara = new byte[inStr.length()];
		byte[] rethash;
		int i;

		for (i = 0; i < inStr.length(); i++)
			bpara[i] = (byte) (inStr.charAt(i) & 0xff);

		try {
			MessageDigest md5er = MessageDigest.getInstance(MD5);
			rethash = md5er.digest(bpara);
		} catch (GeneralSecurityException e) {
			throw new RuntimeException(e);
		}

		StringBuffer r = new StringBuffer(32);
		for (i = 0; i < rethash.length; i++) {
			String x = Integer.toHexString(rethash[i] & 0xff);
			if (x.length() < 2)
				r.append("0");
			r.append(x);
		}
		return r.toString();
	}
	
	public static String getSHA2(String inStr) {
		byte[] bpara = new byte[inStr.length()];
		byte[] rethash;
		int i;

		for (i = 0; i < inStr.length(); i++)
			bpara[i] = (byte) (inStr.charAt(i) & 0xff);

		try {
			MessageDigest md5er = MessageDigest.getInstance(SHA256);
			rethash = md5er.digest(bpara);
		} catch (GeneralSecurityException e) {
			throw new RuntimeException(e);
		}

		StringBuffer r = new StringBuffer(32);
		for (i = 0; i < rethash.length; i++) {
			String x = Integer.toHexString(rethash[i] & 0xff);
			if (x.length() < 2)
				r.append("0");
			r.append(x);
		}
		return r.toString();
	}
	
	public String decrypt(String buffer) throws Exception {
		return buffer;
	}

	public String encrypt(String buffer) throws Exception {
		return buffer;
	}
	
	/**
	 * RSA 복호화
	 * 
	 * @param is
	 * @param original
	 * @return
	 * @throws Exception
	 */
	public static String RSAdecrypt(InputStream is, String encryptedStr) throws Exception {
		byte[] encodedKey = new byte[is.available()];
		is.read(encodedKey);

		PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(encodedKey);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		PrivateKey pkPrivate = kf.generatePrivate(privateKeySpec);

		Cipher pkCipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
		pkCipher.init(Cipher.DECRYPT_MODE, pkPrivate);
		byte[] decryptedInByte = pkCipher.doFinal(CryptoUtil.hexToByteArray(encryptedStr));

		String originalString = new String(decryptedInByte);
		return originalString;
	}
	
  /**
   * AES/ECB/NoPadding encode
   */
//  public static String encryptWithKey(String key, String text) throws Exception {
//    Key secureKey = new SecretKeySpec(key.getBytes(), "AES");
//    Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
//    cipher.init(Cipher.ENCRYPT_MODE, secureKey);
//    byte[] bytes = text.getBytes();
//    int length = (int)Math.ceil((float)bytes.length / 16.0) * 16;
//    byte[] buf = cipher.doFinal(Arrays.copyOf(bytes, length));
//    String encrypted = new String(Base64Coder.encode(buf));
//    return encrypted;
//  }
	
	public static void main(String ap[]){
		try {
			System.out.println(encodeTripleDES("123123"));
			System.out.println(CryptoUtil.AESencrypt("C20", "f4150d4a1ac5708c29e437749045a39a"));
			System.out.println(CryptoUtil.AESencrypt("C1001039144463", "f4150d4a1ac5708c29e437749045a39a"));
			System.out.println(CryptoUtil.AESdecrypt("96864fdea0531180c4d0fb8765b453ca0f4297a81123278887c4c26aa2563bf743211e6dd0398afa08be57cebc63905f", "e5fb322cadfdeee6e2d53e2e980aa322"));
			System.out.println(CryptoUtil.AESdecrypt("e02e1f4e9b45385928d523ac6740f3f7", "f4150d4a1ac5708c29e437749045a39a"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
