package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 数据源申请授权对象 screen_my_datasource
 *
 * @author pxy
 * @date 2025-08-14
 */
@Builder
public class ScreenMyDatasource extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 申请人ID */
    @Excel(name = "申请人ID")
    private Long userId;

    /** 数据源ID */
    @Excel(name = "数据源ID")
    private Long datasourceId;

    /** 授权状态（0=待审批，1=已通过，2=拒绝） */
    @Excel(name = "授权状态", readConverterExp = "0==待审批，1=已通过，2=拒绝")
    private Integer authStatus;

    /** 授权时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "授权时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date authTime;

    /** 审批人ID */
    @Excel(name = "审批人ID")
    private Long authBy;

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
    public void setDatasourceId(Long datasourceId)
    {
        this.datasourceId = datasourceId;
    }

    public Long getDatasourceId()
    {
        return datasourceId;
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
    public void setAuthBy(Long authBy)
    {
        this.authBy = authBy;
    }

    public Long getAuthBy()
    {
        return authBy;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("userId", getUserId())
                .append("datasourceId", getDatasourceId())
                .append("authStatus", getAuthStatus())
                .append("authTime", getAuthTime())
                .append("authBy", getAuthBy())
                .append("remark", getRemark())
                .toString();
    }
}
