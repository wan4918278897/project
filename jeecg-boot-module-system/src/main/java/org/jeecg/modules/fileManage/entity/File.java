package org.jeecg.modules.fileManage.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jeecg.modules.common.entity.BaseNativeEntity;

import javax.persistence.*;


/**
 * @author yzx
 */
@Getter
@Setter
@Entity
@Accessors(chain = true)
@Access(AccessType.FIELD)
@Table(name = "sys_file_manage")
public class File extends BaseNativeEntity {

    @Column(name = "path")
    private String path;

    @Column(name = "relate_type")
    private String relateType;

    @Column(name = "relate_id")
    private String relateId;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "view_path")
    private String viewPath;
}
