package com.xinlingfamen.app.core.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.net.http.AndroidHttpClient;

/**
 * Created by xuff on 2016/9/1.
 */
public class HttpClientUtils
{
    
    private static final int CONN_TIME_OUT = 10000;
    
    private static final String PRO_MONTH = "month";
    
    private static final String PRO_TOTAL_SIZE = "totalSize";
    
    private static final String PRO_MONTH_SIZE = "monthSize";
    
    public static final String STREAM_LENGTH = "streamLength";
    
    public static synchronized String setAndroidHttpPost(String url, Map params)
    {
        AndroidHttpClient androidHttpClient = AndroidHttpClient.newInstance("rentbook");
        
        HttpPost post = new HttpPost(url);
        List<NameValuePair> nameValuePairList = new ArrayList();
        
        androidHttpClient.getConnectionManager().closeIdleConnections(20L, TimeUnit.SECONDS);
        try
        {
            Map.Entry entry;
            if (params != null)
            {
                Iterator iter = params.entrySet().iterator();
                while (iter.hasNext())
                {
                    entry = (Map.Entry)iter.next();
                    Object key = entry.getKey();
                    Object val = entry.getValue();
                    NameValuePair nameValuePair = new BasicNameValuePair(String.valueOf(key), String.valueOf(val));
                    nameValuePairList.add(nameValuePair);
                }
                post.setEntity(new UrlEncodedFormEntity(nameValuePairList, "UTF-8"));
            }
            HttpResponse response = androidHttpClient.execute(post);
            if (response.getStatusLine().getStatusCode() == 200)
            {
                String content = EntityUtils.toString(response.getEntity());
                
                return content;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (androidHttpClient != null)
            {
                androidHttpClient.close();
            }
        }
        return null;
    }
    
    public static synchronized String setAndroidHttpGet(String url, Map params)
    {
        AndroidHttpClient androidHttpClient = AndroidHttpClient.newInstance("rentbook");
        StringBuffer buffer = new StringBuffer();
        String keyvalue;
        HttpGet get;
        if (params != null)
        {
            for (Object key : params.entrySet())
            {
                keyvalue = String.valueOf(key);
                buffer.append(keyvalue + "&");
            }
            get = new HttpGet(url + "?" + buffer.substring(0, buffer.length() - 1));
        }
        else
        {
            get = new HttpGet(url);
        }
        androidHttpClient.getConnectionManager().closeIdleConnections(20L, TimeUnit.SECONDS);
        try
        {
            HttpResponse response = androidHttpClient.execute(get);
            if (response.getStatusLine().getStatusCode() == 200)
            {
                String content = EntityUtils.toString(response.getEntity());
                return content;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (androidHttpClient != null)
            {
                androidHttpClient.close();
            }
        }
        return null;
    }
    
    public synchronized InputStream sendRequest(String url)
    {
        HttpURLConnection httpConnection = null;
        try
        {
            int currentSize = 0;
            
            if (url == null)
            {
                return null;
            }
            URL uri = new URL(url);
            httpConnection = (HttpURLConnection)uri.openConnection();
            httpConnection.setRequestProperty("User-Agent", "PacificHttpClient");
            if (currentSize > 0)
            {
                httpConnection.setRequestProperty("RANGE", "bytes=" + currentSize + "-");
            }
            httpConnection.setConnectTimeout(10000);
            httpConnection.setReadTimeout(20000);
            httpConnection.setRequestProperty("Content-type", "text/html;charset=UTF-8");
            
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod("GET");
            httpConnection.setUseCaches(false);
            
            int length = httpConnection.getContentLength();
            System.setProperty("streamLength", length + "");
            
            int requestCode = httpConnection.getResponseCode();
            if (requestCode == 200)
            {
                InputStream in = httpConnection.getInputStream();
                
                return in;
            }
        }
        catch (ProtocolException e)
        {
            e.printStackTrace();
            
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
