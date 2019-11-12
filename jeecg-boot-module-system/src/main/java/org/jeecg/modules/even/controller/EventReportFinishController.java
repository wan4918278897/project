package org.jeecg.modules.even.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;

import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.even.entity.EventReport;
import org.jeecg.modules.even.entity.EventReportFinish;
import org.jeecg.modules.even.service.IEventReportFinishService;
import org.jeecg.modules.even.service.IEventReportService;
import org.jeecg.modules.even.utils.ReportStatus;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Description: xxxxxxxxxxx
 * @Author: jeecg-boot
 * @Date: 2019-06-11
 * @Version: V1.0
 */
@Slf4j
@Api(tags = "上报事件办结")
@RestController
@RequestMapping("/even/eventReportFinish")
public class EventReportFinishController {
    @Autowired
    private IEventReportFinishService eventReportFinishService;

    @Autowired
    private IEventReportService eventReportService;

    /**
     * 分页列表查询
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    @AutoLog(value = "xxxxxxxxxxx-分页列表查询")
    @ApiOperation(value = "xxxxxxxxxxx-分页列表查询", notes = "xxxxxxxxxxx-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<EventReport>> queryPageList(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                    String title, String beginDate, String endDate, String evenType, String evenProperty, String evenLevel)  {
        Result<IPage<EventReport>> result = new Result<IPage<EventReport>>();
        Page<EventReport> page = new Page<EventReport>(pageNo, pageSize);
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        IPage<EventReport> pageList = eventReportService.reportList(page,sysUser.getId(),title,beginDate,endDate,evenType,evenProperty,evenLevel, "","finishList");
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    /**
     * 添加
     *
     * @param eventReportFinish
     * @return
     */
    @AutoLog(value = "xxxxxxxxxxx-添加")
    @ApiOperation(value = "xxxxxxxxxxx-添加", notes = "xxxxxxxxxxx-添加")
    @PostMapping(value = "/add")
    public Result<EventReportFinish> add(@RequestBody EventReportFinish eventReportFinish) {
        Result<EventReportFinish> result = new Result<EventReportFinish>();
        try {
            eventReportFinishService.save(eventReportFinish);
            result.success("添加成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500("操作失败");
        }
        return result;
    }

    /**
     * 编辑
     *
     * @param eventReportFinish
     * @return
     */
    @AutoLog(value = "xxxxxxxxxxx-编辑")
    @ApiOperation(value = "xxxxxxxxxxx-编辑", notes = "xxxxxxxxxxx-编辑")
    @PutMapping(value = "/edit")
    public Result<EventReportFinish> edit(@RequestBody EventReportFinish eventReportFinish) {
        Result<EventReportFinish> result = new Result<EventReportFinish>();
        EventReportFinish eventReportFinishEntity = eventReportFinishService.getById(eventReportFinish.getId());
        if (eventReportFinishEntity == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = eventReportFinishService.updateById(eventReportFinish);
            //TODO 返回false说明什么？
            if (ok) {
                result.success("修改成功!");
            }
        }

        return result;
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "xxxxxxxxxxx-通过id删除")
    @ApiOperation(value = "xxxxxxxxxxx-通过id删除", notes = "xxxxxxxxxxx-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<EventReportFinish> delete(@RequestParam(name = "id", required = true) String id) {
        Result<EventReportFinish> result = new Result<EventReportFinish>();
        EventReportFinish eventReportFinish = eventReportFinishService.getById(id);
        if (eventReportFinish == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = eventReportFinishService.removeById(id);
            if (ok) {
                result.success("删除成功!");
            }
        }

        return result;
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "xxxxxxxxxxx-批量删除")
    @ApiOperation(value = "xxxxxxxxxxx-批量删除", notes = "xxxxxxxxxxx-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<EventReportFinish> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        Result<EventReportFinish> result = new Result<EventReportFinish>();
        if (ids == null || "".equals(ids.trim())) {
            result.error500("参数不识别！");
        } else {
            this.eventReportFinishService.removeByIds(Arrays.asList(ids.split(",")));
            result.success("删除成功!");
        }
        return result;
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "xxxxxxxxxxx-通过id查询")
    @ApiOperation(value = "xxxxxxxxxxx-通过id查询", notes = "xxxxxxxxxxx-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<EventReportFinish> queryById(@RequestParam(name = "id", required = true) String id) {
        Result<EventReportFinish> result = new Result<EventReportFinish>();
        EventReportFinish eventReportFinish = eventReportFinishService.getById(id);
        if (eventReportFinish == null) {
            result.error500("未找到对应实体");
        } else {
            result.setResult(eventReportFinish);
            result.setSuccess(true);
        }
        return result;
    }

    /**
     * 导出excel
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, HttpServletResponse response) {
        // Step.1 组装查询条件
        QueryWrapper<EventReportFinish> queryWrapper = null;
        try {
            String paramsStr = request.getParameter("paramsStr");
            if (oConvertUtils.isNotEmpty(paramsStr)) {
                String deString = URLDecoder.decode(paramsStr, "UTF-8");
                EventReportFinish eventReportFinish = JSON.parseObject(deString, EventReportFinish.class);
                queryWrapper = QueryGenerator.initQueryWrapper(eventReportFinish, request.getParameterMap());
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //Step.2 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        List<EventReportFinish> pageList = eventReportFinishService.list(queryWrapper);
        //导出文件名称
        mv.addObject(NormalExcelConstants.FILE_NAME, "xxxxxxxxxxx列表");
        mv.addObject(NormalExcelConstants.CLASS, EventReportFinish.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("xxxxxxxxxxx列表数据", "导出人:Jeecg", "导出信息"));
        mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
        return mv;
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile file = entity.getValue();// 获取上传文件对象
            ImportParams params = new ImportParams();
            params.setTitleRows(2);
            params.setHeadRows(1);
            params.setNeedSave(true);
            try {
                List<EventReportFinish> listEventReportFinishs = ExcelImportUtil.importExcel(file.getInputStream(), EventReportFinish.class, params);
                for (EventReportFinish eventReportFinishExcel : listEventReportFinishs) {
                    eventReportFinishService.save(eventReportFinishExcel);
                }
                return Result.ok("文件导入成功！数据行数:" + listEventReportFinishs.size());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return Result.error("文件导入失败:" + e.getMessage());
            } finally {
                try {
                    file.getInputStream().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return Result.ok("文件导入失败！");
    }

}
