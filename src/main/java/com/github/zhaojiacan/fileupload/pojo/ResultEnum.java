package com.github.zhaojiacan.fileupload.pojo;

/**
 * 自定义返回码
 *
 * @author admin
 */

public enum ResultEnum {
    /**
     * 成功
     */
    OK(0, "success"),
    FAIL(-1, "fail"),
    ALERT(1001, "alert"),


    /**
     * 系统错误
     */
    ERROR(5000, "error"),
    GATEWAY_TIMEOUT(5004, "gateway_timeout"),
    SERVICE_UNAVAILABLE(5003, "service_unavailable"),

    /**
    * http
    */
    HTTP_PAGE_NOT_FOUND(401,"访问地址找不到"),
    HTTP_PARAM_ERROR(422,"操作失败,参数错误");
    private int code;
    private String message;

    ResultEnum() {
    }

    private ResultEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ResultEnum getResultEnum(int code) {
        for (ResultEnum type : ResultEnum.values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        return ERROR;
    }

    public static ResultEnum getResultEnum(String message) {
        for (ResultEnum type : ResultEnum.values()) {
            if (type.getMessage().equals(message)) {
                return type;
            }
        }
        return ERROR;
    }


    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }


}
