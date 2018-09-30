package com.gene.exception;


import com.gene.enums.ResponseCode;

/**
 * 业务异常
 */
public class BusinessException extends RuntimeException {
    /**
     * 错误编码
     */
    private String errorCode;

    /**
     * 消息是否为属性文件中的Key
     */
    private boolean propertiesKey = true;


    public static BusinessException ofErrorUnauthorized(){
        return new BusinessException(ResponseCode.UNAUTHORIZED);
    }

    public static BusinessException ofErrorTakeCashFrozen(){
        return new BusinessException(ResponseCode.TAKE_CASH_FROZEN);
    }


    public static BusinessException ofErrorBadRequest(){
        return new BusinessException(ResponseCode.BAD_REQUEST);
    }

    public static BusinessException ofErrorRepeat(){
        return new BusinessException(ResponseCode.REPEAT);
    }
    public static BusinessException ofApplyRepeat(){
        return new BusinessException(ResponseCode.APPLY_EXISTS_ERROR);
    }
    public static BusinessException ofStepError(){
        return new BusinessException(ResponseCode.STEP_ERROR);
    }
    public static BusinessException ofPadTokenError(){
        return new BusinessException(ResponseCode.PAD_TOKEN_ERROR);
    }


    /**
     * 构造一个基本异常.
     *
     * @param message 信息描述
     */
    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(ResponseCode responseCode) {
        this(responseCode.getValue(), responseCode.getMessage(), true);
    }

    /**
     * 构造一个基本异常.
     *
     * @param errorCode 错误编码
     * @param message   信息描述
     */
    public BusinessException(String errorCode, String message) {
        this(errorCode, message, true);
    }

    /**
     * 构造一个基本异常.
     *
     * @param errorCode 错误编码
     * @param message   信息描述
     */
    public BusinessException(String errorCode, String message, Throwable cause) {
        this(errorCode, message, cause, true);
    }

    /**
     * 构造一个基本异常.
     *
     * @param errorCode     错误编码
     * @param message       信息描述
     * @param propertiesKey 消息是否为属性文件中的Key
     */
    public BusinessException(String errorCode, String message, boolean propertiesKey) {
        super(message);
        this.setErrorCode(errorCode);
        this.setPropertiesKey(propertiesKey);
    }

    /**
     * 构造一个基本异常.
     *
     * @param errorCode 错误编码
     * @param message   信息描述
     */
    public BusinessException(String errorCode, String message, Throwable cause, boolean propertiesKey) {
        super(message, cause);
        this.setErrorCode(errorCode);
        this.setPropertiesKey(propertiesKey);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public boolean isPropertiesKey() {
        return propertiesKey;
    }

    public void setPropertiesKey(boolean propertiesKey) {
        this.propertiesKey = propertiesKey;
    }

}
