package com.guarantee.util;

import sun.misc.BASE64Decoder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class Base64Util {
    public static String base64ChangeImage(String baseStr, String savePath) {
        if (baseStr == null) {
            return null;
        }

        OutputStream out = null;
        String filePath = savePath + UUID.randomUUID().toString() + ".jpg";
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // 解密
            byte[] b = decoder.decodeBuffer(baseStr);
            // 处理数据
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            out = new FileOutputStream(filePath);
            out.write(b);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            filePath = null;
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            return filePath;
        }
    }
}
