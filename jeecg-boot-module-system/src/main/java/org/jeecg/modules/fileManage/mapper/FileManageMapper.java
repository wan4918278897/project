package org.jeecg.modules.fileManage.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.*;
import org.jeecg.modules.fileManage.entity.FileManage;

/**
 * @Description: 附件管理
 * @Author: jeecg-boot
 * @Date:   2019-05-27
 * @Version: V1.0
 */
@Mapper
public interface FileManageMapper extends BaseMapper<FileManage> {

    @Update("update sys_file_manage set relate_id=#{newRelateId} where relate_id = #{oldRelateId};")
    void updateRelatedId(@Param("oldRelateId")String oldRelateId, @Param("newRelateId")String newRelateId);
    @Delete("delete from sys_file_manage where relate_id =#{relateId}")
    boolean deleteByRelateId(@Param("relateId")String relateId);
    @Select("SELECT * FROM sys_file_manage WHERE id = (#{relateId})")
    public IPage<FileManage> seleteByRelateId(Page<FileManage> page, @Param("relateId") String relateId);
}
