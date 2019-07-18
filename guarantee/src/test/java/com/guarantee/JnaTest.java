package com.guarantee;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;

/**
 * @author: wgf
 * @create: 2019-07-16 20:34
 * @description:
 **/
public class JnaTest {

    public static void main(String[] args) {
        String str = "{\"binaryValue\":\"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\",\"type\":\"image\",\"error\":null}";
        String result = JNATestDll.instanceDll.DaYangDistinguish(str);
        System.out.println(result);
    }

    public interface JNATestDll extends Library {
        JNATestDll instanceDll = (JNATestDll)
                Native.loadLibrary("D:\\DaYangDistinguish", JNATestDll.class);

        String DaYangDistinguish(String str);
    }
}