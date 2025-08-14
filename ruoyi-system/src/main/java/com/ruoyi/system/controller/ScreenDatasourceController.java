package com.ruoyi.system.controller;

import java.util.List;
import java.util.Map;
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
import com.ruoyi.system.domain.ScreenDatasource;
import com.ruoyi.system.service.IScreenDatasourceService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 数据源信息Controller
 *
 * @author pxy
 * @date 2025-06-11
 */
@RestController
@RequestMapping("/screen/datasource")
public class ScreenDatasourceController extends BaseController
{
    @Autowired
    private IScreenDatasourceService screenDatasourceService;

    /**
     * 查询数据源信息列表
     */
    @PreAuthorize("@ss.hasPermi('screen:datasource:list')")
    @GetMapping("/list")
    public TableDataInfo list(ScreenDatasource screenDatasource)
    {
        startPage();
        List<ScreenDatasource> list = screenDatasourceService.selectScreenDatasourceList(screenDatasource);
        return getDataTable(list);
    }

    /**
     * 导出数据源信息列表
     */
    @PreAuthorize("@ss.hasPermi('screen:datasource:export')")
    @Log(title = "数据源信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ScreenDatasource screenDatasource)
    {
        List<ScreenDatasource> list = screenDatasourceService.exportScreenDatasourceList(screenDatasource);
        ExcelUtil<ScreenDatasource> util = new ExcelUtil<ScreenDatasource>(ScreenDatasource.class);
        util.exportExcel(response, list, "数据源信息数据");
    }

    /**
     * 获取数据源信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('screen:datasource:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(screenDatasourceService.selectScreenDatasourceById(id));
    }

    /**
     * 获取我的数据源信息详细信息
     * 仅可用状态和授权状态时可查
     */
    @PreAuthorize("@ss.hasPermi('screen:datasource:show')")
    @GetMapping(value = "/showmydatasource/{id}")
    public AjaxResult showMyDatasourceInfo(@PathVariable("id") Long id)
    {
        return success(screenDatasourceService.showMyDatasourceById(id));
    }

    /**
     * 新增数据源信息
     */
    @PreAuthorize("@ss.hasPermi('screen:datasource:add')")
    @Log(title = "数据源信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ScreenDatasource screenDatasource)
    {
        return toAjax(screenDatasourceService.insertScreenDatasource(screenDatasource));
    }

    /**
     * 修改数据源信息
     */
    @PreAuthorize("@ss.hasPermi('screen:datasource:edit')")
    @Log(title = "数据源信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ScreenDatasource screenDatasource)
    {
        return toAjax(screenDatasourceService.updateScreenDatasource(screenDatasource));
    }

    /**
     * 删除数据源信息
     */
    @PreAuthorize("@ss.hasPermi('screen:datasource:remove')")
    @Log(title = "数据源信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(screenDatasourceService.deleteScreenDatasourceByIds(ids));
    }

    /**
     * 获取可申请的数据源列表
     * @return 可申请的数据源列表,格式为[{label:xxx, value:xxx}]
     */
    @GetMapping("/apply")
    public AjaxResult getScreenOptions()
    {
        List<Map<String, String>> options = screenDatasourceService.selectSourceOptionsByUsername(SecurityUtils.getUsername());
        return success(options);
    }
}
