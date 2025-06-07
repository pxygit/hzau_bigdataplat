package com.ruoyi.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.ScreenUidMapping;
import com.ruoyi.system.service.IScreenUidMappingService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户ID映射关系Controller
 *
 * @author pxy
 * &#064;date  2025-05-10
 */
@RestController
@RequestMapping("/screen/uidmapping")
public class ScreenUidMappingController extends BaseController
{
    @Autowired
    private IScreenUidMappingService screenUidMappingService;

    /**
     * 查询用户ID映射关系列表
     */
    @PreAuthorize("@ss.hasPermi('screen:uidmapping:list')")
    @GetMapping("/list")
    public TableDataInfo list(ScreenUidMapping screenUidMapping)
    {
        startPage();
        List<ScreenUidMapping> list = screenUidMappingService.selectScreenUidMappingList(screenUidMapping);
        return getDataTable(list);
    }

    /**
     * 导出用户ID映射关系列表
     */
    @PreAuthorize("@ss.hasPermi('screen:uidmapping:export')")
    @Log(title = "用户ID映射关系", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ScreenUidMapping screenUidMapping)
    {
        List<ScreenUidMapping> list = screenUidMappingService.selectScreenUidMappingList(screenUidMapping);
        ExcelUtil<ScreenUidMapping> util = new ExcelUtil<ScreenUidMapping>(ScreenUidMapping.class);
        util.exportExcel(response, list, "用户ID映射关系数据");
    }

    /**
     * 获取用户ID映射关系详细信息
     */
    @PreAuthorize("@ss.hasPermi('screen:uidmapping:query')")
    @GetMapping(value = "/{dUserId}")
    public AjaxResult getInfo(@PathVariable("dUserId") Long dUserId)
    {
        return success(screenUidMappingService.selectScreenUidMappingByDUserId(dUserId));
    }

    /**
     * 新增用户ID映射关系
     */
    @PreAuthorize("@ss.hasPermi('screen:uidmapping:add')")
    @Log(title = "用户ID映射关系", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ScreenUidMapping screenUidMapping)
    {
        return toAjax(screenUidMappingService.insertScreenUidMapping(screenUidMapping));
    }

    /**
     * 修改用户ID映射关系
     */
    @PreAuthorize("@ss.hasPermi('screen:uidmapping:edit')")
    @Log(title = "用户ID映射关系", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ScreenUidMapping screenUidMapping)
    {
        return toAjax(screenUidMappingService.updateScreenUidMapping(screenUidMapping));
    }

    /**
     * 删除用户ID映射关系
     */
    @PreAuthorize("@ss.hasPermi('screen:uidmapping:remove')")
    @Log(title = "用户ID映射关系", businessType = BusinessType.DELETE)
    @DeleteMapping("/{dUserIds}")
    public AjaxResult remove(@PathVariable Long[] dUserIds)
    {
        return toAjax(screenUidMappingService.deleteScreenUidMappingByDUserIds(dUserIds));
    }

    /**
     * 检查用户ID映射关系，有则返回token
     * @return
     */
    @GetMapping("/check")
    public AjaxResult checkUidMapping() {
        return success(screenUidMappingService.checkUidMapping());
    }
}
