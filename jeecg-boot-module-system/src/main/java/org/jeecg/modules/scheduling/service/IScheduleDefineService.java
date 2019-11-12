package org.jeecg.modules.scheduling.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.scheduling.entity.ScheduleDefine;

import java.util.List;
import java.util.Map;

/**
 * @Description: 1
 * @Author: jeecg-boot
 * @Date: 2019-06-18
 * @Version: V1.0
 */
public interface IScheduleDefineService extends IService<ScheduleDefine> {

    public void defineSave(ScheduleDefine scheduleDefines, String ids);

    /**
     * 自动排版查询所有相关记录
     *
     * @return
     */
    List<Map<String, String>> queryScheduleList(int week);

    /**
     * 过滤在休息的的员工
     * @param restDays
     * @param defineId 班次id
     * @return
     */
    List<Map<String, String>> selectPerson(String restDays, String defineId);


    /**
     * 下拉框选择班次信息
     * @return
     */
    public List<Map<String, String>> selectDefineList();
}
