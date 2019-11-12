package org.jeecg.modules.emergencyduty.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.emergencyduty.entity.Contacts;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 通讯录管理
 * @Author: jeecg-boot
 * @Date:   2019-05-22
 * @Version: V1.0
 */
public interface IContactsService extends IService<Contacts> {

    //根据部门或者公司id查询
    public IPage getUserByOrgIdOrDepId(Page<Contacts> page, String id,String name,String mobile,String officePhone);

}
