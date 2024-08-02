package com.tools.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;

import com.tools.model.PricingProfile;
import com.tools.model.RentalAgreement;
import com.tools.model.Tool;
import com.tools.model.ToolMaster;

public class RentalService {

	private static boolean isHoliday(LocalDate day) {
		int year = LocalDate.now().getYear();
		//if falls on weekend, it is observed on the closest weekday (If sat, then friday before, if Sunday, then Monday after)
		LocalDate julyFour = LocalDate.of(year, 6,4);
		if (julyFour.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
			julyFour = LocalDate.of(year, 6, 3);
		} else if (julyFour.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
			julyFour = LocalDate.of(year,  6, 5);
		}
		if (day.equals(julyFour)) {
			return true;
		}
		//labor day, first monday in september
		LocalDate laborDay = LocalDate.of(year, 9, 1);
		laborDay = laborDay.with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));
		if (day.equals(laborDay)) {
			return true;
		}
		return false;
	}
	
	private static boolean isWeekend(LocalDate day) {
		return (day.getDayOfWeek().equals(DayOfWeek.SATURDAY) || day.getDayOfWeek().equals(DayOfWeek.SUNDAY));		
	}
	
	private static boolean isWeekday(LocalDate day) {
		return !isWeekend(day);
	}
	
	public static RentalAgreement createRentalAgreement(Tool tool, LocalDate startDate, LocalDate endDate, int discount) {
		
		ToolMaster master = ToolMasterService.findToolMaster(tool.getToolMaster().getToolCode());
		PricingProfile pricing = master.getPricingProfile();
		
		RentalAgreement ra = new RentalAgreement();
		ra.setPreDiscountCharge(0);
		
		startDate.datesUntil(endDate.plusDays(1)).forEach((d) -> {
			if (isHoliday(d)) {
				if (pricing.isHolidayCharge()) {
					ra.setPreDiscountCharge (ra.getPreDiscountCharge() + pricing.getDailyCharge());
				}
			} else if (isWeekend(d)) {
				if (pricing.isWeekendCharge()) {
					ra.setPreDiscountCharge (ra.getPreDiscountCharge() + pricing.getDailyCharge());
				}
			} else if (isWeekday(d)) {
				if (pricing.isWeekdayCharge()) {
					ra.setPreDiscountCharge (ra.getPreDiscountCharge() + pricing.getDailyCharge());
				}
			}
		});

		
		
		
		return ra;
		
	}
}
