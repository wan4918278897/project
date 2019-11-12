package org.jeecg.modules.forecastInfo.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.forecastInfo.entity.ReserveInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 预案信息
 * @Author: jeecg-boot
 * @Date:   2019-07-23
 * @Version: V1.0
 */
public interface ReserveInfoMapper extends BaseMapper<ReserveInfo> {

    /**
     * 预警信息列表
     * @param page
     * @param name 名称
     * @param type 类型
     * @param publishOrg 发布单位
     * @param reserveStatues 状态
     * @param beginDate 时间段查询的开始时间
     * @param endDate 结束时间
     *
     */
    IPage<ReserveInfo> reserveInfoList(Page page,@Param("queryType")String queryType,@Param("userId")String userId, @Param("name")String name, @Param("type")String type, @Param("publishOrg")String publishOrg,
                                        @Param("reserveStatues")String reserveStatues, @Param("beginDate")String beginDate, @Param("endDate")String endDate);
}
