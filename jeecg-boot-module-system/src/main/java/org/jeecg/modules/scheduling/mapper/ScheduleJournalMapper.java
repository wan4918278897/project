package org.jeecg.modules.scheduling.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.scheduling.entity.ScheduleJournal;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 值班日志
 * @Author: jeecg-boot
 * @Date:   2019-07-23
 * @Version: V1.0
 */
public interface ScheduleJournalMapper extends BaseMapper<ScheduleJournal> {
    /**
     *  查询流程当前处理人
     * @param id
     * @return
     */
    Map<String,Object> findSchedulePerson(@Param("id") String id);

    IPage<ScheduleJournal> queryList(Page page, @Param("userId")String userId,  @Param("checkDate")String checkDate);
}
