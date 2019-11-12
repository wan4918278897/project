package org.jeecg.modules.system.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.system.entity.SysJournal;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 个人日志
 * @Author: jeecg-boot
 * @Date:   2019-07-22
 * @Version: V1.0
 */
public interface SysJournalMapper extends BaseMapper<SysJournal> {
    /**
     *  根据当前用户获取上报信息
     */
    IPage<SysJournal> queryList(Page page, @Param("title")String title, @Param("reporter")String reporter,@Param("checkDate")String checkDate);
    //查询是否有该日日志
    int selectByDate(@Param("date")String date);

}
