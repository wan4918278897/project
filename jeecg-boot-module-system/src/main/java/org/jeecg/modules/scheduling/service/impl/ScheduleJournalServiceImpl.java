package org.jeecg.modules.scheduling.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.scheduling.entity.ScheduleJournal;
import org.jeecg.modules.scheduling.mapper.ScheduleJournalMapper;
import org.jeecg.modules.scheduling.mapper.SchedulePersonMapper;
import org.jeecg.modules.scheduling.service.IScheduleJournalService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Description: 值班日志
 * @Author: jeecg-boot
 * @Date:   2019-07-23
 * @Version: V1.0
 */
@Service
public class ScheduleJournalServiceImpl extends ServiceImpl<ScheduleJournalMapper, ScheduleJournal> implements IScheduleJournalService {
    @Resource
    private ScheduleJournalMapper scheduleJournalMapper;

    @Override
    public List<Map<String,Object>> findSchedulePerson(String ids){
        List<Map<String,Object>>list=new ArrayList<Map<String, Object>>() ;
        for(String id:ids.split(","))
            list.add(scheduleJournalMapper.findSchedulePerson(id));
        return list;
    }

    public IPage<ScheduleJournal> queryList(Page page, String userId, String checkDate)
    {
        return scheduleJournalMapper.queryList(page,userId,checkDate);
    }

}
