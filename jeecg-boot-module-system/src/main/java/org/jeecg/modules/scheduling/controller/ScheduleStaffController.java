package org.jeecg.modules.scheduling.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.util.oConvertUtils;

import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.scheduling.entity.SchedulePerson;
import org.jeecg.modules.scheduling.entity.ScheduleStaff;
import org.jeecg.modules.scheduling.entity.ScheduleStaffForm;
import org.jeecg.modules.scheduling.service.IScheduleStaffService;
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
 * @Description: 1
 * @Author: jeecg-boot
 * @Date: 2019-06-18
 * @Version: V1.0
 */
@Slf4j
@Api(tags = "1")
@RestController
@RequestMapping("/scheduling/scheduleStaff")
public class ScheduleStaffController {
    @Autowired
    private IScheduleStaffService scheduleStaffService;

    /**
     * 分页列表查询
     *
     * @param
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "1-分页列表查询")
    @ApiOperation(value = "1-分页列表查询", notes = "1-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<ScheduleStaffForm>> queryPageList(
            @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
            HttpServletRequest req) {
        String queryValue = req.getParameter("queryValue");
        if (StringUtils.isBlank(queryValue)) {
            queryValue = "";
        }
        Result<IPage<ScheduleStaffForm>> result = new Result<IPage<ScheduleStaffForm>>();
        Page<ScheduleStaffForm> page = new Page<ScheduleStaffForm>(pageNo, pageSize);
        IPage<ScheduleStaffForm> pageList = scheduleStaffService.scheduleStaffList(page, queryValue);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    /**
     * 添加
     *
     * @param
     * @return
     */
    @AutoLog(value = "1-添加")
    @ApiOperation(value = "1-添加", notes = "1-添加")
    @PostMapping(value = "/add")
    public Result<ScheduleStaff> add(@RequestBody Map<String, String> userIds) {
        Result<ScheduleStaff> result = new Result<ScheduleStaff>();
        try {
            String userId = userIds.get("userIds");
            if (StringUtils.isNotBlank(userId)) {
                for (String s : userId.split(",")) {
                    QueryWrapper<ScheduleStaff> queryWrapper = new QueryWrapper<ScheduleStaff>();
                    queryWrapper.eq("user_id", s);
                    ScheduleStaff scheduleStaffs=scheduleStaffService.getOne(queryWrapper);
                    if (scheduleStaffs != null) {
                        continue;
                    }
                    ScheduleStaff scheduleStaff = new ScheduleStaff();
                    scheduleStaff.setUserId(s);
                    scheduleStaffService.save(scheduleStaff);
                }
            }
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
     * @param scheduleStaff
     * @return
     */
    @AutoLog(value = "1-编辑")
    @ApiOperation(value = "1-编辑", notes = "1-编辑")
    @PutMapping(value = "/edit")
    public Result<ScheduleStaff> edit(@RequestBody ScheduleStaff scheduleStaff) {
        Result<ScheduleStaff> result = new Result<ScheduleStaff>();
        ScheduleStaff scheduleStaffEntity = scheduleStaffService.getById(scheduleStaff.getId());
        if (scheduleStaffEntity == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = scheduleStaffService.updateById(scheduleStaff);
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
    @AutoLog(value = "1-通过id删除")
    @ApiOperation(value = "1-通过id删除", notes = "1-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<ScheduleStaff> delete(@RequestParam(name = "id", required = true) String id) {
        Result<ScheduleStaff> result = new Result<ScheduleStaff>();
        ScheduleStaff scheduleStaff = scheduleStaffService.getById(id);
        if (scheduleStaff == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = scheduleStaffService.removeById(id);
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
    @AutoLog(value = "1-批量删除")
    @ApiOperation(value = "1-批量删除", notes = "1-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<ScheduleStaff> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        Result<ScheduleStaff> result = new Result<ScheduleStaff>();
        if (ids == null || "".equals(ids.trim())) {
            result.error500("参数不识别！");
        } else {
            this.scheduleStaffService.removeByIds(Arrays.asList(ids.split(",")));
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
    @AutoLog(value = "1-通过id查询")
    @ApiOperation(value = "1-通过id查询", notes = "1-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<ScheduleStaff> queryById(@RequestParam(name = "id", required = true) String id) {
        Result<ScheduleStaff> result = new Result<ScheduleStaff>();
        ScheduleStaff scheduleStaff = scheduleStaffService.getById(id);
        if (scheduleStaff == null) {
            result.error500("未找到对应实体");
        } else {
            result.setResult(scheduleStaff);
            result.setSuccess(true);
        }
        return result;
    }

    @PostMapping(value = "/setRest")
    public Result<ScheduleStaff> setRest(@RequestBody Map<String,String> map){
        Result<ScheduleStaff> result = new Result<ScheduleStaff>();
        String str=map.get("str");
        String id=map.get("id");
        ScheduleStaff scheduleStaff=scheduleStaffService.getById(id);
        if (scheduleStaff!=null){
            scheduleStaff.setRestDays(str);
            scheduleStaffService.updateById(scheduleStaff);
        }
        result.setSuccess(true);
        return result;
    }

}
