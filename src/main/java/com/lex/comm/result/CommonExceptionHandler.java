package com.lex.comm.result;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.lex.comm.result.CommonErrorCode;
import com.lex.comm.annotation.IgnoreResponseAdvice;
import com.lex.comm.result.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.List;

/**
 * {@link RestControllerAdvice} 基础全局异常处理
 *
 * @author <a href="mailto:yaoonlyi@gmail.com">purgeyao</a>
 * @since 1.0.0
 */
@RestControllerAdvice
public class CommonExceptionHandler {
    private static final Log log = LogFactory.get();

    /**
     * NoHandlerFoundException 404 异常处理
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = NoHandlerFoundException.class)
    public Result<Object> handlerNoHandlerFoundException(NoHandlerFoundException e) throws Throwable {
        errorDispose(e);
        outPutErrorWarn(NoHandlerFoundException.class, CommonErrorCode.NOT_FOUND, e);
        return Result.ofFail(CommonErrorCode.NOT_FOUND);
    }

    /**
     * HttpRequestMethodNotSupportedException 405 异常处理
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result<Object> handlerHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) throws Throwable {
        errorDispose(e);
        outPutErrorWarn(HttpRequestMethodNotSupportedException.class,
                CommonErrorCode.METHOD_NOT_ALLOWED, e);
        return Result.ofFail(CommonErrorCode.METHOD_NOT_ALLOWED);
    }

    /**
     * HttpMediaTypeNotSupportedException 415 异常处理
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public Result<Object> handlerHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) throws Throwable {
        errorDispose(e);
        outPutErrorWarn(HttpMediaTypeNotSupportedException.class,
                CommonErrorCode.UNSUPPORTED_MEDIA_TYPE, e);
        return Result.ofFail(CommonErrorCode.UNSUPPORTED_MEDIA_TYPE);
    }

    /**
     * Exception 类捕获 500 异常处理
     */
    @ExceptionHandler(value = Exception.class)
    public Result<Object> handlerException(Exception e) throws Throwable {
        errorDispose(e);
        outPutError(Exception.class, CommonErrorCode.EXCEPTION, e);
        return Result.ofFail(CommonErrorCode.EXCEPTION);
    }

    /**
     * HttpMessageNotReadableException 参数错误异常
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) throws Throwable {
        errorDispose(e);
        outPutError(HttpMessageNotReadableException.class, CommonErrorCode.PARAM_ERROR, e);
        String msg = String.format("%s : 错误详情( %s )", CommonErrorCode.PARAM_ERROR.getMessage(), e.getRootCause().getMessage());
        return Result.ofFail(CommonErrorCode.PARAM_ERROR.getCode(), msg);
    }

    /**
     * MethodArgumentNotValidException 参数错误异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) throws Throwable {
        errorDispose(e);
        BindingResult bindingResult = e.getBindingResult();
        return getBindResultDTO(bindingResult);
    }

    /**
     * BindException 参数错误异常
     */
    @ExceptionHandler(BindException.class)
    public Result<Object> handleBindException(BindException e) throws Throwable {
        errorDispose(e);
        outPutError(BindException.class, CommonErrorCode.PARAM_ERROR, e);
        BindingResult bindingResult = e.getBindingResult();
        return getBindResultDTO(bindingResult);
    }

    private Result<Object> getBindResultDTO(BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if (log.isDebugEnabled()) {
            for (FieldError error : fieldErrors) {
                log.error("{} -> {}", error.getDefaultMessage(), error.getDefaultMessage());
            }
        }

        if (fieldErrors.isEmpty()) {
            log.error("validExceptionHandler error fieldErrors is empty");
            Result.ofFail(CommonErrorCode.BUSINESS_ERROR.getCode(), "");
        }

        return Result.ofFail(CommonErrorCode.PARAM_ERROR.getCode(), fieldErrors.get(0).getDefaultMessage());
    }

    /**
     * 校验是否进行异常处理
     *
     * @param e   异常
     * @param <T> extends Throwable
     * @throws Throwable 异常
     */
    private <T extends Throwable> void errorDispose(T e) throws Throwable {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (ObjectUtil.isNotNull(requestAttributes)) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            HandlerMethod handlerMethod = (HandlerMethod) request.getAttribute("org.springframework.web.servlet.HandlerMapping.bestMatchingHandler");

            // 获取异常 Controller
            Class<?> beanType = handlerMethod.getBeanType();
            // 获取异常方法
            Method method = handlerMethod.getMethod();

            // 判断方法是否存在 IgnoreResponseAdvice 注解
            IgnoreResponseAdvice methodAnnotation = method.getAnnotation(IgnoreResponseAdvice.class);
            if (methodAnnotation != null) {
                // 是否使用异常处理
                if (methodAnnotation.errorDispose()) throw e;
                else return;
            }
            // 判类是否存在 IgnoreResponseAdvice 注解
            IgnoreResponseAdvice classAnnotation = beanType.getAnnotation(IgnoreResponseAdvice.class);
            if (classAnnotation != null && classAnnotation.errorDispose()) throw e;
        }

    }

    public void outPutError(Class<? extends Exception> errorType, Enum<CommonErrorCode> secondaryErrorType, Throwable throwable) {
        log.error("[{}] {}: {}", errorType.getSimpleName(), secondaryErrorType, throwable.getMessage(), throwable);
    }

    public void outPutErrorWarn(Class<? extends Exception> errorType, Enum<CommonErrorCode> secondaryErrorType, Throwable throwable) {
        log.warn("[{}] {}: {}", errorType.getSimpleName(), secondaryErrorType, throwable.getMessage());
    }

}
