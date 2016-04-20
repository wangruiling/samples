package com.bluejean.modules.utils;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.invoke.MethodHandles;

/**
 * @author: wangrl
 * @Date: 2016-01-19 15:52
 */
public class Qiniu {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final String ACCESS_KEY = "-gEfxGXICjFwHMb7yK2jZ01pNSm87dvCLldPU5tT";
    private static final String SECRET_KEY = "JkP6Bx9cra9TnNeYc_lKyveyQjA6R2T2bWU-62s0";
    Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);

    public static void main(String[] args) {
        Qiniu qiniu = new Qiniu();
        String token = qiniu.getUpToken0("lanniuzai");
        System.out.println(qiniu.getUpToken0("lanniuzai"));
        StringMap map = new StringMap();
        map.put("returnUrl", "http://img2.lanniuzai.com/蓝牛仔-FCR-456182_3.jpg");
        qiniu.put(new File("D:/simephoto/Upload_batch_04_11_2015/FCR-456182_3.jpg"), "蓝牛仔-FCR-456182_3.jpg", token, null, "image/jpeg", false);

    }

    // 简单上传，使用默认策略
    private String getUpToken0(String bucket){
        return auth.uploadToken(bucket);
    }

    // 覆盖上传
    private String getUpToken1(String bucket, String key){
        return auth.uploadToken(bucket, key);
    }

    // 设置指定上传策略
    private String getUpToken2(String bucket){
        return auth.uploadToken(bucket, null, 3600, new StringMap()
                .put("callbackUrl", "call back url").putNotEmpty("callbackHost", "")
                .put("callbackBody", "key=$(key)&hash=$(etag)"));
    }

    // 设置预处理、去除非限定的策略字段
    private String getUpToken3(){
        return auth.uploadToken("bucket", null, 3600, new StringMap()
                .putNotEmpty("persistentOps", "").putNotEmpty("persistentNotifyUrl", "")
                .putNotEmpty("persistentPipeline", ""), true);
    }


    /**
     * 上传数据
     * @param data     上传的数据 byte[]、File、filePath
     * @param key      上传数据保存的文件名
     * @param token    上传凭证
     * @param params   自定义参数，如 params.put("x:foo", "foo")
     * @param mime     指定文件mimetype
     * @param checkCrc 是否验证crc32
     * @return
     * @throws QiniuException
     */
    public Response put(File data, String key, String token, StringMap params, String mime, boolean checkCrc) {
        UploadManager uploadManager = new UploadManager();
        try {
            Response response = uploadManager.put(data, key, token, params, mime, checkCrc);
            if (response.isOK()) {
                System.out.println("ok");
            }
            System.out.println(response.toString());
            System.out.println("-----------------------------------");
            System.out.println(response.bodyString());
        } catch (QiniuException e) {
            Response r = e.response;
            // 请求失败时简单状态信息
            logger.error(r.toString());
            try {
                // 响应的文本信息
                logger.error(r.bodyString());
            } catch (QiniuException e1) {
                //ignore
            }
        }
        return null;
    }
}
