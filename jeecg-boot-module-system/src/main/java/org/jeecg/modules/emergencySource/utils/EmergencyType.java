package org.jeecg.modules.emergencySource.utils;

import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @author yzx
 */

public enum EmergencyType {

    @Excel(name = "危险源", width = 15) A,
    @Excel(name = "防护目标", width = 15) B,
    @Excel(name = "应急管理机构", width = 15) C,
    @Excel(name = "应急物资", width = 15) D,
    @Excel(name = "物资储备", width = 15) E,
    @Excel(name = "应急救援队伍", width = 15) F,
}
