package com.ray.utils;


import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;


import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/**
 * @author Ray.Ma
 * @date 2019/7/9 10:51
 */
public class HttpUtils {


    @SuppressWarnings("rawtypes")
    public static StringBuilder sendRequest(String method, String urlAddr, Map<String, Object> paramMap, String encoding) {
        HttpURLConnection conn = null;
        StringBuilder builder = new StringBuilder();

        try {
            if (paramMap != null) {
                Iterator it = paramMap.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry element = (Map.Entry) it.next();
                    builder.append(element.getKey());
                    builder.append("=");
                    builder.append(element.getValue());
                    builder.append("&");
                }
                if (builder.length() > 0) {
                    builder.deleteCharAt(builder.length() - 1);
                }
                paramMap.clear();
                paramMap = null;
            }

            URL url = new URL(urlAddr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);

            conn.setDoOutput(true);
            conn.setRequestMethod(method);
            conn.setUseCaches(false);
            conn.setRequestProperty("Content-Length", String.valueOf(builder.length()));
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Accept", "image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, application/x-shockwave-flash, application/msword, application/vnd.ms-excel, application/vnd.ms-powerpoint, */*");
            conn.setRequestProperty("Accept-Language", "zh-cn");
            conn.setRequestProperty("Cache-Control", "no-cache");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; SV1)");
            conn.setRequestProperty("Cookie", "");
            conn.setDoInput(true);
            conn.connect();

            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
            out.write(builder.toString());
            out.flush();
            out.close();

            int code = conn.getResponseCode();
            if (code != 200) {
                throw new Exception(method + "请求失败[错误码:" + code + "]");
            }
            else {
                builder.delete(0, builder.length());
                InputStream in = conn.getInputStream();
                byte[] bytes = new byte[1024];
                int pos = 0;
                do {
                    pos = in.read(bytes, 0, 1024);
                    builder.append(new String(bytes, encoding));
                } while (pos != -1);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        finally {
            conn.disconnect();
            conn = null;
        }

        return builder;
    }

    /**
     * 采用post提交的方式，模拟发送Http请求
     *
     * @author chengxf
     * @date May 31, 2012
     * @param urlAddr
     *            请求地址
     * @param paramMap
     *            参数列表，该参数为Map类型，其中key为参数名，value为参数值
     * @param encoding
     *            读取编码格式
     * @return 然后请求后接收回的字符串类型的数据
     */
    @SuppressWarnings("deprecation")
    public static String doPost(String urlAddr, Map<String, String> paramMap, String encoding) {
        HttpPost httpPost = null;
        HttpClient httpclient = null;
        HttpResponse httpResponse = null;
        HttpEntity entity = null;

        try {
            // 设置POST请求对象
            httpPost = new HttpPost(urlAddr);
            httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.1.2)");
            httpPost.setHeader("Accept-Language", "zh-cn,zh;q=0.5");

            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            if (paramMap != null) {
                Iterator<Map.Entry<String, String>> iterator = paramMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, String> element = iterator.next();
                    nvps.add(new BasicNameValuePair(element.getKey(), element.getValue()));
                }
            }

            httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

            // 开始发送HTTP-POST请求
            httpclient = urlAddr.startsWith("https") ? new SSLClients() : new DefaultHttpClient();
            httpResponse = httpclient.execute(httpPost);
            entity = httpResponse.getEntity();

            // 分析返回的数据
            return EntityUtils.toString(entity, encoding);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (httpPost != null) httpPost.abort();
            if (httpclient != null)
                httpclient.getConnectionManager().shutdown();

            httpPost = null;
            httpclient = null;
            httpResponse = null;
            entity = null;
        }

