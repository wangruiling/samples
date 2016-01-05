package com.bluejean.modules.utils;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.*;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.CodingErrorAction;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: wangrl
 * @Date: 2014-08-08 15:59
 */
public class HttpClientUtils {
    private static CloseableHttpClient httpClient;
    /** 连接超时时间，由bean factory设置，缺省为8秒钟 */
    private static final int DEFAULT_CONNECTION_TIMEOUT = 8 * 1000;

    /** 回应超时时间, 由bean factory设置，缺省为30秒钟 */
    private static final  int DEFAULT_SOCKET_TIMEOUT = 30 * 1000;

    /** 闲置连接超时时间, 由bean factory设置，缺省为60秒钟 */
    private int                        defaultIdleConnTimeout              = 60000;

    private int                        defaultMaxConnPerHost               = 30;

    private int                        defaultMaxTotalConn                 = 80;

    /** 默认等待HttpConnectionManager返回连接超时（只有在达到最大连接数时起作用）：1秒*/
    private static final long          defaultHttpConnectionManagerTimeout = 3 * 1000;

    private static final int POOL_SIZE = 20;

    private static Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

    public static CloseableHttpClient getHttpClient() {
        return httpClient;
    }

    private HttpClientUtils() {
    }


    static {
        initHttpClient();
    }

    public static void initHttpClient() {
        // 创建包含connection pool与超时设置的client
        try {
            SSLContext sslContext = SSLContexts.custom().useTLS().build();
            sslContext.init(null,
                    new TrustManager[] { new X509TrustManager() {

                        public X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }

                        public void checkClientTrusted(
                                X509Certificate[] certs, String authType) {
                        }

                        public void checkServerTrusted(
                                X509Certificate[] certs, String authType) {
                        }
                    }}, null);
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.INSTANCE)
                    .register("https", new SSLConnectionSocketFactory(sslContext))
                    .build();

            PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);

            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(DEFAULT_SOCKET_TIMEOUT)
                    .setConnectTimeout(DEFAULT_CONNECTION_TIMEOUT).build();

            // Create socket configuration
            SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).build();
            connManager.setDefaultSocketConfig(socketConfig);

            // Create message constraints
            MessageConstraints messageConstraints = MessageConstraints.custom()
                    .setMaxHeaderCount(20000)
                    .setMaxLineLength(20000)
                    .build();

            // Create connection configuration
            ConnectionConfig connectionConfig = ConnectionConfig.custom()
                    .setMalformedInputAction(CodingErrorAction.IGNORE)
                    .setUnmappableInputAction(CodingErrorAction.IGNORE)
                    .setCharset(Consts.UTF_8)
                    .setMessageConstraints(messageConstraints)
                    .build();
            connManager.setDefaultConnectionConfig(connectionConfig);
            connManager.setMaxTotal(200);
            connManager.setDefaultMaxPerRoute(20);

            httpClient = HttpClients.custom().setMaxConnTotal(POOL_SIZE)
                    .setMaxConnPerRoute(POOL_SIZE)
                    .setDefaultRequestConfig(requestConfig)
                    .setConnectionManager(connManager)
                    .build();
        } catch (KeyManagementException e) {
            logger.error("KeyManagementException", e);
        } catch (NoSuchAlgorithmException e) {
            logger.error("NoSuchAlgorithmException", e);
        }
    }

    public static String doGet(CloseableHttpClient httpClient1, String url) {
        String result = null;
        HttpGet httpGet = new HttpGet(url);
        try {
            CloseableHttpResponse response = httpClient1.execute(httpGet);
            result = EntityUtils.toString(response.getEntity(), Charset.forName("utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String doPost(CloseableHttpClient httpClient1, String url, HttpEntity entity) {
        HttpPost httpPost = new HttpPost(url);
        String result = null;
        try {

            httpPost.setEntity(entity);
            CloseableHttpResponse response = httpClient1.execute(httpPost);
            result = EntityUtils.toString(response.getEntity(), Charset.forName("utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 关闭httpclient连接
     */
    public static void destroyApacheHttpClient() {
        try {
            if (logger.isDebugEnabled()) {
                logger.debug("httpclient start close");
            }
            httpClient.close();
        } catch (IOException e) {
            logger.error("httpclient close fail", e);
        }
    }
}
