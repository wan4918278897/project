package org.jeecg.modules.forecastInfo.entity;


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
 * @Description: 预测信息
 * @Author: jeecg-boot
 * @Date: 2019-05-24
 * @Version: V1.0
 */
@Entity
@Data
@Table(name = "ep_forecast_info")
@TableName("ep_forecast_info")
public class ForecastInfo extends BaseNativeEntity {

    /**
     * 预测编号
     */
    @Column(name = "fore_id")
    private String foreId;


    /**
     * 信息标题
     */
    @Column(name = "fore_title")
    private String foreTitle;


    /**
     * 事件类型
     */
    @Column(name = "fore_type")
    private String foreType;


    /**
     * 预测单位
     */
    @Column(name = "fore_dep")
    private String foreDep;


    /**
     * 影响范围行政区划
     */
    @Column(name = "district_id")
    private String districtId;


    /**
     * 专业预测内容
     */
    @Column(length=2000)
    private String content;


    /**
     * 上报单位
     */
    @Column(name = "report_dep")
    private String reportDep;


    /**
     * 上报时间
     */
    @Column(name = "report_time")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date reportTime;


    /**
     * 保密等级
     */
    @Column(name = "security_level")
    private String securityLevel;

    /**
     * 备注
     */
    private String notes;
}
