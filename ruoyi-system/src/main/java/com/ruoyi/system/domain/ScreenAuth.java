package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 大屏授权状态对象 screen_auth
 *
 * @author pxy
 * @date 2025-05-03
 */
@Builder
public class ScreenAuth extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 用户ID */
    private Long userId;

    /** 大屏ID */
    @Excel(name = "大屏ID")
    private String resourceId;

    /** 授权状态 */
    @Excel(name = "授权状态")
    private Integer authStatus;

    /** 授权时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "授权时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date authTime;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getUserId()
    {
        return userId;
    }
    public void setResourceId(String resourceId)
    {
        this.resourceId = resourceId;
    }

    public String getResourceId()
    {
        return resourceId;
    }
    public void setAuthStatus(Integer authStatus)
    {
        this.authStatus = authStatus;
    }

    public Integer getAuthStatus()
    {
        return authStatus;
    }
    public void setAuthTime(Date authTime)
    {
        this.authTime = authTime;
    }

    public Date getAuthTime()
    {
        return authTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("userId", getUserId())
                .append("resourceId", getResourceId())
                .append("authStatus", getAuthStatus())
                .append("authTime", getAuthTime())
                .toString();
    }
}
