package org.jeecg.modules.emergencySource.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.jeecg.modules.common.entity.BaseNativeEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Description: 应急物资保障信息
 * @Author: jeecg-boot
 * @Date:   2019-05-24
 * @Version: V1.0
 */
@Entity
@Data
@Table(name = "es_material")
@TableName("es_material")
public class Material extends BaseNativeEntity {
    /**
     * 物资保障编号
     */
    @Column(name = "material_id")
    private String materialId;

    /**
     * 所属单位编号
     */
    @Column(name = "repertory_id")
    private String repertoryId;

    /**
     * 所属单位名称
     */
    @Column(name = "repertory_name")
    private String repertoryName;

    /**
     * 名称
     */
    @Column(name = "material_name")
    private String materialName;

    /**
     * 别名
     */
    private String alias;

    /**
     * 类型
     */
    @Column(name = "material_type")
    private String materialType;

    /**
     * 规格型号
     */
    private String model;

    /**
     * 计量单位
     */
    @Column(name = "measure_unit")
    private String measureUnit;

    /**
     * 物资数量
     */
    @Column(name = "material_cnt")
    private Integer materialCnt;

    /**
     * 物资描述
     */
    @Column(name = "material_desc",length=1000)
    private String materialDesc;

    /**
     * 备注
     */
    @Column(length=500)
    private String notes;


}
