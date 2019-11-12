package org.jeecg.modules.testDemo.service;

import org.jeecg.modules.testDemo.entity.TestDemo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: 22
 * @Author: jeecg-boot
 * @Date:   2019-08-02
 * @Version: V1.0
 */
public interface ITestDemoService extends IService<TestDemo> {

    List<TestDemo> indexNameSource (String name);

    List<TestDemo> indexDepartSource (String departName);

    List<TestDemo> indexAddressSource (String address);

}
