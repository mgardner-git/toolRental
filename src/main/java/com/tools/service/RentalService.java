package com.tools.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import com.tools.model.PricingProfile;
import com.tools.model.RentalAgreement;
import com.tools.model.Tool;
import com.tools.model.ToolMaster;
import com.tools.model.ToolStatus;

public class RentalService extends DataService {

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
	
	public static RentalAgreement createRentalAgreement(Tool tool, LocalDate startDate, LocalDate endDate, int discount)throws SQLException {
		/**
		 * TODO: Need to check if this tool is already rented during the given period.
		 */
		if (! startDate.isBefore(endDate)) {
			throw new IllegalArgumentException("End date must be after start date");
		}
		if (discount < 0 || discount > 100) {
			throw new IllegalArgumentException("discount must be between 1 and 100");
		}
		if (!isAvailable(tool.getSerialNumber(), startDate, endDate)) {
			throw new IllegalArgumentException("Tool #" + tool.getSerialNumber() + " is rented during that period.");
		}
		ToolMaster master = ToolMasterService.findToolMaster(tool.getToolMaster().getToolCode());
		PricingProfile pricing = master.getPricingProfile();
		
		RentalAgreement ra = new RentalAgreement();	
		ra.setCreateDate(LocalDate.now());
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
	

	
	/**
	 * Returns true if and only if there are no rentalAgreements for the given tool that intersect the given date range
	 * @param serialNumber
	 * @param beingDate
	 * @param endDate
	 * @return
	 */
	public static boolean isAvailable(String serialNumber, LocalDate beginDate, LocalDate endDate) throws SQLException {
		Connection con = getConnection();
		
		String query = "select startDate, endDate from rentalagreement where serialNumber = ?";
		PreparedStatement statement = con.prepareStatement(query);
		statement.setString(1,  serialNumber);		
		ResultSet results = statement.executeQuery();
		while (results.next()) {
			LocalDate rentalBegin = results.getObject(1, LocalDate.class);
			LocalDate rentalEnd = results.getObject(2, LocalDate.class);
			
			if ( (rentalEnd.isAfter(beginDate) && rentalEnd.isBefore(endDate)) ||
				 (rentalBegin.isAfter(beginDate) &&  rentalBegin.isBefore(endDate))
				) {
				return false;
			}
		} 
		return true;
	}
	public static void saveRentalAgreement(RentalAgreement ra) throws SQLException {
		Connection con = getConnection();
		
		String update = "insert into rentalagreement (startDate,endDate,checkoutDate, createDate, discount, dailyCharge, chargeDays, preDiscountCharge, serialNumber, customer) VALUES (?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement statement = con.prepareStatement(update);
		statement.setDate(1,  java.sql.Date.valueOf(ra.getStartDate()));
		statement.setDate(2,  java.sql.Date.valueOf(ra.getEndDate()));
		statement.setDate(3,  java.sql.Date.valueOf(ra.getCheckoutDate()));
		statement.setDate(4, java.sql.Date.valueOf(ra.getCreateDate()));
		statement.setInt(5, ra.getDiscount());
		statement.setDouble(6,  ra.getDailyCharge());
		statement.setInt(7,  ra.getChargeDays());
		statement.setDouble(8,  ra.getPreDiscountCharge());
		statement.setString(9, ra.getTool().getSerialNumber());
		statement.setString(10, ra.getCustomer());
		statement.executeUpdate();		
	}
	
	public static void checkoutTool(Tool tool, RentalAgreement ra) throws SQLException {
		if (tool.getCurrentStatus() != ToolStatus.ONSHELF) {
			throw new IllegalArgumentException("Tool # " + tool.getSerialNumber() + " is not on the shelf");
			//TODO: See if there is another rental agreement, or another customer that has recently checked out this tool
		}
		//!ra.getStartDate().isAfter(LocalDate.now())) {
		else if (LocalDate.now().isBefore(ra.getStartDate())) {				
			throw new IllegalArgumentException("The start date for tool# " + tool.getSerialNumber() + " is " + ra.getStartDate());
		} else {
			tool.setCurrentStatus(ToolStatus.RENTED);
			ra.setCheckoutDate(LocalDate.now());
			ToolsService.updateStatus(tool);
			updateCheckinCheckout(ra);
		}
	}
	
	public static void checkinTool(Tool tool, RentalAgreement ra) throws SQLException{
		
		if (LocalDate.now().isAfter(ra.getEndDate())) {
			//TODO: What to do with late checkins
		} else {
			tool.setCurrentStatus(ToolStatus.ONSHELF);
			ra.setCheckinDate(LocalDate.now());
			ToolsService.updateStatus(tool);
			updateCheckinCheckout(ra);
		}
	}
	
	private static java.sql.Date convertToSqlDate(LocalDate ld) {
		if (ld == null) {
			return null;
		} else {
			return java.sql.Date.valueOf(ld);
		}
	}
	private static void updateCheckinCheckout(RentalAgreement ra) throws SQLException {
		Connection con =  getConnection();
		
		String update = "update rentalagreement set checkinDate=?, checkoutDate=? where id=?";		
		PreparedStatement statement = con.prepareStatement(update);
		statement.setDate(1, convertToSqlDate(ra.getCheckinDate()));
		statement.setDate(2, convertToSqlDate(ra.getCheckoutDate()));
		statement.setInt(3, ra.getId());
		
		statement.executeUpdate();

	}
	

}
