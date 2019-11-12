package org.jeecg.modules.scheduling.mapper;


        import com.baomidou.mybatisplus.core.mapper.BaseMapper;
        import org.apache.ibatis.annotations.Param;
        import org.jeecg.modules.scheduling.entity.ScheduleDefine;

        import java.util.List;
        import java.util.Map;

/**
 * @Description: 1
 * @Author: jeecg-boot
 * @Date: 2019-06-18
 * @Version: V1.0
 */
public interface ScheduleDefineMapper extends BaseMapper<ScheduleDefine> {

    /**
     * 查询这个星期后面几天的排班情况
     * @param week
     * @return
     */
    List<Map<String, String>> queryScheduleList(@Param("week") int week);

    /**
     * 自动排班查询过滤掉正在休息的人
     * @param restDays
     * @param defineId
     * @return
     */
    List<Map<String, String>> selectPerson(@Param("restDays") String restDays, @Param("defineId") String defineId);

    /**
     *选择班次定义时的下拉框取值
     * @return
     */
    List<Map<String, String>> selectDefineList();

}
