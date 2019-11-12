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
@TableName("ev_report_auditing")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="信息审核表", description="记录审核信息")
@Entity
@Table(name = "ev_report_auditing")
@Setter
@Getter
public class EvenAuditing extends BaseNativeEntity {
    private static final long serialVersionUID = 4179457762027141806L;

//    /**
//     * 关联上报信息记录Id
//     */
//    @Column(name = "report_id")
//    private String reportId;

    /**
     * 记录处理人
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 记录过程
     */
    @Column(name = "process")
    private String process;

    /**
     * 结论
     */
    @Column(name = "result")
    private String result;

    /**
     * 关联处理信息Id
     */
    @Column(name = "deal_information_id")
    private String dealInformationId;

    /**
     * 关联上报事件id
     */
    @Column(name = "report_id")
    private String reportId;
}
