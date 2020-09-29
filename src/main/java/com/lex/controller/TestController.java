package com.lex.controller;

import com.lex.comm.annotation.IgnoreResponseAdvice;
import com.lex.entity.Test;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述：控制器
 * 创建人：lex
 * 创建时间：2020-09-29 14:12
 * 更新人：
 * 更新时间：
 */
@RestController
@RequestMapping("/")
@IgnoreResponseAdvice(errorDispose = false)
public class TestController {
    @GetMapping("res")
    public Integer res() {
        return 1;
    }

    @GetMapping("resResult")
    public Object resResult() {
        return new Test();
    }
}
