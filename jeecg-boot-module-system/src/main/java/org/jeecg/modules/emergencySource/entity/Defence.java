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
 * @Description: 防护目标
 * @Author: jeecg-boot
 * @Date:   2019-05-24
 * @Version: V1.0
 */
@Entity
@Data
@Table(name = "es_defence")
@TableName("es_defence")
public class Defence extends BaseNativeEntity {
    /**
     * 防护目标编号
     */
    @Column(name = "defence_id")
    private String defenceId;

    /**
     * 名称
     */
    @Column(name = "defence_name")
    private String defenceName;

    /**
     * 类型
     */
    @Column(name = "defence_type")
    private String defenceType;

    /**
     * 行政区划代码
     */
    @Column(name = "district_id")
    private String districtId;

    /**
     * 所属单位或机构
     */
    @Column(name = "spervisal_dep")
    private String spervisalDep;

    /**
     * 所属单位组织机构代码
     */
    @Column(name = "org_code")
    private String orgCode;

    /**
     * 地址
     */
    private String address;

    /**
     * 负责人
     */
    private String principal;

    /**
     * 联系电话
     */
    private String tel;

    /**
     * 移动电话
     */
    @Column(name = "mobile_tel")
    private String mobileTel;

    /**
     * 传真
     */
    private String fax;

    /**
     * 邮编
     */
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
     * 受灾形式
     */
    @Column(name = "possible_event")
    private String possibleEvent;

    /**
     * 防护等级
     */
    @Column(name = "defence_lev")
    private String defenceLev;

    /**
     * 防护区域
     */
    @Column(name = "defence_scope")
    private String defenceScope;

    /**
     * 备注
     */
    @Column(length = 500)
    private String notes;
}
