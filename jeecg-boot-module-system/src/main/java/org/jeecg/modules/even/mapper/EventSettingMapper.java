package org.jeecg.modules.even.mapper;

import org.jeecg.modules.even.entity.EventSetting;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * @Description: 事件类型
 * @Author: jeecg-boot
 * @Date:   2019-07-08
 * @Version: V1.0
 */
public interface EventSettingMapper extends BaseMapper<EventSetting> {

    public List<Map<String,Object>> selectValue();
}
