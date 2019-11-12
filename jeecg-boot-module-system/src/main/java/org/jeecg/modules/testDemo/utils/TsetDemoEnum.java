package org.jeecg.modules.testDemo.utils;

import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @author yzx
 */
public enum TsetDemoEnum {
    @Excel(name = "主校区", width = 15) A,
    @Excel(name = "东校区", width = 15) B,
    @Excel(name = "同济校区", width = 15) C,
}
