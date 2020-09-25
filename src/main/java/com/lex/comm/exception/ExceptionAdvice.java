package com.lex.comm.exception;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 描述：全局异常处理
 * 创建人：lxx
 * 创建时间：2020-09-17 13:20
 * 更新人：
 * 更新时间：
 */
@ControllerAdvice
public class ExceptionAdvice {

    /**
     * 异常处理
     *
     * @param request 请求域
     * @param e       异常信息
     * @return 返回值
     */
    @ResponseBody // 不加注解默认跳转到view
    @ExceptionHandler(Exception.class) // 监管所有的exception
    private Object ExceptionHandle(HttpServletRequest request, Exception e) {
        Log log = LogFactory.get();
        log.info("开始对全局异常开始处理{}", "啦啦啦");
        if (e instanceof DataException || e instanceof ServerException) {
            return e.getMessage();
        } else return ExceptionUtil.getRootCauseMessage(e);
    }
}
