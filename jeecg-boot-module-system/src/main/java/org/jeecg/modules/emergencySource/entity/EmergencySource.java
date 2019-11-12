package org.jeecg.modules.emergencySource.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.jeecg.modules.emergencySource.utils.EmergencyType;
import org.jeecg.modules.common.entity.BaseNativeEntity;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;

/**
 * @author yzx
 */
@Entity
@Data
@Table(name = "es_emergency_source")
@TableName("es_emergency_source")
public class EmergencySource extends BaseNativeEntity {

    @Excel(name = "名称", width = 15)
    private String name;

    @Excel(name = "地址", width = 15)
    private String address;

    @Excel(name = "联系人名称", width = 15)
    @Column(name = "contacts_name")
    private String contactsName;

    @Excel(name = "联系人电话", width = 15)
    @Column(name = "contacts_phone")
    private String contactsPhone;

    @Excel(name = "防护等级", width = 15)
    private String grade;

    @Excel(name = "资源类型", width = 15)
    @Enumerated(EnumType.STRING)
    @Column(length = 80, nullable = false)
    private EmergencyType type = EmergencyType.A;

    @Excel(name = "关联单个资源表Id", width = 15)
    @Column(name = "related_id")
    private String relatedId;

    /**
     * 纬度
     */
    @Column(precision = 8, scale = 4)
    private Float latitude;

    /**
     * 经度
     */
    @Column(precision = 8, scale = 4)
    private Float longitude;
}
