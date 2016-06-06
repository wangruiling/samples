package com.bluejean.modules.utils;

import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.lang.invoke.MethodHandles;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 使用spring的restTemplate替代httpclient工具
 * Created by wangrl on 2016/4/21.
 */
public class RestTemplateUtil {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static RestTemplate restTemplate;

    private RestTemplateUtil() {
    }

    static {
        initRestTemplate();
    }

    private static void initRestTemplate() {
        // 长连接保持30秒
        PoolingHttpClientConnectionManager pollingConnectionManager = new PoolingHttpClientConnectionManager(30, TimeUnit.SECONDS);
        // 总连接数
        pollingConnectionManager.setMaxTotal(1000);
        // 同路由的并发数
        pollingConnectionManager.setDefaultMaxPerRoute(1000);

        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        httpClientBuilder.setConnectionManager(pollingConnectionManager);
        // 重试次数，默认是3次，没有开启
        httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(2, true));
        // 保持长连接配置，需要在头添加Keep-Alive
        httpClientBuilder.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy());

//        RequestConfig.Builder builder = RequestConfig.custom();
//        builder.setConnectionRequestTimeout(200);
//        builder.setConnectTimeout(5000);
//        builder.setSocketTimeout(5000);
//
//        RequestConfig requestConfig = builder.build();
//        httpClientBuilder.setDefaultRequestConfig(requestConfig);

        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.16 Safari/537.36"));
        headers.add(new BasicHeader("Accept-Encoding", "gzip,deflate"));
        headers.add(new BasicHeader("Accept-Language", "zh-CN"));
        headers.add(new BasicHeader("Connection", "Keep-Alive"));

        httpClientBuilder.setDefaultHeaders(headers);

        HttpClient httpClient = httpClientBuilder.build();

        // httpClient连接配置，底层是配置RequestConfig
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        // 连接超时
        clientHttpRequestFactory.setConnectTimeout(5000);
        // 数据读取超时时间，即SocketTimeout
        clientHttpRequestFactory.setReadTimeout(5000);
        // 连接不够用的等待时间，不宜过长，必须设置，比如连接不够用时，时间过长将是灾难性的
        clientHttpRequestFactory.setConnectionRequestTimeout(200);
        // 缓冲请求数据，默认值是true。通过POST或者PUT大量发送数据时，建议将此属性更改为false，以免耗尽内存。
        // clientHttpRequestFactory.setBufferRequestBody(false);

        // 添加内容转换器
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        messageConverters.add(new FormHttpMessageConverter());
        //messageConverters.add(new MappingJackson2XmlHttpMessageConverter());
        messageConverters.add(new MappingJackson2HttpMessageConverter());

        restTemplate = new RestTemplate(messageConverters);
        restTemplate.setRequestFactory(clientHttpRequestFactory);
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler());

        logger.info("RestClient初始化完成");
    }

    public static <T> T doGet(String url, Class<T> responseType) {
        ResponseEntity<T> responseEntity = getRestTemplate().getForEntity(url, responseType);
        if (responseEntity != null) {
            logger.info("statusCode:{}", responseEntity.getStatusCode());
            return responseEntity.getBody();
        } else {
            return null;
        }
    }

    /**
     * 注意：delete方法没有返回值
     * @param url
     */
    public static void doDelete(String url) {
        getRestTemplate().delete(url);
    }

    public static <T> T doPost(String url, Class<T> responseType){
        return doPost(url, null, responseType);
    }

    public static <T> T doPost(String url, Object requestBody, Class<T> responseType) {
        ResponseEntity<T> responseEntity = getRestTemplate().postForEntity(url, requestBody, responseType);
        if (responseEntity != null) {
            logger.info("statusCode:{}", responseEntity.getStatusCode());
            return responseEntity.getBody();
        } else {
            return null;
        }
    }

    public static <T> T doPut(String url, Class<T> responseType) {
        return doPut(url, responseType, null);
    }

    public static <T> T doPut(String url, Class<T> responseType, Map<String, ?> uriVariables) {
        ResponseEntity<T> responseEntity;
        if (uriVariables == null || uriVariables.size() <= 0) {
            responseEntity = getRestTemplate().exchange(url, HttpMethod.PUT, null, responseType);
        } else {
            responseEntity = getRestTemplate().exchange(url, HttpMethod.PUT, null, responseType, uriVariables);
        }

        if (responseEntity != null) {
            logger.info("statusCode:{}", responseEntity.getStatusCode());
            return responseEntity.getBody();
        } else {
            return null;
        }
    }

    public static RestTemplate getRestTemplate() {
        if (restTemplate == null) {
            initRestTemplate();
        }
        return restTemplate;
    }
}
