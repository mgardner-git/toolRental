package com.tools.model;

import java.time.LocalDate;

public class RentalAgreement {
	private Tool tool;
	private LocalDate startDate;
	private LocalDate endDate;
	private int discount; //as a whoel number 0-100 (20=20% off)
	private LocalDate checkoutDate;
	private double dailyCharge;
	private int chargeDays; //count of chargeable days from day after checkout through and including due date, excluding "no chage" days as specified by the PricingProfile
	private double preDiscountCharge;
	private int discountPercent;
	private double discountAmount;
	private double finalCharge; //pre-discount charge - discount amount
	
	public Tool getTool() {
		return tool;
	}
	public void setTool(Tool tool) {
		this.tool = tool;
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
	public int getDiscountPercent() {
		return discountPercent;
	}
	public void setDiscountPercent(int discountPercent) {
		this.discountPercent = discountPercent;
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
