package com.tools.model;

import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import com.tools.service.RentalService;
import com.tools.service.ToolMasterService;

public class RentalAgreement {
	
	public static final NumberFormat CURRENCY_FORMAT = NumberFormat.getCurrencyInstance(); //TODO: Consider using BigDecimal instead
	public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yy");
	private Tool tool;
	private LocalDate startDate;
	private LocalDate endDate;
	private int discount; //as a whole number 0-100 (20=20% off)
	private LocalDate checkoutDate;  //note that the checkout date is distinct from the begin date. Checkout date is when they pay for the agreement.
	private double dailyCharge;
	private int chargeDays; //count of chargeable days from day after checkout through and including due date, excluding "no chage" days as specified by the PricingProfile
	private double preDiscountCharge;
	
	private double discountAmount;
	private double finalCharge; //pre-discount charge - discount amount
	
	
	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append("Tool Code: " + tool.getToolMaster().getToolCode() + "\n");
		result.append("Tool Type: " + tool.getToolMaster().getToolType() + "\n");
		result.append("Tool Brand: " + tool.getToolMaster().getBrand() + "\n");
		
		result.append("Rental Days: " + getRentalDays()+ "\n");
		result.append("Check out date: " + DATE_FORMAT.format(checkoutDate) + "\n");
		result.append("Begin date: " + DATE_FORMAT.format(startDate) + "\n");
		result.append("Due Date: " + DATE_FORMAT.format(endDate) + "\n");
		result.append("Daily Rental Charge: " + CURRENCY_FORMAT.format(dailyCharge) + "\n");
		result.append("Charge Days: " + chargeDays + "\n");
		result.append("Pre-discount charge: " + CURRENCY_FORMAT.format(preDiscountCharge) + "\n");
		result.append("Discount percent: " + discount + "%\n");
		result.append("Discount Amount: " + CURRENCY_FORMAT.format(discountAmount) + "\n");
		result.append("Final Charge: " + CURRENCY_FORMAT.format(finalCharge) + "\n");
		return result.toString();
		
	}
	public Tool getTool() {
		return tool;
	}
	public void setTool(Tool tool) {
		this.tool = tool;
	}
	
	public int getRentalDays() {
		return (int)startDate.until(endDate, ChronoUnit.DAYS)+1; 
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public int getDiscount() {
		return discount;
	}
	public void setDiscount(int discount) {
		this.discount = discount;
	}
	public LocalDate getCheckoutDate() {
		return checkoutDate;
	}
	public void setCheckoutDate(LocalDate checkoutDate) {
		this.checkoutDate = checkoutDate;
	}
	public double getDailyCharge() {
		return dailyCharge;
	}
	public void setDailyCharge(double dailyCharge) {
		this.dailyCharge = dailyCharge;
	}
	public int getChargeDays() {
		return chargeDays;
	}
	public void setChargeDays(int chargeDays) {
		this.chargeDays = chargeDays;
	}
	public double getPreDiscountCharge() {
		return preDiscountCharge;
	}
	public void setPreDiscountCharge(double preDiscountCharge) {
		this.preDiscountCharge = preDiscountCharge;
	}
	public double getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(double discountAmount) {
		this.discountAmount = discountAmount;
	}
	public double getFinalCharge() {
		return finalCharge;
	}
	public void setFinalCharge(double finalCharge) {
		this.finalCharge = finalCharge;
	}

	public static void main(String[] args) throws IOException {
		ToolMasterService.loadToolMasters();
		ToolMaster master = ToolMasterService.findToolMaster("JAKR");		
		Tool tool = master.getTools().get(0);		

		RentalAgreement result = RentalService.createRentalAgreement(tool, 
				LocalDate.of(2020, 7,2), 
				LocalDate.of(2020, 7,5),
				50);
		System.out.println(result);
	}
}
