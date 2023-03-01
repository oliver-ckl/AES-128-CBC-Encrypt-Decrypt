# AES-128-CBC-Encrypt-Decrypt
Cross language encryption and decryption code scraps. PHP for encryption and JAVA for decryption

**Table of Contents**
- [Summary](#summary)
- [AES-128-CBC](#aes-128-cbc)
  - [Encryption (PHP)](#encryption-php)
  - [Decryption (JAVA)](#decryption-java)
# Summary

The program perform cross language encrypt/decrypt process. 
We use PHP to encrypt here and decrypt with Java.
Since we are using AES-128-CBC, we should have a **key** and **iv** with *16 bytes* long.
* You need to write the API to pass the data yourself

-----
## Encryption (PHP)

```PHP
$plaintext="test123!!!"; //this is the text you want to encrypt
$cipher = "AES-128-CBC";
$key="1234567891234567"; //your key for the encryption
$ivlen = openssl_cipher_iv_length($cipher);
$iv = openssl_random_pseudo_bytes($ivlen);
$ciphertext = openssl_encrypt($plaintext, $cipher, $key, $options=0,$iv); //if you do not want to use iv, just remove from the parameter but it is not recommended
$ivToJava=bin2hex($iv); //pass this hex form iv to java
```

## Decryption (JAVA)
```Java
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
```
