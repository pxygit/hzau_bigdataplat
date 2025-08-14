package com.ruoyi.system.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.ScreenMyDatasource;
import com.ruoyi.system.mapper.ScreenMyDatasourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.ScreenDatasourceMapper;
import com.ruoyi.system.domain.ScreenDatasource;
import com.ruoyi.system.service.IScreenDatasourceService;

/**
 * 数据源信息Service业务层处理
 *
 * @author pxy
 * @date 2025-06-11
 */
@Service
public class ScreenDatasourceServiceImpl implements IScreenDatasourceService
{
    @Autowired
    private ScreenDatasourceMapper screenDatasourceMapper;
    @Autowired
    private ScreenMyDatasourceMapper screenMyDatasourceMapper;

    /**
     * 查询数据源信息
     *
     * @param id 数据源信息主键
     * @return 数据源信息
     */
    @Override
    public ScreenDatasource selectScreenDatasourceById(Long id)
    {
        return screenDatasourceMapper.selectScreenDatasourceById(id);
    }

    /**
     * 查询数据源信息
     * 有状态限制（可用和授权状态）
     *
     * @param id 数据源信息主键
     * @return 数据源信息
     */
    @Override
    public ScreenDatasource showMyDatasourceById(Long id){
        ScreenMyDatasource screenMyDatasource = ScreenMyDatasource.builder()
                .userId(SecurityUtils.getUserId())
                .datasourceId(id)
                .authStatus(1).build();// 只查询已授权状态的数据源;
        if(screenMyDatasourceMapper.selectScreenMyDatasourceList(screenMyDatasource).isEmpty()){
            return null;
        }
        return screenDatasourceMapper.showMyDatasourceInfo(new ScreenDatasource() {{
            setId(id);
            setStatus(1); // 只查询可用状态的数据源
        }});
    }

    /**
     * 查询数据源信息列表
     *
     * @param screenDatasource 数据源信息
     * @return 数据源信息
     */
    @Override
    public List<ScreenDatasource> selectScreenDatasourceList(ScreenDatasource screenDatasource)
    {
        return screenDatasourceMapper.selectScreenDatasourceList(screenDatasource);
    }

    /**
     * 导出数据源信息列表
     * @param screenDatasource 数据源信息
     * @return 数据源信息
     */
    @Override
    public List<ScreenDatasource> exportScreenDatasourceList(ScreenDatasource screenDatasource)
    {
        return screenDatasourceMapper.exportScreenDatasourceList(screenDatasource);
    }

    /**
     * 新增数据源信息
     *
     * @param screenDatasource 数据源信息
     * @return 结果
     */
    @Override
    public int insertScreenDatasource(ScreenDatasource screenDatasource)
    {
        Date time = DateUtils.getNowDate();
        String username = SecurityUtils.getUsername();
        screenDatasource.setCreateTime(time);
        screenDatasource.setUpdateTime(time);
        screenDatasource.setCreateBy(username);
        screenDatasource.setUpdateBy(username);
        return screenDatasourceMapper.insertScreenDatasource(screenDatasource);
    }

    /**
     * 修改数据源信息
     *
     * @param screenDatasource 数据源信息
     * @return 结果
     */
    @Override
    public int updateScreenDatasource(ScreenDatasource screenDatasource)
    {
        screenDatasource.setUpdateTime(DateUtils.getNowDate());
        screenDatasource.setUpdateBy(SecurityUtils.getUsername());
        return screenDatasourceMapper.updateScreenDatasource(screenDatasource);
    }

    /**
     * 批量删除数据源信息
     *
     * @param ids 需要删除的数据源信息主键
     * @return 结果
     */
    @Override
    public int deleteScreenDatasourceByIds(Long[] ids)
    {
        return screenDatasourceMapper.deleteScreenDatasourceByIds(ids);
    }

    /**
     * 删除数据源信息信息
     *
     * @param id 数据源信息主键
     * @return 结果
     */
    @Override
    public int deleteScreenDatasourceById(Long id)
    {
        return screenDatasourceMapper.deleteScreenDatasourceById(id);
    }

    /**
     * 根据用户名查询数据源选项列表
     *
     * @param username 用户名
     * @return 数据源选项列表
     */
    @Override
    public List<Map<String, String>> selectSourceOptionsByUsername(String username)
    {
        return screenDatasourceMapper.selectSourceOptionsByUsername(username);
    }
}
