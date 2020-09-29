package com.lex.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 描述：todo
 * 创建人：lex
 * 创建时间：2020-09-29 14:14
 * 更新人：
 * 更新时间：
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Test {
    private String a = "aaa";
    private String b = "bbb";
    private Integer c = 3333;
    private Date d = new Date();
}
