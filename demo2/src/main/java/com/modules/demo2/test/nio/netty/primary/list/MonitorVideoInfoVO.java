/**
 *
 */
package com.modules.demo2.test.nio.netty.primary.list;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class MonitorVideoInfoVO implements Serializable {

	private String orderSeqNo;		// 订单号
	private String taskName;		// 任务名称
	private String monitorType;		// 监测类型 0-图片 1-文字 2-音频 3-视频 4-舆情
	private String monitorStatus;		// 监测状态
	private Long monitorCycle;		// 监测周期
	private String fileNum;		// 文件个数
	private Long infringementNum;		// 疑似侵权次数
	private Date taskBegin;		// 开始监测时间
	private Long payAmount;		// 支付总额
	private String payWay;		// 支付方式：AliPay - 支付宝、WechatPay - 微信、Hongbao - 红包、Balance -账户余额
	private String payStatus;		// 支付状态：unpay(默认-待支付)、paid(已支付)、refund(退款)
	private Date payDate;		// 支付时间
	private String tranSerialsNo;		// 交易流水号
	private Long beanAmount;		// 使用余额金额（之前称版豆）
	private String isUseHongbao;		// 是否使用红包
	private Long hongbaoAmount;		// 使用红包金额
	private String source;		// 申请渠道
	private String registerId;		// 用户ID

	private String  taskStartTime;//监测任务开始时间

	private String  taskEndTime;//监测任务结束时间

	private String remarks; //备注

	private String contentSource; //监测内容来源 0-存证 1-手动上传

	private String refOrderSeqNo; //关联订单号

	private MonitorVideoVO monitorVideo;

	private List<MonitorContentVideoVO> monitorContentVideos;

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

    public String getMonitorType() {
        return monitorType;
    }

    public void setMonitorType(String monitorType) {
        this.monitorType = monitorType;
    }

    public String getMonitorStatus() {
        return monitorStatus;
    }

    public void setMonitorStatus(String monitorStatus) {
        this.monitorStatus = monitorStatus;
    }

    public Long getMonitorCycle() {
        return monitorCycle;
    }

    public void setMonitorCycle(Long monitorCycle) {
        this.monitorCycle = monitorCycle;
    }

    public String getFileNum() {
        return fileNum;
    }

    public void setFileNum(String fileNum) {
        this.fileNum = fileNum;
    }

    public Long getInfringementNum() {
        return infringementNum;
    }

    public void setInfringementNum(Long infringementNum) {
        this.infringementNum = infringementNum;
    }

    public Date getTaskBegin() {
        return taskBegin;
    }

    public void setTaskBegin(Date taskBegin) {
        this.taskBegin = taskBegin;
    }

    public Long getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(Long payAmount) {
        this.payAmount = payAmount;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public String getTranSerialsNo() {
        return tranSerialsNo;
    }

    public void setTranSerialsNo(String tranSerialsNo) {
        this.tranSerialsNo = tranSerialsNo;
    }

    public Long getBeanAmount() {
        return beanAmount;
    }

    public void setBeanAmount(Long beanAmount) {
        this.beanAmount = beanAmount;
    }

    public String getIsUseHongbao() {
        return isUseHongbao;
    }

    public void setIsUseHongbao(String isUseHongbao) {
        this.isUseHongbao = isUseHongbao;
    }

    public Long getHongbaoAmount() {
        return hongbaoAmount;
    }

    public void setHongbaoAmount(Long hongbaoAmount) {
        this.hongbaoAmount = hongbaoAmount;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getRegisterId() {
        return registerId;
    }

    public void setRegisterId(String registerId) {
        this.registerId = registerId;
    }

    public String getTaskStartTime() {
        return taskStartTime;
    }

    public void setTaskStartTime(String taskStartTime) {
        this.taskStartTime = taskStartTime;
    }

    public String getTaskEndTime() {
        return taskEndTime;
    }

    public void setTaskEndTime(String taskEndTime) {
        this.taskEndTime = taskEndTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public MonitorVideoVO getMonitorVideo() {
        return monitorVideo;
    }

    public void setMonitorVideo(MonitorVideoVO monitorVideo) {
        this.monitorVideo = monitorVideo;
    }

    public List<MonitorContentVideoVO> getMonitorContentVideos() {
        return monitorContentVideos;
    }

    public void setMonitorContentVideos(List<MonitorContentVideoVO> monitorContentVideos) {
        this.monitorContentVideos = monitorContentVideos;
    }
}