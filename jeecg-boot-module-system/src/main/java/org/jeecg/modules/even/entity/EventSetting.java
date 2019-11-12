package org.jeecg.modules.even.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.jeecg.modules.common.entity.BaseNativeEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author yzx
 */
@Entity
@Data
@Table(name = "ev_event_setting")
@TableName("ev_event_setting")
public class EventSetting extends BaseNativeEntity {
    /**
     * 事件类型
     * */
    @Column(name="event_type")
    private String eventType;
    /**
     * 顺序
     *  */
    @Column(name="event_name")
    private String eventName;

    @Column(name="event_code")
    private String eventCode;

    @Column(name="sort")
    private Integer sort;
}