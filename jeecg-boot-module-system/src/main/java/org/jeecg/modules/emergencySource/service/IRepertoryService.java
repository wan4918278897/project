package org.jeecg.modules.emergencySource.service;

import org.jeecg.modules.emergencySource.entity.Repertory;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 物资储备库
 * @Author: jeecg-boot
 * @Date:   2019-06-17
 * @Version: V1.0
 */
public interface IRepertoryService extends IService<Repertory> {

    /**
     * 物资储备关联总表保存方法
     * @param entity
     */
    public void repertorySave(Repertory entity);

    /**
     * 物资储备关联总表编辑方法
     * @param entity
     */
    public void repertoryEdit(Repertory entity);

    /**
     * 物资储备关联总表删除方法
     * @param id
     */
    public void repertoryRemove(String id);

    /**
     * 物资储备关联总表批量删除方法
     * @param ids
     */
    public void repertoryRemoveBatch(String ids);

}
