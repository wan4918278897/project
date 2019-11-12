package org.jeecg.modules.emergencySource.service;

import org.jeecg.modules.emergencySource.entity.Emorg;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 应急管理机构
 * @Author: jeecg-boot
 * @Date:   2019-06-17
 * @Version: V1.0
 */
public interface IEmorgService extends IService<Emorg> {

    /**
     * 应急管理联总表保存方法
     * @param entity
     */
    public void emorgSave(Emorg entity);

    /**
     * 应急管理联总表编辑方法
     * @param entity
     */
    public void emorgEdit(Emorg entity);

    /**
     * 应急管理联总表删除方法
     * @param id
     */
    public void emorgRemove(String id);

    /**
     * 应急管理联总表批量删除方法
     * @param ids
     */
    public void emorgRemoveBatch(String ids);

}
