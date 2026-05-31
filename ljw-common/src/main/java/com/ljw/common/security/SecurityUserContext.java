package com.ljw.common.security;

import com.ljw.common.exception.BizException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 当前登录用户上下文工具。
 *
 * <p>Controller、Dispatch、Service 如果确实需要当前登录用户，
 * 统一从这里获取，不要再从 HttpServletRequest 里取 attribute。
 * 这样业务层不会和 Servlet API 绑定，后续也更容易扩展权限体系。</p>
 */
public class SecurityUserContext {

    /**
     * 获取当前登录用户。
     *
     * @return 当前登录用户
     */
    public static LoginUser getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getPrincipal() == null) {
            throw new BizException(401, "请先登录");
        }

        Object principal = authentication.getPrincipal();

        if (!(principal instanceof LoginUser loginUser)) {
            throw new BizException(401, "登录状态异常");
        }

        return loginUser;
    }

    /**
     * 获取当前登录用户 id。
     *
     * @return 当前登录用户 id
     */
    public static Long getUserId() {
        return getLoginUser().getUserId();
    }

    /**
     * 获取当前登录用户名。
     *
     * @return 当前登录用户名
     */
    public static String getUsername() {
        return getLoginUser().getUsername();
    }

    /**
     * 尝试获取当前登录用户，允许为空。
     *
     * <p>日志、异步任务封装等非强登录场景可以用这个方法，
     * 避免没有登录态时直接抛出业务异常。</p>
     *
     * @return 当前登录用户，可能为 null
     */
    public static LoginUser getLoginUserOrNull() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof LoginUser loginUser)) {
            return null;
        }

        return loginUser;
    }
}
