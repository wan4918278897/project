package org.jeecg.modules.even.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jeecg.modules.common.entity.BaseNativeEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * @author yzx
 */
@Getter
@Setter
@Entity
@Accessors(chain = true)
@Access(AccessType.FIELD)
@Table(name = "ev_report_backLog")
@TableName("ev_report_backLog")
public class EventReportsBacklog extends BaseNativeEntity {


    @Column(name = "report_id")
    private String reportId;

    @Column(name = "user_id")
    private String userId;


}
