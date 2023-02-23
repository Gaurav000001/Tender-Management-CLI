package com.masai.Bean;

import java.time.LocalDate;

public interface Bidder {
	
	public String getBidderId();

	public void setBidderId(String bidderId);

	public String getVendorId();

	public void setVendorId(String vendorId);

	public String getTenderId();

	public void setTenderId(String tenderId);

	public int getBidAmount();

	public void setBidAmount(int bidAmount);

	public LocalDate getBidDate();

	public void setBidDate(LocalDate bidDate);

	public String getBidStatus();

	public void setBidStatus(String bidStatus);
}
