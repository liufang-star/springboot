package com.aop.aspect;

import com.alibaba.fastjson.JSON;
import com.aop.annotation.OperationLogDetail;
import com.aop.model.OperationLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * AOP切面类
 */
@Aspect   //切面注解，可以用来配置事务、做日志、权限验证、在用户请求时做一些处理等.
@Component
public class LogAspect {
    private static final Logger LOG = LoggerFactory.getLogger(LogAspect.class);

    /**
     * 定义切点
     * 此处的切点是注解的方式，也可以用包名的方式达到相同的效果
     * '@Pointcut("execution(* com.bowen.service.impl.*.*(..))")'
     */
    //@Pointcut("@annotation(com.bowen.annotation.OperationLogDetail)")
    @Pointcut("execution(* com.aop.controller.*.*(..))")
    public void operationLog(){}


    /**
     * 环绕增强，相当于MethodInterceptor
     */
    @Around("operationLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object res = null;
        long time = System.currentTimeMillis();
        try {
            res =  joinPoint.proceed();
            time = System.currentTimeMillis() - time;
            return res;
        } finally {
            try {
                //方法执行完成后增加日志
                addOperationLog(joinPoint,res,time);
            }catch (Exception e){
                LOG.error("LogAspect 操作失败：" + e.getMessage());
            }
        }
    }

    /**
     * 方法执行完成后增加日志
     * @param joinPoint
     * @param res
     * @param time
     */
    private void addOperationLog(JoinPoint joinPoint, Object res, long time){
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        OperationLog operationLog = new OperationLog();
        operationLog.setRunTime(time);
        operationLog.setReturnValue(JSON.toJSONString(res));
        operationLog.setId(UUID.randomUUID().toString());
        operationLog.setArgs(JSON.toJSONString(joinPoint.getArgs()));
        operationLog.setCreateTime(new Date());
        operationLog.setMethod(signature.getDeclaringTypeName() + "." + signature.getName());
        operationLog.setUserId("#{currentUserId}");
        operationLog.setUserName("#{currentUserName}");
        OperationLogDetail annotation = null;
        try {
            //获取抽象方法
            Method method = signature.getMethod();
            //获取当前类的对象
            Class<?> clazz = joinPoint.getTarget().getClass();
            //获取当前类有 OperationLogDetail 注解的方法
            method = clazz.getMethod(method.getName(), method.getParameterTypes());
            annotation = method.getAnnotation(OperationLogDetail.class);
        } catch (Exception e) {
            LOG.error("获取当前类有 OperationLogDetail 注解的方法 异常",e);
        }
        if(annotation != null){
            operationLog.setLevel(annotation.level());
            operationLog.setDescribe(annotation.detail());
            //operationLog.setDescribe(getDetail((signature).getParameterNames(),joinPoint.getArgs(),annotation));
            operationLog.setOperationType(annotation.operationType().getValue());
            operationLog.setOperationUnit(annotation.operationUnit().getValue());
        }
        //TODO 这里保存日志
        LOG.info("记录日志:" + operationLog.toString());
        //operationLogService.insert(operationLog);
    }

    /**
     * 对当前登录用户和占位符处理
     * @param argNames 方法参数名称数组
     * @param args 方法参数数组
     * @param annotation 注解信息
     * @return 返回处理后的描述
     */
    @Deprecated
    private String getDetail(String[] argNames, Object[] args, OperationLogDetail annotation){

        Map<Object, Object> map = new HashMap<>(4);
        for(int i = 0;i < argNames.length;i++){
            map.put(argNames[i],args[i]);
        }

        String detail = annotation.detail();
        try {
            detail = "'" + "#{currentUserName}" + "'=》" + annotation.detail();
            for (Map.Entry<Object, Object> entry : map.entrySet()) {
                Object k = entry.getKey();
                Object v = entry.getValue();
                detail = detail.replace("{{" + k + "}}", JSON.toJSONString(v));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return detail;
    }

    @Before("operationLog()")
    public void doBeforeAdvice(JoinPoint joinPoint){
        LOG.info("进入方法前执行.....");
    }

    /**
     * 处理完请求，返回内容
     * @param ret
     */
    @AfterReturning(returning = "ret", pointcut = "operationLog()")
    public void doAfterReturning(Object ret) {
        LOG.info("方法的返回值 : " + ret);
    }

    /**
     * 后置异常通知
     */
    @AfterThrowing("operationLog()")
    public void throwss(JoinPoint jp){
        LOG.info("方法异常时执行.....");
    }

    /**
     * 后置最终通知,final增强，不管是抛出异常或者正常退出都会执行
     */
    @After("operationLog()")
    public void after(JoinPoint jp){
        LOG.info("方法最后执行.....");
    }

}
