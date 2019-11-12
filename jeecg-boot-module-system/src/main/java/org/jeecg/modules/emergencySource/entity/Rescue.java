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
 * @Description: 应急救援队伍信息
 * @Author: jeecg-boot
 * @Date:   2019-05-24
 * @Version: V1.0
 */
@Entity
@Data
@Table(name = "es_rescue")
@TableName("es_rescue")
public class Rescue extends BaseNativeEntity {
    /**
     * 队伍编号
     */
    @Column(name = "team_id")
    private String teamId;

    /**
     * 名称
     */
    private String name;

    /**
     * 主管部门
     */
    @Column(name = "supervisal_dep")
    private String supervisalDep;

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
     * 行政区划代码
     */
    @Column(name = "district_id")
    private String districtId;

    /**
     * 负责人
     */
    private String principal;

    /**
     * 负责人电话
     */
    @Column(name = "principal_tel")
    private String principalTel;

    /**
     * 负责人移动电话
     */
    @Column(name = "mobile_tel")
    private String mobileTel;

    /**
     * 值班电话
     */
    @Column(name = "duty_tel")
    private String dutyTel;

    /**
     * 传真
     */
    private String fax;

    /**
     * 现有人数
     */
    @Column(name = "member_cnt",columnDefinition = "tinyint(1) DEFAULT '0'", nullable = false)
    private Integer memberCnt;    /**

     * 人员编制数
     */
    @Column(name = "employees",columnDefinition = "tinyint(1) DEFAULT '0'", nullable = false)
    private Integer employees;

    /**
     * 成立时间
     */
    @Column(name = "founded_date")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date foundedDate;

    /**
     * 主要装备描述
     */
    @Column(name = "equip_desc",length = 2000)
    private String equipDesc;

    /**
     * 专长描述
     */
    @Column(length=2000)
    private String skilled;

    /**
     * 救援队类型
     */
    @Column(name = "team_type")
    private String teamType;

    /**
     * 备注
     */
    @Column(length=500)
    private String notes;
}