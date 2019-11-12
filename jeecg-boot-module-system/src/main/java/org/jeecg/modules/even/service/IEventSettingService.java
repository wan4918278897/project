package org.jeecg.modules.even.service;

import org.jeecg.modules.even.entity.EventSetting;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * @Description: 事件类型
 * @Author: jeecg-boot
 * @Date:   2019-07-08
 * @Version: V1.0
 */
public interface IEventSettingService extends IService<EventSetting> {

    /**
     * 查询事件上报下拉框值进行排序
     * @return
     */
    public List<Map<String,Object>> selectValue();


}
