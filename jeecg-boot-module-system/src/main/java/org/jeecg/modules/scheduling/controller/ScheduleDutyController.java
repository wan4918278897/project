package org.jeecg.modules.scheduling.controller;

import java.util.*;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.scheduling.entity.ScheduleDefine;
import org.jeecg.modules.scheduling.entity.ScheduleDuty;
import org.jeecg.modules.scheduling.service.IScheduleDefineService;
import org.jeecg.modules.scheduling.service.IScheduleDutyService;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.service.ISysUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Description: 12321
 * @Author: jeecg-boot
 * @Date: 2019-06-20
 * @Version: V1.0
 */
@Slf4j
@Api(tags = "12321")
@RestController
@RequestMapping("/scheduling/scheduleDuty")
public class ScheduleDutyController {
    @Autowired
    private IScheduleDutyService scheduleDutyService;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private IScheduleDefineService scheduleDefineService;

    /**
     * 分页列表查询
     *
     * @param req
     * @return
     */
    @AutoLog(value = "12321-分页列表查询")
    @ApiOperation(value = "12321-分页列表查询", notes = "12321-分页列表查询")
    @GetMapping(value = "/list")
    public Result queryPageList(HttpServletRequest req) {
        Result result = new Result();
        String startDate = "2019-01-03";
        String endDate = "2020-07-30";
        List<Map<String, String>> list = scheduleDutyService.calenderList(startDate, endDate);
        for (Map<String, String> scheduleDuty : list) {
            String userId = scheduleDuty.get("userId");
            String userName = "";
            if (StringUtils.isNotBlank(userId)) {
                for (String s : userId.split(",")) {
                    SysUser sysUser = sysUserService.getById(s);
                    if (sysUser != null) {
                        userName += sysUser.getRealname() + ",";
                    }
                }
                scheduleDuty.put("userName", userName);
            }
        }
        result.setSuccess(true);
        result.setResult(list);
        return result;
    }

    /**
     * 添加
     *
     * @param scheduleDuty
     * @return
     */
    @AutoLog(value = "12321-添加")
    @ApiOperation(value = "12321-添加", notes = "12321-添加")
    @PostMapping(value = "/add")
    public Result<ScheduleDuty> add(@RequestBody ScheduleDuty scheduleDuty) {
        Result<ScheduleDuty> result = new Result<ScheduleDuty>();
        try {
            scheduleDutyService.save(scheduleDuty);
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
    @AutoLog(value = "12321-编辑")
    @ApiOperation(value = "12321-编辑", notes = "12321-编辑")
    @PutMapping(value = "/edit")
    public Result<ScheduleDuty> edit(@RequestBody Map<String, String> map) {
        Result<ScheduleDuty> result = new Result<ScheduleDuty>();
        String id = map.get("id");
        ScheduleDuty scheduleDuty = scheduleDutyService.getById(id);
        if (scheduleDuty == null) {
            result.error500("未找到对应实体");
        } else {
            scheduleDuty.setUserId(map.get("userIds"));
            boolean ok = scheduleDutyService.updateById(scheduleDuty);
            //TODO 返回false说明什么？
            if (ok) {
                result.success("修改成功!");
            }
        }
        return result;
    }

    @GetMapping(value = "/getDutyDetail")
    public Result<Map<String, Object>> getDutyDetail(HttpServletRequest request) {
        Result<Map<String, Object>> result = new Result<>();
        String id = request.getParameter("id");
        ScheduleDuty scheduleDuty = scheduleDutyService.getById(id);
        Map<String, Object> map = new HashMap<>();
        if (scheduleDuty != null) {
            map.put("id", scheduleDuty.getId());
            map.put("startDate", scheduleDuty.getStartDate());
            map.put("endDate", scheduleDuty.getEndDate());
            map.put("startTime", scheduleDuty.getStartTime());
            map.put("endTime", scheduleDuty.getEndTime());
            map.put("userIds", scheduleDuty.getUserId());
            List list = new ArrayList();
            for (String s : scheduleDuty.getUserId().split(",")) {
                SysUser sysUser = sysUserService.getById(s);
                if (sysUser != null) {
                    list.add(sysUser.getRealname());
                }
            }
            map.put("userName", list);
            ScheduleDefine scheduleDefine = scheduleDefineService.getById(scheduleDuty.getScheduleId());
            String describe = "";
            String scheduleType = "";
            String scheduleId = "";
            if (scheduleDefine != null) {
                describe = scheduleDefine.getScheduleDescribe();
                scheduleType = scheduleDefine.getScheduleType();
                scheduleId = scheduleDefine.getId();
            }
            map.put("describe", describe);
            map.put("scheduleType", scheduleType);
            map.put("scheduleId", scheduleId);
        }
        result.setSuccess(true);
        result.setResult(map);
        return result;
    }

    @DeleteMapping(value = "/delete")
    public Result<ScheduleDuty> delete(@RequestParam(name = "id", required = true) String id) {
        Result<ScheduleDuty> result = new Result<ScheduleDuty>();
        ScheduleDuty scheduleDuty = scheduleDutyService.getById(id);
        if (scheduleDuty == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = scheduleDutyService.removeById(id);
            if (ok) {
                result.success("删除成功!");
            }
        }
        return result;
    }

}
