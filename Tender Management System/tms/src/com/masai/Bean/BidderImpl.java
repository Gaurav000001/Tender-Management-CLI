package com.masai.Bean;

import java.time.LocalDate;

import com.masai.utility.IdNaming;
import com.masai.utility.LogedinUser;

public class BidderImpl implements Bidder{
	private String bidderId;
	private String vendorId;
	private String tenderId;
	private int bidAmount;
	private LocalDate bidDate;
	private String bidStatus;
	
	public BidderImpl() {}
	
	//constructor to get derails from database
	public BidderImpl(int b_Id, int v_Id, int t_Id, int bidAmount, String bidDate, String bidStatus) {
		super();
		int bid_id = b_Id;
		this.bidderId = IdNaming.generateId(bid_id, "B");
		
		int vendor_id = v_Id;
		this.vendorId = IdNaming.generateId(vendor_id, "V");
		
		int tender_id = t_Id;
		this.tenderId = IdNaming.generateId(tender_id, "T");
		
		this.bidAmount = bidAmount;
		
		
		
		this.bidDate = LocalDate.parse(bidDate);
		this.bidStatus = bidStatus;
	}
	
	
	//constructor to get data from vendor
	public BidderImpl(String tenderId, int bidAmount) {
		super();
		this.vendorId = IdNaming.generateId((LogedinUser.v_Id), "V");
		this.tenderId = tenderId;
		this.bidAmount = bidAmount;
		this.bidDate = LocalDate.now();
	}
	
	
	public String getBidderId() {
		return bidderId;
	}

	public void setBidderId(String bidderId) {
		this.bidderId = bidderId;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public String getTenderId() {
		return tenderId;
	}

	public void setTenderId(String tenderId) {
		this.tenderId = tenderId;
	}

	public int getBidAmount() {
		return bidAmount;
	}

	public void setBidAmount(int bidAmount) {
		this.bidAmount = bidAmount;
	}

	public LocalDate getBidDate() {
		return bidDate;
	}

	public void setBidDate(LocalDate bidDate) {
		this.bidDate = bidDate;
	}

	public String getBidStatus() {
		return bidStatus;
	}

	public void setBidStatus(String bidStatus) {
		this.bidStatus = bidStatus;
	}

	
	
}
