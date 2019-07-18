package com.guarantee.util;

import java.net.URL;

/**
 * Created by acer on 2019/7/18.
 */
public class FileUtil {
    private FileUtil(){}

    /**
     * 读取resources下文件路径
     * @param fileName
     * @return
     */
    public static String getFilePath(String fileName) {
        URL urlPath = FileUtil.class.getResource(String.format("/%s", fileName));
        String execStr = String.format("python %s", urlPath.getPath().substring(1));
        return execStr;
    }

    public interface FileName {
        String DaYangDistinguish = "DaYangDistinguish.dll";
    }
}
