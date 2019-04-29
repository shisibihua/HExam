package com.library.bexam.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * http请求工具类
 * @author  caoqian
 * @date 2018/11/13
 */
public class HttpServerUtil {
    private static final Logger logger = LoggerFactory.getLogger(HttpServerUtil.class);
    private static OkHttpClient okClient=new OkHttpClient();
    /**
     * 请求连接服务接口
     * @param url 链接url
     * @param params 请求参数
     * post请求
     * @return
     */
    public static JSONObject sendPost(String url, Map<String,String> params){
        JSONObject re_value =new JSONObject();
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                ,JSON.toJSONString(params));
        Request build = new Request.Builder().url(url).post(requestBody).build();
        return getRequestResult(okClient,build,re_value,url);
    }
    /**
     * 请求连接服务接口
     * @param url 链接url
     * @param params 请求参数
     * get请求
     * @return
     */
    public static JSONObject sendGet(String url, Map<String,Object> params){
        JSONObject re_value =new JSONObject();
        Request build = new Request.Builder().url(url+"?"+convertParams(params)).build();
        return getRequestResult(okClient,build,re_value,url);
    }
    private static JSONObject getRequestResult(OkHttpClient okClient,Request build,JSONObject value,String url){
        Call call = okClient.newCall(build);
        Response response = null;
        try {
            response = call.execute();
            if (response.isSuccessful()) {
                ResponseBody response1 = response.body();
                String str = response1.string();
                value = JSONObject.parseObject(str);
                logger.debug("请求连接服务连接成功，url为："+url);
            }else{
                logger.error("请求连接服务失败，url为："+url);
            }
        } catch (IOException e) {
            logger.error("请求连接服务异常,url为："+url,e);
        }
        return value;
    }
    private static String convertParams(Map<String,Object> params){
        String strParams="";
        StringBuffer stringBuffer=new StringBuffer();
        if(params!=null && !params.isEmpty()){
            Iterator iterator=params.keySet().iterator();
            while (iterator.hasNext()){
                String keyStr=(String)iterator.next();
                stringBuffer.append(keyStr).append("=").append(params.get(keyStr)).append("&");
            }
        }
        strParams=stringBuffer.toString();
        if(strParams.endsWith("&")){
            strParams=strParams.substring(0,strParams.lastIndexOf("&"));
        }
        return strParams;
    }
}
