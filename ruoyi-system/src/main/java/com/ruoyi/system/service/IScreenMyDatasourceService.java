package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.ScreenMyDatasource;
import com.ruoyi.system.domain.vo.MyDatasourceVO;

/**
 * 数据源申请授权Service接口
 *
 * @author pxy
 * @date 2025-08-14
 */
public interface IScreenMyDatasourceService
{
    /**
     * 查询数据源申请授权
     *
     * @param id 数据源申请授权主键
     * @return 数据源申请授权
     */
    public ScreenMyDatasource selectScreenMyDatasourceById(Long id);

    /**
     * 查询数据源申请授权列表
     *
     * @param screenMyDatasource 数据源申请授权
     * @return 数据源申请授权集合
     */
    public List<MyDatasourceVO> selectScreenMyDatasourceList(ScreenMyDatasource screenMyDatasource);

    /**
     * 新增数据源申请授权
     *
     * @param screenMyDatasource 数据源申请授权
     * @return 结果
     */
    public int insertScreenMyDatasource(ScreenMyDatasource screenMyDatasource);

    /**
     * 修改数据源申请授权
     *
     * @param screenMyDatasource 数据源申请授权
     * @return 结果
     */
    public int updateScreenMyDatasource(ScreenMyDatasource screenMyDatasource);

    /**
     * 批量删除数据源申请授权
     *
     * @param ids 需要删除的数据源申请授权主键集合
     * @return 结果
     */
    public int deleteScreenMyDatasourceByIds(Long[] ids);

    /**
     * 删除数据源申请授权信息
     *
     * @param id 数据源申请授权主键
     * @return 结果
     */
    public int deleteScreenMyDatasourceById(Long id);
}
