package com.dom.solver.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
    String adId;
    String message;
    Long reward;
    Long expiresIn;
    String probability;
    Integer probabilityValue;
    Long encrypted;

    public boolean isRot13Encrypted() {
        return encrypted != null && encrypted.equals(2L);
    }

    public boolean isBase64Encrypted() {
        return encrypted != null && encrypted.equals(1L);
    }
}
