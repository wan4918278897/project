package org.jeecg.modules.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.jeecg.modules.system.entity.SysDictItem;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @Author zhangweijian
 * @since 2018-12-28
 */
public interface ISysDictItemService extends IService<SysDictItem> {
    public List<SysDictItem> selectItemsByMainId(String mainId);
    public IPage<SysDictItem> getList(Page <SysDictItem>page, String dictCode);
}
