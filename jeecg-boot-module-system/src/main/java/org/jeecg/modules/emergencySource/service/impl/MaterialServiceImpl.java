package org.jeecg.modules.emergencySource.service.impl;

import org.jeecg.modules.emergencySource.entity.Material;
import org.jeecg.modules.emergencySource.mapper.MaterialMapper;
import org.jeecg.modules.emergencySource.service.IMaterialService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 应急物资保障信息
 * @Author: jeecg-boot
 * @Date:   2019-06-17
 * @Version: V1.0
 */
@Service
public class MaterialServiceImpl extends ServiceImpl<MaterialMapper, Material> implements IMaterialService {

}
