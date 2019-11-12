package org.jeecg.modules.system.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.modules.common.entity.BaseNativeEntity;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 个人日志表单
 * */

@Data

public class SysJournalForm extends BaseNativeEntity {
    //编写人id
    private String userId;

    //编写人姓名
    private String userName;

    //编写时间
    @Column(name="write_time")
    @Excel(name = "writeTime", width = 20, format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "writeTime")
    private Date writeTime;

    //标题
    private String title;

    //内容
    private String context;
}
