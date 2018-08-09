package kxjl.demo.openapi.util;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * HTTP工具类
 * Created by alex on 2018/6/22.
 */
public class HttpUtil {
    private int connectionTimeout = 5000;
    private int readTimeout = 5000;
    private String charset = "UTF-8";
    private String contentType = "application/json; charset=" + charset;

    public HttpUtil() {

    }

    /**
     * JSON对象的HTTP请求响应
     *
     * @param url      请求url
     * @param jsonData 请求对象
     * @return 将响应文本转换为JSON对象
     * @throws Exception
     */
    public JSONObject post(String url, JSONObject jsonData) throws Exception {
        String respText = post(url, jsonData.toJSONString());
        return JSONObject.parseObject(respText);
    }

    /**
     * 文本的 HTTP POST请求响应
     *
     * @param url      请求url
     * @param textData 请求文本
     * @return
     * @throws Exception
     */
    public String post(String url, String textData) throws Exception {
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        try {
            //设置HTTP参数
            conn.setConnectTimeout(connectionTimeout);//连接超时
            conn.setReadTimeout(readTimeout); //读取超时
            conn.setRequestMethod("POST");//POST请求
            conn.setRequestProperty("Content-Type", contentType);
            conn.setUseCaches(false);  //不允许缓存
            conn.setDoOutput(true);   //需要输出
            //发送数据
            if (textData != null && textData.length() > 0) {
                conn.setDoOutput(true);//需要输入
                OutputStream outs = conn.getOutputStream();
                try {
                    outs.write(textData.getBytes(charset));
                    outs.flush();
                } finally {
                    if (outs != null) {
                        outs.close();
                    }
                }
            }

            int responseCode = conn.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new IllegalStateException(String.format("unexpected response [%s] %s", responseCode, conn.getResponseMessage()));
            }

            StringBuffer text = new StringBuffer();
            BufferedReader ins = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            try {
                while (ins.ready()) {
                    String line = ins.readLine();
                    if (line == null) {
                        break;
                    }
                    text.append(line);
                }
            } finally {
                if (ins != null) {
                    ins.close();
                }
            }


            return text.toString();
        } finally {
            conn.disconnect();
        }
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
