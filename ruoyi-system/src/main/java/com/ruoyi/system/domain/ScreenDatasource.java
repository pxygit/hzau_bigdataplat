package com.ruoyi.system.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;


/**
 * 数据源信息对象 screen_datasource
 *
 * @author pxy
 * &#064;date  2025-06-11
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ScreenDatasource extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增ID */
    private Long id;

    /** 数据源名称 */
    @Excel(name = "数据源名称")
    private String name;

    /** 数据源类型 */
    @Excel(name = "数据源类型")
    private String type;

    /** 数据源描述 */
    @Excel(name = "数据源描述")
    private String desc;

    /** 版本信息 */
    @Excel(name = "版本信息")
    private String version;

    /** 地址 */
    @Excel(name = "地址")
    private String ip;

    /** 端口 */
    @Excel(name = "端口")
    private String port;

    /** 数据库名称 */
    @Excel(name = "数据库名称")
    private String database;

    /** 用户名 */
    @Excel(name = "用户名")
    private String username;

    /** 密码 */
    @Excel(name = "密码")
    private String password;

    /** 其他必要配置 */
    @Excel(name = "其他必要配置")
    private String otherInfo;

    /** 可用状态 */
    @Excel(name = "可用状态")
    private Integer status;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("name", getName())
                .append("type", getType())
                .append("desc", getDesc())
                .append("version", getVersion())
                .append("ip", getIp())
                .append("port", getPort())
                .append("database", getDatabase())
                .append("username", getUsername())
                .append("password", getPassword())
                .append("otherInfo", getOtherInfo())
                .append("status", getStatus())
                .append("createTime", getCreateTime())
                .append("createBy", getCreateBy())
                .append("updateTime", getUpdateTime())
                .append("updateBy", getUpdateBy())
                .toString();
    }
}
