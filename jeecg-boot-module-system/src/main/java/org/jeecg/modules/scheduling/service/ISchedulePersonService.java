package org.jeecg.modules.scheduling.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.scheduling.entity.SchedulePerson;

import java.util.List;
import java.util.Map;

/**
 * @Description: 1
 * @Author: jeecg-boot
 * @Date:   2019-06-18
 * @Version: V1.0
 */
public interface ISchedulePersonService extends IService<SchedulePerson> {

    /**
     *班次定义时候回显查询人员
     * @param id
     * @return
     */
    public List<Map<String,Object>> findSchedulePerson(String id);

}
