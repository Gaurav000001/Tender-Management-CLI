package com.masai.Dao;

import java.util.List;

import com.masai.Bean.Bidder;
import com.masai.Bean.Tender;
import com.masai.Exception.BidNotFoundException;
import com.masai.Exception.TenderNotFoundException;

public interface VendorDao {
	
	/**
	 * 
	 * @param username
	 * @param password
	 * @return String massage indicating whether user successfully logedin or not
	 */
	public String userLogin(String username, String password);
	
	
	/**
	 * 
	 * @return List<Tender> which are open
	 */
	public List<Tender> getAllOpenTenders();
	
	
	/**
	 * 
	 * @param Tender
	 * @return String message to inform bidder that if he had bided for this tender earlier
	 * Or tender is closed already
	 * @throws TenderNotFoundException If no tender found for this tenderId
	 */
	public String bidForTender(Bidder bid) throws TenderNotFoundException;
	
	
	/**
	 * 
	 * @param bidId
	 * @return Bidder Object
	 * @throws BidNotFoundException If the vendor is trying to access details of bid which is placed
	 *  by another vender
	 */
	public Bidder getStatusOfABid(String bidId) throws BidNotFoundException;
	
	
	/**
	 * 
	 * @return List<Bidder> indicating all the bid history of the logedIn user
	 */
	public List<Bidder> getBidHistory();
}
