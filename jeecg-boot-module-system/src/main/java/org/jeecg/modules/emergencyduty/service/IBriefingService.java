package org.jeecg.modules.emergencyduty.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.emergencyduty.entity.Briefing;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.emergencyduty.utils.Constant;

/**
 * @Description: 简报
 * @Author: jeecg-boot
 * @Date:   2019-06-19
 * @Version: V1.0
 */
public interface IBriefingService extends IService<Briefing> {
    void updateStatue(String id,  int status);

    public IPage queryList(Page page,String title,String reviewStatus,String befType,String reporter,String pubStartDate,String pubEndDate);
}
