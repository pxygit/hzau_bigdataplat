package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 大屏信息对象 screen_info
 *
 * @author pxy
 * @date 2025-05-03
 */
public class ScreenInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 大屏名称 */
    @Excel(name = "大屏名称")
    private String name;

    /** 大屏ID */
    @Excel(name = "大屏ID")
    private String resourceId;

    /** 映射字符 */
    @Excel(name = "映射字符")
    private String path;

    /** 访问密码 */
    @Excel(name = "访问密码")
    private String pwd;

    /** 发布状态 */
    @Excel(name = "发布状态")
    private Integer pubStatus;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setResourceId(String resourceId)
    {
        this.resourceId = resourceId;
    }

    public String getResourceId()
    {
        return resourceId;
    }
    public void setPath(String path)
    {
        this.path = path;
    }

    public String getPath()
    {
        return path;
    }
    public void setPwd(String pwd)
    {
        this.pwd = pwd;
    }

    public String getPwd()
    {
        return pwd;
    }
    public void setPubStatus(Integer pubStatus)
    {
        this.pubStatus = pubStatus;
    }

    public Integer getPubStatus()
    {
        return pubStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("name", getName())
                .append("resourceId", getResourceId())
                .append("path", getPath())
                .append("pwd", getPwd())
                .append("pubStatus", getPubStatus())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .toString();
    }
}
