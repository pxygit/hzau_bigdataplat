package com.ruoyi.system.service.impl;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.ScreenAuth;
import com.ruoyi.system.domain.vo.ScreenInfoVO;
import com.ruoyi.system.mapper.ScreenAuthMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.ScreenInfoMapper;
import com.ruoyi.system.domain.ScreenInfo;
import com.ruoyi.system.service.IScreenInfoService;

/**
 * 大屏信息Service业务层处理
 *
 * @author pxy
 * @date 2025-05-03
 */
@Service
public class ScreenInfoServiceImpl implements IScreenInfoService
{
    @Autowired
    private ScreenInfoMapper screenInfoMapper;
    @Autowired
    private ScreenAuthMapper screenAuthMapper;

    /**
     * 查询大屏信息
     *
     * @param id 大屏信息主键
     * @return 大屏信息
     */
    @Override
    public ScreenInfo selectScreenInfoById(Long id)
    {
        return screenInfoMapper.selectScreenInfoById(id);
    }

    /**
     * 查询大屏信息列表
     *
     * @param screenInfo 大屏信息
     * @return 大屏信息
     */
    @Override
    public List<ScreenInfoVO> selectScreenInfoList(ScreenInfo screenInfo)
    {
        List<ScreenInfo> screenInfoList = screenInfoMapper.selectScreenInfoList(screenInfo);
        return screenInfoList.stream().map(screenInfo1 -> {
            ScreenInfoVO screenInfoVO1 = new ScreenInfoVO();
            BeanUtils.copyProperties(screenInfo1, screenInfoVO1);
            ScreenAuth screenAuth1 = screenAuthMapper.selectScreenAuth(ScreenAuth.builder().resourceId(screenInfo1.getResourceId())
                    .userId(SecurityUtils.getUserId()).build());
            screenInfoVO1.setStatus(screenAuth1 == null ? 0 : screenAuth1.getAuthStatus());
            return screenInfoVO1;
        }).toList();
    }

    /**
     * 新增大屏信息
     *
     * @param screenInfo 大屏信息
     * @return 结果
     */
    @Override
    public int insertScreenInfo(ScreenInfo screenInfo)
    {
        screenInfo.setCreateTime(DateUtils.getNowDate());
        return screenInfoMapper.insertScreenInfo(screenInfo);
    }

    /**
     * 修改大屏信息
     *
     * @param screenInfo 大屏信息
     * @return 结果
     */
    @Override
    public int updateScreenInfo(ScreenInfo screenInfo)
    {
        return screenInfoMapper.updateScreenInfo(screenInfo);
    }

    /**
     * 批量删除大屏信息
     *
     * @param ids 需要删除的大屏信息主键
     * @return 结果
     */
    @Override
    public int deleteScreenInfoByIds(Long[] ids)
    {
        return screenInfoMapper.deleteScreenInfoByIds(ids);
    }

    /**
     * 删除大屏信息信息
     *
     * @param id 大屏信息主键
     * @return 结果
     */
    @Override
    public int deleteScreenInfoById(Long id)
    {
        return screenInfoMapper.deleteScreenInfoById(id);
    }

    /**
     * 获取大屏复制信息
     *
     * @param resourceId
     * @return
     */
    @Override
    public ScreenInfo getCopyInfo(String resourceId){
        ScreenAuth screenAuth = screenAuthMapper.selectScreenAuth(ScreenAuth.builder()
                .resourceId(resourceId.trim())
                .userId(SecurityUtils.getUserId()).build());
        if(screenAuth != null && screenAuth.getAuthStatus() == 1) {
            return screenInfoMapper.selectScreenInfoByResourceId(resourceId);
        } else {
            throw  new RuntimeException("没有权限");
        }
    }

    /**
     * 根据用户名查询大屏选项列表
     *
     * @param username 用户名
     * @return 大屏选项列表
     */
    @Override
    public List<Map<String, String>> selectScreenOptionsByUsername(String username)
    {
        return screenInfoMapper.selectScreenOptionsByUsername(username);
    }
}
