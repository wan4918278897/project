package org.jeecg.modules.even.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.even.entity.EventReportsBacklog;

/**
 * @Description: 上报事件关联用户
 * @Author: jeecg-boot
 * @Date:   2019-06-06
 * @Version: V1.0
 */
public interface EventReportBacklogMapper extends BaseMapper<EventReportsBacklog> {

    /**
     *  根据事件上报Id查询抄送人员
     * @param id
     * @return
     */
    List<Map<String,Object>> queryCopyPerson(@Param("id") String id);

    /**
     *  查询流程当前处理人
     * @param id
     * @return
     */
    List<Map<String,Object>>  queryDealList(@Param("id") String id);
}
