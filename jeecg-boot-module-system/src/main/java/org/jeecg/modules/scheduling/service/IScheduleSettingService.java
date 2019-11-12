package org.jeecg.modules.scheduling.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.scheduling.entity.ScheduleSetting;

import java.util.List;
import java.util.Map;

/**
 * @Description: 1
 * @Author: jeecg-boot
 * @Date:   2019-06-18
 * @Version: V1.0
 */
public interface IScheduleSettingService extends IService<ScheduleSetting> {

    /**
     * 班次设置关联保存
     * @param scheduleSetting
     */
    void settingSave(ScheduleSetting scheduleSetting);

    /**
     * 班次设置列表
     * @param page
     * @return
     */
    public IPage<ScheduleSetting> scheduleSettingList(Page page);

}
