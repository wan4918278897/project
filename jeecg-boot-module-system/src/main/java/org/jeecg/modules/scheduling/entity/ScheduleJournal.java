package org.jeecg.modules.scheduling.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.modules.common.entity.BaseNativeEntity;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 值班日志
 * */
@Entity
@Data
@Table(name = "eg_schedule_journal")
@TableName("eg_schedule_journal")
public class ScheduleJournal extends BaseNativeEntity {
    //记录人
    @Column(name="user_id")
    private String userId;

    //标题
    private String title;

    //班次
    @Column(name="schedule_id")
    private String scheduleId;

    //班次类型
    @Column(name="schedule_type")
    private String scheduleType;

    //值班日期
    @Excel(name = "dutyTime", width = 20, format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "dutyDate")
    @Column(name="duty_date")
    private Date dutyDate;

    //查看人
    private String viewer;

    //值班记录
    @Column(name="duty_log")
    private String dutyLog;
}
