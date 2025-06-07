package com.ruoyi.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.system.domain.vo.ScreenAuthVO;
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
import com.ruoyi.system.domain.ScreenAuth;
import com.ruoyi.system.service.IScreenAuthService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 大屏授权状态Controller
 *
 * @author pxy
 * @date 2025-05-03
 */
@RestController
@RequestMapping("/screen/auth")
public class ScreenAuthController extends BaseController
{
    @Autowired
    private IScreenAuthService screenAuthService;

    /**
     * 查询大屏授权状态列表
     */
    @PreAuthorize("@ss.hasPermi('screen:auth:list')")
    @GetMapping("/list")
    public TableDataInfo list(ScreenAuth screenAuth)
    {
        startPage();
        List<ScreenAuthVO> list = screenAuthService.selectScreenAuthList(screenAuth);
        return getDataTable(list);
    }

    /**
     * 获取大屏授权状态详细信息
     */
    @PreAuthorize("@ss.hasPermi('screen:auth:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(screenAuthService.selectScreenAuthById(id));
    }

    /**
     * 新增大屏授权状态
     */
    @PreAuthorize("@ss.hasPermi('screen:auth:add')")
    @Log(title = "大屏授权状态", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ScreenAuth screenAuth)
    {
        return toAjax(screenAuthService.insertScreenAuth(screenAuth));
    }

    /**
     * 修改大屏授权状态
     */
    @PreAuthorize("@ss.hasPermi('screen:auth:edit')")
    @Log(title = "大屏授权状态", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ScreenAuth screenAuth)
    {
        return toAjax(screenAuthService.updateScreenAuth(screenAuth));
    }

    /**
     * 删除大屏授权状态
     */
    @PreAuthorize("@ss.hasPermi('screen:auth:remove')")
    @Log(title = "大屏授权状态", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(screenAuthService.deleteScreenAuthByIds(ids));
    }
}
