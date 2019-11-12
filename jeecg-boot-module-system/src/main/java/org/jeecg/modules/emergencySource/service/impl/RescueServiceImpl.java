package org.jeecg.modules.emergencySource.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jeecg.modules.emergencySource.entity.EmergencySource;
import org.jeecg.modules.emergencySource.entity.Rescue;
import org.jeecg.modules.emergencySource.mapper.RescueMapper;
import org.jeecg.modules.emergencySource.service.IRescueService;
import org.jeecg.modules.emergencySource.utils.EmergencyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

/**
 * @Description: 应急救援队伍信息
 * @Author: jeecg-boot
 * @Date:   2019-06-17
 * @Version: V1.0
 */
@Service
public class RescueServiceImpl extends ServiceImpl<RescueMapper, Rescue> implements IRescueService {

    @Autowired
    private EmergencySourceServiceImpl emergencySourceService;

    @Override
    @Transactional
    public void rescueSave(Rescue entity) {
        save(entity);
        EmergencySource emergencySource = new EmergencySource();
        emergencySource.setAddress(entity.getAddress());
        emergencySource.setContactsName(entity.getPrincipal());
        emergencySource.setContactsPhone(entity.getPrincipalTel());
//        emergencySource.setGrade(entity.getHazardLev());
        emergencySource.setName(entity.getName());
        emergencySource.setLatitude(entity.getLatitude());
        emergencySource.setLongitude(entity.getLongitude());
        emergencySource.setRelatedId(entity.getId());
        emergencySource.setType(EmergencyType.F);
        emergencySourceService.save(emergencySource);
    }

    @Override
    @Transactional
    public void rescueEdit(Rescue entity) {
        updateById(entity);
        QueryWrapper<EmergencySource> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("related_id", entity.getId()).eq("type", EmergencyType.F    );
        EmergencySource emergencySource = emergencySourceService.getOne(queryWrapper);
        if (emergencySource != null) {
            emergencySource.setAddress(entity.getAddress());
            emergencySource.setContactsName(entity.getPrincipal());
            emergencySource.setContactsPhone(entity.getPrincipalTel());
//        emergencySource.setGrade(entity.getHazardLev());
            emergencySource.setName(entity.getName());
            emergencySource.setLatitude(entity.getLatitude());
            emergencySource.setLongitude(entity.getLongitude());
            emergencySource.setRelatedId(entity.getId());
            emergencySource.setType(EmergencyType.F);
            emergencySourceService.updateById(emergencySource);
        }
    }

    @Override
    @Transactional
    public void rescueRemove(String id){
        QueryWrapper<EmergencySource> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("related_id", id).eq("type", EmergencyType.F);
        emergencySourceService.remove(queryWrapper);
        removeById(id);
    }

    @Override
    @Transactional
    public void rescueRemoveBatch(String ids){
        this.removeByIds(Arrays.asList(ids.split(",")));
        for (String s : ids.split(",")){
            QueryWrapper<EmergencySource> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("related_id", s).eq("type", EmergencyType.F);
            emergencySourceService.remove(queryWrapper);
        }

    }
}
