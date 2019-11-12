package org.jeecg.modules.even.entity;

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

/**
 * @author yzx
 */
@Data
@TableName("ev_report_phone")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="上报电话表")
@Entity
@Table(name = "ev_report_phone")
@Setter
@Getter
public class EvenCallPhone extends BaseNativeEntity {
    private static final long serialVersionUID = -8454317101942659798L;

    @Column(name = "phone")
    private String phone;
}
