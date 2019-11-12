package org.jeecg.modules.scheduling.entity;


import lombok.Getter;
import lombok.Setter;
import org.jeecgframework.poi.excel.annotation.Excel;
import java.io.Serializable;

@Setter
@Getter
public class ScheduleStaffForm implements Serializable {

    private static final long serialVersionUID = 2929826844795738540L;

    private String id;

    private String userId;
    /**
     *员工姓名
     */
    private String name;

    /**
     *性别
     */
    private String sex;

    /**
     *休息日
     */
    private String restDays;

    /**
     * 头像
     */
    @Excel(name = "头像", width = 15)
    private String avatar;

    /**
     * 电话
     */
    @Excel(name = "电话", width = 15)
    private String phone;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 单位名称
     */
    private String orgName;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }


}
