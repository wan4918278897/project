package org.jeecg.modules.even.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class EvenPublishForm {

    /**
     * title
     */
    @Excel(name = "事件标题", width = 15)
    private java.lang.String title;


    @Excel(name = "申请人", width = 15)
    private String applyUser;

    @Excel(name = "行政区划", width = 15)
    private java.lang.String administrativeDivision;
    /**
     * date
     */
    @Excel(name = "事发时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date date;
    /**
     * deathNum
     */
    @Excel(name = "死亡人数", width = 15)
    private java.lang.Integer deathNum;
    /**
     * evenDescribe
     */
    @Excel(name = "事件描述", width = 15)
    private java.lang.String evenDescribe;
    /**
     * evenLevel
     */
    @Excel(name = "事件级别", width = 15)
    private java.lang.String evenLevel;
    /**
     * evenProperty
     */
    @Excel(name = "事件性质", width = 15)
    private java.lang.String evenProperty;
    /**
     * evenType
     */
    @Excel(name = "事件类型", width = 15)
    private java.lang.String evenType;
    /**
     * injuredNum
     */
    @Excel(name = "受伤人数", width = 15)
    private java.lang.Integer injuredNum;
    /**
     * missNum
     */
    @Excel(name = "失踪人数", width = 15)
    private java.lang.Integer missNum;
    /**
     * place
     */
    @Excel(name = "事发地点", width = 15)
    private java.lang.String place;
    /**
     * prePlanName
     */
    @Excel(name = "预案名称", width = 15)
    private java.lang.String prePlanName;
    /**
     * reason
     */
    @Excel(name = "事发原因", width = 15)
    private java.lang.String reason;

    @Excel(name = "处理人", width = 15)
    private java.lang.String dealPerson;

    @Excel(name = "处理过程", width = 15)
    private java.lang.String dealProcess;

    @Excel(name = "审核人", width = 15)
    private java.lang.String publishPerson;

    @Excel(name = "审核过程", width = 15)
    private java.lang.String publishProcess;
}
