package org.jeecg.modules.scheduling.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecg.modules.common.entity.BaseNativeEntity;
import org.jeecgframework.poi.excel.annotation.Excel;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * @author yzx
 */
@Entity
@Table(name = "eg_schedule_duty")
@Data
@TableName("eg_schedule_duty")
@Setter
@Getter
public class ScheduleDuty extends BaseNativeEntity {

    /**
     * 开始时间 时分
     */

    @Column(name = "start_time")
    private String startTime;

    /**
     *结束时间  时分
     */
    @Column(name = "end_time")
    private String endTime;

    /**
     * 开始日期 精确的到天
     */
    @Column(name = "start_date")
    private String startDate;

    /**
     * 结束日期 精确的到天
     */
    @Column(name = "end_date")
    private String endDate;

    /**
     * 值班人Id
     */
    @Column(name = "user_id")
    private String userId;


    /**
     * 班次
     */
    @Column(name = "schedule_id")
    private String scheduleId;

}
