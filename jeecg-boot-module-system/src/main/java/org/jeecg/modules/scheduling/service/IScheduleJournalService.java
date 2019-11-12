package org.jeecg.modules.scheduling.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.scheduling.entity.ScheduleJournal;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * @Description: 值班日志
 * @Author: jeecg-boot
 * @Date:   2019-07-23
 * @Version: V1.0
 */
public interface IScheduleJournalService extends IService<ScheduleJournal> {
    /**
     *班次定义时候回显查询人员
     * @param id
     * @return
     */
    public List<Map<String,Object>> findSchedulePerson(String id);

    IPage<ScheduleJournal> queryList(Page page, @Param("userId")String userId, @Param("checkDate")String checkDate);
}
