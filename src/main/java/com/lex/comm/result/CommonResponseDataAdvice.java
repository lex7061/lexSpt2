package com.lex.comm.result;

import com.alibaba.fastjson.JSONObject;
import com.lex.comm.annotation.IgnoreResponseAdvice;
import com.lex.comm.result.Result;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 描述：全局controller返回前的数据处理
 * 创建人：lex
 * 创建时间：2020-09-17 13:20
 * 更新人：
 * 更新时间：
 */
@RestControllerAdvice
public class CommonResponseDataAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> converterType) {
        // 检查class是否存在注解IgnoreResponseAdvice，存在则按原始数据返回
        if (methodParameter.getDeclaringClass().isAnnotationPresent(IgnoreResponseAdvice.class)) {
            return false;
        }
        return null == methodParameter.getMethod() || !methodParameter.getMethod().isAnnotationPresent(IgnoreResponseAdvice.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType selectedContentType
            , Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // 返回值为 Object 类型  并且返回为空是  AbstractMessageConverterMethodProcessor#writeWithMessageConverters 方法
        // 无法触发调用本类的 beforeBodyWrite 处理，开发在 Controller 尽量避免直接使用 Object 类型返回。
        // o is null -> return response
        if (null == body) {
            // 当 o 返回类型为 string 并且为null会出现 java.lang.ClassCastException: Result cannot be cast to java.lang.String
            if ("java.lang.String".equals(methodParameter.getParameterType().getName()))
                return JSONObject.toJSONString(Result.ofSuccess());
            return Result.ofSuccess();
        } else if (body instanceof Result) return body; // o is instanceof ConmmonResponse -> return o
            // string 特殊处理 java.lang.ClassCastException: Result cannot be cast to java.lang.String
        else if (body instanceof String) return JSONObject.toJSONString(Result.ofSuccess(body));
        else return Result.ofSuccess(body);
    }

}
