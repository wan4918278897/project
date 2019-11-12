package org.jeecg.modules.fileManage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.fileManage.entity.FileManage;
import org.jeecg.modules.system.entity.SysDictItem;

/**
 * @Description: 附件管理
 * @Author: jeecg-boot
 * @Date:   2019-05-27
 * @Version: V1.0
 */
public interface IFileManageService extends IService<FileManage> {
    void updateRelatedId(String id,String relateId);

    boolean deleteByRelateId(String relateId);

    public IPage<FileManage> seleteByRelateId(Page<FileManage> page, String relateId);




}
