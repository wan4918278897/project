package org.jeecg.modules.even.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jeecg.modules.common.entity.BaseNativeEntity;

import javax.persistence.*;

/**
 * 事件处理保存关联数据
 * @author yzx
 */
@Getter
@Setter
@Entity
@Accessors(chain = true)
@Access(AccessType.FIELD)
@Table(name = "ev_report_deal")
@TableName("ev_report_deal")
public class EventDealInformation extends BaseNativeEntity {


    @Column(name = "report_id")
    private String reportId;

    @Column(name = "user_id")
    private String userId;


    @Column(name = "title")
    private String title;

    @Column(name = "process")
    private String process;

    @Column(name = "result")
    private String result;


}
