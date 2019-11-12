package org.jeecg.modules.even.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.even.entity.EventReport;
import org.jeecg.modules.even.entity.EventReportFinish;
import org.jeecg.modules.even.entity.EventReportsBacklog;
import org.jeecg.modules.even.mapper.EventReportBacklogMapper;
import org.jeecg.modules.even.service.IEventReportBacklogService;
import org.jeecg.modules.even.service.IEventReportService;
import org.jeecg.modules.even.utils.ReportStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Description: 上报事件关联用户
 * @Author: jeecg-boot
 * @Date: 2019-06-06
 * @Version: V1.0
 */
@Service
public class EventReportBacklogServiceImpl extends ServiceImpl<EventReportBacklogMapper, EventReportsBacklog> implements IEventReportBacklogService {

    @Resource
    private EventReportBacklogMapper eventReportBacklogMapper;


    @Override
    public List<Map<String,Object>> queryCopyPerson(String id) {
        return eventReportBacklogMapper.queryCopyPerson(id);
    }

    @Override
    public List<Map<String,Object>> queryDealList(String id){
        return eventReportBacklogMapper.queryDealList(id);
    }

}
