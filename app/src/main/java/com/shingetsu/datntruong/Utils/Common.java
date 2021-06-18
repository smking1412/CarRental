package com.shingetsu.datntruong.Utils;

import com.shingetsu.datntruong.Models.User;

public class Common {
    public static int REQUEST_CODE_SIGN_UP = 303;
    public static final String USER_INFO_REFERENCE = "User";
    public static final String CATEGORY_CAR_1 = "xe con";
    public static final String CATEGORY_CAR_2 = "xe khách";
    public static final String CATEGORY_CAR_3 = "xe bán tải";
    public static final String CATEGORY_CAR_4 = "xe tải";


    public static User userModel;

    public static String buildWelcomeMessage() {
        if (Common.userModel != null){
            return new StringBuilder("well come ")
                    .append(Common.userModel.getUsername()).toString();
        }else {return" ";}

    }
}
