package org.jeecg.modules.emergencyduty.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.emergencyduty.entity.Briefing;
import org.jeecg.modules.emergencyduty.mapper.BriefingMapper;
import org.jeecg.modules.emergencyduty.service.IBriefingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

/**
 * @Description: 简报
 * @Author: jeecg-boot
 * @Date:   2019-06-19
 * @Version: V1.0
 */
@Service
public class BriefingServiceImpl extends ServiceImpl<BriefingMapper, Briefing> implements IBriefingService {

    @Resource
    private BriefingMapper briefingMapper;

    @Override
    public void updateStatue(String id,  int status)
    {
        briefingMapper.updateStatue(id,status);
    }

    @Override
    public IPage queryList(Page page,String title,String reviewStatus,String befType,String reporter,String pubStartDate,String pubEndDate) {
        return briefingMapper.queryList(page,title,reviewStatus,befType,reporter,pubStartDate,pubEndDate);
    }
}
