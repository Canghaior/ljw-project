package com.ljw.common;

/**
 * 统一接口返回对象。
 *
 * <p>项目中所有 Controller 都可以返回这个对象，前端只需要统一判断
 * code、message、data 三个字段即可。</p>
 *
 * @param <T> data 字段的数据类型
 */
public class Result<T> {

    // 状态码：200 成功，401 未登录，403 无权限，500 业务失败或服务器异常。
    private Integer code;

    // 返回给前端的提示信息。
    private String message;

    // 真正返回给前端的数据。
    private T data;

    /**
     * 成功返回，有业务数据。
     *
     * @param data 返回数据
     * @param <T> 返回数据类型
     * @return 统一成功结果
     */
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.code = 200;
        result.message = "success";
        result.data = data;
        return result;
    }

    /**
     * 失败返回，默认使用 500 状态码。
     *
     * @param message 错误提示
     * @param <T> 返回数据类型
     * @return 统一失败结果
     */
    public static <T> Result<T> fail(String message) {
        return fail(500, message);
    }

    /**
     * 失败返回，可以指定状态码。
     *
     * @param code 状态码
     * @param message 错误提示
     * @param <T> 返回数据类型
     * @return 统一失败结果
     */
    public static <T> Result<T> fail(Integer code, String message) {
        Result<T> result = new Result<>();
        result.code = code;
        result.message = message;
        result.data = null;
        return result;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
