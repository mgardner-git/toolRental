package com.tools.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.tools.model.PricingProfile;
import com.tools.model.Tool;
import com.tools.model.ToolMaster;
import com.tools.model.ToolStatus;

public class ToolMasterService {

	private static List<ToolMaster> toolMasters;
	
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
	
	public static ToolMaster findToolMaster(String toolCode) {
		for (ToolMaster checkMaster: toolMasters) {
			if (checkMaster.getToolCode().equals(toolCode)) {
				return checkMaster;
			}
		}
		return null;
	}
	

	public static void main(String[] args) throws IOException{
		loadToolMasters();
		ToolMaster result = findToolMaster("CHNS");
		System.out.println(result);
		ToolMaster result2 = findToolMaster("---");
		System.out.println(result2);
	}
}
