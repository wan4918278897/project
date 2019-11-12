package org.jeecg.modules.emergencySource.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.jeecg.modules.common.entity.BaseNativeEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Description: 应急管理机构
 * @Author: jeecg-boot
 * @Date:   2019-05-24
 * @Version: V1.0
 */
@Entity
@Data
@Table(name = "es_emorg")
@TableName("es_emorg")
public class Emorg extends BaseNativeEntity {
    /**
     * 机构编号
     */
    @Column(name = "org_id")
    private String orgId;

    /**
     * 名称
     */
    @Column(name = "org_name")
    private String orgName;

    /**
     * 机构分类
     */
    @Column(name = "org_type")
    private String orgType;

    /**
     * 机构职责
     */
    @Column(name = "org_duty",length=2000)
    private String orgDuty;

    /**
     * 行政区划代码
     */
    @Column(name = "ref_district_id")
    private String refDistrictId;

    /**
     * 地址
     */
    private String address;

    /**
     * 经度
     */
    @Column(precision=8,scale=4)
    private Float longitude;

    /**
     * 纬度
     */
    @Column(precision=8,scale=4)
    private Float latitude;

    /**
     * 高程
     */
    @Column(precision=8,scale=4)
    private Float elevation;

    /**
     * 邮编
     */
    @Column(name = "post_code",length=6)
    private String postCode;

    /**
     * 应急办领导
     */
    @Column(name = "first_leader")
    private String firstLeader;

    /**
     * 应急办领导联系电话
     */
    @Column(name = "first_tel")
    private String firstTel;

    /**
     * 应急办领导移动电话
     */
    @Column(name = "first_mobile_tel")
    private String firstMobileTel;

    /**
     * 分管领导
     */
    @Column(name = "second_leader")
    private String secondLeader;

    /**
     * 分管领导联系电话
     */
    @Column(name = "second_tel")
    private String secondTel;

    /**
     * 分管领导移动电话
     */
    @Column(name = "second_mobile_tel")
    private String secondMobileTel;

    /**
     * 值班电话
     */
    @Column(name = "duty_tel")
    private String dutyTel;

    /**
     * 值班传真
     */
    @Column(name = "duty_fax")
    private String dutyFax;

    /**
     * 备注
     */
    @Column(length = 500)
    private String notes;

}
