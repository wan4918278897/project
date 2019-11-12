package org.jeecg.modules.scheduling.mapper;

        import java.util.List;
        import java.util.Map;

        import org.apache.ibatis.annotations.Param;
        import com.baomidou.mybatisplus.core.mapper.BaseMapper;
        import org.jeecg.modules.scheduling.entity.ScheduleDuty;

/**
 * @Description: 12321
 * @Author: jeecg-boot
 * @Date: 2019-06-20
 * @Version: V1.0
 */
public interface ScheduleDutyMapper extends BaseMapper<ScheduleDuty> {

    /**
     * 日历显示每个星期数据
     * @param startDate
     * @param endDate
     * @return
     */
    public List<Map<String,String>> calenderList(@Param("startDate") String startDate, @Param("endDate") String endDate);

}
