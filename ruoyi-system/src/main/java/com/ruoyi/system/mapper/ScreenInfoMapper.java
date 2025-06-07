package com.ruoyi.system.mapper;

import java.util.List;
import java.util.Map;
import com.ruoyi.system.domain.ScreenInfo;
import com.ruoyi.system.domain.vo.ScreenInfoVO;

/**
 * 大屏信息Mapper接口
 *
 * @author pxy
 * @date 2025-05-03
 */
public interface ScreenInfoMapper
{
    /**
     * 查询大屏信息
     *
     * @param id 大屏信息主键
     * @return 大屏信息
     */
    public ScreenInfo selectScreenInfoById(Long id);

    /**
     * 查询大屏信息
     *
     * @param resourceId 大屏ID
     * @return 大屏信息
     */
    public ScreenInfo selectScreenInfoByResourceId(String resourceId);

    /**
     * 查询大屏信息列表
     *
     * @param screenInfo 大屏信息
     * @return 大屏信息集合
     */
    public List<ScreenInfo> selectScreenInfoList(ScreenInfo screenInfo);

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
     * 删除大屏信息
     *
     * @param id 大屏信息主键
     * @return 结果
     */
    public int deleteScreenInfoById(Long id);

    /**
     * 批量删除大屏信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteScreenInfoByIds(Long[] ids);

    /**
     * 根据用户名查询大屏选项列表
     *
     * @param username 用户名
     * @return 大屏选项列表
     */
    public List<Map<String, String>> selectScreenOptionsByUsername(String username);

}
