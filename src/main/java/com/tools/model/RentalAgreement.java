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
import com.tools.service.ToolsService;

public class RentalAgreement {
	
	public static final NumberFormat CURRENCY_FORMAT = NumberFormat.getCurrencyInstance(); //TODO: Consider using BigDecimal instead
	public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yy");
	private int id;
	private Tool tool;	
	private LocalDate createDate; //the day the contract was signed and paid for
	private LocalDate startDate;  //the day the rental is expected to begin
	private LocalDate endDate;  //the day the rental is expected to end
	private int discount; //as a whole number 0-100 (20=20% off)
	private LocalDate checkoutDate;  //note that the checkout date is distinct from the begin date. Checkout date is when they actually take it out
	private LocalDate checkinDate; //the day the tool was returned
	private double dailyCharge;
	private int chargeDays; //count of chargeable days from day after checkout through and including due date, excluding "no chage" days as specified by the PricingProfile
	private double preDiscountCharge;
	
	private double discountAmount;
	private double finalCharge; //pre-discount charge - discount amount
	private String customer; //link to the customer table
	

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
	
	
	
	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public LocalDate getCheckinDate() {
		return checkinDate;
	}

	public void setCheckinDate(LocalDate checkinDate) {
		this.checkinDate = checkinDate;
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
	public LocalDate getCreateDate() {
		return createDate;
	}



	public void setCreateDate(LocalDate createDate) {
		this.createDate = createDate;
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

	public static void main(String[] args) throws Exception {
		//ToolMasterService.loadToolMasters();
				
		Tool tool = ToolsService.getTool("4");		

		RentalAgreement result = RentalService.createRentalAgreement(tool, 
				LocalDate.of(2020, 7,2), 
				LocalDate.of(2020, 7,5),
				50);
		System.out.println(result);
	}
}
