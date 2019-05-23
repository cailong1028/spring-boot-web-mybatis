/******************************************************************************
    File: SimpleSession.java

    Version 1.0
    Date            Author                Changes
    July.20,2010     xuxu          		  Created

    Copyright (c) 2010, dppc
    all rights reserved.
 ******************************************************************************/
package ess.base.util.session;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

import ess.base.bo.ESSObject;
import ess.base.db.ESSDataStoreManager;
import ess.base.exp.ESSConnectionGetTimeOutException;

public class SimpleSessionManager {

	private static HashMap<Object, SessionInfo> tellerMap = new HashMap<Object, SessionInfo>();

	private static synchronized String generateTeller() {
		return String.valueOf(UUID.randomUUID());
	}

	/**
	 * 启用用户
	 * 
	 * @param teller
	 */
	public static SessionInfo activeTeller() {
		SessionInfo si = null;

		try {
			si = new SessionInfo();
			si.setSessionID(generateTeller());
			tellerMap.put(si.getSessionID(), si);
		} catch (ESSConnectionGetTimeOutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return si;
	}

	/**
	 * 注销用户
	 * 
	 * @param teller
	 */
	public static void inactiveTeller(SessionInfo si) {
		try {
			ESSDataStoreManager.getInstance().freeConnection(si.getConn());
		} catch (ESSConnectionGetTimeOutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		tellerMap.remove(si.getSessionID());
	}
}
