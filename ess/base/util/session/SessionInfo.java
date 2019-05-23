/******************************************************************************
    File: SessionInfo.java

    Version 1.0
    Date            Author                Changes
    July.20,2010     xuxu          		  Created

    Copyright (c) 2010, dppc
    all rights reserved.
 ******************************************************************************/
package ess.base.util.session;

import java.sql.Connection;
import java.sql.SQLException;

import ess.base.bo.ESSObject;
import ess.base.exp.ESSConnectionGetTimeOutException;

public class SessionInfo {

	private String sessionID = null;

	private Long linkTime = null;

	private Connection conn = null;

	public SessionInfo() throws ESSConnectionGetTimeOutException, SQLException {
		linkTime = new Long(System.currentTimeMillis());
		conn = ESSObject.getDataStore().getConnection();
	}

	public Long getLinkTime() {
		return linkTime;
	}

	public Connection getConn() {
		return conn;
	}

	public String getSessionID() {
		return sessionID;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

}
