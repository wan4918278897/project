package org.jeecg.modules.emergencySource.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jeecg.modules.emergencySource.entity.EmergencySource;
import org.jeecg.modules.emergencySource.entity.Emorg;
import org.jeecg.modules.emergencySource.mapper.EmorgMapper;
import org.jeecg.modules.emergencySource.service.IEmorgService;
import org.jeecg.modules.emergencySource.utils.EmergencyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

/**
 * @Description: 应急管理机构
 * @Author: jeecg-boot
 * @Date:   2019-06-17
 * @Version: V1.0
 */
@Service
public class EmorgServiceImpl extends ServiceImpl<EmorgMapper, Emorg> implements IEmorgService {

    @Autowired
    private EmergencySourceServiceImpl emergencySourceService;

    @Override
    @Transactional
    public void emorgSave(Emorg entity) {
        save(entity);
        EmergencySource emergencySource = new EmergencySource();
        emergencySource.setAddress(entity.getAddress());
        emergencySource.setContactsName(entity.getFirstLeader());
        emergencySource.setContactsPhone(entity.getFirstTel());
//            emergencySource.setGrade(entity.getf());
        emergencySource.setName(entity.getOrgName());
        emergencySource.setLatitude(entity.getLatitude());
        emergencySource.setLongitude(entity.getLongitude());
        emergencySource.setRelatedId(entity.getId());
        emergencySource.setType(EmergencyType.C);
        emergencySourceService.updateById(emergencySource);
        emergencySourceService.save(emergencySource);
    }

    @Override
    @Transactional
    public void emorgEdit(Emorg entity) {
        updateById(entity);
        QueryWrapper<EmergencySource> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("related_id", entity.getId()).eq("type", EmergencyType.C);
        EmergencySource emergencySource = emergencySourceService.getOne(queryWrapper);
        if (emergencySource != null) {
            emergencySource.setAddress(entity.getAddress());
            emergencySource.setContactsName(entity.getFirstLeader());
            emergencySource.setContactsPhone(entity.getFirstTel());
//            emergencySource.setGrade(entity.getf());
            emergencySource.setName(entity.getOrgName());
            emergencySource.setLatitude(entity.getLatitude());
            emergencySource.setLongitude(entity.getLongitude());
            emergencySource.setRelatedId(entity.getId());
            emergencySource.setType(EmergencyType.C);
            emergencySourceService.updateById(emergencySource);
        }
    }

    @Override
    @Transactional
    public void emorgRemove(String id){
        QueryWrapper<EmergencySource> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("related_id", id).eq("type", EmergencyType.C);
        emergencySourceService.remove(queryWrapper);
        removeById(id);
    }

    @Override
    @Transactional
    public void emorgRemoveBatch(String ids){
        this.removeByIds(Arrays.asList(ids.split(",")));
        for (String s : ids.split(",")){
            QueryWrapper<EmergencySource> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("related_id", s).eq("type", EmergencyType.C);
            emergencySourceService.remove(queryWrapper);
        }

    }

}
