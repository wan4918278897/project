package org.jeecg.modules.scheduling.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.scheduling.entity.ScheduleSetting;

import java.util.List;
import java.util.Map;

/**
 * @Description: 1
 * @Author: jeecg-boot
 * @Date:   2019-06-18
 * @Version: V1.0
 */
public interface ScheduleSettingMapper extends BaseMapper<ScheduleSetting> {

    /**
     * 班次设置列表查询
     * @param page 分页
     * @return
     */
    IPage<ScheduleSetting> scheduleSettingList(Page page);
}
