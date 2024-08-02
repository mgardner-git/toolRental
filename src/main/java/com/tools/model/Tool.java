package com.tools.model;

/**
 * A specific instance of a toolMaster;
 * @author User
 *
 */
public class Tool {

	private ToolStatus currentStatus;
	private String serialNumber;
	private ToolMaster toolMaster;
	public ToolStatus getCurrentStatus() {
		return currentStatus;
	}
	public void setCurrentStatus(ToolStatus currentStatus) {
		this.currentStatus = currentStatus;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public ToolMaster getToolMaster() {
		return toolMaster;
	}
	public void setToolMaster(ToolMaster toolMaster) {
		this.toolMaster = toolMaster;
	}
	
}
