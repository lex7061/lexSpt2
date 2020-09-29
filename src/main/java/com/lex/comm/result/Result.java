package com.lex.comm.result;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.Serializable;

/**
 * 描述：
 * 创建人：lex
 * 创建时间：2020-09-17 13:01
 * 更新人：
 * 更新时间：
 */
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private Boolean status = true; // 返回状态
    private Long sysTime = System.currentTimeMillis(); // 服务器当前时间戳
    private String code;
    private String message;
    private T data;

    public Result() {
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Long getSysTime() {
        return sysTime;
    }

    public void setSysTime(Long sysTime) {
        this.sysTime = sysTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public Result<T> setData(T data) {
        this.data = data;
        return this;
    }

    public Result(Boolean status, Long sysTime, T data, String code, String message) {
        this.status = status;
        this.sysTime = sysTime;
        this.data = data;
        this.code = code;
        this.message = message;
    }

    public static Result<Object> ofSuccess() {
        return new Result<>();
    }

    public static Result<Object> ofSuccess(Object data) {
        return new Result<>().setData(data);
    }

    public static Result<Object> ofFail(String code, String msg) {
        Result<Object> result = new Result<>();
        result.status = false;
        result.code = code;
        result.message = msg;
        return result;
    }

    public static Result<Object> ofFail(String code, String msg, Object data) {
        Result<Object> result = new Result<>().setData(data);
        result.status = false;
        result.code = code;
        result.message = msg;
        return result;
    }

    public static Result<Object> ofFail(CommonErrorCode resultEnum) {
        Result<Object> result = new Result<>();
        result.status = false;
        result.code = resultEnum.getCode();
        result.message = resultEnum.getMessage();
        return result;
    }

    /**
     * 获取 json
     *
     * @return json
     */
    public String buildResultJson() {
        JSONObject json = new JSONObject();
        json.put("status", this.status);
        json.put("code", this.code);
        json.put("sysTime", this.sysTime);
        json.put("message", this.message);
        json.put("data", this.data);
        // //SerializerFeature是个枚举类型。消除循环引用
        return JSONObject.toJSONString(json, SerializerFeature.DisableCircularReferenceDetect);
    }

    @Override
    public String toString() {
        return String.format("result: { status: %s, sysTime: %s, data: %s, code: %s, message: %s}"
                , status, sysTime, JSONObject.toJSONString(data), code, message);
    }
}
