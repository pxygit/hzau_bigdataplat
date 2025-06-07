package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.ScreenAuth;
import com.ruoyi.system.domain.vo.ScreenAuthVO;

/**
 * 大屏授权状态Service接口
 *
 * @author pxy
 * @date 2025-05-03
 */
public interface IScreenAuthService
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
    public List<ScreenAuthVO> selectScreenAuthList(ScreenAuth screenAuth);

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
     * 批量删除大屏授权状态
     *
     * @param ids 需要删除的大屏授权状态主键集合
     * @return 结果
     */
    public int deleteScreenAuthByIds(Long[] ids);

    /**
     * 删除大屏授权状态信息
     *
     * @param id 大屏授权状态主键
     * @return 结果
     */
    public int deleteScreenAuthById(Long id);
}
