package org.jeecg.modules.scheduling.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.util.DateUtils;
import org.jeecg.modules.scheduling.entity.ScheduleDefine;
import org.jeecg.modules.scheduling.entity.SchedulePerson;
import org.jeecg.modules.scheduling.mapper.ScheduleDefineMapper;
import org.jeecg.modules.scheduling.service.IScheduleDefineService;
import org.jeecg.modules.scheduling.service.ISchedulePersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description: 1
 * @Author: jeecg-boot
 * @Date: 2019-06-18
 * @Version: V1.0
 */
@Service
public class ScheduleDefineServiceImpl extends ServiceImpl<ScheduleDefineMapper, ScheduleDefine> implements IScheduleDefineService {


    @Resource
    private ScheduleDefineMapper scheduleDefineMapper;

    @Autowired
    private ISchedulePersonService schedulePersonService;

    @Override
    @Transactional
    public void defineSave(ScheduleDefine scheduleDefines, String ids) {
        if (StringUtils.isNotBlank(scheduleDefines.getId())){
            updateById(scheduleDefines);
        }else {
            save(scheduleDefines);
        }
        for (String s : ids.split(",")) {
            QueryWrapper<SchedulePerson> queryWrapper = new QueryWrapper<SchedulePerson>();
            queryWrapper.eq("user_id", s).eq("schedule_define_id", scheduleDefines.getId());
            SchedulePerson schedulePersons = schedulePersonService.getOne(queryWrapper);
            if (schedulePersons != null) {
                continue;
            }
            SchedulePerson schedulePerson = new SchedulePerson();
            schedulePerson.setScheduleDefineId(scheduleDefines.getId());
            schedulePerson.setUserId(s);
            schedulePersonService.save(schedulePerson);
        }

    }


    @Override
    public List<Map<String, String>> queryScheduleList(int week) {
        return scheduleDefineMapper.queryScheduleList(week);
    }


    @Override
    public List<Map<String, String>> selectPerson(String restDays, String defineId) {
        return scheduleDefineMapper.selectPerson(restDays, defineId);
    }

    @Override
    public List<Map<String, String>> selectDefineList() {
        return scheduleDefineMapper.selectDefineList();
    }
}
