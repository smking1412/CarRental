package com.shingetsu.datntruong.Utils;

import com.shingetsu.datntruong.Models.User;

public class Common {
    public static int REQUEST_CODE_SIGN_UP = 303;
    public static final String USER_INFO_REFERENCE = "User";

    public static User userModel;

    public static String buildWelcomeMessage() {
        if (Common.userModel != null){
            return new StringBuilder("well come ")
                    .append(Common.userModel.getUsername()).toString();
        }else {return" ";}

    }
}
