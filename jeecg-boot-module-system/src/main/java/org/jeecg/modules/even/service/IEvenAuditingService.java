package org.jeecg.modules.even.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.even.entity.EvenAuditing;

/**
 * @Description: 关联处理信息Id
 * @Author: jeecg-boot
 * @Date: 2019-06-14
 * @Version: V1.0
 */
public interface IEvenAuditingService extends IService<EvenAuditing> {


    public void evenAuditingSave(EvenAuditing evenAuditing);

    public void doBack(String reportId, String reason);

}
