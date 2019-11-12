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

import org.jeecg.modules.even.entity.EvenAuditing;
import org.jeecg.modules.even.entity.EventDealInformation;
import org.jeecg.modules.even.service.IEvenAuditingService;
import org.jeecg.modules.even.service.IEventDealInformationService;
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
 * @Description: 222
 * @Author: jeecg-boot
 * @Date: 2019-06-13
 * @Version: V1.0
 */
@Slf4j
@Api(tags = "事件处理保存关联数据")
@RestController
@RequestMapping("/even/eventDealInformation")
public class EventDealInformationController {
    @Autowired
    private IEventDealInformationService eventDealInformationService;

    @Autowired
    private IEvenAuditingService iEvenAuditingService;

    /**
     * 分页列表查询
     *
     * @param eventDealInformation
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "222-分页列表查询")
    @ApiOperation(value = "222-分页列表查询", notes = "222-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<EventDealInformation>> queryPageList(EventDealInformation eventDealInformation,
                                                             @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                             @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                             HttpServletRequest req) {
        Result<IPage<EventDealInformation>> result = new Result<IPage<EventDealInformation>>();
        QueryWrapper<EventDealInformation> queryWrapper = QueryGenerator.initQueryWrapper(eventDealInformation, req.getParameterMap());
        Page<EventDealInformation> page = new Page<EventDealInformation>(pageNo, pageSize);
        IPage<EventDealInformation> pageList = eventDealInformationService.page(page, queryWrapper);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    /**
     * 添加
     *
     * @param eventDealInformation
     * @return
     */
    @PostMapping(value = "/add")
    public Result<EventDealInformation> add(@RequestBody EventDealInformation eventDealInformation) {
        Result<EventDealInformation> result = new Result<EventDealInformation>();
        try {

            eventDealInformationService.dealInformationdSave(eventDealInformation);
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
     * @param eventDealInformation
     * @return
     */
    @AutoLog(value = "222-编辑")
    @ApiOperation(value = "222-编辑", notes = "222-编辑")
    @PutMapping(value = "/edit")
    public Result<EventDealInformation> edit(@RequestBody EventDealInformation eventDealInformation) {
        Result<EventDealInformation> result = new Result<EventDealInformation>();
        EventDealInformation eventDealInformationEntity = eventDealInformationService.getById(eventDealInformation.getId());
        if (eventDealInformationEntity == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = eventDealInformationService.updateById(eventDealInformation);
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
    @AutoLog(value = "222-通过id删除")
    @ApiOperation(value = "222-通过id删除", notes = "222-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<EventDealInformation> delete(@RequestParam(name = "id", required = true) String id) {
        Result<EventDealInformation> result = new Result<EventDealInformation>();
        EventDealInformation eventDealInformation = eventDealInformationService.getById(id);
        if (eventDealInformation == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = eventDealInformationService.removeById(id);
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
    @AutoLog(value = "222-批量删除")
    @ApiOperation(value = "222-批量删除", notes = "222-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<EventDealInformation> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        Result<EventDealInformation> result = new Result<EventDealInformation>();
        if (ids == null || "".equals(ids.trim())) {
            result.error500("参数不识别！");
        } else {
            this.eventDealInformationService.removeByIds(Arrays.asList(ids.split(",")));
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
    @AutoLog(value = "222-通过id查询")
    @ApiOperation(value = "222-通过id查询", notes = "222-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<EventDealInformation> queryById(@RequestParam(name = "id", required = true) String id) {
        Result<EventDealInformation> result = new Result<EventDealInformation>();
        EventDealInformation eventDealInformation = eventDealInformationService.getById(id);
        if (eventDealInformation == null) {
            result.error500("未找到对应实体");
        } else {
            result.setResult(eventDealInformation);
            result.setSuccess(true);
        }
        return result;
    }


    @GetMapping(value = "/findInformation")
    public Result findInformation(HttpServletRequest request) {
        Result result = new Result<>();
        String reportId = request.getParameter("reportId");
        QueryWrapper<EventDealInformation> queryWrapper = new QueryWrapper<EventDealInformation>();
        queryWrapper.eq("report_id", reportId).orderByDesc("create_time");
        EventDealInformation eventDealInformation = eventDealInformationService.getOne(queryWrapper);
        List list = eventDealInformationService.findInformation(reportId);
        if (list.size() > 0) {
            result.setResult(list.get(0));
        }else {
            result.setResult(eventDealInformation);
        }
        result.setSuccess(true);
        return result;
    }

    @GetMapping(value = "/findAud")
    public Result<EvenAuditing> findAud(HttpServletRequest request) {
        Result<EvenAuditing> result = new Result<EvenAuditing>();
        String reportId = request.getParameter("reportId");
        QueryWrapper<EvenAuditing> queryWrapper = new QueryWrapper<EvenAuditing>();
        queryWrapper.eq("report_id", reportId);
        EvenAuditing auditing = iEvenAuditingService.getOne(queryWrapper);
        result.setSuccess(true);
        result.setResult(auditing);
        return result;
    }
}
