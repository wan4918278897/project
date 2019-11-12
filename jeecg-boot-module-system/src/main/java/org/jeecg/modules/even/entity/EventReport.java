package org.jeecg.modules.even.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecg.modules.common.entity.BaseNativeEntity;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author yzx
 */
@Getter
@Setter
@Entity
@Accessors(chain = true)
@Access(AccessType.FIELD)
@Table(name = "ev_report_pre")
@TableName("ev_report_pre")
public class EventReport extends BaseNativeEntity {

    @Excel(name = "事件标题", width = 15)
    @Column(name = "title")
    private String title;

    @Excel(name = "事件类型", width = 15)
    @Column(name = "even_type")
    private String evenType;


    @Excel(name = "事件性质", width = 15)
    @Column(name = "even_property")
    private String evenProperty;


    @Excel(name = "事件级别", width = 15)
    @Column(name = "even_level")
    private String evenLevel;


    @Excel(name = "事发时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "date")
    private Date date;

    @Excel(name = "行政区划", width = 15)
    @Column(name = "administrative_division")
    private String administrativeDivision;

    @Excel(name = "预案名称", width = 15)
    @Column(name = "pre_plan_name")
    private String prePlanName ;

    @Excel(name = "事发地点", width = 15)
    @Column(name = "place")
    private String place ;

    @Excel(name = "事发原因", width = 15)
    @Column(name = "reason")
    private String reason ;

    @Excel(name = "事件描述", width = 15)
    @Column(name = "even_describe")
    private String evenDescribe ;

    @Excel(name = "死亡人数", width = 15)
    @Column(name = "death_num")
    private Integer deathNum ;

    @Excel(name = "受伤人数", width = 15)
    @Column(name = "injured_num")
    private Integer injuredNum ;

    @Excel(name = "失踪人数", width = 15)
    @Column(name = "miss_num")
    private Integer missNum ;

//    @Excel(name = "事件状态", width = 15, dicCode = "even_status")
//    @Dict(dicCode = "even_status")
    @Column(name = "even_status",columnDefinition = "tinyint(1) DEFAULT '0'", nullable = false)
    private Integer evenStatus ;

    /**
     * 填报人
     */
    @Column(name = "apply_user")
    private String applyUser ;

    /**
     * 联系人电话
     */
    @Column(name = "phone")
    private String phone ;

    /**
     * 联系人姓名
     */
    @Column(name = "name")
    private String name ;



}
