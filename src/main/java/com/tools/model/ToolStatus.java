package com.tools.model;

public enum ToolStatus {
	ORDERED, INTRANSIT, RENTED, ONSHELF, DISPOSED;

	
	
	
	public static ToolStatus getStatus(String status) {
		for (ToolStatus checkStatus : ToolStatus.values()) {
			if (checkStatus.name().equals(status)) {
				return checkStatus;
			}
		}
		return null;
	}
}
