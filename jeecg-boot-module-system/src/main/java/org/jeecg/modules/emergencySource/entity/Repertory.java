package org.jeecg.modules.emergencySource.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.jeecg.modules.common.entity.BaseNativeEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Description: 物资储备库
 * @Author: jeecg-boot
 * @Date:   2019-05-24
 * @Version: V1.0
 */
@Entity
@Data
@Table(name = "es_repertory")
@TableName("es_repertory")
public class Repertory extends BaseNativeEntity {
    /**
     * 物资储备库编号
     */
    @Column(name = "repertory_id")
    private String repertoryId;

    /**
     * 名称
     */
    private String name;

    /**
     * 级别
     */
    private String grade;

    /**
     * 行政区划代码
     */
    @Column(name = "district_id")
    private String districtId;

    /**
     * 主管部门
     */
    @Column(name = "supervisal_dept")
    private String supervisalDept;

    /**
     * 地址
     */
    private String address;

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

    /**
     * 负责人
     */
    private String principal;

    /**
     * 负责人电话
     */
    @Column(name = "principal_tel")
    private String principalTel;

    /**
     * 负责人移动电话
     */
    @Column(name = "mobile_tel")
    private String mobileTel;

    /**
     * 储备物资
     */
    private String material;

    /**
     * 总库容
     */
    private String capacity;

    /**
     * 空仓库容
     */
    private String unoccupy;

    /**
     * 库容单位
     */
    private String measure;

    /**
     * 应急值班电话
     */
    @Column(name = "duty_tel")
    private String dutyTel;

    /**
     * 应急值班传真
     */
    @Column(name = "duty_fax")
    private String dutyFax;

    /**
     * 备注
     */
    @Column(length=500)
    private String notes;

}
