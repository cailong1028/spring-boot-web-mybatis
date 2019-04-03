/**
 *
 */
package com.modules.demo2.test.nio.netty.primary.list;

import java.io.Serializable;
import java.util.Date;

public class MonitorVideoVO implements Serializable {

	private String orderSeqNo;		// 文件个数
	private String taskName;		// 任务名称
	private String title;		// 标题/内容
	private String fileNum;		// 文件个数
	private Long monitorCycle;		// 监测周期
	private String monitorStatus;		// 监测状态
	private Long infringementMainNum;		// 疑似侵权主体数
	private Long infringementNum;		// 疑似侵权次数
	private Long beInfringementNum;		// 疑似被侵权次数
	private Date taskBegin;		// 开始监测时间
	private String contentSource;		// 监测内容来源 0-存证 1-手动上传
	private String refOrderSeqNo;		// 关联订单号
	private String registerId;		// 用户ID

	public String getOrderSeqNo() {
		return orderSeqNo;
	}

	public void setOrderSeqNo(String orderSeqNo) {
		this.orderSeqNo = orderSeqNo;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFileNum() {
		return fileNum;
	}

	public void setFileNum(String fileNum) {
		this.fileNum = fileNum;
	}

	public Long getMonitorCycle() {
		return monitorCycle;
	}

	public void setMonitorCycle(Long monitorCycle) {
		this.monitorCycle = monitorCycle;
	}

	public String getMonitorStatus() {
		return monitorStatus;
	}

	public void setMonitorStatus(String monitorStatus) {
		this.monitorStatus = monitorStatus;
	}

	public Long getInfringementMainNum() {
		return infringementMainNum;
	}

	public void setInfringementMainNum(Long infringementMainNum) {
		this.infringementMainNum = infringementMainNum;
	}

	public Long getInfringementNum() {
		return infringementNum;
	}

	public void setInfringementNum(Long infringementNum) {
		this.infringementNum = infringementNum;
	}

	public Long getBeInfringementNum() {
		return beInfringementNum;
	}

	public void setBeInfringementNum(Long beInfringementNum) {
		this.beInfringementNum = beInfringementNum;
	}

	public Date getTaskBegin() {
		return taskBegin;
	}

	public void setTaskBegin(Date taskBegin) {
		this.taskBegin = taskBegin;
	}

	public String getContentSource() {
		return contentSource;
	}

	public void setContentSource(String contentSource) {
		this.contentSource = contentSource;
	}

	public String getRefOrderSeqNo() {
		return refOrderSeqNo;
	}

	public void setRefOrderSeqNo(String refOrderSeqNo) {
		this.refOrderSeqNo = refOrderSeqNo;
	}

	public String getRegisterId() {
		return registerId;
	}

	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}
}