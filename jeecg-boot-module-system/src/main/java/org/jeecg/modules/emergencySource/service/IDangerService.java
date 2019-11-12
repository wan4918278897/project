package org.jeecg.modules.emergencySource.service;

import org.jeecg.modules.emergencySource.entity.Danger;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 危险源
 * @Author: jeecg-boot
 * @Date:   2019-06-17
 * @Version: V1.0
 */
public interface IDangerService extends IService<Danger> {

    /**
     * 危险源关联总表保存方法
     * @param entity
     */
    public void dangerSave(Danger entity);

    /**
     * 危险源关联总表编辑方法
     * @param entity
     */
    public void dangerEdit(Danger entity);

    /**
     * 危险源关联总表删除方法
     * @param id
     */
    public void dangerRemove(String id);

    /**
     * 危险源关联总表批量删除方法
     * @param ids
     */
    public void dangerRemoveBatch(String ids);

    }
