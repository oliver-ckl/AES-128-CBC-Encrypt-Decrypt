import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Decrypt {
	public static String padRight(String s, int n) {
	     return String.format("%-" + n + "s", s);  
	}

	public static byte[] hexStringToByteArray(String s) {
	   int len = s.length();
	   byte[] data = new byte[len / 2];
	   for (int i = 0; i < len; i += 2) {
	       data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                               + Character.digit(s.charAt(i+1), 16));
	   }
	   return data;
	}
	
	public String decrypt(String cipherText,String key, String ivHex){
	   String plainText="";
	   try {
	       String keyString = key;
	       byte[] iv=new byte[16];
	       if(keyString.length()<16)//perform nul padding
	           keyString=padRight(keyString,16).replace(' ', '\0');
	       else if (keyString.length()>16)//truncate excess substring
	           keyString=keyString.substring(0, 16);
	
	       Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
	       if (ivHex!=null){
	           iv=hexStringToByteArray(ivHex);    
	       }
	
	       SecretKeySpec keyspec = new SecretKeySpec(keyString.getBytes(StandardCharsets.UTF_8), "AES");
	
	       IvParameterSpec ivspec = new IvParameterSpec(iv);  
	       byte[] cipherTextByteArray = Base64.getDecoder().decode(cipherText);
	
	       cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
	       byte[] original = cipher.doFinal(cipherTextByteArray);
	       plainText = new String(original, StandardCharsets.UTF_8);
	       return plainText;
	   } catch (Exception e) {
	       System.out.println("Exception"+ e);
	       return null;
	   }
	}
}
