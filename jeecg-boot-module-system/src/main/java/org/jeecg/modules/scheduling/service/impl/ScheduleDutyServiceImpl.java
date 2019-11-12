package org.jeecg.modules.scheduling.service.impl;


import org.jeecg.modules.scheduling.entity.ScheduleDuty;
import org.jeecg.modules.scheduling.mapper.ScheduleDutyMapper;
import org.jeecg.modules.scheduling.service.IScheduleDutyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Description: 12321
 * @Author: jeecg-boot
 * @Date: 2019-06-20
 * @Version: V1.0
 */
@Service
public class ScheduleDutyServiceImpl extends ServiceImpl<ScheduleDutyMapper, ScheduleDuty> implements IScheduleDutyService {

    @Resource
    private ScheduleDutyMapper scheduleDutyMapper;


    @Override
    public List<Map<String,String>> calenderList(String startDate, String endDate) {
        return scheduleDutyMapper.calenderList(startDate, endDate);
    }
}
