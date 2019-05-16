package com.library.bexam.flter;

import com.alibaba.fastjson.JSON;
import com.library.bexam.common.pojo.model.Result;
import com.library.bexam.util.RedisUtil;
import com.library.bexam.common.util.TipsMessage;
import com.library.bexam.dao.UserDao;
import jodd.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 过滤器，验证接口安全性
 * @author caoqian
 * @date 20181215
 */
//TODO:测试时暂时屏蔽，不走过滤器
//@Component
public class AuthFilter implements Filter {
    private static final String OPTIONS = "OPTIONS";
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        Result result = new Result();
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if(request.getMethod().equals(OPTIONS)){
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "OPTIONS,GET,POST,DELETE,PUT");
            response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me, " +
                    "X-Token,TPD-SecretID,TPD-CallBack-Auth");
            filterChain.doFilter(request,response);
        }else {
            //验证token
            String token=request.getHeader("X-Token")==null?"":request.getHeader("X-Token");
            //接口地址
            String urlPath=request.getServletPath();
            if (!StringUtil.isEmpty(token) || urlPath.contains("user/login") || urlPath.contains("user/getUserByToken") ||
                    urlPath.contains("druid")){
                //登录接口无需验证
                if(urlPath.contains("user/login") || urlPath.contains("user/getUserByToken") || urlPath.contains("druid")){
                    //过滤通过
                    filterChain.doFilter(request, response);
                }else{
                    if(checkUserIsValid(token)){
                        filterChain.doFilter(request, response);
                    }else{
                        result.setCode(TipsMessage.FAILED_CODE);
                        result.setMsg(TipsMessage.NO_AUTHORITY);
                        send(response, result);
                    }
                }
            }else {
                //header无token或userName
                result.setCode(TipsMessage.FAILED_CODE);
                result.setMsg(TipsMessage.NO_HEADER);
                send(response, result);
            }
        }
    }

    /**
     * 根据token验证用户是否有接口访问权限
     * @param token         用户token
     * @return
     */
    private boolean checkUserIsValid(String token) {
        boolean valid=false;
        //获取redis中的token
        String userInfo = RedisUtil.getString(token);
        if (!StringUtil.isEmpty(userInfo)){
            valid=true;
        }else{
            valid=false;
        }
        return valid;
    }

    private void send(HttpServletResponse response, Object result) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(JSON.toJSONString(result));
        response.getWriter().flush();
        response.getWriter().close();
    }
}
