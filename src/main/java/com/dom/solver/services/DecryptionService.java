package com.dom.solver.services;

import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class DecryptionService {

    public String decryptRot13(String text) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c >= 'a' && c <= 'm') {
                c += 13;
            } else if (c >= 'A' && c <= 'M') {
                c += 13;
            } else if (c >= 'n' && c <= 'z') {
                c -= 13;
            } else if (c >= 'N' && c <= 'Z') {
                c -= 13;
            }
            sb.append(c);
        }
        return sb.toString();
    }

    public String decryptBase64(String message) {
        return new String(Base64.getDecoder().decode(message));
    }
}
