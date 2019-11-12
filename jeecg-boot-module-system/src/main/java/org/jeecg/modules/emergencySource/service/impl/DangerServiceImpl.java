package org.jeecg.modules.emergencySource.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jeecg.modules.emergencySource.entity.Danger;
import org.jeecg.modules.emergencySource.entity.EmergencySource;
import org.jeecg.modules.emergencySource.mapper.DangerMapper;
import org.jeecg.modules.emergencySource.service.IDangerService;
import org.jeecg.modules.emergencySource.utils.EmergencyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

/**
 * @Description: 危险源
 * @Author: jeecg-boot
 * @Date: 2019-06-17
 * @Version: V1.0
 */
@Service
public class DangerServiceImpl extends ServiceImpl<DangerMapper, Danger> implements IDangerService {

    @Autowired
    private EmergencySourceServiceImpl emergencySourceService;

    @Override
    @Transactional
    public void dangerSave(Danger entity) {
        save(entity);
        EmergencySource emergencySource = new EmergencySource();
        emergencySource.setAddress(entity.getAddress());
        emergencySource.setContactsName(entity.getPrincipal());
        emergencySource.setContactsPhone(entity.getTel());
        emergencySource.setGrade(entity.getHazardLev());
        emergencySource.setName(entity.getDangerName());
        emergencySource.setLatitude(entity.getLatitude());
        emergencySource.setLongitude(entity.getLongitude());
        emergencySource.setRelatedId(entity.getId());
        emergencySource.setType(EmergencyType.A);
        emergencySourceService.save(emergencySource);
    }

    @Override
    @Transactional
    public void dangerEdit(Danger entity) {
        updateById(entity);
        QueryWrapper<EmergencySource> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("related_id", entity.getId()).eq("type", EmergencyType.A);
        EmergencySource emergencySource = emergencySourceService.getOne(queryWrapper);
        if (emergencySource != null) {
            emergencySource.setAddress(entity.getAddress());
            emergencySource.setContactsName(entity.getPrincipal());
            emergencySource.setContactsPhone(entity.getTel());
            emergencySource.setGrade(entity.getHazardLev());
            emergencySource.setName(entity.getDangerName());
            emergencySource.setLatitude(entity.getLatitude());
            emergencySource.setLongitude(entity.getLongitude());
            emergencySource.setRelatedId(entity.getId());
            emergencySource.setType(EmergencyType.A);
            emergencySourceService.updateById(emergencySource);
        }
    }

    @Override
    @Transactional
    public void dangerRemove(String id){
        QueryWrapper<EmergencySource> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("related_id", id).eq("type", EmergencyType.A);
        emergencySourceService.remove(queryWrapper);
        removeById(id);
    }

    @Override
    @Transactional
    public void dangerRemoveBatch(String ids){
        this.removeByIds(Arrays.asList(ids.split(",")));
        for (String s : ids.split(",")){
            QueryWrapper<EmergencySource> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("related_id", s).eq("type", EmergencyType.A);
            emergencySourceService.remove(queryWrapper);
        }

    }
}
