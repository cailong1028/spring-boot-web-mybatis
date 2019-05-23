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
 * �������û���ʶ��һ��ȱʡʵ�֡� 
 */
public class UserContext extends ESSObject implements User {

    /**
     * �����Ĳ���
     */
	private String context = null;
    
    /**
     * ԭ�����Ĳ���
     */
	private String oldContext = null;
    
    /**
     * �û�����
     */
    private String userID = null;
    
    /**
     * �û�����
     */
    private String userName = null;
    
    /**
     * �û�״̬
     */
    private String status = null;
    
    /**
     * �û���¼����
     */
    private String loginID = null;
    
    /**
     * �û���¼����
     */
    private String passwd = null;
    
    /**
     * ��������
     */
    private String orgID = null;
    
    /**
     * ��������
     */
    private String orgName = null;
    
    /**
     * ��������
     */
    private String orgType = null;
    
    /**
     * �û���ɫ
     */
    private String[] userRole = null;
    
    /**
     * �û�ӵ�е�Ȩ��
     */
    private String[] userAuth = null;
    
    /**
     * �û�ӵ�еĹ���
     */
    private String[][] userFuncs = null;
   
    /**
     * �û�������
     */
    private String userType = null;
    
    /**
     * ���ֿ�����
     */
    private String scoreCardID = null;

	/**
	 * @return �û��Ƿ���Ч
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @return �û���¼����
	 */
	public String getLoginID() {
		return loginID;
	}

	/**
	 * @return ��������
	 */
	public String getOrgID() {
		return orgID;
	}

	/**
	 * @return ��������
	 */
	public String getOrgName() {
		return orgName;
	}

	/**
	 * @return ��������
	 */
	public String getOrgType() {
		return orgType;
	}

	/**
	 * @return �û���¼����
	 */
	public String getPasswd() {
		return passwd;
	}

	/**
	 * @return �û�ӵ�еĹ���
	 */
	public String[][] getUserFuncs() {
		return userFuncs;
	}

	/**
	 * @return �û�����
	 */
	public String getUserID() {
		return userID;
	}

	/**
	 * @return �û�����
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @return �û������ĸ�λ
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
     * ����Ƿ�ӵ�е�Ȩ��
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
	 * @return ȡ�����Ĳ���
	 */
	public String getContext() {
        if (context == null)
            this.context = String.valueOf(System.currentTimeMillis());
		return context;
	}

	/**
	 * Sets the �����Ĳ���.
	 * @param context The context to set
	 */
	public void setContext(String context) {
        this.oldContext = this.context;
		this.context = context;
	}
    
    /**
     * ��ԭ�����Ĳ���
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