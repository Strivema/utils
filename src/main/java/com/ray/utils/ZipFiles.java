package com.ray.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * @author Ray.Ma
 * @date 2019/7/9 11:20
 */
public class ZipFiles {
    //文件打包下载

    /***
     *
     * @param files 待下载的文件
     * @param dirAndFile
     * @param request
     * @param response
     * @return
     */
    public static HttpServletResponse downLoadFiles(List<File> files,
                                                    String dirAndFile,
                                                    HttpServletRequest request,
                                                    HttpServletResponse response)
    {
        try {
            /**这个集合就是你想要打包的所有文件，
             * 这里假设已经准备好了所要打包的文件*/
//            files

            /**创建一个临时压缩文件，
             * 我们会把文件流全部注入到这个文件中
             * 这里的文件你可以自定义是.rar还是.zip*/
            File file = new File(dirAndFile);
            if (!file.exists()){
                file.createNewFile();
            }
            response.reset();
            //response.getWriter()
            //创建文件输出流
            FileOutputStream fous = new FileOutputStream(file);
            /**打包的方法我们会用到ZipOutputStream这样一个输出流,
             * 所以这里我们把输出流转换一下*/
//            org.apache.tools.zip.ZipOutputStream zipOut
//                = new org.apache.tools.zip.ZipOutputStream(fous);
            ZipOutputStream zipOut = new ZipOutputStream(fous);
            /**这个方法接受的就是一个所要打包文件的集合，
             * 还有一个ZipOutputStream*/
            zipFile(files, zipOut);
            zipOut.close();
            fous.close();
            return downloadZip(file,response);
        }catch (Exception e) {
            e.printStackTrace();
        }
        /**直到文件的打包已经成功了，
         * 文件的打包过程被我封装在FileUtil.zipFile这个静态方法中，
         * 稍后会呈现出来，接下来的就是往客户端写数据了*/
        // OutputStream out = response.getOutputStream();


        return response ;
    }

    /**
     * 把接受的全部文件打成压缩包
     */
    public static void zipFile
    (List files,ZipOutputStream outputStream) {
        int size = files.size();
        for(int i = 0; i < size; i++) {
            File file = (File) files.get(i);
            zipFile(file, outputStream);
        }
    }

    public static HttpServletResponse downloadZip(File file, HttpServletResponse response) {
        try {
            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(file.getPath()));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();

            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }finally{
            try {
                File f = new File(file.getPath());
                f.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    /**
     * 根据输入的文件与输出流对文件进行打包
     */
    public static void zipFile(File inputFile,
                               ZipOutputStream ouputStream) {
        try {
            if(inputFile.exists()) {
                /**如果是目录的话这里是不采取操作的，
                 * 至于目录的打包正在研究中*/
                if (inputFile.isFile()) {
                    FileInputStream IN = new FileInputStream(inputFile);
                    BufferedInputStream bins = new BufferedInputStream(IN, 512);
                    //org.apache.tools.zip.ZipEntry
                    ZipEntry entry = new ZipEntry(inputFile.getName());
                    ouputStream.putNextEntry(entry);
                    // 向压缩文件中输出数据
                    int nNumber;
                    byte[] buffer = new byte[512];
                    while ((nNumber = bins.read(buffer)) != -1) {
                        ouputStream.write(buffer, 0, nNumber);
                    }
                    // 关闭创建的流对象
                    bins.close();
                    IN.close();
                } else {
                    try {
                        File[] files = inputFile.listFiles();
                        for (int i = 0; i < files.length; i++) {
                            zipFile(files[i], ouputStream);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**  解压缩（压缩文件中包含多个文件）可代替上面的方法使用。
     * ZipInputStream类
     * 当我们需要解压缩多个文件的时候，ZipEntry就无法使用了，
     * 如果想操作更加复杂的压缩文件，我们就必须使用ZipInputStream类
     * */
    public static void unZipFile(String zippath ,String outzippath) throws Exception{
        File file = new File(zippath);
        File outFile = null;
        ZipFile zipFile = new ZipFile(file);
        ZipInputStream zipInput = new ZipInputStream(new BufferedInputStream(new FileInputStream(file)));
        ZipEntry entry = null;
        InputStream input = null;
        OutputStream output = null;
        while((entry = zipInput.getNextEntry()) != null){
            try {
                System.out.println("解压缩" + entry.getName() + "文件");
                outFile = new File(outzippath + File.separator + entry.getName());
                if(!outFile.getParentFile().exists()){
                    outFile.getParentFile().mkdirs();
                }
                if(!outFile.exists()){
                    outFile.createNewFile();
                }
                input = zipFile.getInputStream(entry);
                output = new FileOutputStream(outFile);
                int temp = 0;
                while((temp = input.read()) != -1){
                    output.write(temp);
                }

            } catch (Exception e) {
                throw e;
            } finally {
                output.flush();
                input.close();
                output.close();
            }

        }
        zipFile.close();
        zipInput.closeEntry();
        zipInput.close();
    }

    /**
     * 下载远程文件并保存到本地
     * @param remoteFilePath 远程文件路径
     * @param localFilePath 本地文件路径
     * @param domain 用于验证防盗链的域名，可为空
     */
    public static String downloadFile2Local(String remoteFilePath, String localFilePath, String domain) throws Exception
    {
        URL urlfile = null;
        HttpURLConnection httpUrl = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        File f = new File(localFilePath);
        if(!f.getParentFile().exists()){
            f.getParentFile().mkdirs();
        }
        OutputStream os = new FileOutputStream(f);
        boolean hasData = false;
        try
        {
            urlfile = new URL(remoteFilePath);
            httpUrl = (HttpURLConnection)urlfile.openConnection();
            if (StringUtils.isNotBlank(domain)) {
                httpUrl.setRequestProperty("Referer", domain);
            }

            httpUrl.connect();
            bis = new BufferedInputStream(httpUrl.getInputStream());
            bos = new BufferedOutputStream(os);
            int len = 2048;
            byte[] b = new byte[len];
            while ((len = bis.read(b)) != -1)
            {
                hasData = true;
                bos.write(b, 0, len);
            }
            bos.flush();
            bis.close();
            httpUrl.disconnect();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {
            try
            {
                bis.close();
                bos.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        if (!hasData) {
            f.delete();
        }
        if (f.exists()) {
            return f.getName();
        } else {
            return null;
        }

    }

    public static void main(String[] args) {
        String name;
        try {
            name = ZipFiles.downloadFile2Local("https://www.51cunzheng.com/searchResult?r=1259518691676733440299", "/Users/zhao/temp/contract/rlt.zip", "www.zhaojingzi.top");
            System.out.println(name);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
