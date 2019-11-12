package org.jeecg.modules.scheduling.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.ColumnDefault;
import org.jeecg.modules.common.entity.BaseNativeEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "eg_schedule_staff")
@Data
@TableName("eg_schedule_staff")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Setter
@Getter
@ApiModel(value="值班人员", description="参与值班的人员")
public class ScheduleStaff extends BaseNativeEntity {

    /**
     * 用户Id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 状态
     */
    @Column(name = "schedule_status")
    private String scheduleStatus;

    /**
     * 休息日期
     */
    @Column(name = "rest_days",columnDefinition = "varchar(80)  DEFAULT '0'", nullable = false)
    private String restDays;
}
