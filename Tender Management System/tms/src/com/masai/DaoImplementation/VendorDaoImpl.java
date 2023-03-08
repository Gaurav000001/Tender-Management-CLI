package com.masai.DaoImplementation;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;

import com.masai.Bean.Bidder;
import com.masai.Bean.BidderImpl;
import com.masai.Bean.Tender;
import com.masai.Bean.Vendor;
import com.masai.Bean.VendorImpl;
import com.masai.Dao.VendorDao;
import com.masai.Exception.BidNotFoundException;
import com.masai.Exception.TenderNotFoundException;
import com.masai.utility.DBUtils;
import com.masai.utility.IdNaming;
import com.masai.utility.LogedinUser;

public class VendorDaoImpl implements VendorDao{

	@Override
	public boolean userLogin(String username, String password) {
		// TODO Auto-generated method stub
		
		try(Connection con = DBUtils.connectToDatabase()){
			
			PreparedStatement ps = con.prepareStatement("SELECT * FROM Vendor WHERE userName = ? AND password = ?");
			
			ps.setString(1, username);
			ps.setString(2, password);
			
			ResultSet r = ps.executeQuery();
			
			if(AdministratorDaoImpl.isResultSetEmpty(r)) {
				return false;
			}
			else {
				r.next();
				LogedinUser.v_Id = r.getInt("Vid");
				LogedinUser.password = r.getString("password");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return true;
	}
	
	

	@Override
	public List<Tender> getAllCurrentTenders() {
		// TODO Auto-generated method stub
		List<Tender> list = null;
		int id = LogedinUser.v_Id;
		
		try(Connection con = DBUtils.connectToDatabase()){
			
			PreparedStatement ps = con.prepareStatement("SELECT t.* FROM Bidder b INNER JOIN Tender t ON b.Tender_id = t.Tid where Vendor_id = "+ id +" and Bid_status = 'Selected'");
			
			ResultSet r = ps.executeQuery();
			
			if(AdministratorDaoImpl.isResultSetEmpty(r)) {
				return list;
			}
			else {
				
				list = AdministratorDaoImpl.getListOfTendersFromResultSet(r);
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	

	@Override
	public String bidForTender(Bidder bid) throws TenderNotFoundException {
		// TODO Auto-generated method stub
		String str = "Something went wrong";
		
		try(Connection con = DBUtils.connectToDatabase()){
	
			//check If tender exists for this tenderId
			PreparedStatement ps = con.prepareStatement("SELECT * FROM Tender WHERE Tid = ?");
			ps.setInt(1, IdNaming.extractIdNumber(bid.getTenderId()));
			
			ResultSet r = ps.executeQuery();
			
			if(AdministratorDaoImpl.isResultSetEmpty(r)) {
				throw new TenderNotFoundException("Please check Provided Tender id.");
			}
			
			//checking for deadline of the tender
			r.next();
			LocalDate tender_deadline = LocalDate.parse(String.valueOf(r.getDate("tender_deadline")));
			LocalDate current_date = LocalDate.now();
			
			//If deadline is
			if(ChronoUnit.DAYS.between(current_date, tender_deadline) >= 0) {
				
				ps = con.prepareStatement("SELECT Bid_id FROM Bidder WHERE Tender_id = ? AND Vendor_id = ?");
				ps.setInt(1, IdNaming.extractIdNumber(bid.getTenderId()));
				ps.setInt(2, LogedinUser.v_Id);
				
				r = ps.executeQuery();
				
				//If vendor didn't placed any bid for this tender then add bid
				//Else print message and print details of the bid that user had added
				if(AdministratorDaoImpl.isResultSetEmpty(r)) {
					
					PreparedStatement insert = con.prepareStatement("INSERT INTO Bidder (Vendor_id, Tender_id, Bid_Amount, Bid_Date) VALUES (?, ?, ?, ?)");
					insert.setInt(1, IdNaming.extractIdNumber(bid.getVendorId()));
					insert.setInt(2, IdNaming.extractIdNumber(bid.getTenderId()));
					insert.setInt(3, bid.getBidAmount());
					insert.setDate(4, Date.valueOf(String.valueOf(current_date)));
					
					int affected = insert.executeUpdate();
					
					PreparedStatement p = con.prepareStatement("SELECT Bid_id FROM Bidder ORDER BY Bid_id DESC LIMIT 1");
					ResultSet set = p.executeQuery();
					
					set.next();
					int id = set.getInt("Bid_id");
					
					if(affected > 0) {
						return "Bid added Successfully. Your Bid Id is "+ IdNaming.generateId(id, "B");
					}
					
				}
				else {
					System.out.println("You already placed a bid for this tender\n\nHere the details for the same");
					
					r.next();
					int bid_id = r.getInt("Bid_id");
					int vendor_id = r.getInt("Vendor_id");
					int tender_id = r.getInt("Tender_id");
					int bid_amount = r.getInt("Bid_Amount");
					LocalDate bid_date = LocalDate.parse(String.valueOf(r.getDate("Bid_Date")));
					String bid_status = r.getString("Bid_status");
					
					System.out.println("Bid_id: "+ IdNaming.generateId(bid_id, "B") +"\nVendor_id: "+ IdNaming.generateId(vendor_id, "V")+"\nTender_id: "+ IdNaming.generateId(tender_id, "T") + "\nAmount: "+ bid_amount + "\nBid_Date: "+ bid_date + "\nStatus: " + bid_status);
					
					return "";
				}
			}
			else {
				if(r.getString("tender_status").equals("Open")) {
					
					int t_id = r.getInt("Tid");
					
					//closing the tender
					PreparedStatement ps1 = con.prepareStatement("UPDATE Tender SET tender_status = 'Closed' WHERE Tid = "+ t_id +"");
					ps1.executeUpdate();
					
					//getting the eligible bidder for this tender i.e. minimum bid made by bidders
					//If two or more bidders having the same bid amount then eligibility will be decided by ordering in Ascending order of Bid_Date and if bid_date is also same then eligibility will be decided by Bid_id (who bided first)
					ps1 = con.prepareStatement("SELECT * FROM Bidder WHERE tender_id = "+ t_id +" ORDER BY Bid_Amount, Bid_Date, Bid_id LIMIT 1");
					
					ResultSet result = ps1.executeQuery();
					
					//If result is empty means no one bided for this tender and we cannot do anything for that we have already closed the tender
					if(!AdministratorDaoImpl.isResultSetEmpty(result)) {
						
						result.next();
						int bid_id = result.getInt("Bid_id");
						
						//Selecting the best bider and changing the status to 'Selected'
						ps1 = con.prepareStatement("UPDATE Bidder SET Bid_status = 'Selected' WHERE Bid_id = "+ bid_id +"");
						ps1.executeUpdate();
						
						
						//updating the bid_status of all the other bidders who bided for this tender to 'Rejected'
						ps1 = con.prepareStatement("UPDATE Bidder SET Bid_status = 'Rejected' WHERE Tender_id = "+ t_id +" AND Bid_status = 'Pending'");
						ps1.executeUpdate();
					}
				}
				
				return "You cannot bid for this Tender, it is already closed";
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}

	@Override
	public Bidder getStatusOfABid(String bidId) throws BidNotFoundException {
		// TODO Auto-generated method stub
		Bidder bidDetails = null;
		
		try(Connection con = DBUtils.connectToDatabase()){
			
			//checking if the vendor provided the right bidId or not
			PreparedStatement ps = con.prepareStatement("SELECT * FROM Bidder WHERE Bid_id = ?");
			
			ps.setInt(1, IdNaming.extractIdNumber(bidId));
			ResultSet r = ps.executeQuery();
			
			if(AdministratorDaoImpl.isResultSetEmpty(r)) {
				return bidDetails;
			}
			else {
				r.next();
				if(r.getInt("Vendor_id") == LogedinUser.v_Id) {
					
					
					int b_Id = r.getInt("Bid_id");
					int v_Id = r.getInt("Vendor_id");
					int t_Id = r.getInt("Tender_id");
					int bidAmount = r.getInt("Bid_Amount");
					String bidDate = String.valueOf(r.getDate("Bid_Date"));
					String bidStatus = r.getString("Bid_status");
					
					bidDetails = new BidderImpl(b_Id, v_Id, t_Id, bidAmount, bidDate, bidStatus);

				}
				else {
					
					throw new BidNotFoundException("Alert!, You are trying to access details of another Bidder");
					
				}
			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bidDetails;
	}
	

	@Override
	public List<Bidder> getBidHistory() {
		// TODO Auto-generated method stub
		List<Bidder> list = null;
		
		try(Connection con = DBUtils.connectToDatabase()){
			
			PreparedStatement ps = con.prepareStatement("SELECT * FROM Bidder WHERE Vendor_id = ?");
			ps.setInt(1, LogedinUser.v_Id);
			
			ResultSet r = ps.executeQuery();
			
			if(AdministratorDaoImpl.isResultSetEmpty(r)) {
				return list;
			}
			else {
				
				list = AdministratorDaoImpl.getListOfBiddersFromResultSet(r);
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	
	@Override
	public void printProfileDetails() {
		int id = LogedinUser.v_Id;
		
		try(Connection con = DBUtils.connectToDatabase()){
			
			PreparedStatement ps = con.prepareStatement("SELECT * FROM Vendor WHERE Vid = ?");
			ps.setInt(1, id);
			
			ResultSet r = ps.executeQuery();
			r.next();
			
			String userId = IdNaming.generateId(r.getInt("Vid"), "V");
			String name = r.getString("userName");
			String email = r.getString("email");
			String mobile = r.getString("mobileNumber");
			String address = r.getString("address");
			String company = r.getString("companyName");
			
			System.out.println("Here's your Profile details.");
			System.out.println();
			System.out.println("UserId: "+userId+"\nUsername: "+name+"\nEmail Id: "+email+"\nMobile no: "+mobile+"\nAddress: "+address+"\nCompany: "+company);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean updateProfileDetails(Vendor vendor) {
		int id = LogedinUser.v_Id;
		
		try(Connection con = DBUtils.connectToDatabase()){
			
			PreparedStatement ps = con.prepareStatement("UPDATE Vendor SET userName = ?, email = ?, mobileNumber = ?, address = ?, companyName = ? WHERE Vid = ?");
			ps.setString(1, vendor.getUserName());
			ps.setString(2, vendor.getEmail());
			ps.setString(3, vendor.getMobileNumber());
			ps.setString(4, vendor.getAddress());
			ps.setString(5, vendor.getCompanyName());
			ps.setInt(6, id);
			
			int affected = ps.executeUpdate();
			
			if(affected > 0) {
				return true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	
	@Override
	public boolean changePassword(String pass) {
		int id = LogedinUser.v_Id;
		
		
		try(Connection con = DBUtils.connectToDatabase()){
			
			PreparedStatement ps = con.prepareStatement("UPDATE Vendor SET password = ? WHERE Vid = ?");
			ps.setString(1, pass);
			ps.setInt(2, id);
			
			int affected = ps.executeUpdate();
			
			if(affected > 0) {
				return true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}



	@Override
	public String deleteABid(String bidID) throws BidNotFoundException {
		String msg = "Something Went Wrong";
		
		int id = IdNaming.extractIdNumber(bidID);
		
		try(Connection con = DBUtils.connectToDatabase()){
			
			PreparedStatement ps = con.prepareStatement("SELECT * FROM Bidder WHERE Bid_id = "+ id +"");
			
			ResultSet r = ps.executeQuery();
			
			if(AdministratorDaoImpl.isResultSetEmpty(r)) {
				throw new BidNotFoundException("Wrong Bid-ID Entered, Please check Bid-ID Entered");
			}
			else {
				r.next();
				int vendor_id = r.getInt("Vendor_id");
				
				if(vendor_id != LogedinUser.v_Id) {
					throw new BidNotFoundException("You can't delete any other Vendor's Bid!!");
				}
			}
			
			ps = con.prepareStatement("DELETE FROM Bidder WHERE Bid_id = "+ id +"");
			
			int affected = ps.executeUpdate();
			
			if(affected > 0) {
				msg = "Bid deleted Successfully";
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return msg;
	}

}







