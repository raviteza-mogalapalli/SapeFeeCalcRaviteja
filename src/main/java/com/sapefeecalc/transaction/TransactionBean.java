package com.sapefeecalc.transaction;

import java.util.Calendar;
import java.util.Date;

public class TransactionBean {

	private String transactionId;
	private String clientId;
	private String securityId;
	private String transactionType;
	private Date transactionDate;
	private double marketValue;
	private String 	priorityFlag;
	
	public TransactionBean(String transactionId, String clientId, String securityId, String transactionType,
			Date transactionDate, double marketValue, String priorityFlag) {
		super();
		this.transactionId = transactionId;
		this.clientId = clientId;
		this.securityId = securityId;
		this.transactionType = transactionType;
		this.transactionDate = transactionDate;
		this.marketValue = marketValue;
		this.priorityFlag = priorityFlag;
	}
	
	public TransactionBean() {
		// TODO Auto-generated constructor stub
	}

	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getSecurityId() {
		return securityId;
	}
	public void setSecurityId(String securityId) {
		this.securityId = securityId;
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
	public double getMarketValue() {
		return marketValue;
	}
	public void setMarketValue(double marketValue) {
		this.marketValue = marketValue;
	}
	public String getPriorityFlag() {
		return priorityFlag;
	}
	public void setPriorityFlag(String priorityFlag) {
		this.priorityFlag = priorityFlag;
	}
	
	@Override
	public String toString() {
		return "TransactionBean [transactionId=" + transactionId + ", clientId=" + clientId + ", securityId="
				+ securityId + ", transactionType=" + transactionType + ", transactionDate=" + transactionDate
				+ ", marketValue=" + marketValue + ", priorityFlag=" + priorityFlag + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clientId == null) ? 0 : clientId.hashCode());
		result = prime * result + ((securityId == null) ? 0 : securityId.hashCode());
		result = prime * result + ((transactionDate == null) ? 0 : transactionDate.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TransactionBean other = (TransactionBean) obj;
		if (clientId == null) {
			if (other.clientId != null)
				return false;
		} else if (!clientId.equals(other.clientId))
			return false;
		if (securityId == null) {
			if (other.securityId != null)
				return false;
		} else if (!securityId.equals(other.securityId))
			return false;
		if (transactionDate == null) {
			if (other.transactionDate != null)
				return false;
		} else {
				Calendar cal1 = Calendar.getInstance();
		        Calendar cal2 = Calendar.getInstance();
		        cal1.setTime(transactionDate);
		        cal2.setTime(other.transactionDate);
		        if (!cal1.equals(cal2)) {
		           return false;
		        }
		}if (transactionType == null) {
			if (other.transactionType != null)
				return false;
		} else {
				if (!(transactionType.equalsIgnoreCase("SELL"))) {
					if(!other.transactionType.equalsIgnoreCase("SELL"))
		           return false;
		        }
				if (!(transactionType.equalsIgnoreCase("BUY"))) {
					if(!other.transactionType.equalsIgnoreCase("BUY"))
		           return false;
		        }
		}
		return true;
	}
	
		
		
	
}
