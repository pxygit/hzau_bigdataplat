package com.ruoyi.system.service;

import java.util.List;
import java.util.Map;

import com.ruoyi.system.domain.ScreenInfo;
import com.ruoyi.system.domain.vo.ScreenInfoVO;

/**
 * 大屏信息Service接口
 *
 * @author pxy
 * @date 2025-05-03
 */
public interface IScreenInfoService
{
    /**
     * 查询大屏信息
     *
     * @param id 大屏信息主键
     * @return 大屏信息
     */
    public ScreenInfo selectScreenInfoById(Long id);

    /**
     * 查询大屏信息列表
     *
     * @param screenInfo 大屏信息
     * @return 大屏信息集合
     */
    public List<ScreenInfoVO> selectScreenInfoList(ScreenInfo screenInfo);

    /**
     * 新增大屏信息
     *
     * @param screenInfo 大屏信息
     * @return 结果
     */
    public int insertScreenInfo(ScreenInfo screenInfo);

    /**
     * 修改大屏信息
     *
     * @param screenInfo 大屏信息
     * @return 结果
     */
    public int updateScreenInfo(ScreenInfo screenInfo);

    /**
     * 批量删除大屏信息
     *
     * @param ids 需要删除的大屏信息主键集合
     * @return 结果
     */
    public int deleteScreenInfoByIds(Long[] ids);

    /**
     * 删除大屏信息信息
     *
     * @param id 大屏信息主键
     * @return 结果
     */
    public int deleteScreenInfoById(Long id);

    /**
     * 获取大屏复制信息
     * @param resourceId
     * @return
     */
    ScreenInfo getCopyInfo(String resourceId);

    /**
     * 根据用户名查询大屏选项列表
     *
     * @param username 用户名
     * @return 大屏选项列表
     */
    List<Map<String, String>> selectScreenOptionsByUsername(String username);
}
