package com.example.KursovaWebSite.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class ImageUploader {
    private final String UPLOAD_FOLDER = "D:\\java\\projects\\KursovaWebSite\\src\\main\\resources\\static\\img";

    public boolean uploadImage(MultipartFile imageProduct) {

        boolean isUpload = false;
        if (imageProduct != null) {
            try {
                Files.copy(imageProduct.getInputStream(),
                        Paths.get(UPLOAD_FOLDER + File.separator, imageProduct.getOriginalFilename()),
                        StandardCopyOption.REPLACE_EXISTING);
                isUpload = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else
            System.out.println("Photo not selected");

        return isUpload;
    }

    public boolean checkExisted(MultipartFile imageProduct) {
        boolean isExisted = false;
        try {
            File file = new File(UPLOAD_FOLDER + "\\" + imageProduct.getOriginalFilename());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isExisted;
    }

    public static byte[] ImageToByte(File file) throws FileNotFoundException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try (FileInputStream fis = new FileInputStream(file)){

            byte[] buf = new byte[1024];
            try {
                for (int readNum; (readNum = fis.read(buf)) != -1; ) {
                    bos.write(buf, 0, readNum);
                    System.out.println("read " + readNum + " bytes,");
                }
            } catch (IOException ex) {
                throw new FileNotFoundException("File not found");
            }
        } catch (IOException e) {
            throw new RuntimeException("Something with ImageUploader.obj");
        }
        byte[] bytes = bos.toByteArray();

        return bytes;
    }
}
