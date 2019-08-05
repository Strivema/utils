package com.ray.utils;

/**
 * @author Ray.Ma
 * @date 2019/8/5 16:07
 */
public class StringUtils {
    /**
     * 字符串中特定字符后加上一个新字符
     *
     * @param str       原始字符串
     * @param searchStr 特定字符
     * @param appendStr 新添加字符串
     * @return
     */
    public static String searchStrAndAppendStr(String str, String searchStr, String appendStr) {
        StringBuffer strBuf = new StringBuffer(str);
        //查询字符出现的第一个索引位置
        int index = strBuf.indexOf(searchStr);
        //获取传入字符大小
        int searchStrLength = StringUtils.getStrCount(searchStr);
        while (index != -1) {
            //添加字符
            strBuf.insert((index + searchStrLength), appendStr);
            //查询第二，三,n个字符出现位置
            index = strBuf.indexOf(searchStr, index + 1);
        }
        return strBuf.toString();
    }

    /**
     * 得到传入字符串的字节大小
     *
     * @param str
     * @return
     */
    public static int getStrCount(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        int count = 0;
        char[] chs = str.toCharArray();
        for (int i = 0; i < chs.length; i++) {
            count += (chs[i] > 0xff) ? 3 : 1;
        }
        return count;
    }
}

