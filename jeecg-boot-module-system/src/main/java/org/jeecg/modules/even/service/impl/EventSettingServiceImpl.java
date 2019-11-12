package org.jeecg.modules.even.service.impl;

import org.jeecg.modules.even.entity.EventSetting;
import org.jeecg.modules.even.mapper.EventSettingMapper;
import org.jeecg.modules.even.service.IEventSettingService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Description: 事件类型
 * @Author: jeecg-boot
 * @Date:   2019-07-08
 * @Version: V1.0
 */
@Service
public class EventSettingServiceImpl extends ServiceImpl<EventSettingMapper, EventSetting> implements IEventSettingService {

    @Resource
    private EventSettingMapper eventSettingMapper;

    @Override
    public List<Map<String,Object>> selectValue(){
        return eventSettingMapper.selectValue();
    }
}
