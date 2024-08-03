package com.tools;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tools.model.RentalAgreement;
import com.tools.model.Tool;
import com.tools.model.ToolMaster;
import com.tools.service.RentalService;
import com.tools.service.ToolMasterService;

class TestScenarios {

	@BeforeEach
	public void setup() throws IOException {
		ToolMasterService.loadToolMasters();
	}
	/*
	@Test
	void testScenario1() {
		ToolMaster master = ToolMasterService.findToolMaster("JAKR");
		assertNotNull(master);
		Tool tool = master.getTools().get(0);
		assertEquals("4",tool.getSerialNumber());
		assertThrows(Exception.class, () -> {
			RentalService.createRentalAgreement(tool, 
					LocalDate.of(2015, 9, 3), 
					LocalDate.of(2015, 9, 7),
					101);
		});		
	}
	
	@Test
	void testScenario2() {
		ToolMaster master = ToolMasterService.findToolMaster("LADW");
		assertNotNull(master);
		Tool tool = master.getTools().get(0);		
		//in 2008, 4jul falls on Friday
		RentalAgreement result = RentalService.createRentalAgreement(tool, 
				LocalDate.of(2020, 7, 2), 
				LocalDate.of(2020, 7, 4),
				10); 
		//4th of july is on a Saturday and LADW does not charge on Holidays, so we should get 2 chargeable days
		assertEquals(2, result.getChargeDays());
		assertEquals(1.99, result.getTool().getToolMaster().getPricingProfile().getDailyCharge());
		assertEquals(3.98, result.getPreDiscountCharge());
		assertEquals(10, result.getDiscount());
		assertEquals(3.582, result.getFinalCharge());
		
		assertEquals("LADW", result.getTool().getToolMaster().getToolCode());			
	}
	
	@Test
	void testScenario3() {
		
		ToolMaster master = ToolMasterService.findToolMaster("CHNS");		
		Tool tool = master.getTools().get(0);		
		RentalAgreement result = RentalService.createRentalAgreement(tool, 
				LocalDate.of(2015, 7,2), 
				LocalDate.of(2015, 7,6),
				25); 
		//4th of july is on a Saturday and LADW does not charge on Holidays  so we should get 4 chargeable days
		assertEquals(3, result.getChargeDays());
		assertEquals(1.49, result.getTool().getToolMaster().getPricingProfile().getDailyCharge());
		assertEquals(4.47, result.getPreDiscountCharge());
		assertEquals(25, result.getDiscount());
		assertEquals(3.3525, result.getFinalCharge());
		
		assertEquals("CHNS", result.getTool().getToolMaster().getToolCode());			
		
	}
	*/
	@Test
	void testScenario4() {
		ToolMaster master = ToolMasterService.findToolMaster("JAKD");		
		Tool tool = master.getTools().get(0);		

		RentalAgreement result = RentalService.createRentalAgreement(tool, 
				LocalDate.of(2015, 9,3), 
				LocalDate.of(2015, 9,8),
				0); 
		//Labor day is on the 7th. JAKD does not charge for holidays or weekends so we should get 3 chargeable days
		assertEquals(3, result.getChargeDays());
		assertEquals(2.99, result.getTool().getToolMaster().getPricingProfile().getDailyCharge());
		assertEquals(8.97, result.getPreDiscountCharge());
		assertEquals(0, result.getDiscount());
		assertEquals(8.97, result.getFinalCharge());
		
		assertEquals("JAKD", result.getTool().getToolMaster().getToolCode());		
	}
	
	@Test
	void testScenario5() {
		ToolMaster master = ToolMasterService.findToolMaster("JAKR");		
		Tool tool = master.getTools().get(0);		

		RentalAgreement result = RentalService.createRentalAgreement(tool, 
				LocalDate.of(2015, 7,2), 
				LocalDate.of(2015, 7,16),
				0); 
		//Labor day is on the 7th. JAKR does not charge for holidays or weekends so we should get 3 chargeable days
		assertEquals(10, result.getChargeDays());
		assertEquals(2.99, result.getTool().getToolMaster().getPricingProfile().getDailyCharge());
		assertEquals(29.90, result.getPreDiscountCharge(), .001);
		assertEquals(0, result.getDiscount());
		assertEquals(29.90, result.getFinalCharge(), .001);
		
		assertEquals("JAKR", result.getTool().getToolMaster().getToolCode());	
	}
	
	@Test
	void testScenario6() {
		ToolMaster master = ToolMasterService.findToolMaster("JAKR");		
		Tool tool = master.getTools().get(0);		

		RentalAgreement result = RentalService.createRentalAgreement(tool, 
				LocalDate.of(2020, 7,2), 
				LocalDate.of(2020, 7,5),
				50);
		assertEquals(1, result.getChargeDays());
		assertEquals(2.99, result.getTool().getToolMaster().getPricingProfile().getDailyCharge());
		assertEquals(2.99, result.getPreDiscountCharge(), .001);
		assertEquals(50, result.getDiscount());
		assertEquals(1.49, result.getFinalCharge(), .009);
		
		assertEquals("JAKR", result.getTool().getToolMaster().getToolCode());			
	}
}
