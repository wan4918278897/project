package org.jeecg.modules.scheduling.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.scheduling.entity.ScheduleDuty;

import java.util.List;
import java.util.Map;

/**
 * @Description: 12321
 * @Author: jeecg-boot
 * @Date:   2019-06-20
 * @Version: V1.0
 */
public interface IScheduleDutyService extends IService<ScheduleDuty> {

    /**
     * 日历查询一周的日期
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return
     */
    public List<Map<String,String>> calenderList(String startDate, String endDate);

}
