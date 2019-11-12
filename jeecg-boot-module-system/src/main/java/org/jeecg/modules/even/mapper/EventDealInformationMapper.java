package org.jeecg.modules.even.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.even.entity.EventDealInformation;

import java.util.List;
import java.util.Map;


/**
 * @Description: 222
 * @Author: jeecg-boot
 * @Date:   2019-06-13
 * @Version: V1.0
 */
public interface EventDealInformationMapper extends BaseMapper<EventDealInformation> {

    List<Map<String,Object>>  findInformation(@Param("reportId") String reportId);

}
