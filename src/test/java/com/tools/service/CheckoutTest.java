package com.tools.service;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tools.model.RentalAgreement;
import com.tools.model.Tool;
import com.tools.model.ToolMaster;
import com.tools.model.ToolStatus;

class CheckoutTest {

	@BeforeEach
	public void setup() throws IOException {
		
	}
	
	@Test
	void test() throws Exception {
				
		Tool tool = ToolsService.getToolsByToolCode("JAKD").get(0);
		ToolMaster master = ToolMasterService.findToolMaster("JAKD");
		tool.setToolMaster(master);
		assertEquals(ToolStatus.ONSHELF, tool.getCurrentStatus());
		RentalAgreement result = RentalService.createRentalAgreement(tool, 
				LocalDate.now(), 
				LocalDate.now().plusDays(3),
				0); 
		RentalService.checkoutTool(tool, result);
		assertEquals(ToolStatus.RENTED, tool.getCurrentStatus());
		
		assertThrows(Exception.class, () -> {
			RentalService.checkoutTool(tool, result);
		});
	}
	
	@Test
	void checkTestAvailability() throws SQLException {
				
		Tool tool = ToolsService.getToolsByToolCode("LADW").get(0);
		ToolMaster master = ToolMasterService.findToolMaster("LADW");
		tool.setToolMaster(master);

		assertThrows(Exception.class, () -> {
			 RentalService.createRentalAgreement(tool, 
					LocalDate.of(2024, 8, 4),
					LocalDate.of(2024,8,8),
					0);			
		});
		
		RentalAgreement result2 = RentalService.createRentalAgreement(tool, 
				LocalDate.of(2023, 8, 4),
				LocalDate.of(2023,8,8),
				0);
		boolean available =  RentalService.isAvailable(tool.getSerialNumber(), result2.getStartDate(), result2.getEndDate());
		assertTrue(available);
		
		
	}

}
