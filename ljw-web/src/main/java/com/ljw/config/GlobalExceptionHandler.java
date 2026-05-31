package com.ljw.config;

import com.ljw.common.Result;
import com.ljw.common.exception.BizException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器。
 *
 * 它的作用是：
 * 当 Controller、Service 里面抛出异常时，不让异常直接变成一大段 HTML 错误页面，而是统一包装成你项目的 Result JSON 返回给前端。
 *
 * <p>把 Controller、Service、Interceptor 中抛出的异常统一转换成 Result JSON，
 * 避免前端收到一整页错误堆栈。</p>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理业务异常。
     *
     * BizException 是你自己定义的异常。
     *
     * 一般用于“代码没有坏，但是业务规则不允许”的情况。
     *
     * 比如：
     *
     *     用户名已存在；
     *     密码错误；
     *     账号不存在；
     *     余额不足；
     *     数据不存在；
     *     当前用户不能操作这条数据。
     *
     * 这些情况不属于系统崩溃，
     * 而是正常的业务失败。
     *
     * 所以可以主动抛出 BizException，
     * 然后由这里统一转换成 Result.fail(...) 返回给前端。
     *
     * @ExceptionHandler(BizException.class)
     * 表示：
     *
     *     只要 Controller 或 Service 抛出了 BizException，
     *     Spring MVC 就会调用这个方法处理。
     */
    @ExceptionHandler(BizException.class)
    public Result<Void> handleBizException(BizException e) {
        return Result.fail(e.getCode(), e.getMessage());
    }

    /**
     * 处理未知异常。
     *
     * Exception 是所有普通异常的父类。
     *
     * 这个方法相当于一个“兜底处理器”。
     *
     * 如果程序抛出的异常不是 BizException，
     * 比如：
     *
     *     NullPointerException 空指针异常；
     *     NumberFormatException 数字格式转换异常；
     *     IllegalArgumentException 参数异常；
     *     RuntimeException 运行时异常；
     *     数据库异常；
     *     其他未知异常；
     *
     * 并且没有其他更具体的异常处理方法处理它，
     * 那么就会进入这个方法。
     *
     * @ExceptionHandler(Exception.class)
     * 表示：
     *
     *     处理所有 Exception 类型的异常。
     */
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {
        // 开发阶段打印堆栈，方便定位问题；生产环境可以改成日志记录。
        e.printStackTrace();
        return Result.fail(500, "服务器异常");
    }
}
