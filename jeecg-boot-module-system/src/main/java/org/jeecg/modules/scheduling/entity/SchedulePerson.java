package org.jeecg.modules.scheduling.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jeecg.modules.common.entity.BaseNativeEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "eg_schedule_person")
@Data
@ApiModel(value="班次关联表", description="班次关联员工记录")
@TableName("eg_schedule_person")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Setter
@Getter
public class SchedulePerson extends BaseNativeEntity {

    /**
     * 关联班次的ID
     */
    @Column(name = "schedule_define_id")
    private String scheduleDefineId;

    /**
     * 关联班次的员工
     */
    @Column(name = "user_id")
    private String userId;

}
