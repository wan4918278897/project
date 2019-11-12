package org.jeecg.modules.forecastInfo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import org.jeecg.modules.common.entity.BaseNativeEntity;

import javax.persistence.*;

/**
 * @author yzx
 */
@Entity
@Getter
@Setter
@Table(name = "ep_reserve_setting")
@TableName("ep_reserve_setting")
public class ReserveSetting extends BaseNativeEntity {

    private String name;

    private String type;

    @Column(name="sort")
    private Integer sort;

}
