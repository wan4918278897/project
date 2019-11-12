package org.jeecg.modules.testDemo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import org.jeecg.modules.common.entity.BaseNativeEntity;
import org.jeecg.modules.emergencySource.utils.EmergencyType;
import org.jeecg.modules.testDemo.utils.TsetDemoEnum;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;

/**
 * @author yzx
 * 地图demo测试表
 */
@Entity
@Accessors(chain = true)
@Access(AccessType.FIELD)
@Data
@Table(name = "hust_basic_data")
@TableName("hust_basic_data")
public class TestDemo extends BaseNativeEntity {


    @Excel(name = "单位名称", width = 15)
    @Column(name = "depart_name")
    private String departName;

    @Excel(name = "地址", width = 15)
    private String address;


    @Excel(name = "校区", width = 15)
    private String area ;

    @Excel(name = "房间", width = 15)
    private String room;
    /**
     * 经度
     */
    @Column(precision=8,scale=4)
    private Float longitude;

    /**
     * 纬度
     */
    @Column(precision=8,scale=4)
    private Float latitude;

    @Excel(name = "备注", width = 15)
    private String remark;

    @Excel(name = "关联Id", width = 15)
    @Column(name = "root_id")
    private String rootId;

}
