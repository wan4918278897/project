package org.jeecg.modules.emergencySource.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jeecg.modules.emergencySource.entity.EmergencySource;
import org.jeecg.modules.emergencySource.entity.Repertory;
import org.jeecg.modules.emergencySource.mapper.RepertoryMapper;
import org.jeecg.modules.emergencySource.service.IRepertoryService;
import org.jeecg.modules.emergencySource.utils.EmergencyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

/**
 * @Description: 物资储备库
 * @Author: jeecg-boot
 * @Date:   2019-06-17
 * @Version: V1.0
 */
@Service
public class RepertoryServiceImpl extends ServiceImpl<RepertoryMapper, Repertory> implements IRepertoryService {

    @Autowired
    private EmergencySourceServiceImpl emergencySourceService;

    @Override
    @Transactional
    public void repertorySave(Repertory entity) {
        save(entity);
        EmergencySource emergencySource = new EmergencySource();
        emergencySource.setAddress(entity.getAddress());
        emergencySource.setContactsName(entity.getPrincipal());
        emergencySource.setContactsPhone(entity.getPrincipalTel());
        emergencySource.setGrade(entity.getGrade());
        emergencySource.setName(entity.getName());
        emergencySource.setLatitude(entity.getLatitude());
        emergencySource.setLongitude(entity.getLongitude());
        emergencySource.setRelatedId(entity.getId());
        emergencySource.setType(EmergencyType.F);
        emergencySourceService.save(emergencySource);
    }

    @Override
    @Transactional
    public void repertoryEdit(Repertory entity) {
        updateById(entity);
        QueryWrapper<EmergencySource> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("related_id", entity.getId()).eq("type", EmergencyType.F);
        EmergencySource emergencySource = emergencySourceService.getOne(queryWrapper);
        if (emergencySource != null) {
            emergencySource.setAddress(entity.getAddress());
            emergencySource.setContactsName(entity.getPrincipal());
            emergencySource.setContactsPhone(entity.getPrincipalTel());
            emergencySource.setGrade(entity.getGrade());
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
    public void repertoryRemove(String id){
        QueryWrapper<EmergencySource> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("related_id", id).eq("type", EmergencyType.F);
        emergencySourceService.remove(queryWrapper);
        removeById(id);
    }

    @Override
    @Transactional
    public void repertoryRemoveBatch(String ids){
        this.removeByIds(Arrays.asList(ids.split(",")));
        for (String s : ids.split(",")){
            QueryWrapper<EmergencySource> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("related_id", s).eq("type", EmergencyType.F);
            emergencySourceService.remove(queryWrapper);
        }

    }

}
