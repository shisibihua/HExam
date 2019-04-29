package com.library.bexam.aspect;
import com.library.bexam.util.MyLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 切片，此示例为记录系统日志
 *
 * @author caoqian
 * @date 2018-09-12
 */
@Aspect
@Component
public class RecordAppAspect {

    @Pointcut("@annotation(com.library.bexam.util.MyLog)")
    public void log(){}

    //配置后置返回通知,使用在方法aspect()上注册的切入点
    @AfterReturning("log()")
    public void afterReturn(JoinPoint joinPoint){
        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取切入点所在的方法
        Method method = signature.getMethod();
        //获取操作
        MyLog myLog = method.getAnnotation(MyLog.class);
        if (myLog != null) {
            String[] value = myLog.value().split("-");

        }
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        /*
            System.out.println("URL : " + request.getRequestURL().toString());
            System.out.println("HTTP_METHOD : " + request.getMethod());
            System.out.println("IP : " + request.getRemoteAddr());
            System.out.println("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
            System.out.println("ARGS : " + Arrays.toString(joinPoint.getArgs()));
        */

    }
}
