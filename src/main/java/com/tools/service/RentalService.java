package com.tools.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import com.tools.model.PricingProfile;
import com.tools.model.RentalAgreement;
import com.tools.model.Tool;
import com.tools.model.ToolMaster;
import com.tools.model.ToolStatus;

public class RentalService {

	private static boolean isHoliday(LocalDate day) {
		int year = day.getYear();
		//if falls on weekend, it is observed on the closest weekday (If sat, then friday before, if Sunday, then Monday after)
		LocalDate julyFour = LocalDate.of(year, 7,4);
		if (julyFour.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
			julyFour = LocalDate.of(year, 7, 3);
		} else if (julyFour.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
			julyFour = LocalDate.of(year,  7, 5);
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
		/**
		 * TODO: Need to check if this tool is already rented during the given period.
		 */
		if (! startDate.isBefore(endDate)) {
			throw new IllegalArgumentException("End date must be after start date");
		}
		if (discount < 0 || discount > 100) {
			throw new IllegalArgumentException("discount must be between 1 and 100");
		}
		ToolMaster master = ToolMasterService.findToolMaster(tool.getToolMaster().getToolCode());
		PricingProfile pricing = master.getPricingProfile();
		
		RentalAgreement ra = new RentalAgreement();		
		ra.setPreDiscountCharge(0);
		ra.setChargeDays(0);
		startDate.datesUntil(endDate.plusDays(1)).forEach((d) -> {
			if (isHoliday(d)) {
				if (pricing.isHolidayCharge()) {
					ra.setPreDiscountCharge (ra.getPreDiscountCharge() + pricing.getDailyCharge());
					ra.setChargeDays(ra.getChargeDays() +1);
				}
				
			} else if (isWeekend(d)) {
				if (pricing.isWeekendCharge()) {
					ra.setPreDiscountCharge (ra.getPreDiscountCharge() + pricing.getDailyCharge());
					ra.setChargeDays(ra.getChargeDays() +1);
				}
			} else if (isWeekday(d)) {
				if (pricing.isWeekdayCharge()) {
					ra.setPreDiscountCharge (ra.getPreDiscountCharge() + pricing.getDailyCharge());
					ra.setChargeDays(ra.getChargeDays() +1);
				}
			}
		});
		ra.setStartDate(startDate);
		ra.setEndDate(endDate);
		ra.setTool(tool);
		ra.setDailyCharge(pricing.getDailyCharge());
		ra.setDiscount(discount);
		double discountRatio = ((double)discount)/100;		
		ra.setDiscountAmount(ra.getPreDiscountCharge() * discountRatio);
		ra.setFinalCharge(ra.getPreDiscountCharge()-ra.getDiscountAmount());		
		ra.setCheckoutDate(LocalDate.now());
		return ra;		
	}
	
	public static void checkoutTool(Tool tool, RentalAgreement ra) {
		if (tool.getCurrentStatus() != ToolStatus.ONSHELF) {
			throw new IllegalArgumentException("Tool # " + tool.getSerialNumber() + " is not on the shelf");
			//TODO: See if there is another rental agreement, or another customer that has recently checked out this tool
		}
		else if (!ra.getStartDate().equals(LocalDate.now())) {
			throw new IllegalArgumentException("The start date for tool# " + tool.getSerialNumber() + " is " + ra.getStartDate());
		} else {
			tool.setCurrentStatus(ToolStatus.RENTED);
			//TODO: Do we need a status element on the rental agreement?
		}
	}
}
