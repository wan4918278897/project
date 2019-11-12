package org.jeecg.modules.forecastInfo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.jeecg.modules.common.entity.BaseNativeEntity;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Description: 预案信息
 * @Author: jeecg-boot
 * @Date: 2019-05-24
 * @Version: V1.0
 */
@Entity
@Data
@Table(name = "ep_reserve_info")
@TableName("ep_reserve_info")
public class ReserveInfo extends BaseNativeEntity {

    @Excel(name = "发布人", width = 15)
    @Column(name = "user_id")
    private String userId;

    @Excel(name = "预案名称", width = 15)
    private String name;

    @Excel(name = "预案代号", width = 15)
    private String code;

    @Excel(name = "版本号", width = 15)
    private String version ;

    /**
     * 预案类别---关联预案设置Id
     */
    private String type;

    @Excel(name = "发布单位", width = 15)
    @Column(name = "publish_org")
    private String publishOrg;

    @Excel(name = "预案状态", width = 15)
    @Column(name = "reserve_statues",columnDefinition = "tinyint(1) DEFAULT '0'", nullable = false)
    private Integer reserveStatues;

    /**
     * 发布日期
     */
    @Column(name = "date")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd ")
    @DateTimeFormat(pattern="yyyy-MM-dd ")
    private Date date;

    /**
     * 预案内容
     */
    @Lob
    private String content;

    @Excel(name = "行政区划", width = 15)
    @Column(name = "administrative_division")
    private String administrativeDivision;

    @Excel(name = "备注", width = 15)
    private String remark;

}
