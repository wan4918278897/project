package org.jeecg.modules.even.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.even.entity.EventReport;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 事件上报
 * @Author: jeecg-boot
 * @Date:   2019-06-03
 * @Version: V1.0
 */
public interface EventReportMapper extends BaseMapper<EventReport> {


    /**
     *  依据抄送人或者处理人查询数据
     * @param page
     * @param id
     * @return
     */
    IPage<EventReport> queryListByPersonId(Page page, @Param("id") String id);

    /**
     *  根据当前用户获取上报信息
     * @param userId
     * @return
     */
    IPage<EventReport> queryList(Page page,@Param("userId")String userId);

    /**
     *  待办列表
     * @param userId
     * @return
     */
    IPage<EventReport> queryBacklogList(Page page,@Param("userId")String userId);

    /**
     *  b办结流程列表
     * @param userId
     * @return
     */
    IPage<EventReport> queryFinishList(Page page,@Param("userId")String userId ,@Param("queryName")String queryName);

    IPage<EventReport> reportList(Page page,@Param("userId")String userId ,@Param("title")String title,@Param("beginDate")String beginDate,@Param("endDate")String endDate,@Param("evenType")String evenType,
                                  @Param("evenProperty")String evenProperty,@Param("evenLevel")String evenLevel, @Param("evenStatus")String evenStatus,@Param("listType")String listType);

}
