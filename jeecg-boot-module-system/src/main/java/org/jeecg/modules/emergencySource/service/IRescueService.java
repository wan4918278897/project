package org.jeecg.modules.emergencySource.service;

import org.jeecg.modules.emergencySource.entity.Rescue;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 应急救援队伍信息
 * @Author: jeecg-boot
 * @Date:   2019-06-17
 * @Version: V1.0
 */
public interface IRescueService extends IService<Rescue> {

    /**
     * 救援队伍关联总表保存方法
     * @param entity
     */
    public void rescueSave(Rescue entity);

    /**
     * 救援队伍关联总表编辑方法
     * @param entity
     */
    public void rescueEdit(Rescue entity);

    /**
     * 救援队伍关联总表删除方法
     * @param id
     */
    public void rescueRemove(String id);

    /**
     * 救援队伍关联总表批量删除方法
     * @param ids
     */
    public void rescueRemoveBatch(String ids);
}
