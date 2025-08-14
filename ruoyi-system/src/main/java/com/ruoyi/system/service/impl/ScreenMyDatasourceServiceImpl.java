package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.ScreenDatasource;
import com.ruoyi.system.domain.vo.MyDatasourceVO;
import com.ruoyi.system.mapper.ScreenDatasourceMapper;
import com.ruoyi.system.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.ScreenMyDatasourceMapper;
import com.ruoyi.system.domain.ScreenMyDatasource;
import com.ruoyi.system.service.IScreenMyDatasourceService;

/**
 * 数据源申请授权Service业务层处理
 *
 * @author pxy
 * @date 2025-08-14
 */
@Service
public class ScreenMyDatasourceServiceImpl implements IScreenMyDatasourceService
{
    @Autowired
    private ScreenMyDatasourceMapper screenMyDatasourceMapper;
    @Autowired
    private ScreenDatasourceMapper screenDatasourceMapper;
    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 查询数据源申请授权
     *
     * @param id 数据源申请授权主键
     * @return 数据源申请授权
     */
    @Override
    public ScreenMyDatasource selectScreenMyDatasourceById(Long id)
    {
        return screenMyDatasourceMapper.selectScreenMyDatasourceById(id);
    }

    /**
     * 查询数据源申请授权列表
     *
     * @param screenMyDatasource 数据源申请授权
     * @return 数据源申请授权
     */
    @Override
    public List<MyDatasourceVO> selectScreenMyDatasourceList(ScreenMyDatasource screenMyDatasource)
    {
        screenMyDatasource.setUserId(SecurityUtils.getUserId());
        List<MyDatasourceVO> list = screenMyDatasourceMapper.selectScreenMyDatasourceList(screenMyDatasource).stream().map( item -> {
            MyDatasourceVO myDatasourceVO = new MyDatasourceVO();
            myDatasourceVO.setDatasourceId(item.getDatasourceId());
            myDatasourceVO.setAuthStatus(item.getAuthStatus());
            myDatasourceVO.setAuthTime(item.getAuthTime());
            myDatasourceVO.setAuthBy(sysUserMapper.selectUserById(item.getAuthBy()).getNickName());

            ScreenDatasource screenDatasource = screenDatasourceMapper.selectScreenDatasourceById(item.getDatasourceId());
            myDatasourceVO.setName(screenDatasource.getName());
            myDatasourceVO.setType(screenDatasource.getType());
            myDatasourceVO.setStatus(screenDatasource.getStatus());

            return myDatasourceVO;
        }).toList();
        return list;
    }

    /**
     * 新增数据源申请授权
     *
     * @param screenMyDatasource 数据源申请授权
     * @return 结果
     */
    @Override
    public int insertScreenMyDatasource(ScreenMyDatasource screenMyDatasource)
    {
        return screenMyDatasourceMapper.insertScreenMyDatasource(screenMyDatasource);
    }

    /**
     * 修改数据源申请授权
     *
     * @param screenMyDatasource 数据源申请授权
     * @return 结果
     */
    @Override
    public int updateScreenMyDatasource(ScreenMyDatasource screenMyDatasource)
    {
        return screenMyDatasourceMapper.updateScreenMyDatasource(screenMyDatasource);
    }

    /**
     * 批量删除数据源申请授权
     *
     * @param ids 需要删除的数据源申请授权主键
     * @return 结果
     */
    @Override
    public int deleteScreenMyDatasourceByIds(Long[] ids)
    {
        return screenMyDatasourceMapper.deleteScreenMyDatasourceByIds(ids);
    }

    /**
     * 删除数据源申请授权信息
     *
     * @param id 数据源申请授权主键
     * @return 结果
     */
    @Override
    public int deleteScreenMyDatasourceById(Long id)
    {
        return screenMyDatasourceMapper.deleteScreenMyDatasourceById(id);
    }
}
