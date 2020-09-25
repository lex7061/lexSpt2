package com.lex.comm.result;

import lombok.Data;

import java.io.Serializable;

/**
 * 描述：
 * 创建人：lxx
 * 创建时间：2020-09-17 13:01
 * 更新人：
 * 更新时间：
 */
@Data
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private Boolean status;
    private String code;
    private String msg;
    private T data;
}
