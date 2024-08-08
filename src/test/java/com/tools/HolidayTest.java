package com.tools;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tools.model.RentalAgreement;
import com.tools.model.Tool;
import com.tools.model.ToolMaster;
import com.tools.service.RentalService;
import com.tools.service.ToolMasterService;
import com.tools.service.ToolsService;

class HolidayTest {

	@BeforeEach
	public void setup() throws IOException {
		//ToolMasterService.loadToolMasters();
	}
	@Test
	void test() throws SQLException{
		
		
		Tool tool = ToolsService.getToolsByToolCode("LADW").get(0);	
		
		assertEquals("2",tool.getSerialNumber());
		//in 2008, 4jul falls on Friday
		RentalAgreement result = RentalService.createRentalAgreement(tool, 
				LocalDate.of(2008, 7, 4), 
				LocalDate.of(2008, 7, 5),
				50); 
		
		assertEquals("LADW", result.getTool().getToolMaster().getToolCode());
		assertEquals("Ladder", result.getTool().getToolMaster().getToolType());
		assertEquals("Werner", result.getTool().getToolMaster().getBrand());
		assertEquals(2, result.getRentalDays());
		assertEquals(LocalDate.now(), result.getCheckoutDate());
		assertEquals(LocalDate.of(2008, 7,5), result.getEndDate());
		assertEquals(1.99, result.getDailyCharge());
		assertEquals(1, result.getChargeDays());
		assertEquals(1.99, result.getPreDiscountCharge());
		assertEquals(50, result.getDiscount());
		//note that the discount amount is stored with full precision, but displayed in currency format with 2 places.
		//when this is moved to a database, this may need to be discussed
		assertEquals(.995, result.getDiscountAmount());		
		assertEquals(.995, result.getFinalCharge());
	}
	
	@Test
	void testSaturday() throws SQLException {
		
		Tool tool = ToolsService.getToolsByToolCode("LADW").get(0);		
		assertEquals("2",tool.getSerialNumber());
		//in 2009, 4jul falls on Saturday, so the 3rd would count as Holiday and not be charged
		RentalAgreement result = RentalService.createRentalAgreement(tool, 
				LocalDate.of(2009, 7, 2), 
				LocalDate.of(2009, 7, 3),
				50);
		assertEquals(1, result.getChargeDays());
		
	}
	
	@Test
	void testSunday() throws SQLException{
				
		Tool tool = ToolsService.getToolsByToolCode("LADW").get(0);
		//in 2004, 4jul falls on Sunday, so the 5th would count as Holiday and not be charged
		RentalAgreement result = RentalService.createRentalAgreement(tool, 
				LocalDate.of(2004, 7, 5), 
				LocalDate.of(2004, 7, 6),
				50);
		assertEquals(1, result.getChargeDays());		
	}
	
	@Test
	void testLaborDay() throws SQLException {
		Tool tool = ToolsService.getToolsByToolCode("LADW").get(0);		

		//in 2024, Labor day falls on Sep 2
		RentalAgreement result = RentalService.createRentalAgreement(tool, 
				LocalDate.of(2024, 9, 1), 
				LocalDate.of(2024, 9, 2), 0);
		assertEquals(1, result.getChargeDays());
	}
	
	@Test 
	void testNoHolidays() throws SQLException {
		
		Tool tool = ToolsService.getToolsByToolCode("LADW").get(0);
		RentalAgreement result = RentalService.createRentalAgreement(tool, 
				LocalDate.of(2024,  1, 1),
				LocalDate.of(2024, 1, 4),0);
		assertEquals(4, result.getChargeDays());		
	}
	
	@Test
	void testWeekend() throws SQLException {
		
		Tool tool = ToolsService.getToolsByToolCode("CHNS").get(0);
		RentalAgreement result = RentalService.createRentalAgreement(tool, 
				LocalDate.of(2024,  8, 2),
				LocalDate.of(2024, 8, 3),0);
		assertEquals(1, result.getChargeDays());
	}

}
