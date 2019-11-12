package org.jeecg.modules.system.model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author yzx
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@NoArgsConstructor
public class SysUserDepartsVO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**用户id*/
	private String userId;
	/**对应的部门id集合*/
	private String departIdList;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDepartIdList() {
        return departIdList;
    }

    public void setDepartIdList(String departIdList) {
        this.departIdList = departIdList;
    }
}
