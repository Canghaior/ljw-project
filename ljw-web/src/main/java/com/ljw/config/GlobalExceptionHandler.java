package com.ljw.config;

import com.ljw.common.Result;
import com.ljw.common.exception.BizException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器。
 *
 * <p>把 Controller、Service、Interceptor 中抛出的异常统一转换成 Result JSON，
 * 避免前端收到一整页错误堆栈。</p>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理业务异常。
     *
     * @param e 业务异常
     * @return 统一失败结果
     */
    @ExceptionHandler(BizException.class)
    public Result<Void> handleBizException(BizException e) {
        return Result.fail(e.getCode(), e.getMessage());
    }

    /**
     * 处理未知异常。
     *
     * @param e 未知异常
     * @return 统一失败结果
     */
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {
        // 开发阶段打印堆栈，方便定位问题；生产环境可以改成日志记录。
        e.printStackTrace();
        return Result.fail(500, "服务器异常");
    }
}
