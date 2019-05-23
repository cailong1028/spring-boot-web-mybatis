/******************************************************************************
	File UserContext.java

	Version 1.0
	Date            Author          Changes
	Feb.10,2003      Lishengwang    Created

	Copyright (c), 2003
	all rights reserved
******************************************************************************/
package ess.base.bo;

import ess.base.bo.ESSObject;

/**
 * 本类是用户标识的一个缺省实现。 
 */
public class UserContext extends ESSObject implements User {

    /**
     * 上下文参数
     */
	private String context = null;
    
    /**
     * 原上下文参数
     */
	private String oldContext = null;
    
    /**
     * 用户代码
     */
    private String userID = null;
    
    /**
     * 用户名称
     */
    private String userName = null;
    
    /**
     * 用户状态
     */
    private String status = null;
    
    /**
     * 用户登录代码
     */
    private String loginID = null;
    
    /**
     * 用户登录密码
     */
    private String passwd = null;
    
    /**
     * 机构代码
     */
    private String orgID = null;
    
    /**
     * 机构名称
     */
    private String orgName = null;
    
    /**
     * 机构类型
     */
    private String orgType = null;
    
    /**
     * 用户角色
     */
    private String[] userRole = null;
    
    /**
     * 用户拥有的权限
     */
    private String[] userAuth = null;
    
    /**
     * 用户拥有的功能
     */
    private String[][] userFuncs = null;
   
    /**
     * 用户的类型
     */
    private String userType = null;
    
    /**
     * 积分卡卡号
     */
    private String scoreCardID = null;

	/**
	 * @return 用户是否有效
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @return 用户登录代码
	 */
	public String getLoginID() {
		return loginID;
	}

	/**
	 * @return 机构代码
	 */
	public String getOrgID() {
		return orgID;
	}

	/**
	 * @return 机构名称
	 */
	public String getOrgName() {
		return orgName;
	}

	/**
	 * @return 机构类型
	 */
	public String getOrgType() {
		return orgType;
	}

	/**
	 * @return 用户登录密码
	 */
	public String getPasswd() {
		return passwd;
	}

	/**
	 * @return 用户拥有的功能
	 */
	public String[][] getUserFuncs() {
		return userFuncs;
	}

	/**
	 * @return 用户代码
	 */
	public String getUserID() {
		return userID;
	}

	/**
	 * @return 用户名称
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @return 用户所属的岗位
	 */
	public String[] getUserRole() {
		return userRole;
	}

	/**
	 * Sets the isValid.
	 * @param isValid The isValid to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Sets the loginID.
	 * @param loginID The loginID to set
	 */
	public void setLoginID(String loginID) {
		this.loginID = loginID;
	}

	/**
	 * Sets the orgID.
	 * @param orgID The orgID to set
	 */
	public void setOrgID(String orgID) {
		this.orgID = orgID;
	}

	/**
	 * Sets the orgName.
	 * @param orgName The orgName to set
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	/**
	 * Sets the orgType.
	 * @param orgType The orgType to set
	 */
	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	/**
	 * Sets the passwd.
	 * @param passwd The passwd to set
	 */
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	/**
	 * Sets the userFuncs.
	 * @param userFuncs The userFuncs to set
	 */
	public void setUserFuncs(String[][] userFuncs) {
		this.userFuncs = userFuncs;
	}

	/**
	 * Sets the userID.
	 * @param userID The userID to set
	 */
	public void setUserID(String userID) {
		this.userID = userID;
	}

	/**
	 * Sets the userName.
	 * @param userName The userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Sets the userRole.
	 * @param userRole The userRole to set
	 */
	public void setUserRole(String[] userRole) {
		this.userRole = userRole;
	}
    
    /**
     * @return String[]
     */
    public String[] getUserAuth() {
        return userAuth;
    }

    /**
     * Sets the userAuth.
     * @param userAuth The userAuth to set
     */
    public void setUserAuth(String[] userAuth) {
        this.userAuth = userAuth;
    }

    /**
     * 检查是否拥有的权限
     */
    public boolean isAllowed (String strAuthID) {
        for (int i = 0; i < userAuth.length; i++) {
			if (this.userAuth[i].equals(strAuthID)) {
                return true;
			}
		}
        return false;
    }

	/**
	 * @return 取上下文参数
	 */
	public String getContext() {
        if (context == null)
            this.context = String.valueOf(System.currentTimeMillis());
		return context;
	}

	/**
	 * Sets the 上下文参数.
	 * @param context The context to set
	 */
	public void setContext(String context) {
        this.oldContext = this.context;
		this.context = context;
	}
    
    /**
     * 还原上下文参数
     */
    public void restoreContext() {
        this.context = this.oldContext;
        this.oldContext = null;
    }
    
	/**
	 * @return String
	 */
	public String getScoreCardID() {
		return scoreCardID;
	}

	/**
	 * @return String
	 */
	public String getUserType() {
		return userType;
	}

	/**
	 * Sets the scoreCardID.
	 * @param scoreCardID The scoreCardID to set
	 */
	public void setScoreCardID(String scoreCardID) {
		this.scoreCardID = scoreCardID;
	}

	/**
	 * Sets the userType.
	 * @param userType The userType to set
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

}