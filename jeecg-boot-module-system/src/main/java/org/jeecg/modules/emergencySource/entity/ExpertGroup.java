package org.jeecg.modules.emergencySource.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.jeecg.modules.common.entity.BaseNativeEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Description: 专家组
 * @Author: jeecg-boot
 * @Date:   2019-05-24
 * @Version: V1.0
 */
@Entity
@Data
@Table(name = "es_expert_group")
@TableName("es_expert_group")
public class ExpertGroup extends BaseNativeEntity {
    /**
     * 专家组编号
     */
    @Column(name = "group_id")
    private String groupId;

    /**
     * 专家组名称
     */
    @Column(name = "group_name")
    private String groupName;

    /**
     * 组长
     */
    private String leader;

    /**
     * 联系电话
     */
    private String tel;

    /**
     * 第一副组长
     */
    @Column(name = "sub_leader1")
    private String subLeader1;

    /**
     * 第二副组长
     */
    @Column(name = "sub_leader2")
    private String subLeader2;

    /**
     * 成员组成
     */
    private String nationality;

    /**
     * 工作内容
     */
    @Column(name = "work_unit",length=500)
    private String workUnit;

    /**
     * 备注
     */
    @Column(length=500)
    private String notes;
}
