package com.ray.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Ray.Ma
 * @date 2019/7/9 10:42
 */
public class CsvUtils {

    /**
     * 读取Csv文件
     *
     * @param value    查询条件值
     * @param index    条件值所在位置
     * @param filePath 文件路径
     * @return
     * @throws IOException
     */
    public static String[] selectByValue(String value, int index, String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
        String line = null;
        while ((line = br.readLine()) != null) {
            String[] info = line.split(",");
            if (info[index] != null && value.equals(info[index].trim())) {
                return info;
            }
        }
        return null;
    }
}
