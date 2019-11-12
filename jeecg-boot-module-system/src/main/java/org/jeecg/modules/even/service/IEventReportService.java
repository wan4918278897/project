package org.jeecg.modules.even.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.even.entity.EventReport;

import java.beans.Transient;

/**
 * @Description: 事件上报
 * @Author: jeecg-boot
 * @Date: 2019-06-03
 * @Version: V1.0
 */
public interface IEventReportService extends IService<EventReport> {

    public IPage<EventReport> queryListByPersonId(Page<EventReport> page, String id);

    public IPage<EventReport> queryList(Page<EventReport> page, String userId);

    public IPage<EventReport> queryBacklogList(Page<EventReport> page, String userId);

    /***
     * 保存方法
     * @param eventReport
     * @param userIds
     * @param relateId 附件表保存的虚拟Id
     */
    public void saveReports(EventReport eventReport, String userIds ,String relateId);

    /***
     * 事件送处理
     * @param eventReport  上报事件Id
     * @param userId  处理人Id
     */
    public void dealWith(EventReport eventReport, String userId);

    /**
     * 已办事件
     * @param page  上报事件Id
     * @param userId  处理人Id
     */
    public IPage<EventReport> queryFinishList(Page page, String userId, String queryName);

    public  IPage<EventReport>  reportList(Page<EventReport> page,String userId ,String title,String beginDate,
                                           String endDate,String evenType,String evenProperty,String evenLevel, String evenStatus,String listType);

}


