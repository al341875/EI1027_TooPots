package com.example.ei1027.config;

import org.apache.commons.lang3.RandomStringUtils;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.stereotype.Service;

/**
 * Created by CIT on 22/05/2019.
 */
@Service
public class EncryptorFactory {

    private BasicPasswordEncryptor encryptor;

    public BasicPasswordEncryptor getEncryptor() {
        if (this.encryptor == null)
            this.encryptor = new BasicPasswordEncryptor();
        return this.encryptor;
    }
    public String generateRandomPassword() {
        return RandomStringUtils.randomAlphanumeric(7);
    }
}
