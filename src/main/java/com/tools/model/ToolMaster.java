package com.tools.model;

import java.util.ArrayList;
import java.util.List;
/**
 * A ToolMaster is a brand and make/type of tool from a particular brand.
 * @author User
 *
 */
public class ToolMaster {

	private String toolCode;
	private String toolType;
	private String brand;
	private PricingProfile pricingProfile;
	private List<Tool> tools;
	
	public ToolMaster() {
		tools = new ArrayList<Tool>();
	}
	public String getToolCode() {
		return toolCode;
	}
	public void setToolCode(String toolCode) {
		this.toolCode = toolCode;
	}
	public String getToolType() {
		return toolType;
	}
	public void setToolType(String toolType) {
		this.toolType = toolType;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public PricingProfile getPricingProfile() {
		return pricingProfile;
	}
	public void setPricingProfile(PricingProfile pricingProfile) {
		this.pricingProfile = pricingProfile;
	}
	
	public void addTool(Tool tool) {
		tools.add(tool);
	}
	public List<Tool> getTools() {
		return tools;
	}
	
}
