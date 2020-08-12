package com.github.zhaojiacan.fileupload.pojo;


import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @classname: ResultBody
 * @author: zhaojiacan
 * @description: json 返回结果
 * @date: 2019/5/24 11:05
 * @version:1.0
 */

public class ResultBody<T> implements Serializable {
    private static final long serialVersionUID = -6190689122701100762L;
    private static final int[] SUCCESS_COCES = {0,100,101};
    /**
     * 响应编码
     */
    private int code = 0;
    /**
     * 提示消息
     */
    private String message;

    /**
     * 请求路径
     */
    private String path;

    /**
     * 响应数据
     */
    private T data;

    /**
     * http状态码
     */
    private int httpStatus;

    /**
     * 附加数据
     */
    private Map<String, Object> extra;

    /**
     * 响应时间
     */
    private long timestamp = System.currentTimeMillis();

   
    public boolean isOk() {
        return this.code == 0;
    }

    public ResultBody() {
        super();
    }
    public static <T> ResultBody success() {
        return new ResultBody().setCode(ResultEnum.OK.getCode());
    }

    public static <T> ResultBody success(T data) {
        return new ResultBody().setData(data).setCode(ResultEnum.OK.getCode());
    }

    public static <T> ResultBody success(String msg, T result) {
        return new ResultBody().setMessage(msg).setData(result);
    }

    public static ResultBody failed(String msg) {
        return new ResultBody().setCode(ResultEnum.FAIL.getCode()).setMessage(msg);
    }

    public static ResultBody failed() {
        return new ResultBody().setCode(ResultEnum.FAIL.getCode()).setMessage(ResultEnum.FAIL.getMessage());
    }

    public static ResultBody error() {
        return new ResultBody().setCode(ResultEnum.ERROR.getCode()).setMessage(ResultEnum.ERROR.getMessage());
    }

    public static ResultBody failed(Integer code, String msg) {
        return new ResultBody().setCode(code).setMessage(msg);
    }

    public static ResultBody failed(ResultEnum code, String msg) {
        return failed(code.getCode(), msg);
    }

    public int getCode() {
        return code;
    }

    public ResultBody setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return this.message;
    }

    public ResultBody setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public ResultBody setData(T data) {
        this.data = data;
        return this;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public ResultBody setTimestamp(long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public Map<String, Object> getExtra() {
        return extra;
    }

    public ResultBody setExtra(Map<String, Object> extra) {
        this.extra = extra;
        return this;
    }

    public ResultBody putExtra(String key, Object value) {
        if (this.extra == null) {
            this.extra = new HashMap<>();
        }
        this.extra.put(key, value);
        return this;
    }



    public String getPath() {
        return path;
    }

    public ResultBody setPath(String path) {
        this.path = path;
        return this;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public ResultBody setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
        return this;
    }

    public boolean successed(){
        return Arrays.asList(SUCCESS_COCES).contains(this.code);
    }
    public boolean noSucced(){
        return !successed();
    }

    public static<T> ResultBody instance(Integer code, String msg, T t){
        return  new ResultBody<T>().setCode(code).setMessage(msg).setData(t);
    }

    @Override
    public String toString() {
        return "ResultBody{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", path='" + path + '\'' +
                ", data=" + data +
                ", httpStatus=" + httpStatus +
                ", extra=" + extra +
                ", timestamp=" + timestamp +
                '}';
    }
}
