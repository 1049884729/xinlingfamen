package com.xinlingfamen.app.qiniu;

/**
 * Created by xuff on 2016/9/1.
 */

import java.io.UnsupportedEncodingException;

import com.qiniu.android.utils.StringMap;

public final class Auth
{
    
    /**
     * 上传策略，参数规格详见
     * <p/>
     * http://developer.qiniu.com/docs/v6/api/reference/security/put-policy.html
     */
    private static final String[] policyFields =
        new String[] {"callbackUrl", "callbackBody", "callbackHost", "callbackBodyType", "callbackFetchKey",
            
            "returnUrl", "returnBody",
            
            "endUser", "saveKey", "insertOnly",
            
            "detectMime", "mimeLimit", "fsizeLimit", "fsizeMin",
            
            "persistentOps", "persistentNotifyUrl", "persistentPipeline",
            
            "deleteAfterDays",};
    
    private static final String[] deprecatedPolicyFields = new String[] {"asyncOps",};
    

    

    private static void copyPolicy(final StringMap policy, StringMap originPolicy, final boolean strict)
    {
        if (originPolicy == null)
        {
            return;
        }
        originPolicy.forEach(new StringMap.Consumer()
        {
            @Override
            public void accept(String key, Object value)
            {
                if (QiNiuStringUtils.inStringArray(key, deprecatedPolicyFields))
                {
                    throw new IllegalArgumentException(key + " is deprecated!");
                }
                if (!strict || QiNiuStringUtils.inStringArray(key, policyFields))
                {
                    policy.put(key, value);
                }
            }
        });
    }

    

    private String utf8Bytes(String data)
    {
        String str;
        try
        {
            str = new String(data.getBytes(), "utf-8");
        }
        catch (UnsupportedEncodingException uee)
        {
            str = new String(data.getBytes());
        }
        return str;
    }
    


    
    public String signWithData(String data)
    {
        return signWithData(utf8Bytes(data));
    }
    
    /**
     * 下载签名
     *
     * @param baseUrl 待签名文件url，如 http://img.domain.com/u/3.jpg 、 http://img.domain.com/u/3.jpg?imageView2/1/w/120
     * @return
     */
    public String privateDownloadUrl(String baseUrl)
    {
        return privateDownloadUrl(baseUrl, 3600);
    }
    
    /**
     * 下载签名
     *
     * @param baseUrl 待签名文件url，如 http://img.domain.com/u/3.jpg 、 http://img.domain.com/u/3.jpg?imageView2/1/w/120
     * @param expires 有效时长，单位秒。默认3600s
     * @return
     */
    public String privateDownloadUrl(String baseUrl, long expires)
    {
        long deadline = System.currentTimeMillis() / 1000 + expires;
        return privateDownloadUrlWithDeadline(baseUrl, deadline);
    }
    
    String privateDownloadUrlWithDeadline(String baseUrl, long deadline)
    {
        StringBuilder b = new StringBuilder();
        b.append(baseUrl);
        int pos = baseUrl.indexOf("?");
        if (pos > 0)
        {
            b.append("&e=");
        }
        else
        {
            b.append("?e=");
        }
        b.append(deadline);
//        String token = sign(b.toString());
//        b.append("&token=");
//        b.append(token);
        return b.toString();
    }
    
    /**
     * scope = bucket 一般情况下可通过此方法获取token
     *
     * @param bucket 空间名
     * @return 生成的上传token
     */
    public String uploadToken(String bucket)
    {
        return uploadToken(bucket, null, 3600, null, true);
    }
    
    /**
     * scope = bucket:key 同名文件覆盖操作、只能上传指定key的文件可以可通过此方法获取token
     *
     * @param bucket 空间名
     * @param key key，可为 null
     * @return 生成的上传token
     */
    public String uploadToken(String bucket, String key)
    {
        return uploadToken(bucket, key, 3600, null, true);
    }
    
    /**
     * 生成上传token
     *
     * @param bucket 空间名
     * @param key key，可为 null
     * @param expires 有效时长，单位秒
     * @param policy 上传策略的其它参数，如 new StringMap().put("endUser", "uid").putNotEmpty("returnBody", "")。 scope通过
     *            bucket、key间接设置，deadline 通过 expires 间接设置
     * @return 生成的上传token
     */
    public String uploadToken(String bucket, String key, long expires, StringMap policy)
    {
        return uploadToken(bucket, key, expires, policy, true);
    }
    
    /**
     * 生成上传token
     *
     * @param bucket 空间名
     * @param key key，可为 null
     * @param expires 有效时长，单位秒。默认3600s
     * @param policy 上传策略的其它参数，如 new StringMap().put("endUser", "uid").putNotEmpty("returnBody", "")。 scope通过
     *            bucket、key间接设置，deadline 通过 expires 间接设置
     * @param strict 是否去除非限定的策略字段，默认true
     * @return 生成的上传token
     */
    public String uploadToken(String bucket, String key, long expires, StringMap policy, boolean strict)
    {
        long deadline = System.currentTimeMillis() / 1000 + expires;
        return uploadTokenWithDeadline(bucket, key, deadline, policy, strict);
    }
    
    public String uploadTokenWithDeadline(String bucket, String key, long deadline, StringMap policy, boolean strict)
    {
        // TODO UpHosts Global
        String scope = bucket;
        if (key != null)
        {
            scope = bucket + ":" + key;
        }
        StringMap x = new StringMap();
        copyPolicy(x, policy, strict);
        x.put("scope", scope);
        x.put("deadline", deadline);
        
        String s = Json.encode(x);
        return signWithData(utf8Bytes(s));
    }
    
}
