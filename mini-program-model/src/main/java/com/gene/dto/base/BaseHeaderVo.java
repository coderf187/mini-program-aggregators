package com.gene.dto.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * Created by fengjian on 2017/7/19.
 * 封装header信息
 */
@ApiModel
public class BaseHeaderVo implements Serializable {

    @ApiModelProperty(hidden = true)
    private Long uid;
    @ApiModelProperty(hidden = true)
    private String version;
    @ApiModelProperty(hidden = true)
    private String type;
    @ApiModelProperty(hidden = true)
    private String timestamp;
    @ApiModelProperty(hidden = true)
    private String deviceToken;
    @ApiModelProperty(hidden = true)
    private String channel;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
