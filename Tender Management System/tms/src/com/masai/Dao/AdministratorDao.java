package com.masai.Dao;

import java.util.List;

import com.masai.Bean.Bidder;
import com.masai.Bean.Tender;
import com.masai.Bean.Vendor;
import com.masai.Exception.EmailException;
import com.masai.Exception.MobileNumberException;
import com.masai.Exception.TenderNotFoundException;
import com.masai.Exception.UserNameException;
import com.masai.Exception.VendorNotFoundException;

public interface AdministratorDao {
	
	/**
	 * 
	 * @param username
	 * @param password
	 * @return boolean if username and password matches then it will return true else false
	 */
	public boolean adminLogin(String username, String password);
	
	
	/**
	 * 
	 * @param vendor object of VendorImpl referencing to Vendor Interface
	 * @return String It return an message indicating weather vendor is successfully registered or not
	 * If registered successfully then returns message with VenderId
	 * @throws UserNameException If userName already taken then this Exception will be thrown
	 * @throws EmailException If email already taken then this Exception will be thrown
	 * @throws MobileNumberException If Mobile Number already taken then this Exception will be thrown
	 */
	public String registerNewVendor(Vendor vendor) throws UserNameException, EmailException, MobileNumberException;
	
	
	/**
	 * 
	 * @return List<Vendor> This method will return list of vendors available and null if no vendor found
	 */
	public List<Vendor> getAllVendors();
	
	
	/**
	 * 
	 * @param tender object of TenderImpl referencing to Tender Interface
	 * @return String It return an message indicating weather tender is successfully added or not 
	 * If added successfully then returns message with TenderId
	 */
	public String createNewTender(Tender tender);
	
	
	/**
	 * 
	 * @return List<Tender> This method will return list of tenders available and null if No tender found
	 */
	public List<Tender> getAllTenders();
	
	
	/**
	 * 
	 * @param tenderId String which is provided to Administrator while tender Creation
	 * @return List<Bidder> All the Bidds of the given tender
	 */
	public List<Bidder> getAllBiddsOfThisTender(String tenderId) throws TenderNotFoundException;
	
	
	/**
	 * 
	 * @param tenderId
	 * @param vendorId
	 * @return String
	 * @throws TenderNotFoundException
	 * @throws VendorNotFoundException
	 */
	public String assignTenderToVendor(String tenderId, String vendorId)  throws TenderNotFoundException, VendorNotFoundException;
	
	
	/**
	 * 
	 * @return String 
	 * Message of completion of vendor or not
	 */
	public String deleteVendor();
	
	
	/**
	 * 
	 * @param tenderId of the tender to be deleted
	 * @throws TenderNotFoundException
	 * If Tender id is wrong
	 */
	public void deleteTender(String tenderId) throws TenderNotFoundException;
}
