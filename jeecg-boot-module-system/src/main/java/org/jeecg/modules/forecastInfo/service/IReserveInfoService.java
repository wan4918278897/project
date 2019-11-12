package org.jeecg.modules.forecastInfo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.even.entity.EventReport;
import org.jeecg.modules.forecastInfo.entity.ReserveInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Description: 预案信息
 * @Author: jeecg-boot
 * @Date:   2019-07-23
 * @Version: V1.0
 */
public interface IReserveInfoService extends IService<ReserveInfo> {

    /**
     * 预警信息列表
     * @param page
     * @param name 名称
     * @param type 类型
     * @param publishOrg 发布单位
     * @param reserveStatues 状态
     * @param beginDate 时间段查询的开始时间
     * @param endDate 结束时间
     * @return
     */
    IPage<ReserveInfo> reserveInfoList(Page<ReserveInfo> page,String queryType,String userId,String name, String type, String publishOrg, String reserveStatues, String beginDate, String endDate);

    /**
     *
     * @param reserveInfo
     * @param relateId 保存到fileMangeList表中的relateIdId
     */
    void reserveInfoSave(ReserveInfo reserveInfo,String relateId);

    /**
     *
     * @param reserveInfo
     * @param relateId 保存到fileMangeList表中的relateIdId
     */
    void reserveInfoEdit(ReserveInfo reserveInfo,String relateId);
    }
