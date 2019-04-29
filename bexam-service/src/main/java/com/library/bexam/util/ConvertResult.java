package com.library.bexam.util;

import com.library.bexam.common.pojo.model.Result;
import com.library.bexam.common.util.ParamUtil;
import com.library.bexam.common.util.TipsMessage;
import com.library.bexam.serviceManager.SQLFileAnnotation;
import org.springframework.stereotype.Service;

/**
 * 整理接口结果，返回Result
 * @author caoqian
 * @date 20180821
 */
@Service
@SQLFileAnnotation(SQLFileName="bexam.sql")
public class ConvertResult {

    /**
     * 返回成功结果
     * @param result_obj   返回的结果
     * @return
     */
    public static Result getSuccessResult(Object result_obj){
        Result result=getResult(result_obj);
        result.setCode(Result.Code.Success.value());
        return result;
    }
    /**
     * 返回参数错误结果
     * @return
     */
    public static Result getParamErrorResult(String errorMsg){
        return getParamErrorResult(errorMsg,null);
    }
    /**
     * 返回参数错误结果
     * @param errorMsg     错误提示
     * @param result_obj   返回的结果
     * @return
     */
    public static Result getParamErrorResult(String errorMsg,Object result_obj){
        Result result=getResult(result_obj);
        result.setCode(Result.Code.ParamError.value());
        if(!ParamUtil.isEmpty(errorMsg)){
            result.setMsg(errorMsg);
        }else{
            result.setMsg(TipsMessage.PARAM_ERROR);
        }
        return result;
    }
    /**
     * 返回内部错误结果
     * @return
     */
    public static Result getErrorResult(String errorMsg){
        return getErrorResult(errorMsg,null);
    }
    /**
     * 返回内部错误结果
     * @param errorMsg     错误提示
     * @param result_obj   返回的结果
     * @return
     */
    public static Result getErrorResult(String errorMsg,Object result_obj){
        Result result=getResult(result_obj);
        result.setCode(Result.Code.UnKnowError.value());
        if(!ParamUtil.isEmpty(errorMsg)){
            result.setMsg(errorMsg);
        }else{
            result.setMsg(TipsMessage.INNER_ERROR_MSG);
        }
        return result;
    }
    private static Result getResult(Object result_obj){
        Result result=new Result();
        if (result_obj != null) {
            if(result_obj instanceof Result) {
                result = (Result) result_obj;
            }else{
                result.setResult(result_obj);
            }
        }else{
            result.setResult(result_obj);
        }
        if(result.getMsg()==null){
            result.setMsg("");
        }
        return result;
    }
}
