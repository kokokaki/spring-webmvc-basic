package com.spring.mvc.web.common.upload;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class FileUtilsTest {

    @Test
    void uuidTest() {

        String fileName = "dog.jpg";
        String newFileName = UUID.randomUUID().toString() + "_" + fileName;

        System.out.println("newFileName = " + newFileName);
    }

}