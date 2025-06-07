package com.ruoyi.system.dataease.mapper;

import java.util.List;
import com.ruoyi.system.dataease.domain.DeSysUser;

/**
 * Dataease系统用户Mapper接口
 *
 * @author pxy
 * @date 2025-05-10
 */
public interface DeSysUserMapper
{
    /**
     * 查询Dataease系统用户
     *
     * @param userId Dataease系统用户主键
     * @return Dataease系统用户
     */
    public DeSysUser selectDeSysUserByUserId(Long userId);

    /**
     * 查询Dataease系统用户列表
     *
     * @param deSysUser Dataease系统用户
     * @return Dataease系统用户集合
     */
    public List<DeSysUser> selectDeSysUserList(DeSysUser deSysUser);

    /**
     * 新增Dataease系统用户
     *
     * @param deSysUser Dataease系统用户
     * @return 结果
     */
    public int insertDeSysUser(DeSysUser deSysUser);

    /**
     * 修改Dataease系统用户
     *
     * @param deSysUser Dataease系统用户
     * @return 结果
     */
    public int updateDeSysUser(DeSysUser deSysUser);

    /**
     * 删除Dataease系统用户
     *
     * @param userId Dataease系统用户主键
     * @return 结果
     */
    public int deleteDeSysUserByUserId(Long userId);

    /**
     * 批量删除Dataease系统用户
     *
     * @param userIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDeSysUserByUserIds(Long[] userIds);
}
