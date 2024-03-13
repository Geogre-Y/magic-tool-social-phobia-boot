//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.modules.utils;

import com.jeecg.weibo.exception.BusinessException;

public class Assist {
    public Assist() {
    }

    public static void threw(boolean shouldThrew, String message, Object... args) throws BusinessException {
        if (shouldThrew) {
            throw new BusinessException(String.format(message, args));
        }
    }

}
