package org.jeecg.modules.fileManage.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.fileManage.entity.FileManage;
import org.jeecg.modules.fileManage.mapper.FileManageMapper;
import org.jeecg.modules.fileManage.service.IFileManageService;
import org.jeecg.modules.system.entity.SysDictItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 附件管理
 * @Author: jeecg-boot
 * @Date:   2019-05-27
 * @Version: V1.0
 */
@Service
public class FileManageServiceImpl extends ServiceImpl<FileManageMapper, FileManage> implements IFileManageService {
    @Autowired
    private FileManageMapper fileManageMapper;
    @Override
    public void updateRelatedId(String id, String relateId)
    {
        fileManageMapper.updateRelatedId(id,relateId);
        System.out.println("id:"+id+"\nrelateId:"+relateId);

    }
    @Override
    public boolean deleteByRelateId(String relateId)
    {
        try
        {
            fileManageMapper.deleteByRelateId(relateId);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }

    }
    @Override
    public IPage<FileManage> seleteByRelateId(Page<FileManage> page, String relateId)
    {
        return fileManageMapper.seleteByRelateId(page, relateId);
    }

}
