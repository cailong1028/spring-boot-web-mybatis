/**
 *
 */
package com.modules.demo2.test.nio.netty.primary.list;

import java.io.Serializable;


public class MonitorContentVideoVO implements Serializable {

	private String orderSeqNo;		// 订单号
	private String fileName;		// 文件名
	private String fileUrl;		// 文档URL
	private String registerId;		// 用户ID

	public String getOrderSeqNo() {
		return orderSeqNo;
	}

	public void setOrderSeqNo(String orderSeqNo) {
		this.orderSeqNo = orderSeqNo;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getRegisterId() {
		return registerId;
	}

	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}
}