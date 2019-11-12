package org.jeecg.modules.forecastInfo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.fileManage.entity.FileManage;
import org.jeecg.modules.fileManage.service.IFileManageService;
import org.jeecg.modules.forecastInfo.entity.ReserveInfo;
import org.jeecg.modules.forecastInfo.mapper.ReserveInfoMapper;
import org.jeecg.modules.forecastInfo.service.IReserveInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Description: 预案信息
 * @Author: jeecg-boot
 * @Date: 2019-07-23
 * @Version: V1.0
 */
@Service
public class ReserveInfoServiceImpl extends ServiceImpl<ReserveInfoMapper, ReserveInfo> implements IReserveInfoService {

    @Resource
    private ReserveInfoMapper reserveInfoMapper;

    @Autowired
    private IFileManageService fileManageService;

    @Override
    public IPage<ReserveInfo> reserveInfoList(Page page, String queryType, String userId, String name, String type, String publishOrg, String reserveStatues, String beginDate, String endDate) {
        return reserveInfoMapper.reserveInfoList(page, queryType, userId, name, type, publishOrg, reserveStatues, beginDate, endDate);
    }

    @Override
    public void reserveInfoSave(ReserveInfo reserveInfo,String relateId){
        save(reserveInfo);
        QueryWrapper<FileManage> queryFileWrappers = new QueryWrapper<FileManage>();
        queryFileWrappers.eq("relate_id", relateId).eq("relate_type", "2");
        List<FileManage> fileManageList=fileManageService.list(queryFileWrappers);
        if (fileManageList.size()>0){
            for (FileManage s:fileManageList){
                s.setRelateId(reserveInfo.getId());
                fileManageService.updateById(s);
            }
        }
    }

    @Override
    public void reserveInfoEdit(ReserveInfo reserveInfo,String relateId){
        updateById(reserveInfo);
        QueryWrapper<FileManage> queryFileWrappers = new QueryWrapper<FileManage>();
        queryFileWrappers.eq("relate_id", relateId).eq("relate_type", "2");
        List<FileManage> fileManageList=fileManageService.list(queryFileWrappers);
        if (fileManageList.size()>0){
            for (FileManage s:fileManageList){
                s.setRelateId(reserveInfo.getId());
            }
            fileManageService.updateBatchById(fileManageList);
        }
    }
}
