package org.jeecg.modules.even.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.even.entity.EventDealInformation;

import java.util.List;
import java.util.Map;

/**
 * @Description: 222
 * @Author: jeecg-boot
 * @Date:   2019-06-13
 * @Version: V1.0
 */
public interface IEventDealInformationService extends IService<EventDealInformation> {

    /***
     * 事件处理保存关联数据
     * @param eventDealInformation
     */
    public void dealInformationdSave(EventDealInformation eventDealInformation);

    public List<Map<String,Object>> findInformation(String reportId);

}
