package org.jeecg.modules.scheduling.service.impl;


        import com.baomidou.mybatisplus.core.metadata.IPage;
        import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
        import org.jeecg.modules.scheduling.entity.ScheduleStaff;
        import org.jeecg.modules.scheduling.entity.ScheduleStaffForm;
        import org.jeecg.modules.scheduling.mapper.ScheduleStaffMapper;
        import org.jeecg.modules.scheduling.service.IScheduleStaffService;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;

        import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

        import javax.annotation.Resource;

/**
 * @Description: 1
 * @Author: jeecg-boot
 * @Date: 2019-06-18
 * @Version: V1.0
 */
@Service
public class ScheduleStaffServiceImpl extends ServiceImpl<ScheduleStaffMapper, ScheduleStaff> implements IScheduleStaffService {

    @Resource
    private ScheduleStaffMapper scheduleStaffMapper;

    @Override
    public IPage<ScheduleStaffForm> scheduleStaffList(Page<ScheduleStaffForm> page, String queryValue) {
        return scheduleStaffMapper.scheduleStaffList(page, queryValue);
    }

}
