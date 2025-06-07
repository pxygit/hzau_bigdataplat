package com.ruoyi.system.dataease.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Dataease系统用户对象 de_sys_user
 *
 * @author pxy
 * &#064;date  2025-05-10
 */
@Data
public class DeSysUser implements Serializable
{
    @Serial
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long userId;

    /** 部门名称 */
    @Excel(name = "部门名称")
    private Long deptId;

    /** 用户名 */
    @Excel(name = "用户名")
    private String username;

    /** 昵称 */
    @Excel(name = "昵称")
    private String nickName;

    /** 性别 */
    @Excel(name = "性别")
    private String gender;

    /** 手机号码 */
    @Excel(name = "手机号码")
    private String phone;

    /** 邮箱 */
    @Excel(name = "邮箱")
    private String email;

    /** 密码 */
    @Excel(name = "密码")
    private String password;

    /** 是否为admin账号 */
    @Excel(name = "是否为admin账号")
    private Boolean isAdmin;

    /** 状态：1启用、0禁用 */
    @Excel(name = "状态：1启用、0禁用")
    private Long enabled;

    /** 修改密码的时间 */
    @Excel(name = "修改密码的时间")
    private Long pwdResetTime;

    /** 语言 */
    @Excel(name = "语言")
    private String language;

    /** 来源 */
    @Excel(name = "来源")
    private Integer from;

    /** oidc用户ID */
    @Excel(name = "oidc用户ID")
    private String sub;

    /** 手机号前缀 */
    @Excel(name = "手机号前缀")
    private String phonePrefix;

    /** 搜索值 */
    @JsonIgnore
    private String searchValue;

    /** 创建者 */
    private String createBy;

    /** 创建时间 */
    private Long createTime;

    /** 更新者 */
    private String updateBy;

    /** 更新时间 */
    private Long updateTime;

    /** 备注 */
    private String remark;

    /** 请求参数 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, Object> params;

    public Map<String, Object> getParams()
    {
        if (params == null)
        {
            params = new HashMap<>();
        }
        return params;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("userId", getUserId())
                .append("deptId", getDeptId())
                .append("username", getUsername())
                .append("nickName", getNickName())
                .append("gender", getGender())
                .append("phone", getPhone())
                .append("email", getEmail())
                .append("password", getPassword())
                .append("isAdmin", getIsAdmin())
                .append("enabled", getEnabled())
                .append("createBy", getCreateBy())
                .append("updateBy", getUpdateBy())
                .append("pwdResetTime", getPwdResetTime())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("language", getLanguage())
                .append("from", getFrom())
                .append("sub", getSub())
                .append("phonePrefix", getPhonePrefix())
                .toString();
    }
}
