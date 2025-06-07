package com.ruoyi.system.domain.vo;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.system.domain.ScreenInfo;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ScreenInfoVO extends ScreenInfo {

    /** 授权状态 */
    @Excel(name = "授权状态")
    private Integer status;

    @Excel(name = "用户ID")
    private Long userId;

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("status", getStatus())
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
