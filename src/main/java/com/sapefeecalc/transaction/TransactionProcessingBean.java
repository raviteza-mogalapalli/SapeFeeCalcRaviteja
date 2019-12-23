package com.sapefeecalc.transaction;

import java.util.Comparator;
import java.util.Date;

public class TransactionProcessingBean {

	private String clientId;
	private String transactionType;
	private Date transactionDate;	
	private String Priority;
	private double processingFee;
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getPriority() {
		return Priority;
	}
	public void setPriority(String priority) {
		Priority = priority;
	}
	public double getProcessingFee() {
		return processingFee;
	}
	public void setProcessingFee(double processingFee) {
		this.processingFee = processingFee;
	}
	
	@Override
	public String toString() {
		return "TransactionProcessingBean [clientId=" + clientId + ", transactionType=" + transactionType
				+ ", transactionDate=" + transactionDate + ", Priority=" + Priority + ", processingFee=" + processingFee
				+ "]";
	}
	
	
}
