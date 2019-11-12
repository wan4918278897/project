package org.jeecg.modules.emergencyduty.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Description: 应急简报管理
 * @Author: jeecg-boot
 * @Date:   2019-05-24
 * @Version: V1.0
 */

@Data
public class EmergencyBriefing {
    /**正文*/
    @Excel(name = "正文", width = 30)
    @ApiModelProperty(value = "context")
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
    /**分类*/

    /**单位id*/
    @Excel(name = "orgId", width = 15)
    @ApiModelProperty(value = "orgId")
    @Column(name = "org_id")
    private String orgId;
    /**单位名称*/
    @Excel(name = "orgName", width = 15)
    @ApiModelProperty(value = "orgName")
    @Column(name = "org_name")
    private String orgName;
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

    @Excel(name = "bumen", width = 15)
    private String departName;
    @Excel(name = "shenqingren", width = 15)
    private String realname;
    @Excel(name = "leixing", width = 15)
    private String befType;
    @Excel(name = "baogaoshijian", width = 15)
    private String reportTime;
    @Excel(name = "报告人id", width = 15)
    private String reporter;
    @Excel(name = "reviewStatus", width = 15)
    private String reviewStatus;

}
