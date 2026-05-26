package com.ljw.common.exception;

/**
 * 业务异常。
 *
 * <p>用于表示可以预期的业务错误，例如用户名或密码错误、账号禁用、
 * 未登录等。抛出该异常后，会由全局异常处理器转换成统一的 JSON 返回。</p>
 */
public class BizException extends RuntimeException {

    // 业务错误状态码。
    private final Integer code;

    /**
     * 创建默认业务异常，状态码为 500。
     *
     * @param message 错误提示
     */
    public BizException(String message) {
        this(500, message);
    }

    /**
     * 创建指定状态码的业务异常。
     *
     * @param code 状态码
     * @param message 错误提示
     */
    public BizException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
