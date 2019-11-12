package org.jeecg.modules.emergencyduty.entity;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.jeecg.modules.common.entity.BaseNativeEntity;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Description: 通讯录管理
 * @Author: jeecg-boot
 * @Date:   2019-05-22
 * @Version: V1.0
 */
@Data
@TableName("eg_contacts_book")
@Table(name="eg_contacts_book")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="address_book对象", description="通讯录管理")
@Setter
@Getter
@Entity
public class Contacts extends BaseNativeEntity {


    /**dapartName*/
    /**dutyPhone*/
    @Excel(name = "值班电话", width = 15)
    @ApiModelProperty(value = "dutyPhone")
    @Column(name="duty_phone")
    private java.lang.String dutyPhone;
    /**job*/
    @Excel(name = "职务", width = 15)
    @ApiModelProperty(value = "job")
    @Column(name="job")
    private java.lang.String job;
    /**name*/
    @Excel(name = "姓名", width = 15)
    @ApiModelProperty(value = "name")
    @Column(name="name")
    private java.lang.String name;
    /**mobile*/
    @Excel(name = "mobile", width = 15)
    @ApiModelProperty(value = "mobile")
    @Column(name="mobile")
    private java.lang.String mobile;
    /**name*/
    @Excel(name = "部门Id", width = 15)
    @ApiModelProperty(value = "dapartId")
    @Column(name="depart_id")
    private java.lang.String departId;
    /**officePhone*/
    @Excel(name = "办公电话", width = 15)
    @ApiModelProperty(value = "officePhone")
    @Column(name="office_phone")
    private java.lang.String officePhone;
    /**orgName*/

    @Excel(name = "公司Id", width = 15)
    @ApiModelProperty(value = "orgId")
    @Column(name="org_id")
    private java.lang.String orgId;

//    用来排序
    @Column(name="sort")
    private Integer sort;
}
