package org.jeecg.modules.even.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.common.utils.WebSocket;
import org.jeecg.modules.even.entity.EventReport;
import org.jeecg.modules.even.entity.EventReportFinish;
import org.jeecg.modules.even.entity.EventReportsBacklog;
import org.jeecg.modules.even.mapper.EventReportMapper;
import org.jeecg.modules.even.service.IEventReportBacklogService;
import org.jeecg.modules.even.service.IEventReportFinishService;
import org.jeecg.modules.even.service.IEventReportService;
import org.jeecg.modules.even.utils.ReportStatus;
import org.jeecg.modules.fileManage.entity.FileManage;
import org.jeecg.modules.fileManage.service.IFileManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @Description: 事件上报
 * @Author: jeecg-boot
 * @Date: 2019-06-03
 * @Version: V1.0
 */
@Service
public class EventReportServiceImpl extends ServiceImpl<EventReportMapper, EventReport> implements IEventReportService {

    @Resource
    private EventReportMapper eventReportMapper;

    @Autowired
    private IEventReportBacklogService eventReportBacklogService;

    @Autowired
    private IEventReportFinishService eventReportFinishService;

    @Autowired
    private IFileManageService fileManageService;

    @Autowired
    private WebSocket webSocket;

    @Override
    public IPage<EventReport> queryListByPersonId(Page<EventReport> page, String id) {
        return eventReportMapper.queryListByPersonId(page, id);
    }

    @Override
    public IPage<EventReport> queryList(Page<EventReport> page, String userId) {
        return eventReportMapper.queryList(page, userId);
    }

    @Override
    public IPage<EventReport> queryBacklogList(Page<EventReport> page, String userId) {
        return eventReportMapper.queryBacklogList(page, userId);
    }

    @Override
    public IPage<EventReport> queryFinishList(Page page, String userId, String queryName) {
        return eventReportMapper.queryFinishList(page, userId, queryName);
    }


    @Override
    @Transactional
    public void saveReports(EventReport eventReport, String userId, String relateId) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if (StringUtils.isBlank(eventReport.getId())) {
            eventReport.setApplyUser(sysUser.getId());
            save(eventReport);

            QueryWrapper<FileManage> queryFileWrappers = new QueryWrapper<FileManage>();
            queryFileWrappers.eq("relate_id", relateId).eq("relate_type", "1");
            List<FileManage> fileManageList=fileManageService.list(queryFileWrappers);
            if (fileManageList.size()>0){
                for (FileManage s:fileManageList){
                    s.setRelateId(eventReport.getId());
                    fileManageService.updateById(s);
                }
            }
        } else {
            updateById(eventReport);
            QueryWrapper<EventReportsBacklog> queryBacklogWrappers = new QueryWrapper<EventReportsBacklog>();
            queryBacklogWrappers.eq("report_id", eventReport.getId());
            eventReportBacklogService.remove(queryBacklogWrappers);
        }
        String reportId = eventReport.getId();
        if (StringUtils.isNotBlank(userId)) {
            QueryWrapper<EventReportFinish> queryFinishWrapper = new QueryWrapper<EventReportFinish>();
            queryFinishWrapper.eq("report_id", reportId).eq("user_id", sysUser.getId());
            eventReport.setEvenStatus(ReportStatus.haveReport);
            updateById(eventReport);
            EventReportFinish eventReportFinish = eventReportFinishService.getOne(queryFinishWrapper);
            if (eventReportFinish == null) {
                EventReportFinish reportFinish = new EventReportFinish();
                reportFinish.setStepName("申请人");
                reportFinish.setUserId(sysUser.getId());
                reportFinish.setReportId(reportId);
                eventReportFinishService.save(reportFinish);
            }
        }

        if (!StringUtils.isEmpty(userId)) {
            for (String s : userId.split(",")) {
                EventReportsBacklog eventReportsBacklog = new EventReportsBacklog();
                eventReportsBacklog.setReportId(reportId);
                eventReportsBacklog.setUserId(s);
                webSocket.sendOneMessage(s, "您有一个上报事件事项需要处理！");
                eventReportBacklogService.save(eventReportsBacklog);
            }
        }

    }


    @Override
    @Transactional
    public void dealWith(EventReport eventReport, String userIds) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if (eventReport != null) {
            eventReport.setEvenStatus(ReportStatus.noDeal);
            updateById(eventReport);
        }
        QueryWrapper<EventReportFinish> queryWrappers = new QueryWrapper<EventReportFinish>();
        queryWrappers.eq("report_id", eventReport.getId()).eq("user_id", sysUser.getId());
        EventReportFinish eventReportFinishs = eventReportFinishService.getOne(queryWrappers);
        if (eventReportFinishs == null) {
            EventReportFinish eventReportFinish = new EventReportFinish();
            eventReportFinish.setReportId(eventReport.getId());
            eventReportFinish.setUserId(sysUser.getId());
            eventReportFinish.setStepName("分派");
            eventReportFinishService.save(eventReportFinish);
        } else {
            eventReportFinishs.setReportId(eventReport.getId());
            eventReportFinishs.setUserId(sysUser.getId());
            eventReportFinishs.setStepName("分派");
            eventReportFinishService.updateById(eventReportFinishs);
        }

        QueryWrapper<EventReportsBacklog> queryWrapper = new QueryWrapper<EventReportsBacklog>();
        queryWrapper.eq("report_id", eventReport.getId());
        eventReportBacklogService.remove(queryWrapper);
        if (!StringUtils.isEmpty(userIds)) {
            for (String s : userIds.split(",")) {
                EventReportsBacklog eventReportsBacklog = new EventReportsBacklog();
                eventReportsBacklog.setReportId(eventReport.getId());
                eventReportsBacklog.setUserId(s);
                webSocket.sendOneMessage(s, "您有一个上报事件事项需要处理！");
                eventReportBacklogService.save(eventReportsBacklog);
            }

        }
    }

    @Override
    public IPage<EventReport> reportList(Page<EventReport> page, String userId, String title
            , String beginDate, String endDate, String evenType, String evenProperty, String evenLevel, String evenStatus, String listType) {
        return eventReportMapper.reportList(page, userId, title, beginDate, endDate, evenType, evenProperty, evenLevel, evenStatus, listType);

    }

}
