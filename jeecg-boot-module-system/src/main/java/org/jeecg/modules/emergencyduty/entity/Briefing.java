package org.jeecg.modules.emergencyduty.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecg.modules.common.entity.BaseNativeEntity;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**动态*/
@Entity
@Data
@TableName("eg_briefing")
@Table(name="eg_briefing")
public class Briefing {
    /**正文*/
    @Excel(name = "正文", width = 30)
    @ApiModelProperty(value = "context")
    @Column(length = 4000)
    private String context;
    /**创建人*/
    @Excel(name = "createBy", width = 15)
    @ApiModelProperty(value = "createBy")
    @Column(name = "create_by")
    private String createBy;
    /**创建时间*/
    @Excel(name = "createTime", width = 20, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "createTime")
    @Column(name = "create_time")
    private Date createTime;
    /**id*/
    @Id
    @TableId(type = IdType.UUID)
    @ApiModelProperty(value = "id")
    private String id;
    /**单位id*/
    @Excel(name = "orgId", width = 15)
    @ApiModelProperty(value = "orgId")
    @Column(name = "org_id")
    private String orgId;
    /**发布时间*/
    @Excel(name = "publishTime", width = 20, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "publishTime")
    @Column(name = "publish_time")
    private Date publishTime;
    /**标题*/
    @Excel(name = "title", width = 15)
    @ApiModelProperty(value = "title")
    private String title;
    /**更新人*/
    @Excel(name = "updateBy", width = 15)
    @ApiModelProperty(value = "updateBy")
    @Column(name = "update_by")
    private String updateBy;
    /**更新时间*/
    @Excel(name = "updateTime", width = 20, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "updateTime")
    @Column(name = "update_time")
    private Date updateTime;

    /**上报人*/
    private String reporter;

    /**上报时间*/
    @Column(name = "report_time")
    @Excel(name = "reportTime", width = 20, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "reportTime")
    private Date reportTime;



    /**审核状态*/
    @Column(name = "review_status")
    private Integer reviewStatus;

    /**动态类型*/
    @Column(name="bef_type")
    private String befType;


}
