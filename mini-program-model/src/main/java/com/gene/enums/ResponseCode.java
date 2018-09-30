package com.gene.enums;

import java.util.Objects;

/**
 * Created by fengjian on 2017/7/19.
 */
public enum ResponseCode {

    //请求成功并返回数据
    OK("200", "操作成功"),
    //无权限请求
    UNAUTHORIZED("401", "无权访问，请重新登录"),
    //验证身份失败
    TOKEN_EXPIRE("420","Token 验证失败！"),
    //参数错误
    BAD_REQUEST("400","请求失败，参数错误！"),
    //用户不存在
    LOGIN_ERROR("421","用户不存在！"),
    //用户已存在
    REGISTER_ERROR("422","用户已存在！"),
    //密码错误
    PASSWORD_ERROR("423","密码错误！"),
    //验证码错误
    VERIFICATION_CODE_ERROR("424","验证码错误，请重新输入！"),
    //
    PASSWORD_STRENGTH("426","密码必须包含数字、大小写字母、符号中两种且长度【8-16】！"),
    VALIDATE_MOBILE_ERROR("427","手机号不正确！"),
    PRODUCT_NO_EXIST("428","产品不存在！"),
    ID_NO_EXIST("429","身份证号已存在，请重新填写！"),
    ID_NO_NAME_ERROR("430","身份证号与姓名不一致，请重新填写！"),
    BANK_CARD_BRANCH_ERROR("432","数据异常，未有鉴权记录！"),
    CASH_TAKE_ERROR("433","单次提现金额不可大于60000！"),
    CASH_TAKE_AVAILABLE_ERROR("434","提现金额需小于可用金额！"),
    CONTRACT_ERROR("435","合同已过期，请重新生成！"),
    GPS_ERROR("436","请开启GPS定位信息！"),
    AMOUNT_ERROR("444","账户余额小于应还金额"),
    INTERNAL_SERVER_ERROR("500", "哎呀，宝宝忙晕了~给个机会，重试下！"),
    REPEAT("520","请勿重复提交！"),
    TIMES_TIMEOUT("532","您的手机号请求过于频繁请稍后再试。"),
    STEP_ERROR("533","步骤异常请刷新页面！"),
    APPLY_EXISTS_ERROR("534","您已存在正在处理中的申请！"),
    TAKE_CASH_FROZEN("4444", "系统维护中，暂无法操作"),
    CUSTOMER_NOT_EXIST("535", "客户不存在"),
    PAD_TOKEN_ERROR("536","账号/密码错误，或未开通权限");

    private final String value;
    private final String message;

    ResponseCode(String value, String reasonPhrase) {
        this.value = value;
        this.message = reasonPhrase;
    }

    @Override
    public String toString() {
        return this.value;
    }

    public String getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

    public static ResponseCode valueOf(int statusCode) {
        for (ResponseCode responseCode : values()) {
            if (Objects.equals(responseCode.value, statusCode)) {
                return responseCode;
            }
        }
        throw new IllegalArgumentException("No matching constant for [" + statusCode + "]");
    }

}
