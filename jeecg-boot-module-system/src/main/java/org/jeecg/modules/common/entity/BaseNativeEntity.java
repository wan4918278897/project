package org.jeecg.modules.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;


import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * @author yzx
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@MappedSuperclass
@Setter
@Getter
@Data
public abstract class BaseNativeEntity implements Serializable {
    private static final long serialVersionUID = -8683251017324832202L;
    
    @Id
    //@GeneratedValue(generator = "idGenerator")
    //@GenericGenerator(name = "idGenerator", strategy = "native")
    //@JsonProperty
    @TableId(type = IdType.UUID)
    private String  id;

    @Excel(name = "createBy", width = 15)
    @ApiModelProperty(value = "createBy")
    @Column(name = "create_by")
    private String createBy;

    @Excel(name = "createTime", width = 20, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "createTime")
    @Column(name = "create_time")
    private Date createTime;

    @Excel(name = "updateBy", width = 15)
    @ApiModelProperty(value = "updateBy")
    @Column(name = "update_by")
    private String updateBy;

    @ApiModelProperty(value = "updateTime")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "update_time")
    private Date updateTime;

    
    
}
