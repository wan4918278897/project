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
 * @Description: 危险源
 * @Author: jeecg-boot
 * @Date:   2019-05-24
 * @Version: V1.0
 */
@Entity
@Data
@Table(name = "es_danger")
@TableName("es_danger")
public class Danger extends BaseNativeEntity {

    /**
     * 危险源编号
     */
    @Column(name = "danger_id",length=32)
    private String dangerId;

    /**
     * 名称
     */
    @Column(name = "danger_name",length=50)
    private String dangerName;

    /**
     * 类型
     */
    @Column(name = "danger_type",length=10)
    private String dangerType;

    /**
     * 行政区划代码
     */
    @Column(name = "district_id",length=12)
    private String districtId;

    /**
     * 所属单位
     */
    @Column(name = "supervisal_org",length=60)
    private String supervisalOrg;

    /**
     * 所属单位组织机构代码
     */
    @Column(name = "org_code",length=10)
    private String orgCode;

    /**
     * 地址
     */
    @Column(length=200)
    private String address;

    /**
     * 负责人
     */
    @Column(length=50)
    private String principal;

    /**
     * 联系电话
     */
    @Column(length=50)
    private String tel;

    /**
     * 移动电话
     */
    @Column(name = "mobile_tel",length=50)
    private String mobileTel;

    /**
     * 传真
     */
    @Column(length=20)
    private String fax;

    /**
     * 邮编
     */
    @Column(length=6)
    private String postcode;

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
     * 建成时间
     */
    @Column(name = "build_date")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date buildDate;

    /**
     * 可能危害形式
     */
    @Column(name = "possible_event")
    private String possibleEvent;

    /**
     * 危险级别
     */
    @Column(name = "hazard_lev")
    private String hazardLev;

    /**
     * 影响范围
     */
    @Column(name = "influence_scope")
    private String influenceScope;


    /**
     * 备注
     */
    @Column(name = "notes",length=500)
    private String notes;

}
