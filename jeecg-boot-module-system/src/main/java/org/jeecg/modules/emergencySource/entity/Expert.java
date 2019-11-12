package org.jeecg.modules.emergencySource.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.jeecg.modules.common.entity.BaseNativeEntity;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
/**
 * @Description: 专家
 * @Author: jeecg-boot
 * @Date:   2019-05-24
 * @Version: V1.0
 */
@Entity
@Data
@Table(name = "es_expert")
@TableName("es_expert")
public class Expert extends BaseNativeEntity {
    /**
     * 专家编号
     */
    @Column(name = "expert_id")
    private String expertId;

    /**
     * 专家姓名
     */
    private String name;

    /**
     * 专家类别
     */
    @Column(name = "expert_type")
    private String expertType;

    /**
     * 性别
     */
    private String sex;

    /**
     * 出生日期
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date birth;

    /**
     * 民族
     */
    private String nationality;

    /**
     * 工作单位
     */
    @Column(name = "work_org")
    private String workOrg;

    /**
     * 职称
     */
    private String post;

    /**
     * 行政职务
     */
    private String headship;

    /**
     * 专业类别
     */
    private String major;

    /**
     * 专业特长描述
     */
    private String proficient;

    /**
     * 移动电话
     */
    @Column(name = "mobile_tel")
    private String mobileTel;

    /**
     * 办公电话
     */
    @Column(name = "office_tel")
    private String officeTel;

    /**
     * 传真
     */
    @Column(name = "fax")
    private String fax;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 家庭电话
     */
    @Column(name = "home_tel")
    private String homeTel;

    /**
     * 家庭住址
     */
    @Column(name = "home_addr")
    private String homeAddr;

    /**
     * 证件类型
     */
    @Column(name = "cer_type")
    private String cerType;

    /**
     * 证件号码
     */
    @Column(name = "cer_no")
    private String cerNo;

    /**
     * 籍贯
     */
    @Column(name = "native_place")
    private String nativePlace;

    /**
     * 参加工作时间
     */
    @Column(name = "join_time")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date joinTime;

    /**
     * 政治面貌
     */
    private String party;

    /**
     * 健康状况
     */
    private String health;

    /**
     * 最高学历
     */
    private String qualification;

    /**
     * 毕业院校
     */
    private String graduate;

    /**
     * 工作单位主管部门
     */
    @Column(name = "supervisal_dep")
    private String supervisalDep;

    /**
     * 通信地址
     */
    @Column(name = "postal_address")
    private String postalAddress;

    /**
     * 单位邮编
     */
    private String postcode;

    /**
     * 户籍所在地
     */
    private String domicile;

    /**
     * 工作简历概述
     */
    @Column(length=1000)
    private String resume;

    /**
     * 所属专家组
     */
    @Column(name = "group_id")
    private String groupId;


    /**
     * 备注
     */
    @Column(length=500)
    private String notes;




}
