package com.tools.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.tools.model.PricingProfile;
import com.tools.model.Tool;
import com.tools.model.ToolMaster;
import com.tools.model.ToolStatus;

public class ToolsService extends DataService {

	
	public List<Tool> getTools() {
		return null;
		
	}
	
	public static Tool getTool(String serialNumber) throws SQLException, ClassNotFoundException {
		Connection con = getConnection();
		Statement statement = con.createStatement();
		String query = "select serialNumber, status, M.toolCode, toolType, brand from Tool T inner join ToolMaster M on (T.toolCode = M.toolCode)"; 
		ResultSet results = statement.executeQuery(query);
		if (results.next()) {
			Tool tool = new Tool();
			tool.setSerialNumber(results.getString("serialNumber"));
			tool.setCurrentStatus(ToolStatus.getStatus(results.getString("status")));
			ToolMaster master = new ToolMaster();
			master.setToolCode(results.getString("toolCode"));
			master.setBrand(results.getString("brand"));
			tool.setToolMaster(master);
			return tool;
		} else {
			return null; //not found
		}
		
	}
	
	public static List<Tool> getToolsByToolCode(String toolCode) throws SQLException {
		Connection con = getConnection();
		
		String query = "select serialNumber, status, M.toolCode, toolType, brand, dailyCharge, weekdayCharge, weekendCharge, holidayCharge from tool T inner join ToolMaster M on (T.toolCode = M.toolCode) inner join pricingProfile p on (P.toolCode=T.toolCode) where T.toolCode = ?";
		PreparedStatement statement = con.prepareStatement(query);
		statement.setString(1, toolCode);
		ResultSet results = statement.executeQuery();
		List<Tool> tools = new ArrayList<>();
		while (results.next()) {
			Tool tool = new Tool();
			tool.setSerialNumber(results.getString("serialNumber"));
			tool.setCurrentStatus(ToolStatus.getStatus(results.getString("status")));

			ToolMaster master = new ToolMaster();
			master.setToolCode(results.getString("toolCode"));
			master.setBrand(results.getString("brand"));
			master.setToolType(results.getString("toolType"));
			tool.setToolMaster(master);
			tools.add(tool);
			
			PricingProfile pricing = new PricingProfile();
			pricing.setDailyCharge(results.getDouble("dailyCharge"));
			pricing.setWeekdayCharge(results.getBoolean("weekdayCharge"));
			pricing.setWeekendCharge(results.getBoolean("weekendCharge"));
			pricing.setHolidayCharge(results.getBoolean("holidayCharge"));			
			master.setPricingProfile(pricing);
			
			
			
		}
		return tools;
	}
	
	
	public static void updateStatus(Tool tool) throws SQLException {
		Connection con =  getConnection();
		
		String update = "update tool set status=? where serialNumber=?";		
		PreparedStatement statement = con.prepareStatement(update);
		statement.setString(1, tool.getCurrentStatus().name());
		statement.setString(2,  tool.getSerialNumber());
		statement.executeUpdate();
	}
}
 