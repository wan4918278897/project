package org.jeecg.modules.emergencySource.service;

import org.jeecg.modules.emergencySource.entity.Defence;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 防护目标
 * @Author: jeecg-boot
 * @Date:   2019-06-17
 * @Version: V1.0
 */
public interface IDefenceService extends IService<Defence> {

    /**
     * 目标防护关联总表保存方法
     * @param entity
     */
    public void defenceSave(Defence entity);

    /**
     * 目标防护关联总表编辑方法
     * @param entity
     */
    public void defenceEdit(Defence entity);

    /**
     * 目标防护关联总表删除方法
     * @param id
     */
    public void defenceRemove(String id);

    /**
     * 目标防护关联总表批量删除方法
     * @param ids
     */
    public void defenceRemoveBatch(String ids);

}