        return null;
    }/**
     * 采用post提交的方式，模拟发送Http请求
     *
     * @author huym
     * @date  August 4 2015
     * @param urlAddr
     *            请求地址
     * @param paramMap
     *            参数列表，该参数为Map类型，其中key为参数名，value为参数值
     * @param encoding
     *            读取编码格式
     * @param key
     *            加密有的密匙
     * @return 然后请求后接收回的字符串类型的数据
     */
    public static String doPostByEncrypt(String urlAddr, Map<String, String> paramMap, String encoding, String key) {
//        paramMap = WDLDDESUtils.encryptParams(paramMap, key);
        return doPost(urlAddr, paramMap, encoding);
    }

    /**
     * 采用get提交的方式，模拟发送Http请求
     *
     * @author chengxf
     * @date May 31, 2012
     * @param urlAddr
     *            请求地址
     * @param paramMap
     *            参数列表，该参数为Map类型，其中key为参数名，value为参数值
     * @param encoding
     *            读取编码格式
     * @return 然后请求后接收回的字符串类型的数据
     */
    public static String doGet(String urlAddr, Map<String, String> paramMap, String encoding) {
        BufferedReader in = null;
        String content = null;
        CloseableHttpClient client = null;

        try {
            if (paramMap != null) {
                StringBuilder params = new StringBuilder();
                Iterator it = paramMap.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry element = (Map.Entry) it.next();
                    params.append(element.getKey());
                    params.append("=");
                    params.append(element.getValue());
                    params.append("&");
                }

                if (params.length() > 0) {
                    params.deleteCharAt(params.length() - 1);

                    if (urlAddr.indexOf("?") != -1) {
                        urlAddr = urlAddr + "&" + params.toString();
                    } else {
                        urlAddr = urlAddr + "?" + params.toString();
                    }
                }
            }

            client = HttpClients.createDefault();
            HttpGet request = new HttpGet();
            request.setURI(new URI(urlAddr));
            HttpResponse response = client.execute(request);

            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), encoding));
            StringBuffer sb = new StringBuffer("");
            String line = null;
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();
            client.close();
            client = null;
            content = sb.toString();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (client != null) {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return content;
    }

    /**
     * 通过response向请求者回送数据
     *
     * @author chengxf
     * @date June 14, 2012
     * @param response
     *            response
     * @param data
     *            需要回送的数据
     * @param encoding
     *            回送时的编码格式
     *            文本格式
     */
    public static void printOut(HttpServletResponse response, String data, String encoding) {
        try {
            response.setCharacterEncoding(encoding); // 设置编码格式
            PrintWriter out = response.getWriter();
            out.print(data);
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void printOut(HttpServletResponse response, Object object, String encoding) {
        try {
            JSONObject json = (JSONObject) JSONObject.toJSON(object);
            response.setCharacterEncoding(encoding); // 设置编码格式
            PrintWriter out = response.getWriter();

            out.print(object);
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String doGetGzip(String urlAddr, Map<String, Object> paramMap, String encoding) {
        HttpURLConnection conn = null;

        try {
            StringBuilder params = new StringBuilder();

            if (paramMap != null) {
                Iterator it = paramMap.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry element = (Map.Entry) it.next();
                    params.append(element.getKey());
                    params.append("=");
                    params.append(element.getValue());
                    params.append("&");
                }

                if (params.length() > 0) {
                    params.deleteCharAt(params.length() - 1);
                }
            }

            URL url = new URL(urlAddr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);

            conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            conn.setUseCaches(false);
            conn.setRequestProperty("Content-Length", String.valueOf(params.length()));
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Accept", "image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, application/x-shockwave-flash, application/msword, application/vnd.ms-excel, application/vnd.ms-powerpoint, */*");
            conn.setRequestProperty("Accept-Encoding", "gzip,deflate");
            conn.setRequestProperty("Accept-Language", "zh-CN");
            conn.setRequestProperty("Cache-Control", "no-cache");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            conn.setDoInput(true);
            conn.connect();

            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
            out.write(params.toString());
            out.flush();
            out.close();

            int code = conn.getResponseCode();
            if (code != 200) {
                throw new Exception("GET请求失败[错误码:" + code + "]");
            }
            else {
                InputStream in = conn.getInputStream();

                GZIPInputStream gin = new GZIPInputStream(in);
                InputStreamReader isr = new InputStreamReader(gin, encoding);
                char[] buffer = new char[1024];
                int pos = 0;
                StringBuilder sb = new StringBuilder();
                while ((pos = isr.read(buffer)) != -1) {
                    sb.append(new String(buffer, 0, pos));
                }
                isr.close();
                in.close();

                return sb.toString();
            }
        }
        catch (SocketTimeoutException te) {
            System.out.println("网页请求超时....");
            return null;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        finally {
            conn.disconnect();
            conn = null;
        }
    }

    public static String doGet(String urlAddr, String encoding) {
        HttpURLConnection conn = null;

        try {
            URL url = new URL(urlAddr);
            conn = (HttpURLConnection)url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            conn.setUseCaches(false);
            conn.setRequestProperty("Content-Length", String.valueOf(0));
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Accept","image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, application/x-shockwave-flash, application/msword, application/vnd.ms-excel, application/vnd.ms-powerpoint, */*");
            conn.setRequestProperty("Accept-Language","zh-CN");
            conn.setRequestProperty("Cache-Control","no-cache");
            conn.setRequestProperty("Connection","close");
            conn.setRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            conn.setDoInput(true);
            conn.connect();

            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
            out.write("");
            out.flush();
            out.close();

            int code = conn.getResponseCode();
            if (code != 200) {
                throw new Exception("GET请求失败[错误码:"+code+"]");
            } else {
                InputStream in = conn.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(in, encoding));
                String str = br.readLine();
                StringBuilder builder = new StringBuilder();
                while(str!= null){
                    builder.append(str);
                    str = br.readLine();
                }
                return builder.toString();
            }
        } catch (SocketTimeoutException te) {
            System.out.println("网页请求超时....");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            conn.disconnect();
            conn = null;
        }
    }

    public static void printOut(HttpServletResponse response, String jsonResult) {
        response.setContentType("application/json;charset=UTF-8");
        printOut(response, jsonResult, "UTF-8");
    }

    public static boolean isAvailable(String urlStr) {
        if (urlStr == null || urlStr.length() <= 0) 	return false;
        int TOTAL_REPEAT_COUNT = 3;

        int tryCount = 0;
        do {
            try {
                URL url = new URL(urlStr);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                int state = con.getResponseCode();
                if (state == 200) {
                    return true;
                }
            } catch (Exception ex) {
                System.out.println("URL不可用，连接第 " + (tryCount+1) + " 次");
            }  finally {
                tryCount++;
            }
        } while (tryCount < TOTAL_REPEAT_COUNT);

        return false;
    }

    public static void main(String[] args){
        System.out.println(HttpUtils.isAvailable("http://www.baidu.com"));
    }
}
