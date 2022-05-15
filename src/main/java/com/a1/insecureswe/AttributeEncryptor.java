package com.a1.insecureswe;

import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.AttributeConverter;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.util.Base64;

@Component
public class AttributeEncryptor implements AttributeConverter<String, String> {
    private final Key key;
    private final Cipher cipher;

    public AttributeEncryptor() throws Exception {
        String SECRET = "insecure-key-123";
        key = new SecretKeySpec(SECRET.getBytes(), "AES");
        cipher = Cipher.getInstance("AES");
    }

    @Override
    public String convertToDatabaseColumn(String attribute) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            System.out.println("Converter: Original data: " + attribute);
            System.out.println("Converter: Encrypted data: " + Base64.getEncoder().encodeToString(cipher.doFinal(attribute.getBytes(StandardCharsets.UTF_8))));
            return Base64.getEncoder().encodeToString(cipher.doFinal(attribute.getBytes(StandardCharsets.UTF_8)));
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            throw new IllegalStateException(ex);
        }
    }

    @Override
    public String convertToEntityAttribute(String data) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
            System.out.println("Encrypted data: " + data);
            System.out.println("Decrypted data: " + new String(cipher.doFinal(Base64.getDecoder().decode(data))));
            return new String(cipher.doFinal(Base64.getDecoder().decode(data)));
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            throw new IllegalStateException(ex);
        }
    }
}
