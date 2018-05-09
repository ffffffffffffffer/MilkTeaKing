package com.milkteaking.ec.sign;

import com.milkteaking.core.util.storage.Preference;

/**
 * @author TanJJ
 * @time 2018/5/9 9:55
 */

public class AccountManager {

    private enum SignTag {
        SIGN_TAG
    }

    public static void setSignTag(boolean tag) {
        Preference.setAppFlag(SignTag.SIGN_TAG.name(), tag);
    }

    private static boolean getSignTag() {
        return Preference.getAppFlag(SignTag.SIGN_TAG.name());
    }

    public static void isCheck(IUserCheck check) {
        if (isSign()) {
            check.onSignIn();
        } else {
            check.onNoSignIn();
        }
    }

    private static boolean isSign() {
        return getSignTag();
    }
}
