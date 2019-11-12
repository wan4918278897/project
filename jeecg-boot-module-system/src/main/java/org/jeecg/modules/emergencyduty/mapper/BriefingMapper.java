package org.jeecg.modules.emergencyduty.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.jeecg.modules.emergencyduty.entity.Briefing;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 简报
 * @Author: jeecg-boot
 * @Date:   2019-06-19
 * @Version: V1.0
 */
public interface BriefingMapper extends BaseMapper<Briefing> {
    @Update("Update eg_briefing set review_status = #{status} WHERE id = #{id}")
    public void updateStatue(String id,  int status);
    /**
     *  根据当前用户获取上报信息
     */
    IPage<Briefing> queryList(Page page,@Param("title")String title,@Param("reviewStatus")String reviewStatus,@Param("befType")String befType,@Param("reporter")String reporter,
                              @Param("pubStartDate")String pubStartDate,@Param("pubEndDate")String pubEndDate);
}
