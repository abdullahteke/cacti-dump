package com.abdullahteke.cactidump.controller;

public class InterfaceRecord {

	private String timeStamp;
	private String interfaceName;
	private String onNode;
	private String throughputIn;
	private String throughputOut;
	
	
	public String getTimeStamp() {
		return timeStamp;
	}
	
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	public String getInterfaceName() {
		return interfaceName;
	}
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	public String getOnNode() {
		return onNode;
	}
	
	public void setOnNode(String onNode) {
		this.onNode = onNode;
	}

	public String getThroughputIn() {
		return throughputIn;
	}

	public void setThroughputIn(String throughputIn) {
		this.throughputIn = throughputIn;
	}

	public String getThroughputOut() {
		return throughputOut;
	}

	public void setThroughputOut(String throughputOut) {
		this.throughputOut = throughputOut;
	}

	
}
