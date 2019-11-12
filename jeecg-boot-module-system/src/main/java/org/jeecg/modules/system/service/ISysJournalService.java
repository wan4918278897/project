package org.jeecg.modules.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.system.entity.SysJournal;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 个人日志
 * @Author: jeecg-boot
 * @Date:   2019-07-22
 * @Version: V1.0
 */
public interface ISysJournalService extends IService<SysJournal> {
    public IPage queryList(Page page, String title, String reporter, String checkDate);

    int selectByDate(String date);

}
