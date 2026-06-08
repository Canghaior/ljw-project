package com.ljw.common.security;

import com.ljw.common.exception.BizException;

/**
 * 当前请求的登录用户上下文。
 *
 * <p>Web 拦截器验证 Token 后把会话放入 ThreadLocal，Controller、Dispatch、Service
 * 可以在不依赖 HttpServletRequest 的情况下读取当前用户。请求完成后必须调用 clear，
 * 否则线程池复用线程时可能把上一个请求的用户信息带到下一个请求。</p>
 */
public final class LoginUserContext {

    /**
     * 保存当前请求登录会话的线程本地变量。
     *
     * <p>Tomcat 通常让一个请求在一个工作线程中执行，因此同一调用链可以读取这里的数据；
     * 不同线程各自保存自己的值，不会直接共享用户信息。</p>
     */
    private static final ThreadLocal<LoginSession> SESSION_HOLDER = new ThreadLocal<>();

    /**
     * 工具类不需要创建对象，因此使用私有构造器禁止外部实例化。
     */
    private LoginUserContext() {
    }

    /**
     * 保存当前请求已经验证通过的登录会话。
     *
     * @param session 登录拦截器校验通过的会话
     */
    public static void setSession(LoginSession session) {
        SESSION_HOLDER.set(session);
    }

    /**
     * 获取当前登录会话，未登录时抛出统一业务异常。
     *
     * @return 当前请求对应的登录会话
     * @throws BizException 当前线程没有登录会话时抛出
     */
    public static LoginSession getSession() {
        LoginSession session = SESSION_HOLDER.get();
        if (session == null) {
            throw new BizException(401, "请先登录");
        }
        return session;
    }

    /**
     * 获取当前登录用户。
     *
     * @return 当前会话中的登录用户
     */
    public static LoginUser getLoginUser() {
        return getSession().getLoginUser();
    }

    /**
     * 获取当前登录用户的数据库主键。
     *
     * @return 当前用户 id
     */
    public static Long getUserId() {
        return getLoginUser().getUserId();
    }

    /**
     * 获取当前请求使用的 Token。
     *
     * <p>退出登录时需要用它删除服务端保存的会话。</p>
     *
     * @return 当前登录 Token
     */
    public static String getToken() {
        return getSession().getToken();
    }

    /**
     * 清理当前线程保存的登录信息，防止线程复用造成用户数据串线。
     */
    public static void clear() {
        SESSION_HOLDER.remove();
    }
}
