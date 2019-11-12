package org.jeecg.modules.fileManage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.jeecg.modules.common.entity.BaseNativeEntity;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Description: 附件管理
 * @author： jeecg-boot
 * @date：   2019-05-27
 * @version： V1.0
 */
@Data
@Table(name = "sys_file_manage")
@Entity
@TableName("sys_file_manage")
public class FileManage  extends BaseNativeEntity {


    /**fileName*/
    @Column(name = "file_name")
    @Excel(name = "fileName", width = 15)
    private java.lang.String fileName;
    /**path*/
    @Excel(name = "path", width = 15)
    @Column(name = "path")
    private java.lang.String path;
    /**relateId*/
    @Excel(name = "relateId", width = 15)
    @Column(name = "relate_id")
    private java.lang.String relateId;
    /**relateType*/
    @Column(name = "relate_type")
    @Excel(name = "relateType", width = 15)
    private java.lang.String relateType;

    @Excel(name = "viewPath", width = 15)
    @Column(name = "view_path")
    private java.lang.String viewPath;

    @Excel(name = "file_type", width = 15)
    @Column(name = "file_type")
    private java.lang.String fileType;
}
