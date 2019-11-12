package org.jeecg.modules.even.controller;


import javax.servlet.http.HttpServletRequest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.even.entity.EventReport;
import org.jeecg.modules.even.entity.EventReportsBacklog;
import org.jeecg.modules.even.service.IEventReportService;
import org.jeecg.modules.even.service.IEventReportBacklogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @Description: 上报事件关联用户
 * @Author: jeecg-boot
 * @Date: 2019-06-06
 * @Version: V1.0
 */
@Slf4j
@Api(tags = "上报事件关联用户")
@RestController
@RequestMapping("/even/eventBacklog")
public class EventReportBacklogController {
    @Autowired
    private IEventReportBacklogService eventReportBacklogService;

    @Autowired
    private IEventReportService eventReportService;

    /**
     * 分页列表查询
     *
     * @param eventReport
     * @param pageNo
     * @param pageSize
     * @return
     */
    @AutoLog(value = "上报事件关联用户-分页列表查询")
    @ApiOperation(value = "上报事件关联用户-分页列表查询", notes = "上报事件关联用户-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<EventReport>> queryPageList(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                    String title, String evenStatus, String beginDate, String endDate,
                                                    String evenType, String evenProperty, String evenLevel)  {
        Result<IPage<EventReport>> result = new Result<IPage<EventReport>>();
        Page<EventReport> page = new Page<EventReport>(pageNo, pageSize);
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        IPage<EventReport> pageList = eventReportService.reportList(page,sysUser.getId(),title,beginDate,endDate,evenType,evenProperty,evenLevel,evenStatus,"backlogList");
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    /**
     * 添加
     *
     * @param request
     * @return
     */
    @AutoLog(value = "上报事件关联用户-添加")
    @ApiOperation(value = "上报事件关联用户-添加", notes = "上报事件关联用户-添加")
    @GetMapping(value = "/dealWith")
    public Result<EventReportsBacklog> dealWith(HttpServletRequest request) {
        Result<EventReportsBacklog> result = new Result<EventReportsBacklog>();
        String reportId = request.getParameter("id");
        String userIds = request.getParameter("userId");
        EventReport eventReport = eventReportService.getById(reportId);
        QueryWrapper<EventReportsBacklog> queryWrapper = new QueryWrapper<EventReportsBacklog>();
        queryWrapper.eq("report_id", reportId);
        List<EventReportsBacklog> eventReportsBacklog=eventReportBacklogService.list(queryWrapper);
        if (eventReport == null||eventReportsBacklog.size()==0) {
            result.error500("事件已处理");
            return result;
        }
        try {
            eventReportService.dealWith(eventReport, userIds);
            result.success("操作成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500("操作失败");
        }
        return result;
    }


    /**
     * 根据reportId查询相关的人员
     *
     * @param
     * @return
     */
    @GetMapping(value = "/queryByReportId")
    @ResponseBody
    public Result<List<Object>> queryByReportId(HttpServletRequest request) {
        Result<List<Object>> result = new Result<>();
        String id = request.getParameter("id");
        List list = eventReportBacklogService.queryCopyPerson(id);
        result.setSuccess(true);
        result.setResult(list);
        return result;
    }

    @GetMapping(value = "/queryDealList")
    @ResponseBody
    public Result<List<Object>> queryDealList(HttpServletRequest request) {
        String reportId = request.getParameter("reportId");
        List list = eventReportBacklogService.queryDealList(reportId);
        Result<List<Object>> result = new Result<>();
        if (list.size() > 0) {
            result.setResult(list);
        }
        result.setSuccess(true);
        return result;
    }

}
