package com.ruoyi.system.mapper;

import java.util.List;
import java.util.Map;

import com.ruoyi.system.domain.ScreenDatasource;

/**
 * 数据源信息Mapper接口
 *
 * @author pxy
 * @date 2025-06-11
 */
public interface ScreenDatasourceMapper
{
    /**
     * 查询数据源信息
     *
     * @param id 数据源信息主键
     * @return 数据源信息
     */
    public ScreenDatasource selectScreenDatasourceById(Long id);

    /**
     * 查询数据源信息列表
     *
     * @param screenDatasource 数据源信息
     * @return 数据源信息集合
     */
    public List<ScreenDatasource> selectScreenDatasourceList(ScreenDatasource screenDatasource);

    /**
     * 查询数据源信息
     * 有状态限制（可用和授权状态）
     * @return 数据源信息
     */
    public ScreenDatasource showMyDatasourceInfo(ScreenDatasource screenDatasource);

    /**
     * 导出数据源信息列表
     * @param screenDatasource 数据源信息
     * @return 数据源信息集合
     */
    public List<ScreenDatasource> exportScreenDatasourceList(ScreenDatasource screenDatasource);

    /**
     * 新增数据源信息
     *
     * @param screenDatasource 数据源信息
     * @return 结果
     */
    public int insertScreenDatasource(ScreenDatasource screenDatasource);

    /**
     * 修改数据源信息
     *
     * @param screenDatasource 数据源信息
     * @return 结果
     */
    public int updateScreenDatasource(ScreenDatasource screenDatasource);

    /**
     * 删除数据源信息
     *
     * @param id 数据源信息主键
     * @return 结果
     */
    public int deleteScreenDatasourceById(Long id);

    /**
     * 批量删除数据源信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteScreenDatasourceByIds(Long[] ids);

    /**
     * 根据用户名查询数据源选项列表
     *
     * @param username 用户名
     * @return 数据源选项列表
     */
    public List<Map<String, String>> selectSourceOptionsByUsername(String username);
}
