package org.jeecg.modules.system.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * @author yzx
 */
@Data
public class SysDepartUsersVO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**部门id*/
	private String depId;
	/**对应的用户id集合*/
	private List<String> userIdList;

    public String getDepId() {
        return depId;
    }

    public void setDepId(String depId) {
        this.depId = depId;
    }

    public List<String> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<String> userIdList) {
        this.userIdList = userIdList;
    }
}
