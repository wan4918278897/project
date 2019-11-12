package org.jeecg.modules.emergencyduty.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.emergencyduty.entity.Contacts;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 通讯录管理
 * @Author: jeecg-boot
 * @Date:   2019-05-22
 * @Version: V1.0
 */
public interface ContactsMapper extends BaseMapper<Contacts> {

    /**
     *  根据单位Id查询用户信息
     * @param page
     * @param id
     * @return
     */
    IPage getUserByOrgId(Page page, @Param("id") String id,@Param("name")String name,@Param("mobile")String mobile,@Param("officePhone")String officePhone);


    /**
     *  匹配部门Id
     * @param page
     * @param id
     * @return
     */
    IPage getUserByDepId(Page page, @Param("id") String id,@Param("name")String name,@Param("mobile")String mobile,@Param("officePhone")String officePhone);


}
