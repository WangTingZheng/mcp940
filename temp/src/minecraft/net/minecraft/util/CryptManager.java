package net.minecraft.util;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CryptManager {
   private static final Logger field_180198_a = LogManager.getLogger();

   public static SecretKey func_75890_a() {
      try {
         KeyGenerator keygenerator = KeyGenerator.getInstance("AES");
         keygenerator.init(128);
         return keygenerator.generateKey();
      } catch (NoSuchAlgorithmException nosuchalgorithmexception) {
         throw new Error(nosuchalgorithmexception);
      }
   }

   public static KeyPair func_75891_b() {
      try {
         KeyPairGenerator keypairgenerator = KeyPairGenerator.getInstance("RSA");
         keypairgenerator.initialize(1024);
         return keypairgenerator.generateKeyPair();
      } catch (NoSuchAlgorithmException nosuchalgorithmexception) {
         nosuchalgorithmexception.printStackTrace();
         field_180198_a.error("Key pair generation failed!");
         return null;
      }
   }

   public static byte[] func_75895_a(String p_75895_0_, PublicKey p_75895_1_, SecretKey p_75895_2_) {
      try {
         return func_75893_a("SHA-1", p_75895_0_.getBytes("ISO_8859_1"), p_75895_2_.getEncoded(), p_75895_1_.getEncoded());
      } catch (UnsupportedEncodingException unsupportedencodingexception) {
         unsupportedencodingexception.printStackTrace();
         return null;
      }
   }

   private static byte[] func_75893_a(String p_75893_0_, byte[]... p_75893_1_) {
      try {
         MessageDigest messagedigest = MessageDigest.getInstance(p_75893_0_);

         for(byte[] abyte : p_75893_1_) {
            messagedigest.update(abyte);
         }

         return messagedigest.digest();
      } catch (NoSuchAlgorithmException nosuchalgorithmexception) {
         nosuchalgorithmexception.printStackTrace();
         return null;
      }
   }

   public static PublicKey func_75896_a(byte[] p_75896_0_) {
      try {
         EncodedKeySpec encodedkeyspec = new X509EncodedKeySpec(p_75896_0_);
         KeyFactory keyfactory = KeyFactory.getInstance("RSA");
         return keyfactory.generatePublic(encodedkeyspec);
      } catch (NoSuchAlgorithmException var3) {
         ;
      } catch (InvalidKeySpecException var4) {
         ;
      }

      field_180198_a.error("Public key reconstitute failed!");
      return null;
   }

   public static SecretKey func_75887_a(PrivateKey p_75887_0_, byte[] p_75887_1_) {
      return new SecretKeySpec(func_75889_b(p_75887_0_, p_75887_1_), "AES");
   }

   public static byte[] func_75894_a(Key p_75894_0_, byte[] p_75894_1_) {
      return func_75885_a(1, p_75894_0_, p_75894_1_);
   }

   public static byte[] func_75889_b(Key p_75889_0_, byte[] p_75889_1_) {
      return func_75885_a(2, p_75889_0_, p_75889_1_);
   }

   private static byte[] func_75885_a(int p_75885_0_, Key p_75885_1_, byte[] p_75885_2_) {
      try {
         return func_75886_a(p_75885_0_, p_75885_1_.getAlgorithm(), p_75885_1_).doFinal(p_75885_2_);
      } catch (IllegalBlockSizeException illegalblocksizeexception) {
         illegalblocksizeexception.printStackTrace();
      } catch (BadPaddingException badpaddingexception) {
         badpaddingexception.printStackTrace();
      }

      field_180198_a.error("Cipher data failed!");
      return null;
   }

   private static Cipher func_75886_a(int p_75886_0_, String p_75886_1_, Key p_75886_2_) {
      try {
         Cipher cipher = Cipher.getInstance(p_75886_1_);
         cipher.init(p_75886_0_, p_75886_2_);
         return cipher;
      } catch (InvalidKeyException invalidkeyexception) {
         invalidkeyexception.printStackTrace();
      } catch (NoSuchAlgorithmException nosuchalgorithmexception) {
         nosuchalgorithmexception.printStackTrace();
      } catch (NoSuchPaddingException nosuchpaddingexception) {
         nosuchpaddingexception.printStackTrace();
      }

      field_180198_a.error("Cipher creation failed!");
      return null;
   }

   public static Cipher func_151229_a(int p_151229_0_, Key p_151229_1_) {
      try {
         Cipher cipher = Cipher.getInstance("AES/CFB8/NoPadding");
         cipher.init(p_151229_0_, p_151229_1_, new IvParameterSpec(p_151229_1_.getEncoded()));
         return cipher;
      } catch (GeneralSecurityException generalsecurityexception) {
         throw new RuntimeException(generalsecurityexception);
      }
   }
}
