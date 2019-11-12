package org.jeecg.modules.testDemo.service.impl;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.testDemo.entity.TestDemo;
import org.jeecg.modules.testDemo.mapper.TestDemoMapper;
import org.jeecg.modules.testDemo.service.ITestDemoService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: 22
 * @Author: jeecg-boot
 * @Date:   2019-08-02
 * @Version: V1.0
 */
@Service
public class TestDemoServiceImpl extends ServiceImpl<TestDemoMapper, TestDemo> implements ITestDemoService {

    @Resource
    private  TestDemoMapper testDemoMapper;

    @Override
   public List<TestDemo> indexNameSource (String name){
       return testDemoMapper.indexNameSource(name);
   }

    @Override
    public List<TestDemo> indexDepartSource (String departName){
        return testDemoMapper.indexDepartSource(departName);
    }

    @Override
    public List<TestDemo> indexAddressSource (String address){
        return testDemoMapper.indexAddressSource(address);
    }
}
