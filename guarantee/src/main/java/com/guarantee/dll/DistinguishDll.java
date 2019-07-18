package com.guarantee.dll;

import com.guarantee.util.FileUtil;
import com.sun.jna.Library;
import com.sun.jna.Native;

/**
 * Created by acer on 2019/7/18.
 */
public interface DistinguishDll extends Library {
    DistinguishDll instanceDll = (DistinguishDll)
            Native.loadLibrary(FileUtil.getFilePath(FileUtil.FileName.DaYangDistinguish), DistinguishDll.class);

    String DaYangDistinguish(String str);
}