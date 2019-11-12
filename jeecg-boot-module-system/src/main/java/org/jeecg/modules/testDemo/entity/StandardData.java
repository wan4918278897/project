package org.jeecg.modules.testDemo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import org.jeecg.modules.common.entity.BaseNativeEntity;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;

/**
 * @author yzx
 */
@Entity
@Accessors(chain = true)
@TableName("hust_standard_data")
@Access(AccessType.FIELD)
@Data
@Table(name = "hust_standard_data")
public class StandardData extends BaseNativeEntity {

    @Excel(name = "名称", width = 15)
    private String name;

    @Excel(name = "地址", width = 15)
    private String address;

    @Excel(name = "匹配项", width = 15)
    @Column(name = "alias_name")
    private String aliasName;

}
