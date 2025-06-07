package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 用户ID映射关系对象 screen_uid_mapping
 *
 * @author pxy
 * @date 2025-05-10
 */
public class ScreenUidMapping extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** de_sys_user表userId */
    @Excel(name = "de_sys_user表userId")
    private Long dUserId;

    /** sys_user表userId */
    @Excel(name = "sys_user表userId")
    private Long ryUserId;

    /** 默认 0不启用 */
    @Excel(name = "默认 0不启用")
    private Integer enabled;

    public void setdUserId(Long dUserId)
    {
        this.dUserId = dUserId;
    }

    public Long getdUserId()
    {
        return dUserId;
    }
    public void setRyUserId(Long ryUserId)
    {
        this.ryUserId = ryUserId;
    }

    public Long getRyUserId()
    {
        return ryUserId;
    }
    public void setEnabled(Integer enabled)
    {
        this.enabled = enabled;
    }

    public Integer getEnabled()
    {
        return enabled;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("dUserId", getdUserId())
                .append("ryUserId", getRyUserId())
                .append("enabled", getEnabled())
                .toString();
    }
}
