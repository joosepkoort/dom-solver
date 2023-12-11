package com.dom.solver.unit;

import com.dom.solver.services.DecryptionService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DecryptionServiceTest {

    @Resource
    DecryptionService decryptionService;

    @Test
    void decryptRot13Test() {
        String encrypted = "uryyb";
        String expected = "hello";
        String actual = decryptionService.decryptRot13(encrypted);
        assertEquals(expected, actual);
    }

    @Test
    void decryptBase64Test() {
        var encodedBase64 = "aGVsbG8=";
        var expected = "hello";
        String actual = decryptionService.decryptBase64(encodedBase64);
        assertEquals(expected, actual);
    }
}