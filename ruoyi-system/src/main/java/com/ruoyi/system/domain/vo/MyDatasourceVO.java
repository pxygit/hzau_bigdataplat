package com.ruoyi.system.domain.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 我的数据源VO对象 screen_my_datasource
 *
 * @author pxy
 * @date 2025-08-14
 */
public class MyDatasourceVO extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long id;

    /** 数据源名称 */
    @Excel(name = "数据源名称")
    private String name;

    /** 数据源ID */
    @Excel(name = "数据源ID")
    private Long datasourceId;

    /** 数据源类型 */
    @Excel(name = "数据源类型")
    private String type;

    /** 可用状态 */
    @Excel(name = "可用状态")
    private Integer status;

    /** 授权状态（0=待审批，1=已通过，2=拒绝） */
    @Excel(name = "授权状态", readConverterExp = "0==待审批，1=已通过，2=拒绝")
    private Integer authStatus;

    /** 授权时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "授权时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date authTime;

    /** 审批人 */
    @Excel(name = "审批人ID")
    private String authBy;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
    public void setAuthBy(String authBy)
    {
        this.authBy = authBy;
    }

    public String getAuthBy()
    {
        return authBy;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("name", getName())
                .append("type", getType())
                .append("status", getStatus())
                .append("datasourceId", getDatasourceId())
                .append("authStatus", getAuthStatus())
                .append("authTime", getAuthTime())
                .append("authBy", getAuthBy())
                .append("remark", getRemark())
                .toString();
    }
}
