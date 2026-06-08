package com.ljw.common.permission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 声明访问 Controller 接口所需权限的自定义注解。
 *
 * <p>权限拦截器会在运行时读取该注解，并检查当前用户是否拥有指定权限编码。
 * 注解可以放在 Controller 类上，也可以放在具体接口方法上。</p>
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequirePermission {

    /**
     * 接口要求的权限编码。
     *
     * <p>该值应与 sys_permission.permission_code 保持一致，例如 {@code user:list}。</p>
     *
     * @return 权限编码
     */
    String value();
}
