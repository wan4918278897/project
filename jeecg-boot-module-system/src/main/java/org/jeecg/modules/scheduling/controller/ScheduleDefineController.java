package org.jeecg.modules.scheduling.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.util.DateUtils;
import org.jeecg.common.util.oConvertUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.scheduling.entity.*;
import org.jeecg.modules.scheduling.service.*;
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
@RequestMapping("/scheduling/scheduleDefine")
public class ScheduleDefineController {
    @Autowired
    private IScheduleDefineService scheduleDefineService;

    @Autowired
    private ISchedulePersonService schedulePersonService;

    @Autowired
    private IScheduleDutyService scheduleDutyService;

    /**
     * 分页列表查询
     *
     * @param scheduleDefine
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "1-分页列表查询")
    @ApiOperation(value = "1-分页列表查询", notes = "1-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<ScheduleDefine>> queryPageList(ScheduleDefine scheduleDefine,
                                                       @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                       @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                       HttpServletRequest req) {
        Result<IPage<ScheduleDefine>> result = new Result<IPage<ScheduleDefine>>();
        QueryWrapper<ScheduleDefine> queryWrapper = QueryGenerator.initQueryWrapper(scheduleDefine, req.getParameterMap());
        Page<ScheduleDefine> page = new Page<ScheduleDefine>(pageNo, pageSize);
        IPage<ScheduleDefine> pageList = scheduleDefineService.page(page, queryWrapper);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    /**
     * 添加
     *
     * @param scheduleDefine
     * @return
     */
    @AutoLog(value = "1-添加")
    @ApiOperation(value = "1-添加", notes = "1-添加")
    @PostMapping(value = "/add")
    public Result<ScheduleDefine> add(@RequestBody ScheduleDefine scheduleDefine, HttpServletRequest res) {
        Result<ScheduleDefine> result = new Result<ScheduleDefine>();
        String ids = res.getParameter("userIds");
        try {
            scheduleDefineService.defineSave(scheduleDefine, ids);
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
     * @param scheduleDefine
     * @return
     */
    @AutoLog(value = "1-编辑")
    @ApiOperation(value = "1-编辑", notes = "1-编辑")
    @PutMapping(value = "/edit")
    public Result<ScheduleDefine> edit(@RequestBody ScheduleDefine scheduleDefine) {
        Result<ScheduleDefine> result = new Result<ScheduleDefine>();
        ScheduleDefine scheduleDefineEntity = scheduleDefineService.getById(scheduleDefine.getId());
        if (scheduleDefineEntity == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = scheduleDefineService.updateById(scheduleDefine);
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
    public Result<ScheduleDefine> delete(@RequestParam(name = "id", required = true) String id) {
        Result<ScheduleDefine> result = new Result<ScheduleDefine>();
        ScheduleDefine scheduleDefine = scheduleDefineService.getById(id);
        if (scheduleDefine == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = scheduleDefineService.removeById(id);
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
    public Result<ScheduleDefine> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        Result<ScheduleDefine> result = new Result<ScheduleDefine>();
        if (ids == null || "".equals(ids.trim())) {
            result.error500("参数不识别！");
        } else {
            this.scheduleDefineService.removeByIds(Arrays.asList(ids.split(",")));
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
    public Result<ScheduleDefine> queryById(@RequestParam(name = "id", required = true) String id) {
        Result<ScheduleDefine> result = new Result<ScheduleDefine>();
        ScheduleDefine scheduleDefine = scheduleDefineService.getById(id);
        if (scheduleDefine == null) {
            result.error500("未找到对应实体");
        } else {
            result.setResult(scheduleDefine);
            result.setSuccess(true);
        }
        return result;
    }

    /**
     * 自动排班接口
     */
    @PostMapping(value = "/autSchedule")
    public Result autSchedule() {
        Calendar calendar = Calendar.getInstance();
        Result result = new Result();
        //计算当前日期星期一和星期天的日期
        Calendar c1 = Calendar.getInstance();
        int mon = c1.get(Calendar.DAY_OF_WEEK) - 1;
        c1.add(Calendar.DATE, -mon + 1);
        Calendar c2 = Calendar.getInstance();
        int sun = c2.get(Calendar.DAY_OF_WEEK) - 1;
        if (sun == 0) {
            sun = 7;
        }
        c2.add(Calendar.DATE, -sun + 7);
        List<Map<String, String>> calenderList = scheduleDutyService.calenderList(DateUtils.formatDate(c1.getTime()), DateUtils.formatDate(c2.getTime()));
        if (calenderList.size() == 0) {
            //存储当天排版人的id
            String userIds = "";
            //获取当前星期
            int dayWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
            List<Map<String, String>> scheduleSettings = scheduleDefineService.queryScheduleList(dayWeek);
            for (Map<String, String> s : scheduleSettings) {
                //获取当天日期
                Calendar cal = Calendar.getInstance();
                //班次设定日期
                int i = Integer.parseInt(s.get("week"));
                //班次设定日期减去当天日期 计算日期
                int j = i - dayWeek;
                ScheduleDuty scheduleDuty = new ScheduleDuty();
                scheduleDuty.setStartTime(s.get("startTime"));
                scheduleDuty.setEndTime(s.get("endTime"));
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss");
                try {
                    Date startTime = simpleDateFormat.parse(s.get("startTime"));
                    Date endTime = simpleDateFormat.parse(s.get("endTime"));
                    cal.add(Calendar.DAY_OF_MONTH, j);
                    scheduleDuty.setStartDate(DateUtils.formatDate(cal.getTime()));
                    if (startTime.after(endTime) && "c".equals(s.get("scheduleType"))) {
                        cal.add(Calendar.DAY_OF_MONTH, 1);
                    }
                } catch (Exception e) {
                    result.error500("系统异常，排班生成失败");
                    return result;
                }
                scheduleDuty.setEndDate(DateUtils.formatDate(cal.getTime()));
                List userList = new ArrayList();
                //存储排版人的Id
                String userId = "";
                //筛选不在休息日期内的人
                List<Map<String, String>> list = scheduleDefineService.selectPerson(String.valueOf(i), s.get("id"));
                //昨天上晚班的人
                String filterUserId = "";
                //筛选上晚班的人不能上第二天早班的
                if (("a").equals(s.get("scheduleType"))) {
                    userIds = "";
                    //前提天日期
                    Calendar beforeDate = Calendar.getInstance();
                    beforeDate.add(Calendar.DAY_OF_MONTH, j - 1);

                    QueryWrapper<ScheduleDuty> queryWrappers = new QueryWrapper<ScheduleDuty>();
                    queryWrappers.eq("start_date", DateUtils.formatDate(beforeDate.getTime())).orderByDesc("start_time");
                    ScheduleDuty beforeScheduleDuty = scheduleDutyService.getOne(queryWrappers);
                    if (beforeScheduleDuty != null) {
                        ScheduleDefine scheduleDefine=scheduleDefineService.getById(beforeScheduleDuty.getScheduleId());
                        if ("c".equals(scheduleDefine.getScheduleType())){
                            filterUserId = beforeScheduleDuty.getUserId();
                        }
                    }
                }
                if (StringUtils.isNotBlank(filterUserId)) {
                    for (Map<String, String> sp : list) {
                        if (!filterUserId.contains(sp.get("userId"))) {
                            userList.add(sp.get("userId"));
                        }
                    }
                } else {
                    for (Map<String, String> sp : list) {
                        userList.add(sp.get("userId"));
                    }
                }
                //获取参与排班人员的数目
                int length = userList.size();
                String src = s.get("num");
                int num = Integer.parseInt(src);
                Random ran = new Random();
                //已经排班的人数
                int addUserNum = 0;
                if (length>=num){
                    while (addUserNum < num && length >= num-addUserNum) {
                        //随机产生的编号
                        int userIdNum = ran.nextInt(length);
                        //判断该用户今天是否存在排班情况，没有排班情况则添加
                        if (!userIds.contains(userList.get(userIdNum).toString())) {
                            userId += userList.get(userIdNum).toString() + ",";
                            addUserNum += 1;
                        }
                        userList.remove(userIdNum);
                        length -= 1;
                    }
                }
                userIds += userId;
//            //随机产生数据
                scheduleDuty.setUserId(userId);
                scheduleDuty.setScheduleId(s.get("id"));
                scheduleDutyService.save(scheduleDuty);
            }
            result.setSuccess(true);
            result.setMessage("排班生成成功!");
            return result;

        } else {
            result.error500("已存在排版记录，请手动修改");
            return result;
        }
    }

    /**
     * 选择班次下拉框取值
     */
    @GetMapping(value = "/selectDefineList")
    public Result<List<Map<String, String>>> selectDefineList() {
        Result<List<Map<String, String>>> result = new Result<>();
        List<Map<String, String>> list = scheduleDefineService.selectDefineList();
        result.setSuccess(true);
        result.setResult(list);
        return result;
    }


    @GetMapping(value = "/test")
    public void test() {
        String time1 = "09:10:00";
        String time2 = "10:10:00";

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss");
        try {
            Date startTime = simpleDateFormat.parse(time1);
            Date endTime = simpleDateFormat.parse(time2);
            if (startTime.after(endTime)) {
                System.out.println("1111111111");
            } else {
                System.out.println("3333333333333");

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        int i = c.get(Calendar.DAY_OF_WEEK) - 1;
        c.add(Calendar.DATE, -i + 1);
        System.out.println(DateUtils.formatDate(c.getTime()));


        Calendar c2 = Calendar.getInstance();
        int j = c2.get(Calendar.DAY_OF_WEEK) - 1;
        if (j == 0) {
            j = 7;
        }
        c2.add(Calendar.DATE, -j + 7);

        System.out.println(DateUtils.formatDate(c2.getTime()));
    }

}
