package com.ray.utils;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

/**
 * @author Ray.Ma
 * @date 2019/7/9 11:12
 */
public class IDCardUtils {
    public static String getGender(String idCard) {
        if (idCard == null || (idCard.length() != 15 && idCard.length() != 18)) {
            return "";
        }

        String sex = "";

        /*//处理18位的性别代码
        if (idCard.length() == 18) {
            sex = idCard.substring(16,17);
        }

        //处理15位的性别代码
        if (idCard.length() == 15) {
            sex = idCard.substring(13, 14);
        }*/

        //处理18位的性别代码
        if (idCard.length() == 18) {
            sex = idCard.substring(14, 17);
        }

        //处理15位的性别代码
        if (idCard.length() == 15) {
            sex = idCard.substring(12, 15);
        }

        //性别代码为偶数是女性奇数为男性
        if ((Long.parseLong(sex) % 2) == 0) {
            sex = "女";
        } else {
            sex = "男";
        }
        return sex;
    }

    public static String getGenderForMaskIdCard(String idCard) {
        if (idCard == null || (idCard.length() != 15 && idCard.length() != 18)) {
            return "";
        }

        String sex = "";

        //处理18位的性别代码
        if (idCard.length() == 18) {
            sex = idCard.substring(16,17);
        }

        //处理15位的性别代码
        if (idCard.length() == 15) {
            sex = idCard.substring(13, 14);
        }

        //性别代码为偶数是女性奇数为男性
        if ((Long.parseLong(sex) % 2) == 0) {
            sex = "女";
        } else {
            sex = "男";
        }
        return sex;
    }


    public static short getAge(String idCard) {
        if (idCard == null || (idCard.length() != 15 && idCard.length() != 18)) {
            return 0;
        }

        String age = "";

        //处理18位的身份证号码从号码中得到生日
        if (idCard.length() == 18) {
            age = idCard.substring(6, 10);
        }

        //处理15位的身份证号码从号码中得到生日
        if (idCard.length() == 15) {
            age = idCard.substring(6, 8);
        }

        //add "19"
        if (age.length() == 2) {
            age = "19" + age;
        }

        int birthYear = Integer.parseInt(age);
        int yearNow = DateUtils.getYear(DateUtils.getNowDate());
        return (short)(yearNow - birthYear);
    }

    public static boolean ageLessThan18(String idCard) {
        try {

            if (idCard == null)
                return false;
            Date birthday = null;
            if (idCard.length() == 18) {
                birthday = new GregorianCalendar(
                        Integer.parseInt(idCard.substring(6, 10)),
                        Integer.parseInt(idCard.substring(10, 12)) - 1,  //month must -1
                        Integer.parseInt(idCard.substring(12, 14))).getTime();
            }
            if (idCard.length() == 15) {
                birthday = new GregorianCalendar(
                        Integer.parseInt("19" + idCard.substring(6, 8)),
                        Integer.parseInt(idCard.substring(8, 10)) - 1,  //month must -1
                        Integer.parseInt(idCard.substring(10, 12))).getTime();

            }

            if (birthday == null)
                return false;
            return ((TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis()) - TimeUnit.MILLISECONDS.toDays(birthday.getTime())) / 365) < 18;
        } catch (Exception e) {
            return false;
        }
    }


}

