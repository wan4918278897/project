package org.jeecg.modules.scheduling.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.scheduling.entity.SchedulePerson;

import java.util.List;
import java.util.Map;

/**
 * @Description: 1
 * @Author: jeecg-boot
 * @Date:   2019-06-18
 * @Version: V1.0
 */
public interface SchedulePersonMapper extends BaseMapper<SchedulePerson> {

    /**
     *  查询流程当前处理人
     * @param id
     * @return
     */
    List<Map<String,Object>> findSchedulePerson(@Param("id") String id);
}
