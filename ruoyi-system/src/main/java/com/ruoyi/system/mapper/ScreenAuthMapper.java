package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.ScreenAuth;
import com.ruoyi.system.domain.vo.ScreenInfoVO;

/**
 * 大屏授权状态Mapper接口
 *
 * @author pxy
 * @date 2025-05-03
 */
public interface ScreenAuthMapper
{
    /**
     * 查询大屏授权状态
     *
     * @param id 大屏授权状态主键
     * @return 大屏授权状态
     */
    public ScreenAuth selectScreenAuthById(Long id);

    /**
     * 查询大屏授权状态列表
     *
     * @param screenAuth 大屏授权状态
     * @return 大屏授权状态集合
     */
    public List<ScreenAuth> selectScreenAuthList(ScreenAuth screenAuth);

    /**
     * 查询大屏授权状态,唯一值
     *
     * @param screenAuth 大屏授权状态
     * @return 大屏授权状态
     */
    public ScreenAuth selectScreenAuth(ScreenAuth screenAuth);

    /**
     * 新增大屏授权状态
     *
     * @param screenAuth 大屏授权状态
     * @return 结果
     */
    public int insertScreenAuth(ScreenAuth screenAuth);

    /**
     * 修改大屏授权状态
     *
     * @param screenAuth 大屏授权状态
     * @return 结果
     */
    public int updateScreenAuth(ScreenAuth screenAuth);

    /**
     * 删除大屏授权状态
     *
     * @param id 大屏授权状态主键
     * @return 结果
     */
    public int deleteScreenAuthById(Long id);

    /**
     * 批量删除大屏授权状态
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteScreenAuthByIds(Long[] ids);

}
