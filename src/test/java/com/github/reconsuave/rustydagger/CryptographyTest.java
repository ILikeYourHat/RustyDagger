package com.github.reconsuave.rustydagger;

import static org.junit.jupiter.api.Assertions.assertEquals;

import DCourt.Tools.Cryptography;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class CryptographyTest {

  @ParameterizedTest(name = "{0}")
  @CsvSource(
      value = {
        "abcdef, gikmoq",
        "The quick brown fox jumps over the lazy dog,"
            + "\u007F42N@E:5>T7HFOGZAKU^IUNRVdT\\LZi^SQmZPjjrWc\\",
        "{itArms|Masamune|60|0|40|right}, :)5c518Bt)<+8A;3KfaNcPifSJBACPZ"
      })
  public void shouldEncryptAndDecryptStrings(String example, String encrypted) {
    String encryptionResult = Cryptography.encrypt(example);
    assertEquals(encrypted, encryptionResult);
    String decryptionResult = Cryptography.decrypt(encryptionResult);
    assertEquals(example, decryptionResult);
  }

  @Test
  public void shouldHandleEmptyString() {
    String encryptionResult = Cryptography.encrypt("");
    assertEquals("", encryptionResult);
    String decryptionResult = Cryptography.decrypt(encryptionResult);
    assertEquals("", decryptionResult);
  }
}
