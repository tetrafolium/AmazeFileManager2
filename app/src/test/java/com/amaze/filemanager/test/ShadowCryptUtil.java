package com.amaze.filemanager.test;

import android.content.Context;
import android.util.Base64;
import com.amaze.filemanager.utils.files.CryptUtil;
import java.io.IOException;
import java.security.GeneralSecurityException;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

@Implements(CryptUtil.class)
public class ShadowCryptUtil {

  private static final String ALGO_AES = "AES/GCM/NoPadding";
  private static final String IV =
      "LxbHiJhhUXcj"; // 12 byte long IV supported by android for GCM

  private static SecretKey secretKey = null;

  static {
    try {
      KeyGenerator keyGen = KeyGenerator.getInstance("AES", "BC");
      keyGen.init(128);
      secretKey = keyGen.generateKey();
    } catch (GeneralSecurityException e) {
      e.printStackTrace();
    }
  }

  /**
   * Method handles encryption of plain text on various APIs
   */
  @Implementation
  public static String encryptPassword(final Context context,
                                       final String plainText)
      throws GeneralSecurityException, IOException {
    return aesEncryptPassword(plainText);
  }

  /**
   * Method handles decryption of cipher text on various APIs
   */
  @Implementation
  public static String decryptPassword(final Context context,
                                       final String cipherText)
      throws GeneralSecurityException, IOException {
    return aesDecryptPassword(cipherText);
  }

  /**
   * Helper method to encrypt plain text password
   */
  private static String aesEncryptPassword(final String plainTextPassword)
      throws GeneralSecurityException {

    Cipher cipher = Cipher.getInstance(ALGO_AES, "BC");
    GCMParameterSpec gcmParameterSpec =
        new GCMParameterSpec(128, IV.getBytes());
    cipher.init(Cipher.ENCRYPT_MODE, secretKey, gcmParameterSpec);
    byte[] encodedBytes = cipher.doFinal(plainTextPassword.getBytes());

    return Base64.encodeToString(encodedBytes, Base64.DEFAULT);
  }

  /**
   * Helper method to decrypt cipher text password
   */
  private static String aesDecryptPassword(final String cipherPassword)
      throws GeneralSecurityException {

    Cipher cipher = Cipher.getInstance(ALGO_AES, "BC");
    GCMParameterSpec gcmParameterSpec =
        new GCMParameterSpec(128, IV.getBytes());
    cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmParameterSpec);
    byte[] decryptedBytes =
        cipher.doFinal(Base64.decode(cipherPassword, Base64.DEFAULT));

    return new String(decryptedBytes);
  }
}
