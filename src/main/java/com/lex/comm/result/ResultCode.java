package com.lex.comm.result;

import lombok.Getter;

@Getter
public enum ResultCode {
    NOT_VALID("valid", "校验失败"), NOT_PERMISSIONS("CLOUD401", "您没有操作权限");

    private String code;
    private String msg;

    ResultCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
