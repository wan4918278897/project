package org.jeecg.modules.even.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.common.utils.WebSocket;
import org.jeecg.modules.even.entity.*;
import org.jeecg.modules.even.mapper.EvenAuditingMapper;
import org.jeecg.modules.even.service.IEvenAuditingService;
import org.jeecg.modules.even.service.IEventDealInformationService;
import org.jeecg.modules.even.service.IEventReportService;
import org.jeecg.modules.even.utils.ReportStatus;
import org.jeecg.modules.system.entity.SysAnnouncement;
import org.jeecg.modules.system.entity.SysAnnouncementSend;
import org.jeecg.modules.system.service.ISysAnnouncementSendService;
import org.jeecg.modules.system.service.ISysAnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.transaction.Transactional;
import java.util.Date;

/**
 * @Description: 关联处理信息Id
 * @Author: jeecg-boot
 * @Date: 2019-06-14
 * @Version: V1.0
 */
@Service
public class EvenAuditingServiceImpl extends ServiceImpl<EvenAuditingMapper, EvenAuditing> implements IEvenAuditingService {

    @Autowired
    private IEventReportService eventReportService;

    @Autowired
    private EventReportBacklogServiceImpl eventReportBacklogService;

    @Autowired
    private IEventDealInformationService eventDealInformationService;

    @Autowired
    private ISysAnnouncementService sysAnnouncementService;

    @Autowired
    private ISysAnnouncementSendService sysAnnouncementSendService;

    @Autowired
    private WebSocket webSocket;

    /**
     * 上报信息审核
     */
    @Override
    @Transactional
    public void evenAuditingSave(EvenAuditing evenAuditing) {
        EventReport eventReport = eventReportService.getById(evenAuditing.getReportId());
        eventReport.setEvenStatus(ReportStatus.publish);
        eventReportService.updateById(eventReport);
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if (StringUtils.isNotBlank(evenAuditing.getId())) {
            updateById(evenAuditing);
        } else {
            evenAuditing.setUserId(sysUser.getId());
            save(evenAuditing);
        }


    }

    @Override
    @Transactional
    public void doBack(String reportId, String reason) {
        EventReport eventReport = eventReportService.getById(reportId);
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        eventReport.setEvenStatus(ReportStatus.noDeal);
        eventReportService.updateById(eventReport);
        QueryWrapper<EventDealInformation> queryWrapper = new QueryWrapper<EventDealInformation>();
        queryWrapper.eq("report_id", reportId);
        EventDealInformation eventDealInformation = eventDealInformationService.getOne(queryWrapper);
        EventReportsBacklog eventReportsBacklog = new EventReportsBacklog();
        eventReportsBacklog.setReportId(reportId);
        eventReportsBacklog.setUserId(eventDealInformation.getUserId());
        eventReportBacklogService.save(eventReportsBacklog);
        SysAnnouncement sysAnnouncement =new SysAnnouncement();
        sysAnnouncement.setMsgContent(reason);
        sysAnnouncement.setSender(sysUser.getRealname());
        sysAnnouncement.setSendStatus("1");
        sysAnnouncement.setUserIds(eventDealInformation.getUserId());
        sysAnnouncement.setTitile("上报事件审核");
        sysAnnouncement.setMsgType("USER");
        sysAnnouncement.setMsgCategory("1");
        sysAnnouncement.setPriority("H");
        sysAnnouncement.setDelFlag("0");
        Date date=new Date();
        sysAnnouncement.setSendTime(date);
        sysAnnouncementService.save(sysAnnouncement);
        SysAnnouncementSend sysAnnouncementSend =new SysAnnouncementSend();
        sysAnnouncementSend.setAnntId(sysAnnouncement.getId());
        sysAnnouncementSend.setUserId(eventDealInformation.getUserId());
        sysAnnouncementSend.setReadFlag("0");
        sysAnnouncementSendService.save(sysAnnouncementSend);
        webSocket.sendOneMessage(eventDealInformation.getUserId(), "您有一个上报事件被退回了！");

    }
}
