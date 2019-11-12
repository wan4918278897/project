package org.jeecg.modules.scheduling.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.scheduling.entity.ScheduleStaff;
import org.jeecg.modules.scheduling.entity.ScheduleStaffForm;

/**
 * @Description: 1
 * @Author: jeecg-boot
 * @Date: 2019-06-18
 * @Version: V1.0
 */
public interface ScheduleStaffMapper extends BaseMapper<ScheduleStaff> {

    IPage<ScheduleStaffForm> scheduleStaffList(Page page, @Param("queryValue") String queryValue);

}
