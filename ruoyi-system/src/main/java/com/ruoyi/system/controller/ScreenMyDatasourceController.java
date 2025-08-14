package com.ruoyi.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.system.domain.vo.MyDatasourceVO;
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
import com.ruoyi.system.domain.ScreenMyDatasource;
import com.ruoyi.system.service.IScreenMyDatasourceService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 数据源申请授权Controller
 *
 * @author pxy
 * @date 2025-08-14
 */
@RestController
@RequestMapping("/screen/mydatasource")
public class ScreenMyDatasourceController extends BaseController
{
    @Autowired
    private IScreenMyDatasourceService screenMyDatasourceService;

    /**
     * 联合查询
     * 查询数据源申请授权列表
     * 查询相应数据源具体信息
     */
    @PreAuthorize("@ss.hasPermi('screen:mydatasource:list')")
    @GetMapping("/list")
    public TableDataInfo list(ScreenMyDatasource screenMyDatasource)
    {
        startPage();
        List<MyDatasourceVO> list = screenMyDatasourceService.selectScreenMyDatasourceList(screenMyDatasource);
        return getDataTable(list);
    }

    /**
     * 导出数据源申请授权列表
     */
    @PreAuthorize("@ss.hasPermi('screen:mydatasource:export')")
    @Log(title = "数据源申请授权", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ScreenMyDatasource screenMyDatasource)
    {
        List<MyDatasourceVO> list = screenMyDatasourceService.selectScreenMyDatasourceList(screenMyDatasource);
        ExcelUtil<MyDatasourceVO> util = new ExcelUtil<MyDatasourceVO>(MyDatasourceVO.class);
        util.exportExcel(response, list, "数据源申请授权数据");
    }

    /**
     * 获取数据源申请授权详细信息
     */
    @PreAuthorize("@ss.hasPermi('screen:mydatasource:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(screenMyDatasourceService.selectScreenMyDatasourceById(id));
    }

    /**
     * 新增数据源申请授权
     */
    @PreAuthorize("@ss.hasPermi('screen:mydatasource:add')")
    @Log(title = "数据源申请授权", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ScreenMyDatasource screenMyDatasource)
    {
        return toAjax(screenMyDatasourceService.insertScreenMyDatasource(screenMyDatasource));
    }

    /**
     * 修改数据源申请授权
     */
    @PreAuthorize("@ss.hasPermi('screen:mydatasource:edit')")
    @Log(title = "数据源申请授权", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ScreenMyDatasource screenMyDatasource)
    {
        return toAjax(screenMyDatasourceService.updateScreenMyDatasource(screenMyDatasource));
    }

    /**
     * 删除数据源申请授权
     */
    @PreAuthorize("@ss.hasPermi('screen:mydatasource:remove')")
    @Log(title = "数据源申请授权", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(screenMyDatasourceService.deleteScreenMyDatasourceByIds(ids));
    }
}
