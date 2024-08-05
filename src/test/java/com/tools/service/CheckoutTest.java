package com.tools.service;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
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
		ToolMasterService.loadToolMasters();
	}
	
	@Test
	void test() {
		ToolMaster master = ToolMasterService.findToolMaster("JAKD");		
		Tool tool = master.getTools().get(0);		
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

}
