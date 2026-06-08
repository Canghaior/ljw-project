-- 手写 RBAC 初始化脚本。
-- 该脚本可以重复执行：表使用 IF NOT EXISTS，基础数据使用唯一键配合 INSERT IGNORE。

CREATE TABLE IF NOT EXISTS sys_role (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '角色主键',
    role_code VARCHAR(50) NOT NULL COMMENT '角色编码，例如 ADMIN',
    role_name VARCHAR(100) NOT NULL COMMENT '角色名称，例如管理员',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态：1正常，0禁用',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_sys_role_code (role_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统角色表';

CREATE TABLE IF NOT EXISTS sys_permission (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '权限主键',
    permission_code VARCHAR(100) NOT NULL COMMENT '权限编码，例如 user:list',
    permission_name VARCHAR(100) NOT NULL COMMENT '权限名称，例如查看用户列表',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态：1正常，0禁用',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_sys_permission_code (permission_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统权限表';

CREATE TABLE IF NOT EXISTS sys_user_role (
    user_id BIGINT NOT NULL COMMENT '用户 id',
    role_id BIGINT NOT NULL COMMENT '角色 id',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (user_id, role_id),
    KEY idx_sys_user_role_role_id (role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';

CREATE TABLE IF NOT EXISTS sys_role_permission (
    role_id BIGINT NOT NULL COMMENT '角色 id',
    permission_id BIGINT NOT NULL COMMENT '权限 id',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (role_id, permission_id),
    KEY idx_sys_role_permission_permission_id (permission_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限关联表';

-- 初始化四种演示角色。
INSERT IGNORE INTO sys_role (role_code, role_name, status) VALUES
    ('ADMIN', '管理员', 1),
    ('USER', '普通用户', 1),
    ('VISITOR', '游客', 1),
    ('DEVELOPER', '开发者', 1);

-- 当前项目只有用户列表接口需要业务权限，后续新增接口时继续追加权限编码。
INSERT IGNORE INTO sys_permission (permission_code, permission_name, status) VALUES
    ('user:list', '查看用户列表', 1);

-- 根据用户名分配演示角色，避免依赖固定用户 id。
INSERT IGNORE INTO sys_user_role (user_id, role_id)
SELECT u.id, r.id
FROM sys_user u
JOIN sys_role r ON r.role_code = 'ADMIN'
WHERE u.username IN ('admin', 'admin2');

INSERT IGNORE INTO sys_user_role (user_id, role_id)
SELECT u.id, r.id
FROM sys_user u
JOIN sys_role r ON r.role_code = 'USER'
WHERE u.username = 'test';

INSERT IGNORE INTO sys_user_role (user_id, role_id)
SELECT u.id, r.id
FROM sys_user u
JOIN sys_role r ON r.role_code = 'VISITOR'
WHERE u.username = 'visitor';

INSERT IGNORE INTO sys_user_role (user_id, role_id)
SELECT u.id, r.id
FROM sys_user u
JOIN sys_role r ON r.role_code = 'DEVELOPER'
WHERE u.username = 'boss';

-- 管理员和开发者可以查看用户列表；普通用户与游客访问时会收到 403。
INSERT IGNORE INTO sys_role_permission (role_id, permission_id)
SELECT r.id, p.id
FROM sys_role r
JOIN sys_permission p ON p.permission_code = 'user:list'
WHERE r.role_code IN ('ADMIN', 'DEVELOPER');
