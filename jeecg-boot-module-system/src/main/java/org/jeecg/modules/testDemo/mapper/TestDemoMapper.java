package org.jeecg.modules.testDemo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.testDemo.entity.TestDemo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 22
 * @Author: jeecg-boot
 * @Date:   2019-08-02
 * @Version: V1.0
 */
public interface TestDemoMapper extends BaseMapper<TestDemo> {


    List<TestDemo> indexNameSource (@Param("name")String name);

    List<TestDemo> indexDepartSource (@Param("departName")String departName);

    List<TestDemo> indexAddressSource (@Param("address")String address);


}
