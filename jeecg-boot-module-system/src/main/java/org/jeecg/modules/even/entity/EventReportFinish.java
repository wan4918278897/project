package org.jeecg.modules.even.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jeecg.modules.common.entity.BaseNativeEntity;

import javax.persistence.*;

/**
 * @author yzx
 */
@Data
@TableName("ev_report_finish")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="上报事件办结表", description="上报事件办结表")
@Entity
@Table(name = "ev_report_finish")
@Setter
@Getter
public class EventReportFinish extends BaseNativeEntity {

    @Column(name = "report_id")
    private String reportId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "step_name")
    private String stepName;

}
