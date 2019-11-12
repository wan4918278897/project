package org.jeecg.modules.scheduling.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.scheduling.entity.ScheduleSetting;
import org.jeecg.modules.scheduling.mapper.ScheduleSettingMapper;
import org.jeecg.modules.scheduling.service.IScheduleSettingService;
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
public class ScheduleSettingServiceImpl extends ServiceImpl<ScheduleSettingMapper, ScheduleSetting> implements IScheduleSettingService {

    @Resource
    private ScheduleSettingMapper scheduleSettingMapper;

    @Override
    public void settingSave(ScheduleSetting scheduleSetting){
        String userIds=scheduleSetting.getWeek();
        for (String s : userIds.split(",")) {
            ScheduleSetting sct=new ScheduleSetting();
            sct.setNum(scheduleSetting.getNum());
            sct.setWeek(s);
            sct.setScheduleId(scheduleSetting.getScheduleId());
            save(sct);
        }
    }

    @Override
    public IPage<ScheduleSetting> scheduleSettingList(Page page){
        return scheduleSettingMapper.scheduleSettingList(page);
    }
}
