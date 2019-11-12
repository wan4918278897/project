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
 * @Description: 预警信息
 * @Author: jeecg-boot
 * @Date: 2019-05-24
 * @Version: V1.0
 */
@Entity
@Data
@Table(name = "ep_warning_info")
@TableName("ep_warning_info")
public class WarningInfo extends BaseNativeEntity {

    /**
     * 预警编号
     */
    @Column(name = "warn_id")
    private String warnId;

    /**
     * 信息标题
     */
    @Column(name = "warning_title")
    private String warningTitle;

    /**
     * 预警类别
     */
    @Column(name = "warn_type")
    private String warnType;

    /**
     * 预警单位
     */
    @Column(name = "warn_org")
    private String warnOrg;

    /**
     * 预警级别
     */
    @Column(name = "warn_level")
    private String warnLevel;

    /**
     * 预警状态
     */
    @Column(name = "warn_state")
    private String warnState;

    /**
     * 预警影响开始时间
     */
    @Column(name = "begin_time")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date beginTime;

    /**
     * 预警影响结束时间
     */
    @Column(name = "end_time")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /**
     * 影响范围
     */
    @Column(name = "impact_scope",length=1000)
    private String impactScope;

    /**
     * 防范措施
     */
    @Column(name = "prevent_step",length=1000)
    private String preventStep;

    /**
     * 危害程度
     */

    @Column(name = "imperil_degree",length=1000)
    private String imperilDegree;

    /**
     * 影响方式
     */
    @Column(name = "Impact_method",length = 1000)
    private String impactMethod;

    /**
     * 影响范围行政区划
     */
    @Column(name = "district_id")
    private String districtId;

    /**
     * 上报单位
     */
    @Column(name = "report_org")
    private String reportOrg;

    /**
     * 上报时间
     */
    @Column(name = "report_time")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date reportTime;

    /**
     * 备注
     */
    private String notes;
}
