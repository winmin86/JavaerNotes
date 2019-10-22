package xyz.up123.springboot.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.thymeleaf.util.StringUtils;
import xyz.up123.springboot.dao.datasource1.WebLogDao;
import xyz.up123.springboot.domain.WebLog;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 接口日志切面
 *
 * @author ganchaoyang
 */
@Aspect
@Component
@Order(100)
public class WebLogAspect {

    private Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

    @Autowired
    private WebLogDao webLogDao;

    private static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<>();

    private static final String START_TIME = "startTime";

    private static final String REQUEST_PARAMS = "requestParams";

    @Pointcut("execution(* xyz.up123.springboot.controller.*.*(..))")
    public void webLog() {}

    @Before(value = "webLog()&& @annotation(controllerWebLog)")
    public void doBefore(JoinPoint joinPoint, ControllerWebLog controllerWebLog) {
        // 开始时间。
        long startTime = System.currentTimeMillis();
        Map<String, Object> threadInfo = new HashMap<>();
        threadInfo.put(START_TIME, startTime);
        // 请求参数。
        /*StringBuilder requestStr = new StringBuilder();
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            for (Object arg : args) {
                requestStr.append(arg.toString());
            }
        }*/
        threadInfo.put(REQUEST_PARAMS, JSON.toJSON(controllerWebLog.pamams()));
        threadLocal.set(threadInfo);




        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = ( MethodSignature ) signature;
        Method currentMethod = methodSignature.getMethod();
        ControllerWebLog webLog = currentMethod.getAnnotation( ControllerWebLog.class );
        if ( webLog.pamams() != null ) {
            HttpServletRequest httpServletRequest = ( (ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

            JSONObject jsonObject = new JSONObject();
            Enumeration<String> paramsMapKeys = httpServletRequest.getParameterNames();
            while( paramsMapKeys.hasMoreElements() ){
                String key = paramsMapKeys.nextElement();
                jsonObject.put( key.toLowerCase() , httpServletRequest.getParameter( key ) );
            }

            if ( joinPoint.getArgs().length > 0 ) {
                Stream.iterate(0 , i -> i + 1 ).limit( joinPoint.getArgs().length ).forEach(i -> {
                    Object params = joinPoint.getArgs()[i];

                    if( Objects.nonNull(params) ) {
                        if ( params.getClass().getTypeName().equals( "java.util.ArrayList" ) ) {
                            jsonObject.put( "arrayIds" , JSONObject.toJSONString( params ) );
                        } else if ( params.getClass().getTypeName().equals( "java.lang.Integer" ) ){
                            jsonObject.put( "P" + i , params );
                        } else {
                            Method method[] = params.getClass().getMethods();
                            Arrays.stream( method ).forEach(currentMethods -> {
                                Class<?>[] currentMethodParameterTypes = currentMethod.getParameterTypes();
                                if (currentMethodParameterTypes != null && currentMethodParameterTypes.length > 0) {
                                    Arrays.stream(currentMethodParameterTypes).forEach( currentParam -> {
                                        if ( currentMethods.getName().startsWith("get") ) {
                                            if ( currentMethods.getReturnType().getTypeName().equals( "java.util.ArrayList" ) ) {

                                            } else {
                                                try {
                                                    jsonObject.put( currentMethods.getName().substring(3).toLowerCase() , params.getClass().getMethod( currentMethods.getName() ).invoke( params, null ) );
                                                } catch (IllegalAccessException illegalAccessException) {
                                                    illegalAccessException.printStackTrace();
                                                } catch (InvocationTargetException invocationTargetException) {
                                                    invocationTargetException.printStackTrace();
                                                } catch (NoSuchMethodException noSuchMethodException) {
                                                    noSuchMethodException.printStackTrace();
                                                }
                                            }
                                        }
                                    });
                                }
                            });
                        }
                    }
                });
            }


            List<String> params = Arrays.stream( webLog.pamams() ).map(sss -> {
                String values = jsonObject.getString( sss.toLowerCase() );
                return StringUtils.isEmpty( values ) ? "-" : values ;
            }).collect(Collectors.toList());

            System.out.println("=================");
            System.out.println(params);
            System.out.println("=================");
        }


        logger.info("{}接口开始调用:requestData={}", controllerWebLog.name(), threadInfo.get(REQUEST_PARAMS));
    }

    @AfterReturning(value = "webLog()&& @annotation(controllerWebLog)", returning = "res")
    public void doAfterReturning(ControllerWebLog controllerWebLog, Object res) {
        Map<String, Object> threadInfo = threadLocal.get();
        long takeTime = System.currentTimeMillis() - (long) threadInfo.getOrDefault(START_TIME, System.currentTimeMillis());
        if (controllerWebLog.intoDb()) {
            insertResult(controllerWebLog.name(), JSON.toJSONString(threadInfo.getOrDefault(REQUEST_PARAMS, "")),
                    JSON.toJSONString(res), takeTime);
        }
        threadLocal.remove();
        logger.info("{}接口结束调用:耗时={}ms,result={}", controllerWebLog.name(),
                takeTime, res);
    }

    @AfterThrowing(value = "webLog()&& @annotation(controllerWebLog)", throwing = "throwable")
    public void doAfterThrowing(ControllerWebLog controllerWebLog, Throwable throwable) {
        Map<String, Object> threadInfo = threadLocal.get();
        if (controllerWebLog.intoDb()) {
            insertError(controllerWebLog.name(), JSON.toJSON(threadInfo.getOrDefault(REQUEST_PARAMS, "")).toString(),
                    throwable);
        }
        threadLocal.remove();
        logger.error("{}接口调用异常，异常信息{}",controllerWebLog.name(), throwable);
    }


    public void insertResult(String operationName, String requestStr, String responseStr, long takeTime) {
        WebLog webLog = new WebLog();
        webLog.setCreateTime(new Date());
        webLog.setError(false);
        webLog.setOperationName(operationName);
        webLog.setRequest(requestStr);
        webLog.setResponse(responseStr);
        webLog.setTaketime(takeTime);
        //webLogDao.insert(webLog);
        printLog(webLog);
    }


    public void insertError(String operationName, String requestStr, Throwable throwable) {
        WebLog webLog = new WebLog();
        webLog.setCreateTime(new Date());
        webLog.setError(true);
        webLog.setOperationName(operationName);
        webLog.setRequest(requestStr);
        webLog.setStack(throwable.getStackTrace().toString());
        //webLogDao.insert(webLog);
        printLog(webLog);
    }

    public void printLog(WebLog webLog) {
        System.out.println("====================================");
        System.out.println(JSON.toJSON(webLog));
        System.out.println("====================================");
    }

}
