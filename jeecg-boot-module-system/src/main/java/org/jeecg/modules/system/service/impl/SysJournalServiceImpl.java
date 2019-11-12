package org.jeecg.modules.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.system.entity.SysJournal;
import org.jeecg.modules.system.mapper.SysJournalMapper;
import org.jeecg.modules.system.service.ISysJournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 个人日志
 * @Author: jeecg-boot
 * @Date:   2019-07-22
 * @Version: V1.0
 */
@Service
public class SysJournalServiceImpl extends ServiceImpl<SysJournalMapper, SysJournal> implements ISysJournalService {


    @Autowired
    private SysJournalMapper sysJournalMapper;
    @Override
    public IPage queryList(Page page, String title, String reporter, String checkDate) {
        return sysJournalMapper.queryList(page,title,reporter,checkDate);
    }

    public int selectByDate(String date)
    {
        return sysJournalMapper.selectByDate(date);
    }
}
