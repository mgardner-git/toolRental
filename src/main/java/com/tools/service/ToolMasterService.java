package com.tools.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.tools.model.PricingProfile;
import com.tools.model.Tool;
import com.tools.model.ToolMaster;
import com.tools.model.ToolStatus;

public class ToolMasterService extends DataService {

	private static List<ToolMaster> toolMasters;
	/*
	public static void loadToolMasters() throws IOException {
		toolMasters = new ArrayList<ToolMaster>();
		File file = new File("database.txt");		
		FileReader reader = new FileReader(file);
		BufferedReader br = new BufferedReader(reader);
		String line = br.readLine();
		line = br.readLine();
		//read tool masters
		while (! line.startsWith("#")) {
			StringTokenizer splitter = new StringTokenizer(line);
			ToolMaster master = new ToolMaster();
			master.setToolCode(splitter.nextToken());
			master.setToolType(splitter.nextToken());
			master.setBrand(splitter.nextToken());
			toolMasters.add(master);
			line = br.readLine();			
		}
		//read tools
		line = br.readLine();
		while (!line.startsWith("#")) {
			StringTokenizer splitter = new StringTokenizer(line);
			Tool tool = new Tool();
			tool.setCurrentStatus(ToolStatus.ONSHELF);
			ToolMaster master = findToolMaster(splitter.nextToken());
			tool.setToolMaster(master);
			master.addTool(tool);
			tool.setSerialNumber(splitter.nextToken());
			line = br.readLine();
		}
		line = br.readLine();
		while (line != null && line.trim().length() > 0) {
			StringTokenizer splitter = new StringTokenizer(line);
			PricingProfile prof = new PricingProfile();
			ToolMaster master  = findToolMaster(splitter.nextToken());
			prof.setDailyCharge(Double.parseDouble(splitter.nextToken()));
			prof.setWeekdayCharge(splitter.nextToken().equals("yes"));
			prof.setWeekendCharge(splitter.nextToken().equals("yes"));
			prof.setHolidayCharge(splitter.nextToken().equals("yes"));
			master.setPricingProfile(prof);
			line = br.readLine();
					
		}
		
		br.close();
	}
	*/
	
	public static ToolMaster findToolMaster(String toolCode) throws SQLException{
		Connection con = getConnection();
		
		String query = "select M.toolCode, toolType, dailyCharge, weekdayCharge, weekendCharge, holidayCharge from ToolMaster M inner join PricingProfile P on M.toolCode = P.toolCode where M.toolCode=?";
		PreparedStatement statement = con.prepareStatement(query);
		statement.setString(1, toolCode);
		ResultSet results = statement.executeQuery();
		if (results.next()) {
			ToolMaster master = new ToolMaster();
			master.setToolCode(results.getString("toolCode"));
			master.setToolType(results.getString("toolType"));
			PricingProfile pricing = new PricingProfile();
			pricing.setDailyCharge(results.getDouble("dailyCharge"));
			pricing.setWeekdayCharge(results.getBoolean("weekdayCharge"));
			pricing.setWeekendCharge(results.getBoolean("weekendCharge"));
			pricing.setHolidayCharge(results.getBoolean("holidayCharge"));
			master.setPricingProfile(pricing);
			
			return master;
		} else {
			return null; //not found
		}
	}
	

	public static void main(String[] args) throws Exception{
		//loadToolMasters();
		ToolMaster result = findToolMaster("CHNS");
		System.out.println(result);
		ToolMaster result2 = findToolMaster("---");
		System.out.println(result2);
	}
}
