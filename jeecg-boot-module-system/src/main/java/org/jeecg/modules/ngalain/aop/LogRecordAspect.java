package org.jeecg.modules.ngalain.aop;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;;


// 暂时注释掉，提高系统性能
@Aspect   //定义一个切面
@Configuration
public class LogRecordAspect {
private static final Logger logger = LoggerFactory.getLogger(LogRecordAspect.class);


    // 定义切点Pointcut
    @Pointcut("execution(public * org.jeecg.modules.*.*.*Controller.*(..))")
    public void excudeService() {
    }

    @Around("excudeService()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();

        String method = request.getMethod();
        Object[] args = pjp.getArgs();
        String params="";
        try{
            params = new Gson().toJson(args[0]);
        }catch (Exception e){

        }
        String queryString = request.getQueryString();
        logger.info("地址:"+request.getRequestURI());
        logger.info("类型:"+method);
        logger.info("参数:"+queryString);
        logger.info("params:"+params);
        // result的值就是被拦截方法的返回值
        Object result = pjp.proceed();
        return result;
    }
}