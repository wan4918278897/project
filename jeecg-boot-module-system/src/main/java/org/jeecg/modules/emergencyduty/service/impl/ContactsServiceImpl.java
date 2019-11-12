package org.jeecg.modules.emergencyduty.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.emergencyduty.entity.Contacts;
import org.jeecg.modules.emergencyduty.mapper.ContactsMapper;
import org.jeecg.modules.emergencyduty.service.IContactsService;
import org.jeecg.modules.system.entity.SysDepart;
import org.jeecg.modules.system.mapper.SysDepartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

/**
 * @Description: 通讯录管理
 * @Author: jeecg-boot
 * @Date:   2019-05-22
 * @Version: V1.0
 */
@Service
public class ContactsServiceImpl extends ServiceImpl<ContactsMapper, Contacts> implements IContactsService {
    @Resource
    private ContactsMapper contactsMapper;
    @Resource
    private SysDepartMapper sysDepartMapper;

    // 根据部门Id查询
    @Override
    public IPage getUserByOrgIdOrDepId(Page page, String id,String name,String mobile,String officePhone) {
        if(id==null)return null;
        SysDepart contacts=sysDepartMapper.selectById(id);
        if(contacts.getParentId().equals(""))
             return contactsMapper.getUserByOrgId(page,id,name,mobile,officePhone);
        else
            return contactsMapper.getUserByDepId(page,id,name,mobile,officePhone);
    }
}
