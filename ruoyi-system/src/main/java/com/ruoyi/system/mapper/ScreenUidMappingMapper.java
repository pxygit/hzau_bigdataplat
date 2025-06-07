package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.ScreenUidMapping;

/**
 * 用户ID映射关系Mapper接口
 *
 * @author pxy
 * @date 2025-05-10
 */
public interface ScreenUidMappingMapper
{
    /**
     * 查询用户ID映射关系
     *
     * @param dUserId 用户ID映射关系主键
     * @return 用户ID映射关系
     */
    public ScreenUidMapping selectScreenUidMappingByDUserId(Long dUserId);

    /**
     * 查询用户ID映射关系
     *
     * @param rUserId 用户ID映射关系主键
     * @return 用户ID映射关系
     */
    public ScreenUidMapping selectScreenUidMappingByRUserId(Long rUserId);

    /**
     * 查询用户ID映射关系列表
     *
     * @param screenUidMapping 用户ID映射关系
     * @return 用户ID映射关系集合
     */
    public List<ScreenUidMapping> selectScreenUidMappingList(ScreenUidMapping screenUidMapping);

    /**
     * 新增用户ID映射关系
     *
     * @param screenUidMapping 用户ID映射关系
     * @return 结果
     */
    public int insertScreenUidMapping(ScreenUidMapping screenUidMapping);

    /**
     * 修改用户ID映射关系
     *
     * @param screenUidMapping 用户ID映射关系
     * @return 结果
     */
    public int updateScreenUidMapping(ScreenUidMapping screenUidMapping);

    /**
     * 删除用户ID映射关系
     *
     * @param dUserId 用户ID映射关系主键
     * @return 结果
     */
    public int deleteScreenUidMappingByDUserId(Long dUserId);

    /**
     * 批量删除用户ID映射关系
     *
     * @param dUserIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteScreenUidMappingByDUserIds(Long[] dUserIds);
}
