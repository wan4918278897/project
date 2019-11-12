package org.jeecg.modules.scheduling.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.scheduling.entity.ScheduleStaff;
import org.jeecg.modules.scheduling.entity.ScheduleStaffForm;

/**
 * @Description: 1
 * @Author: jeecg-boot
 * @Date: 2019-06-18
 * @Version: V1.0
 */
public interface IScheduleStaffService extends IService<ScheduleStaff> {

    public IPage<ScheduleStaffForm> scheduleStaffList(Page<ScheduleStaffForm> page, String queryValue);


}
