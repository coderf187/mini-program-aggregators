package com.gene.dto.base;


import com.gene.enums.ResponseCode;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 *
 * @param <T>
 */
public class ResponseVo<T> implements Serializable {

    private String code;

    private String msg;

    private T data;

    public ResponseVo() {
    }

    public ResponseVo(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> ResponseVo<T> ofError(String code, String msg) {
        return new ResponseVo(code, msg, null);
    }

    public static <T> ResponseVo<T> ofError(String msg) {
        return new ResponseVo("500", msg, null);
    }

    public static <T> ResponseVo<T> ofError(ResponseCode responseCode) {
        return new ResponseVo<>(responseCode.getValue(), responseCode.getMessage(), null);
    }

    public static <T> ResponseVo<T> ofError() {
        return ofError(ResponseCode.INTERNAL_SERVER_ERROR);
    }

    public static <T> ResponseVo<T> ofBadRequest() {
        return ofError(ResponseCode.BAD_REQUEST);
    }

    public static <T> ResponseVo<T> ofSuccess(T result) {
        return new ResponseVo(ResponseCode.OK.getValue(), ResponseCode.OK.getMessage(), result);
    }

    public static <T> ResponseVo<T> ofSuccess() {
        return new ResponseVo(ResponseCode.OK.getValue(), ResponseCode.OK.getMessage(), ResponseCode.OK.getMessage());
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
