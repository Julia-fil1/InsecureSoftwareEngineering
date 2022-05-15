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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

@Component
public class LocalDateEncryptor implements AttributeConverter<LocalDate, String> {
    private final Key key;
    private final Cipher cipher;

    public LocalDateEncryptor() throws Exception {
        String SECRET = "insecure-key-123";
        key = new SecretKeySpec(SECRET.getBytes(), "AES");
        cipher = Cipher.getInstance("AES");
    }

    private String entityAttributeToString(LocalDate date) {
        return date.format(DateTimeFormatter.ISO_DATE);
    }

    @Override
    public String convertToDatabaseColumn(LocalDate localDate) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] toEncrypt = entityAttributeToString(localDate).getBytes(StandardCharsets.UTF_8);
            return Base64.getEncoder().encodeToString(cipher.doFinal(toEncrypt));
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            throw new IllegalStateException(ex);
        }
    }

    @Override
    public LocalDate convertToEntityAttribute(String data) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
            String toParse = new String(cipher.doFinal(Base64.getDecoder().decode(data)));
            return LocalDate.parse(toParse, DateTimeFormatter.ISO_DATE);
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            throw new IllegalStateException(ex);
        }
    }
}
