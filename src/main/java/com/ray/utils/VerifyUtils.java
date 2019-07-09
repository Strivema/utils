package com.ray.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Ray.Ma
 * @date 2019/7/9 11:19
 */
public class VerifyUtils {

    /**
     * 验证是否是手机号码
     * @param mobileId
     * @return
     */
    public static boolean isMobile(String mobileId) {
        if (StringUtils.isBlank(mobileId)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[1][0-9]{10}$");
        Matcher matcher = pattern.matcher(mobileId);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    /**
     * 验证身份证号
     * @param certId
     * @return
     */
    public static boolean isCertId(String certId) {
        if (StringUtils.isBlank(certId)) {
            return false;
        }
        String pattern_str = "\\d{15}||\\d{14}[x,X]||\\d{18}||\\d{17}[x,X]" //大陆身份证
                +"||[A-Za-z]\\d{6}\\([A-Za-z0-9]\\)" //香港身份证
                +"||\\d{7}\\([0-9]\\)" //澳门身份证
                +"||[A-Za-z]\\d{9}"; //台湾身份证
        Pattern pattern = Pattern.compile(pattern_str);
        Matcher matcher = pattern.matcher(certId);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    /**
     * 验证是否是中文
     * @param string
     * @return
     */
    public static boolean isChinese(String string) {
        if (StringUtils.isBlank(string)) {
            return false;
        }
        String regex = "[\\u4E00-\\u9FA5]+";
        if (StringUtils.isNotBlank(string)&&string.matches(regex)) {
            return true;
        }
        return false;
    }

    /**
     * 验证昵称的合法性
     * @param nickName
     * @return
     */
    public static boolean verifyNickName(String nickName) {
        if (StringUtils.isBlank(nickName)) {
            return false;
        }
        int length = 0;
        for (int i = 0; i < nickName.length(); i++) {
            String temp = nickName.substring(i, i + 1);
            if (isChinese(temp)) { //是中文
                length += 2;
            } else {
                length += 1;
            }
        }
        if (length>=4&&length<=16) { //4-16个字符(1个汉字=2个字符)
            return true;
        }
        return false;
    }

    /**
     * 验证被赠送手机号码的合法性
     * @param mobile
     * @return
     */
    public static boolean verifyGiftedMobile(String mobile) {
        if (StringUtils.isBlank(mobile)) {
            return false;
        }
        Pattern pattern = Pattern.compile("((13[0-9]|15[0-9]|18[0-9])\\d{8})(,(13[0-9]|15[0-9]|18[0-9])\\d{8})*");
        Matcher matcher = pattern.matcher(mobile);
        if (StringUtils.isBlank(mobile)||!matcher.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 验证邮箱格式的合法性
     * @param email
     * @return
     */
    public static boolean verifyEmail(String email){
        if(StringUtils.isBlank(email)){
            return false;
        }
        Pattern pattern = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
        Matcher matcher = pattern.matcher(email);
        if(!matcher.matches()){
            return false;
        }
        return true;
    }
    /**
     * 验证IP地址格式的
     * @param ip
     * @return
     */
    public static boolean verifyIp(String ip){
        if(StringUtils.isBlank(ip)){
            return false;
        }
        Pattern pattern = Pattern.compile("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");
        Matcher matcher = pattern.matcher(ip);
        if(!matcher.matches()){
            return false;
        }
        return true;
    }




    public static void main(String[] args){
        Map map1 = new HashMap(1000000);
        Map map2 = new HashMap();
        System.out.println(System.currentTimeMillis());
        for(int i = 0; i < 1000000;i++){
            map1.put(123+i,2345+i);
        }
        System.out.println(System.currentTimeMillis() );
        System.out.println(System.currentTimeMillis());
        for(int i = 0; i < 1000000;i++){
            map2.put(123+i,2345+i);
        }
        System.out.println(System.currentTimeMillis());
    }

}
