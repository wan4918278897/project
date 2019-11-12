package org.jeecg.modules.even.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.even.entity.EventReportsBacklog;

import java.util.List;
import java.util.Map;

/**
 * @Description: 上报事件关联用户
 * @Author: jeecg-boot
 * @Date: 2019-06-06
 * @Version: V1.0
 */
public interface IEventReportBacklogService extends IService<EventReportsBacklog> {

    public List<Map<String,Object>> queryCopyPerson(String id);

    public List<Map<String,Object>> queryDealList(String reportId);


}
