package org.jeecg.modules.scheduling.service.impl;

import org.jeecg.modules.scheduling.entity.SchedulePerson;
import org.jeecg.modules.scheduling.mapper.SchedulePersonMapper;
import org.jeecg.modules.scheduling.service.ISchedulePersonService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Description: 1
 * @Author: jeecg-boot
 * @Date:   2019-06-18
 * @Version: V1.0
 */
@Service
public class SchedulePersonServiceImpl extends ServiceImpl<SchedulePersonMapper, SchedulePerson> implements ISchedulePersonService {

    @Resource
    private SchedulePersonMapper schedulePersonMapper;

    @Override
    public List<Map<String,Object>> findSchedulePerson(String id){
        return schedulePersonMapper.findSchedulePerson(id);
    }

}
