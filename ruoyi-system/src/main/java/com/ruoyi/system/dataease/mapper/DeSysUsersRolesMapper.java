package com.ruoyi.system.dataease.mapper;

import java.util.List;
import com.ruoyi.system.dataease.domain.DeSysUsersRoles;

/**
 * de用户角色关联Mapper接口
 *
 * @author pxy
 * @date 2025-05-18
 */
public interface DeSysUsersRolesMapper
{
    /**
     * 查询de用户角色关联
     *
     * @param userId de用户角色关联主键
     * @return de用户角色关联
     */
    public DeSysUsersRoles selectDeSysUsersRolesByUserId(Long userId);

    /**
     * 查询de用户角色关联列表
     *
     * @param deSysUsersRoles de用户角色关联
     * @return de用户角色关联集合
     */
    public List<DeSysUsersRoles> selectDeSysUsersRolesList(DeSysUsersRoles deSysUsersRoles);

    /**
     * 新增de用户角色关联
     *
     * @param deSysUsersRoles de用户角色关联
     * @return 结果
     */
    public int insertDeSysUsersRoles(DeSysUsersRoles deSysUsersRoles);

    /**
     * 修改de用户角色关联
     *
     * @param deSysUsersRoles de用户角色关联
     * @return 结果
     */
    public int updateDeSysUsersRoles(DeSysUsersRoles deSysUsersRoles);

    /**
     * 删除de用户角色关联
     *
     * @param userId de用户角色关联主键
     * @return 结果
     */
    public int deleteDeSysUsersRolesByUserId(Long userId);

    /**
     * 批量删除de用户角色关联
     *
     * @param userIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDeSysUsersRolesByUserIds(Long[] userIds);
}
