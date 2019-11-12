package org.jeecg.modules.even.controller;

import java.util.*;
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
import org.jeecg.modules.even.entity.EvenCallPhone;
import org.jeecg.modules.even.entity.EventReport;
import org.jeecg.modules.even.service.IEvenCallPhoneService;
import org.jeecg.modules.even.service.IEventReportService;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.even.service.IEventSettingService;
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
 * @Description: 事件上报
 * @Author: jeecg-boot
 * @Date: 2019-06-03
 * @Version: V1.0
 */
@Slf4j
@Api(tags = "事件上报")
@RestController
@RequestMapping("/even/eventReport")
public class EventReportController {
    @Autowired
    private IEventReportService eventReportService;

    @Autowired
    private IEvenCallPhoneService evenCallPhoneService;

    @Autowired
    private IEventSettingService eventSettingService;

    /**
     * 分页列表查询
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
//    @AutoLog(value = "事件上报-分页列表查询")
//    @ApiOperation(value = "事件上报-分页列表查询", notes = "事件上报-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<EventReport>> queryPageList(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                    String title, String evenStatus, String beginDate, String endDate,
                                                    String evenType, String evenProperty, String evenLevel) {
        Result<IPage<EventReport>> result = new Result<IPage<EventReport>>();
        Page<EventReport> page = new Page<EventReport>(pageNo, pageSize);
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        String userId = sysUser.getId();
        IPage<EventReport> pageList = eventReportService.reportList(page,userId,title,beginDate,endDate,evenType,evenProperty,evenLevel,evenStatus,"reportList");

        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }


    /**
     * 添加
     *
     * @param EventReport
     * @return
     */
    @PostMapping(value = "/add")
    public Result<EventReport> add(@RequestBody EventReport EventReport, HttpServletRequest request) {
        Result<EventReport> result = new Result<EventReport>();
        try {
            String userIds = request.getParameter("userIds");
            String relateId = request.getParameter("relateId");
            eventReportService.saveReports(EventReport, userIds, relateId);
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
     * @param
     * @return
     */

    @PutMapping(value = "/edit")
    public Result<EventReport> edit(@RequestBody EventReport EventReport, HttpServletRequest request) {
        Result<EventReport> result = new Result<EventReport>();
        EventReport eventReportEntity = eventReportService.getById(EventReport.getId());
        if (eventReportEntity == null) {
            result.error500("未找到对应实体");
        } else {
            String userIds = request.getParameter("userIds");
            eventReportService.saveReports(EventReport, userIds, EventReport.getId());
            //TODO 返回false说明什么？
//            if (ok) {
//                result.success("修改成功!");updateBatchById
//            }
        }

        return result;
    }

    @GetMapping(value = "/selectValue")
    public List<Map<String,Object>> selectValue() {
        List<Map<String,Object>> settingList = eventSettingService.selectValue();
        return settingList;
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "事件上报-通过id删除")
    @ApiOperation(value = "事件上报-通过id删除", notes = "事件上报-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<EventReport> delete(@RequestParam(name = "id", required = true) String id) {
        Result<EventReport> result = new Result<EventReport>();
        EventReport eventReport = eventReportService.getById(id);
        if (eventReport == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = eventReportService.removeById(id);
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
    @AutoLog(value = "事件上报-批量删除")
    @ApiOperation(value = "事件上报-批量删除", notes = "事件上报-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<EventReport> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        Result<EventReport> result = new Result<EventReport>();
        if (ids == null || "".equals(ids.trim())) {
            result.error500("参数不识别！");
        } else {
            this.eventReportService.removeByIds(Arrays.asList(ids.split(",")));
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
    @AutoLog(value = "事件上报-通过id查询")
    @ApiOperation(value = "事件上报-通过id查询", notes = "事件上报-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<EventReport> queryById(@RequestParam(name = "id", required = true) String id) {
        Result<EventReport> result = new Result<EventReport>();
        EventReport eventReport = eventReportService.getById(id);
        if (eventReport == null) {
            result.error500("未找到对应实体");
        } else {
            result.setResult(eventReport);
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
        QueryWrapper<EventReport> queryWrapper = null;
        try {
            String paramsStr = request.getParameter("paramsStr");
            if (oConvertUtils.isNotEmpty(paramsStr)) {
                String deString = URLDecoder.decode(paramsStr, "UTF-8");
                EventReport eventReport = JSON.parseObject(deString, EventReport.class);
                queryWrapper = QueryGenerator.initQueryWrapper(eventReport, request.getParameterMap());
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //Step.2 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        List<EventReport> pageList = eventReportService.list(queryWrapper);
        //导出文件名称
        mv.addObject(NormalExcelConstants.FILE_NAME, "事件上报列表");
        mv.addObject(NormalExcelConstants.CLASS, EventReport.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("事件上报列表数据", "导出人:Jeecg", "导出信息"));
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
                List<EventReport> listEventReports = ExcelImportUtil.importExcel(file.getInputStream(), EventReport.class, params);
                for (EventReport eventReportExcel : listEventReports) {
                    eventReportService.save(eventReportExcel);
                }
                return Result.ok("文件导入成功！数据行数:" + listEventReports.size());
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


    @AutoLog(value = "事件上报-分页列表查询")
    @ApiOperation(value = "事件上报-分页列表查询", notes = "事件上报-分页列表查询")
    @GetMapping(value = "/publishList")
    public Result<IPage<EventReport>> publishList(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                  @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                  String title, String beginDate, String endDate, String evenType, String evenProperty, String evenLevel)  {
        Result<IPage<EventReport>> result = new Result<IPage<EventReport>>();
        Page<EventReport> page = new Page<EventReport>(pageNo, pageSize);
        IPage<EventReport> pageList = eventReportService.reportList(page,"",title,beginDate,endDate,evenType,evenProperty,evenLevel, String.valueOf(ReportStatus.publish),"auditingList");
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    @GetMapping(value = "/getReceivePhone")
    public String getReceivePhone() {
        String phone = "";
        QueryWrapper<EvenCallPhone> queryWrapper = new QueryWrapper<EvenCallPhone>();
        queryWrapper.orderByDesc("create_time");
        EvenCallPhone evenCallPhone = evenCallPhoneService.getOne(queryWrapper);
        if (evenCallPhone != null) {
            phone = evenCallPhone.getPhone();
        }
        return phone;
    }

    @RequestMapping(value = "/receivePhone", method = RequestMethod.POST)
    @AutoLog(value = "receivePhone")
    @ApiOperation(value = "receivePhone", notes = "事件上报-分页列表查询")
    public void receivePhone(@RequestBody String phone) {
        EvenCallPhone evenCallPhone = new EvenCallPhone();
        evenCallPhone.setPhone(phone);
        evenCallPhoneService.save(evenCallPhone);
    }

}
