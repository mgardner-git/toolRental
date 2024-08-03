package com.tools.model;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class RentalAgreement {
	
	public static NumberFormat CURRENCY_FORMAT = NumberFormat.getCurrencyInstance(); //TODO: Consider using BigDecimal instead
	private Tool tool;
	private LocalDate startDate;
	private LocalDate endDate;
	private int discount; //as a whoel number 0-100 (20=20% off)
	private LocalDate checkoutDate;
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
		result.append("Check out date: " + checkoutDate + "\n");
		result.append("Due Date: " + endDate + "\n");
		result.append("Daily Rental Charge: " + CURRENCY_FORMAT.format(dailyCharge) + "\n");
		result.append("Charge Days: " + chargeDays + "\n");
		result.append("Pre-discount charge: " + CURRENCY_FORMAT.format(chargeDays) + "\n");
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

}
