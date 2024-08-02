package com.tools.model;

public class PricingProfile {

	private double dailyCharge;
	private boolean weekdayCharge;
	
	public double getDailyCharge() {
		return dailyCharge;
	}
	public void setDailyCharge(double dailyCharge) {
		this.dailyCharge = dailyCharge;
	}
	public boolean isWeekdayCharge() {
		return weekdayCharge;
	}
	public void setWeekdayCharge(boolean weekdayCharge) {
		this.weekdayCharge = weekdayCharge;
	}
	public boolean isWeekendCharge() {
		return weekendCharge;
	}
	public void setWeekendCharge(boolean weekendCharge) {
		this.weekendCharge = weekendCharge;
	}
	public boolean isHolidayCharge() {
		return holidayCharge;
	}
	public void setHolidayCharge(boolean holidayCharge) {
		this.holidayCharge = holidayCharge;
	}
	private boolean weekendCharge;
	private boolean holidayCharge;
	
}
