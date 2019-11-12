package org.jeecg.modules.scheduling.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecg.modules.common.entity.BaseNativeEntity;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author yzx
 */
@Entity
@Table(name = "eg_schedule_define")
@Data
@TableName("eg_schedule_define")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Setter
@Getter
public class ScheduleDefine extends BaseNativeEntity {

    private static final long serialVersionUID = -1495553874482675709L;
    /**
     * 班次
     */
    @Excel(name = "班次", width = 15, dicCode = "schedule_type")
    @Dict(dicCode = "schedule_type")
    @Column(name = "schedule_type")
    private String scheduleType;

    /**
     * 开始时间
     */
    @Column(name = "start_time")
    private String startTime;

    /**
     * 结束
     */
    @Column(name = "end_time")
    private String endTime;

    /**
     * 该班次后不能的班次
     */
    @Column(name = "refuse_schedule")
    private String refuseSchedule;

    /**
     * 描述
     */
    @Column(name = "schedule_describe")
    private String scheduleDescribe;
    /**
     * 优先级
     */
    @Column(name = "priority")
    private String priority;

    /**
     * 状态
     */
    @Column(name = "schedule_status")
    private String scheduleStatus;
}
