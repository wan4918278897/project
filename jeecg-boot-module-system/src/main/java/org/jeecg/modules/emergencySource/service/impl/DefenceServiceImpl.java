package org.jeecg.modules.emergencySource.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jeecg.modules.emergencySource.entity.Defence;
import org.jeecg.modules.emergencySource.entity.EmergencySource;
import org.jeecg.modules.emergencySource.mapper.DefenceMapper;
import org.jeecg.modules.emergencySource.service.IDefenceService;
import org.jeecg.modules.emergencySource.utils.EmergencyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.transaction.Transactional;
import java.util.Arrays;

/**
 * @Description: 防护目标
 * @Author: jeecg-boot
 * @Date:   2019-06-17
 * @Version: V1.0
 */
@Service
public class DefenceServiceImpl extends ServiceImpl<DefenceMapper, Defence> implements IDefenceService {

    @Autowired
    private EmergencySourceServiceImpl emergencySourceService;

    @Override
    @Transactional
    public void defenceSave(Defence entity) {
        save(entity);
        EmergencySource emergencySource = new EmergencySource();
        emergencySource.setAddress(entity.getAddress());
        emergencySource.setContactsName(entity.getPrincipal());
        emergencySource.setContactsPhone(entity.getTel());
        emergencySource.setGrade(entity.getDefenceLev());
        emergencySource.setName(entity.getDefenceName());
        emergencySource.setLatitude(entity.getLatitude());
        emergencySource.setLongitude(entity.getLongitude());
        emergencySource.setRelatedId(entity.getId());
        emergencySource.setType(EmergencyType.B);
        emergencySourceService.save(emergencySource);
    }

    @Override
    @Transactional
    public void defenceEdit(Defence entity) {
        updateById(entity);
        QueryWrapper<EmergencySource> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("related_id", entity.getId()).eq("type", EmergencyType.A);
        EmergencySource emergencySource = emergencySourceService.getOne(queryWrapper);
        if (emergencySource != null) {
            emergencySource.setAddress(entity.getAddress());
            emergencySource.setContactsName(entity.getPrincipal());
            emergencySource.setContactsPhone(entity.getTel());
            emergencySource.setGrade(entity.getDefenceLev());
            emergencySource.setName(entity.getDefenceName());
            emergencySource.setLatitude(entity.getLatitude());
            emergencySource.setLongitude(entity.getLongitude());
            emergencySource.setRelatedId(entity.getId());
            emergencySource.setType(EmergencyType.B);
            emergencySourceService.updateById(emergencySource);
        }
    }

    @Override
    @Transactional
    public void defenceRemove(String id){
        QueryWrapper<EmergencySource> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("related_id", id).eq("type", EmergencyType.B);
        emergencySourceService.remove(queryWrapper);
        removeById(id);
    }

    @Override
    @Transactional
    public void defenceRemoveBatch(String ids){
        this.removeByIds(Arrays.asList(ids.split(",")));
        for (String s : ids.split(",")){
            QueryWrapper<EmergencySource> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("related_id", s).eq("type", EmergencyType.B);
            emergencySourceService.remove(queryWrapper);
        }

    }
}
