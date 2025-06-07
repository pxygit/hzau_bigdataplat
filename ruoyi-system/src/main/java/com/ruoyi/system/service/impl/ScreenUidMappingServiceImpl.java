package com.ruoyi.system.service.impl;

import java.util.List;
import java.util.Map;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.dataease.domain.DeSysUser;
import com.ruoyi.system.dataease.domain.TokenInfo;
import com.ruoyi.system.dataease.mapper.DeSysUserMapper;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.util.DataeaseJwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.ScreenUidMappingMapper;
import com.ruoyi.system.domain.ScreenUidMapping;
import com.ruoyi.system.service.IScreenUidMappingService;

/**
 * 用户ID映射关系Service业务层处理
 *
 * @author pxy
 * @date 2025-05-10
 */
@Service
public class ScreenUidMappingServiceImpl implements IScreenUidMappingService
{
    @Autowired
    private ScreenUidMappingMapper screenUidMappingMapper;

    @Autowired
    private DeSysUserMapper deSysUserMapper;

    /**
     * 查询用户ID映射关系
     *
     * @param dUserId 用户ID映射关系主键
     * @return 用户ID映射关系
     */
    @Override
    public ScreenUidMapping selectScreenUidMappingByDUserId(Long dUserId)
    {
        return screenUidMappingMapper.selectScreenUidMappingByDUserId(dUserId);
    }

    /**
     * 查询用户ID映射关系列表
     *
     * @param screenUidMapping 用户ID映射关系
     * @return 用户ID映射关系
     */
    @Override
    public List<ScreenUidMapping> selectScreenUidMappingList(ScreenUidMapping screenUidMapping)
    {
        return screenUidMappingMapper.selectScreenUidMappingList(screenUidMapping);
    }

    /**
     * 新增用户ID映射关系
     *
     * @param screenUidMapping 用户ID映射关系
     * @return 结果
     */
    @Override
    public int insertScreenUidMapping(ScreenUidMapping screenUidMapping)
    {
        return screenUidMappingMapper.insertScreenUidMapping(screenUidMapping);
    }

    /**
     * 修改用户ID映射关系
     *
     * @param screenUidMapping 用户ID映射关系
     * @return 结果
     */
    @Override
    public int updateScreenUidMapping(ScreenUidMapping screenUidMapping)
    {
        return screenUidMappingMapper.updateScreenUidMapping(screenUidMapping);
    }

    /**
     * 批量删除用户ID映射关系
     *
     * @param dUserIds 需要删除的用户ID映射关系主键
     * @return 结果
     */
    @Override
    public int deleteScreenUidMappingByDUserIds(Long[] dUserIds)
    {
        return screenUidMappingMapper.deleteScreenUidMappingByDUserIds(dUserIds);
    }

    /**
     * 删除用户ID映射关系信息
     *
     * @param dUserId 用户ID映射关系主键
     * @return 结果
     */
    @Override
    public int deleteScreenUidMappingByDUserId(Long dUserId)
    {
        return screenUidMappingMapper.deleteScreenUidMappingByDUserId(dUserId);
    }

    /**
     * 检查用户ID映射关系，有则返回token
     */
    @Override
    public Map<String,String> checkUidMapping(){
        Long rUserId = SecurityUtils.getUserId();
        ScreenUidMapping screenUidMapping = screenUidMappingMapper.selectScreenUidMappingByRUserId(rUserId);
        if (screenUidMapping != null) {
            if(screenUidMapping.getEnabled() == 1) {
                DeSysUser deSysUser = deSysUserMapper.selectDeSysUserByUserId(screenUidMapping.getdUserId());
                String token = DataeaseJwtUtils.sign(TokenInfo.builder()
                        .userId(deSysUser.getUserId())
                        .username(deSysUser.getUsername()).build(),deSysUser.getPassword());
                return Map.of("token", token); //可用的映射返回token
            } else {
                return Map.of("hasAccount", "1"); //不可用的映射返回hasAccount=1,表示有映射但是不可用
            }
        } else {
            return Map.of("hasAccount", "0"); //没有映射返回hasAccount=0
        }
    }
}
