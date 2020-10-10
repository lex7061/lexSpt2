package com.lex.comm.exception;

import cn.hutool.core.util.ReUtil;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述：自定义异常，在业务处理过程中存在问题时抛出
 * 创建人：lex
 * 创建时间：2020-09-29 14:45
 * 更新人：
 * 更新时间：
 */
public class ServerException extends RuntimeException {
    public ServerException() {
    }

    public ServerException(String message) {
        super(message);
    }

    public ServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServerException(Throwable cause) {
        super(cause);
    }

    public ServerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }


    public static void main(String[] args) {
        String[] sqlNames = new String[]{"mapping", "q230", "Q320xxx", "Q250R", "Q340"};
        ArrayList<Object> objects = new ArrayList<>();
        for (String name : sqlNames) {

            String pattern = "([Qq][1-5][0-9]{0,2}).*";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(name);
            System.out.println(m.matches());


            String regex = "(Q[1-5][0-9]{0,2})";
            if (!Pattern.matches(regex, name)) objects.add(name);
            else {
                String sqlName = ReUtil.get(regex, name, 1);
                objects.add(sqlName);
            }
        }
        System.out.println(JSONObject.toJSONString(objects));
    }
}
