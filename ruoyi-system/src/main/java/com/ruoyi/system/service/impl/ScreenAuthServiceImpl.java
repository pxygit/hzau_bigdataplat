package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.vo.ScreenAuthVO;
import com.ruoyi.system.mapper.ScreenInfoMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.ScreenAuthMapper;
import com.ruoyi.system.domain.ScreenAuth;
import com.ruoyi.system.service.IScreenAuthService;

/**
 * 大屏授权状态Service业务层处理
 *
 * @author pxy
 * @date 2025-05-03
 */
@Service
public class ScreenAuthServiceImpl implements IScreenAuthService
{
    @Autowired
    private ScreenAuthMapper screenAuthMapper;

    @Autowired
    private ScreenInfoMapper screenInfoMapper;

    /**
     * 查询大屏授权状态
     *
     * @param id 大屏授权状态主键
     * @return 大屏授权状态
     */
    @Override
    public ScreenAuth selectScreenAuthById(Long id)
    {
        return screenAuthMapper.selectScreenAuthById(id);
    }

    /**
     * 查询大屏授权状态列表
     *
     * @param screenAuth 大屏授权状态
     * @return 大屏授权状态
     */
    @Override
    public List<ScreenAuthVO> selectScreenAuthList(ScreenAuth screenAuth)
    {
        screenAuth.setUserId(SecurityUtils.getUserId());
        List<ScreenAuth> screenAuthList = screenAuthMapper.selectScreenAuthList(screenAuth);
        return screenAuthList.stream().map(screenAuth1 -> {
            ScreenAuthVO screenAuthVO = new ScreenAuthVO();
            BeanUtils.copyProperties(screenAuth1, screenAuthVO);
            screenAuthVO.setName(screenInfoMapper.selectScreenInfoByResourceId(screenAuth1.getResourceId()).getName());
            return screenAuthVO;
        }).toList();
    }

    /**
     * 新增大屏授权状态
     *
     * @param screenAuth 大屏授权状态
     * @return 结果
     */
    @Override
    public int insertScreenAuth(ScreenAuth screenAuth)
    {
        return screenAuthMapper.insertScreenAuth(screenAuth);
    }

    /**
     * 修改大屏授权状态
     *
     * @param screenAuth 大屏授权状态
     * @return 结果
     */
    @Override
    public int updateScreenAuth(ScreenAuth screenAuth)
    {
        return screenAuthMapper.updateScreenAuth(screenAuth);
    }

    /**
     * 批量删除大屏授权状态
     *
     * @param ids 需要删除的大屏授权状态主键
     * @return 结果
     */
    @Override
    public int deleteScreenAuthByIds(Long[] ids)
    {
        return screenAuthMapper.deleteScreenAuthByIds(ids);
    }

    /**
     * 删除大屏授权状态信息
     *
     * @param id 大屏授权状态主键
     * @return 结果
     */
    @Override
    public int deleteScreenAuthById(Long id)
    {
        return screenAuthMapper.deleteScreenAuthById(id);
    }
}
