package com.labs.login.component;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class Base64Encoding {

    public String encrypt(String str) {
        Base64.Encoder encoder = Base64.getEncoder();
        return new String(encoder.encode(str.getBytes()));
    }

    public String decrypt(String str) {
        Base64.Decoder decoder = Base64.getDecoder();
        return new String(decoder.decode(str.getBytes()));
    }
}
