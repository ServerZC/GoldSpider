package cn.wolfshadow.gs.common.util;

import lombok.SneakyThrows;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.springframework.util.StringUtils;

import javax.net.ssl.SSLContext;
import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class HttpUtil {

    private static final String DEFAULT_CHARACTER_SET = "UTF-8";

    @SneakyThrows
    public static InputStream getStream(String url){
        //初始化HttpClient
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建HttpGet
        HttpGet httpGet = new HttpGet(url);
        //发起请求，获取response对象
        CloseableHttpResponse response = httpClient.execute(httpGet);
        return response.getEntity().getContent();
    }

    /**
     * Http协议GET请求
     * @param url
     * @return
     * @throws Exception
     */
    @SneakyThrows
    public static String httpGet(String url){
        return httpGet(url,DEFAULT_CHARACTER_SET);
    }
    @SneakyThrows
    public static String httpGet(String url, String characterSet){
        if (StringUtils.isEmpty(characterSet)) characterSet = DEFAULT_CHARACTER_SET;
        //初始化HttpClient
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建HttpGet
        HttpGet httpGet = new HttpGet(url);
        //发起请求，获取response对象
        CloseableHttpResponse response = httpClient.execute(httpGet);
        //获取请求状态码
        //response.getStatusLine().getStatusCode();
        //获取返回数据实体对象
        HttpEntity entity = response.getEntity();
        //转为字符串
        String result = EntityUtils.toString(entity,characterSet);
        return result;
    }

    /**
     * Http协议Post请求
     * @param url
     * @param json
     * @return
     * @throws Exception
     */
    @SneakyThrows
    public static String httpPost (String url, String json){
        //初始HttpClient
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建Post对象
        HttpPost httpPost = new HttpPost(url);
        //设置Content-Type
        httpPost.setHeader("Content-Type","application/json");
        //写入JSON数据
        httpPost.setEntity(new StringEntity(json));
        //发起请求，获取response对象
        CloseableHttpResponse response = httpClient.execute(httpPost);
        //获取请求码
        //response.getStatusLine().getStatusCode();
        //获取返回数据实体对象
        HttpEntity entity = response.getEntity();
        //转为字符串
        String result = EntityUtils.toString(entity,"UTF-8");
        return result;

    }

    @SneakyThrows
    public static String httpsGet(String url){
        CloseableHttpClient hp = createSSLClientDefault();
        HttpGet hg = new HttpGet(url);
        CloseableHttpResponse response = hp.execute(hg);
        HttpEntity entity = response.getEntity();
        String content = EntityUtils.toString(entity,"UTF-8");
        hp.close();
        return content;
    }

    @SneakyThrows
    public static String httpsPost(String url, String json){

        CloseableHttpClient hp = createSSLClientDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type","application/json");
        httpPost.setEntity(new StringEntity(json));
        CloseableHttpResponse response = hp.execute(httpPost);
        HttpEntity entity = response.getEntity();
        String content = EntityUtils.toString(entity,"UTF-8");
        hp.close();
        return content;
    }


    public static CloseableHttpClient createSSLClientDefault() throws Exception{

        SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy(){
            //信任所有
            public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                return true;
            }
        }).build();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
        return HttpClients.custom().setSSLSocketFactory(sslsf).build();
    }


}
