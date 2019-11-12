package org.jeecg.modules.scheduling.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecg.modules.common.entity.BaseNativeEntity;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author yzx
 */
@Entity
@Table(name = "eg_schedule_setting")
@Data
@TableName("eg_schedule_setting")
@Setter
@Getter
@ApiModel(value="班次设置", description="设置班次信息")
public class ScheduleSetting extends BaseNativeEntity {

    private static final long serialVersionUID = 8725643049155032834L;
    /**
     * 星期
     */
    @Excel(name = "星期", width = 15, dicCode = "week")
    @Dict(dicCode = "week")
    @Column(name = "week")
    private String week;

    /**
     * 人数
     */
    @Column(name = "num")
    private String num;

    /**
     * 班次定义Id
     */
    @Column(name = "schedule_id")
    private String scheduleId;

    @Transient
    private String scheduleType;
}
