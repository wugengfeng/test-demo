package com.guarantee.authcode.dll;

import com.guarantee.authcode.constant.Constant;
import com.sun.jna.Library;
import com.sun.jna.Native;

/**
 * Created by acer on 2019/7/18.
 */
public interface DistinguishDll extends Library {
    DistinguishDll instanceDll = (DistinguishDll)
            Native.loadLibrary(Constant.daYangDistinguishPath, DistinguishDll.class);

    String DaYangDistinguish(String str);
}