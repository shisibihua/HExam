package com.library.bexam.util;

import com.alibaba.fastjson.JSONObject;
import com.library.bexam.common.util.ConfigUtil;
import com.library.bexam.common.util.DateUtil;
import com.library.bexam.common.util.HttpServerUtil;
import org.apache.commons.codec.digest.DigestUtils;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 学科网接口调用类
 * @author caoqian
 * @date 20181211
 */
public class DocCenterApi
{
    private final static String ACCESS_URL="http://dev.zxxk.com/api/";
    private static String ACCESS_KEY="";
    private static String ACCESS_SECRET="";
    static {
        ACCESS_KEY=ConfigUtil.getInstance().getPropertyValue("ACCESS_KEY");
        ACCESS_SECRET=ConfigUtil.getInstance().getPropertyValue("ACCESS_SECRET");
    }
    private static DocCenterApi INSTANCE=null;
    public static DocCenterApi getInstance(){
        if(null==INSTANCE){
            synchronized (DocCenterApi.class){
                if(null==INSTANCE){
                    INSTANCE=new DocCenterApi();
                }
            }
        }
        return INSTANCE;
    }
    /**
     * 调用接口
     * @param methodName  学科网api方法名
     * @param paramMap    参数map
     * @return
     */
    public JSONObject searchApiDoc(String methodName,Map<String, Object> paramMap){
        String apiUrl=ACCESS_URL + methodName;
        //公共参数
        paramMap.put("sign",getSign(paramMap));
        paramMap.put("accessKey", ACCESS_KEY);
        Date now = new Date();
        String currentStamp= DateUtil.formatDatetime(now);
        paramMap.put("timestamp", currentStamp);
        return HttpServerUtil.sendGet(apiUrl,paramMap);
    }
/*
   public static void main(String[] args) {
       Map<String, Object> paramMap = new HashMap();
       paramMap.put("textbookId",592);
       JSONObject resultJson=DocCenterApi.getInstance().searchApiDoc("getBookNodeList",paramMap);
       System.out.println(resultJson);
   }*/

    /**
     * 生成签名
     * @return
     */
    private String getSign(Map<String, Object> paramMap){
        // 对参数名进行字典排序
        String[] keyArray = paramMap.keySet().toArray(new String[0]);
        Arrays.sort(keyArray);
        // 拼接有序的参数名-值串
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ACCESS_KEY);
        StringBuilder needEncryptString = new StringBuilder();
        for (String key : keyArray){
            needEncryptString.append(key).append("=").append(paramMap.get(key)).append("&");
        }
        needEncryptString.append("accessSecret").append("=").append(ACCESS_SECRET);
        return DigestUtils.sha1Hex(needEncryptString.toString());
    }
}
