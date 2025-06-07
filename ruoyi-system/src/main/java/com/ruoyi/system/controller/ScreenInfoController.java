package com.ruoyi.system.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.system.domain.vo.ScreenInfoVO;
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
import com.ruoyi.system.domain.ScreenInfo;
import com.ruoyi.system.service.IScreenInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 大屏信息Controller
 *
 * @author pxy
 * @date 2025-05-03
 */
@RestController
@RequestMapping("/screen/info")
public class ScreenInfoController extends BaseController
{
    @Autowired
    private IScreenInfoService screenInfoService;

    /**
     * 查询大屏信息列表
     */
    @PreAuthorize("@ss.hasPermi('screen:info:list')")
    @GetMapping("/list")
    public TableDataInfo list(ScreenInfo screenInfo)
    {
        startPage();
        List<ScreenInfoVO> list = screenInfoService.selectScreenInfoList(screenInfo);
        return getDataTable(list);
    }

//    /**
//     * 导出大屏信息列表
//     */
//    @PreAuthorize("@ss.hasPermi('screen:info:export')")
//    @Log(title = "大屏信息", businessType = BusinessType.EXPORT)
//    @PostMapping("/export")
//    public void export(HttpServletResponse response, ScreenInfo screenInfo)
//    {
//        List<ScreenInfo> list = screenInfoService.selectScreenInfoList(screenInfo);
//        ExcelUtil<ScreenInfo> util = new ExcelUtil<ScreenInfo>(ScreenInfo.class);
//        util.exportExcel(response, list, "大屏信息数据");
//    }

    /**
     * 获取大屏信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('screen:info:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(screenInfoService.selectScreenInfoById(id));
    }

    /**
     * 新增大屏信息
     */
    @PreAuthorize("@ss.hasPermi('screen:info:add')")
    @Log(title = "大屏信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ScreenInfo screenInfo)
    {
        return toAjax(screenInfoService.insertScreenInfo(screenInfo));
    }

    /**
     * 修改大屏信息
     */
    @PreAuthorize("@ss.hasPermi('screen:info:edit')")
    @Log(title = "大屏信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ScreenInfo screenInfo)
    {
        return toAjax(screenInfoService.updateScreenInfo(screenInfo));
    }

    /**
     * 删除大屏信息
     */
    @PreAuthorize("@ss.hasPermi('screen:info:remove')")
    @Log(title = "大屏信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(screenInfoService.deleteScreenInfoByIds(ids));
    }

    /**
     * 获取大屏复制信息
     */
    @PreAuthorize("@ss.hasPermi('screen:auth:copy')")
    @GetMapping("/copy")
    public AjaxResult getCopy(String resourceId)
    {
        return success(screenInfoService.getCopyInfo(resourceId));
    }

    /**
     * 获取可申请的大屏列表
     * @param username 用户名
     * @return 可申请的大屏列表,格式为[{label:xxx, value:xxx}]
     */
    @GetMapping("/apply/{username}")
    public AjaxResult getScreenOptions(@PathVariable("username") String username)
    {
        List<Map<String, String>> options = screenInfoService.selectScreenOptionsByUsername(username);
        return success(options);
    }
}
