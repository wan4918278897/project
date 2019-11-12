package org.jeecg.modules.even.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.even.entity.EventDealInformation;
import org.jeecg.modules.even.entity.EventReport;
import org.jeecg.modules.even.entity.EventReportFinish;
import org.jeecg.modules.even.entity.EventReportsBacklog;
import org.jeecg.modules.even.mapper.EventDealInformationMapper;
import org.jeecg.modules.even.service.IEventDealInformationService;
import org.jeecg.modules.even.service.IEventReportBacklogService;
import org.jeecg.modules.even.service.IEventReportFinishService;
import org.jeecg.modules.even.service.IEventReportService;
import org.jeecg.modules.even.utils.ReportStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

/**
 * @Description: 222
 * @Author: jeecg-boot
 * @Date: 2019-06-13
 * @Version: V1.0
 */
@Service
public class EventDealInformationServiceImpl extends ServiceImpl<EventDealInformationMapper, EventDealInformation> implements IEventDealInformationService {

    @Autowired
    private IEventReportBacklogService eventReportBacklogService;
    @Autowired
    private IEventReportFinishService eventReportFinishService;
    @Autowired
    private IEventReportService eventReportService;

    @Resource
    private EventDealInformationMapper eventDealInformationMapper;

    @Override
    @Transactional
    public void dealInformationdSave(EventDealInformation eventDealInformation) {
        String reportId = eventDealInformation.getReportId();
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        QueryWrapper<EventReportsBacklog> queryBacklog = new QueryWrapper<EventReportsBacklog>();
        queryBacklog.eq("report_id", reportId);
        eventReportBacklogService.remove(queryBacklog);
        QueryWrapper<EventReportFinish> queryFinish = new QueryWrapper<EventReportFinish>();
        queryFinish.eq("report_id", eventDealInformation.getReportId()).eq("user_id", sysUser.getId());
        EventReportFinish eventReportFinishs = eventReportFinishService.getOne(queryFinish);
        if (eventReportFinishs == null) {
            EventReportFinish eventReportFinish = new EventReportFinish();
            eventReportFinish.setReportId(reportId);
            eventReportFinish.setUserId(sysUser.getId());
            eventReportFinish.setStepName("处理");
            eventReportFinishService.save(eventReportFinish);
        } else {
            eventReportFinishs.setReportId(reportId);
            eventReportFinishs.setUserId(sysUser.getId());
            eventReportFinishs.setStepName("处理");
            eventReportFinishService.updateById(eventReportFinishs);
        }
        EventReport eventReport = eventReportService.getById(reportId);
        eventReport.setEvenStatus(ReportStatus.noAuditing);
        eventReportService.updateById(eventReport);
        QueryWrapper<EventDealInformation> infoQuery = new QueryWrapper<EventDealInformation>();
        infoQuery.eq("report_id", eventDealInformation.getId()).eq("user_id", sysUser.getId());
        EventDealInformation queryInfo = getOne(infoQuery);
        if (queryInfo != null) {
            updateById(eventDealInformation);
        } else {
            eventDealInformation.setUserId(sysUser.getId());
            save(eventDealInformation);
        }

    }

    @Override
    public List<Map<String, Object>> findInformation(String reportId) {
        return eventDealInformationMapper.findInformation(reportId);
    }
}
