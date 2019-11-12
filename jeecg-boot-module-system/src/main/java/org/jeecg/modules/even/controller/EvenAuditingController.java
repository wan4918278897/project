package org.jeecg.modules.even.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.common.utils.WebSocket;
import org.jeecg.modules.even.entity.EvenAuditing;
import org.jeecg.modules.even.entity.EventReport;
import org.jeecg.modules.even.service.IEvenAuditingService;
import org.jeecg.modules.even.service.IEventReportService;
import org.jeecg.modules.even.utils.ReportStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Description: 关联处理信息Id
 * @Author: jeecg-boot
 * @Date: 2019-06-14
 * @Version: V1.0
 */
@Slf4j
@Api(tags = "上报事件审核")
@RestController
@RequestMapping("/even/evenAuditing")
public class EvenAuditingController {
    @Autowired
    private IEvenAuditingService evenAuditingService;

    @Autowired
    private IEventReportService eventReportService;

    @Autowired
    private WebSocket webSocket;
    /**
     * 分页列表查询
     * @param evenAuditing
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    /**
     * 分页列表查询
     *
     * @param
     * @param pageNo
     * @param pageSize
     * @return
     */
    @AutoLog(value = "事件上报-分页列表查询")
    @ApiOperation(value = "事件上报-分页列表查询", notes = "事件上报-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<EventReport>> queryPageList(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                    String title, String beginDate, String endDate,
                                                    String evenType, String evenProperty, String evenLevel)  {
        Result<IPage<EventReport>> result = new Result<IPage<EventReport>>();
        Page<EventReport> page = new Page<EventReport>(pageNo, pageSize);
        IPage<EventReport> pageList = eventReportService.reportList(page,"",title,beginDate,endDate,evenType,evenProperty,evenLevel, String.valueOf(ReportStatus.noAuditing),"auditingList");
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    /**
     * 添加
     *
     * @param evenAuditing
     * @return
     */

    @PostMapping(value = "/add")
    public Result<EvenAuditing> add(@RequestBody EvenAuditing evenAuditing) {
        Result<EvenAuditing> result = new Result<EvenAuditing>();
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        evenAuditing.setUserId(sysUser.getId());
        try {
            evenAuditingService.evenAuditingSave(evenAuditing);
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
     * @param evenAuditing
     * @return
     */
    @AutoLog(value = "关联处理信息Id-编辑")
    @ApiOperation(value = "关联处理信息Id-编辑", notes = "关联处理信息Id-编辑")
    @PutMapping(value = "/edit")
    public Result<EvenAuditing> edit(@RequestBody EvenAuditing evenAuditing) {
        Result<EvenAuditing> result = new Result<EvenAuditing>();
        EvenAuditing evenAuditingEntity = evenAuditingService.getById(evenAuditing.getId());
        if (evenAuditingEntity == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = evenAuditingService.updateById(evenAuditing);
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
    @AutoLog(value = "关联处理信息Id-通过id删除")
    @ApiOperation(value = "关联处理信息Id-通过id删除", notes = "关联处理信息Id-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<EvenAuditing> delete(@RequestParam(name = "id", required = true) String id) {
        Result<EvenAuditing> result = new Result<EvenAuditing>();
        EvenAuditing evenAuditing = evenAuditingService.getById(id);
        if (evenAuditing == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = evenAuditingService.removeById(id);
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
    @AutoLog(value = "关联处理信息Id-批量删除")
    @ApiOperation(value = "关联处理信息Id-批量删除", notes = "关联处理信息Id-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<EvenAuditing> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        Result<EvenAuditing> result = new Result<EvenAuditing>();
        if (ids == null || "".equals(ids.trim())) {
            result.error500("参数不识别！");
        } else {
            this.evenAuditingService.removeByIds(Arrays.asList(ids.split(",")));
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
    @AutoLog(value = "关联处理信息Id-通过id查询")
    @ApiOperation(value = "关联处理信息Id-通过id查询", notes = "关联处理信息Id-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<EvenAuditing> queryById(@RequestParam(name = "id", required = true) String id) {
        Result<EvenAuditing> result = new Result<EvenAuditing>();
        EvenAuditing evenAuditing = evenAuditingService.getById(id);
        if (evenAuditing == null) {
            result.error500("未找到对应实体");
        } else {
            result.setResult(evenAuditing);
            result.setSuccess(true);
        }
        return result;
    }


    @PostMapping(value = "/doBack")
    public Result<EvenAuditing> doBack(@RequestBody Map<String, String> map) {
        Result<EvenAuditing> result = new Result<EvenAuditing>();
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        String reason = map.get("reason");
        String reportId = map.get("reportId");
        evenAuditingService.doBack(reportId, reason);
        result.setSuccess(true);

        return result;
    }


}
