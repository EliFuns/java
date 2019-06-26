package com.example.demo.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Md5Utils {

	private static final String HEX_NUMS_STR="0123456789ABCDEF";
	private static final String default_salt="O";   
       
    /**  
     * 验证（前拼盐值）
     * @param plainText
     * @param ciphertext
     * @param digit
     */  
    public static boolean validate_frontSalt(String plainText, String cipherText, Integer digit){
        byte[] ciphertextByte = hexStringToByte(cipherText);   
        byte[] saltByte = new  byte[digit];
        System.arraycopy(ciphertextByte, 0, saltByte, 0, digit);   

        MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
		}   
        md.update(saltByte);
        try {
			md.update(plainText.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
		}   
        byte[] plainDigest = md.digest();   
        byte[] cipherDigest = new byte[ciphertextByte.length - saltByte.length];   
        System.arraycopy(ciphertextByte, saltByte.length, cipherDigest, 0, cipherDigest.length);   
        if (Arrays.equals(plainDigest, cipherDigest)) {   
            return true;   
        } else {   
            return false;   
        }   
    }   
    
    /**  
     * 验证（后拼盐值）
     * @param plainText  
     * @param ciphertext 
     *  @param ciphertext 
     */  
    public static boolean validate_behindSalt(String plainText, String cipherText, Integer digit){
        byte[] ciphertextByte = hexStringToByte(cipherText);   
        byte[] saltByte = new  byte[digit];
        System.arraycopy(ciphertextByte, ciphertextByte.length-saltByte.length, saltByte, 0, digit);   

        MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
		}   
        md.update(saltByte);
        try {
			md.update(plainText.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
		}   
        byte[] plainDigest = md.digest();   
        byte[] cipherDigest = new byte[ciphertextByte.length - saltByte.length];   
        System.arraycopy(ciphertextByte, 0, cipherDigest, 0, cipherDigest.length);   
        if (Arrays.equals(plainDigest, cipherDigest)) {   
            return true;   
        } else {   
            return false;   
        }   
    }
    
    /**
     * 加密
     * @param plainText
     * @return
     */
    public static String Encrypted(String plainText){   
        byte[] plainDigest = null;   
  
        MessageDigest md = null;   
        try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
		}   
        try {
			md.update(plainText.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
		} 
        
         plainDigest = md.digest();   
        return byteToHexString(plainDigest);   
    }   
    
    
    /**
     * 加密
     * @param plainText
     * @return
     */
    public static String Encrypted_deSalt(String plainText){   
        return Encrypted_behindSalt(plainText,default_salt);   
    }   
    
    /**  
     * 加密（前拼盐值）
     * @param originalText  
     */  
    public static String Encrypted_frontSalt(String plainText, String salt){   
        byte[] cipherText = null;   
        byte[] saltBype = salt.getBytes();   
  
        MessageDigest md = null;   
        try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
		}   
        md.update(saltBype);   
        try {
			md.update(plainText.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
		}   
        byte[] plainDigest = md.digest();   
  
        cipherText = new byte[plainDigest.length + saltBype.length];
        
        System.arraycopy(saltBype, 0, cipherText, 0, saltBype.length);   
        System.arraycopy(plainDigest, 0, cipherText, saltBype.length, plainDigest.length);   
        return byteToHexString(cipherText);   
    }   
    
    
    
    /**  
     * 加密 （后拼盐值）
     * @param originalText  
     */  
    public static String Encrypted_behindSalt(String plainText, String salt){   
        byte[] cipherText = null;   
        byte[] saltBype = salt.getBytes();   
  
        MessageDigest md = null;   
        try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
		}   
        md.update(saltBype);   
        try {
			md.update(plainText.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
		}   
        byte[] plainDigest = md.digest();   
  
        cipherText = new byte[plainDigest.length + saltBype.length];   
        System.arraycopy(plainDigest, 0, cipherText, 0, plainDigest.length);   
        System.arraycopy(saltBype, 0, cipherText, plainDigest.length, saltBype.length);   
        return byteToHexString(cipherText);   
    }   
    
    

    /**   
     * 将16进制字符串转换成字节数组   
     * @param hex   
     * @return   
     */  
    private static byte[] hexStringToByte(String hex) {   
        int len = (hex.length() / 2);   
        byte[] result = new byte[len];   
        char[] hexChars = hex.toCharArray();   
        for (int i = 0; i < len; i++) {   
            int pos = i * 2;   
            result[i] = (byte) (HEX_NUMS_STR.indexOf(hexChars[pos]) << 4    
                            | HEX_NUMS_STR.indexOf(hexChars[pos + 1]));   
        }   
        return result;   
    }   
       
    /**  
     * 将指定byte数组转换成16进制字符串  
     * @param b  
     * @return  
     */  
    private static String byteToHexString(byte[] b) {   
        StringBuffer hexString = new StringBuffer();   
        for (int i = 0; i < b.length; i++) {   
            String hex = Integer.toHexString(b[i] & 0xFF);   
            if (hex.length() == 1) {   
                hex = '0' + hex;   
            }   
            hexString.append(hex);   
        }   
        return hexString.toString();   
    }   
    
    
    
    //test
    public static void main(String[] args){
		//test1();
		//test2();
		test4();
    }   
    
    
    public static void test4(){
        String userPwd = "123456";
        System.out.println(Encrypted_deSalt(userPwd));
        System.out.println(Encrypted_behindSalt(userPwd,"O"));
	}
    
    
    public static void test3(){
        String userPwd = "123456";
        System.out.println(Encrypted(userPwd));
        System.out.println(Encrypted_behindSalt(userPwd,"l"));
	}
    
    
    
	 public static void test2(){
	   	Map<String,String> users = new HashMap<String,String>();   
        String userName = "llj";   
        String userPwd = "123";   
        String ciphertext = Encrypted_behindSalt(userPwd, userName.substring(0, 1));   
        users.put(userName, ciphertext);     
        System.out.println(ciphertext);
        String userNametest = "llj111";   
        String plainTexttest = "123";
  	  	String ciphertexttest = (String)users.get(userNametest);   
        if(null != ciphertexttest){  
            if(validate_behindSalt(plainTexttest, ciphertexttest, userName.length())){
          	  System.out.println("口令正确");   
            }else{
          	  System.out.println("口令错误");   
            }
        }else{   
            System.out.println("口令不存在");   
        }   
	}
    
    public static void test1(){
    	 Map<String,String> users = new HashMap<String,String>();   
         String userName = "llj";   
         String plainText = "loginName=131&userPwd=123&operExt=&identifyCode=7996&needIdentifyCode=0";   
         String ciphertext = Encrypted_behindSalt(plainText,"yimif");   
         users.put(userName, ciphertext);     
            System.out.println(ciphertext);
         String userNametest = "llj";   
         String plainTexttest = "loginName=131&userPwd=123&operExt=&identifyCode=7996&needIdentifyCode=0";
   	  String ciphertexttest = (String)users.get(userNametest);   
         if(null != ciphertexttest){  
             if(validate_behindSalt(plainTexttest, ciphertexttest, 5)){
           	  System.out.println("口令正确");   
             }else{
           	  System.out.println("口令错误");   
             }
         }else{   
             System.out.println("口令不存在");   
         }   
    }
}
