package com.example.ei1027.config;

import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.stereotype.Service;

/**
 * Created by CIT on 22/05/2019.
 */
@Service
public class EncryptorFactory {

    public BasicPasswordEncryptor getEncryptor() {
        return new BasicPasswordEncryptor();
    }
}
