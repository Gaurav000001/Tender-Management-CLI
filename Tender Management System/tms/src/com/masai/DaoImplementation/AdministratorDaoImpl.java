package com.masai.DaoImplementation;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.masai.Bean.Bidder;
import com.masai.Bean.BidderImpl;
import com.masai.Bean.Tender;
import com.masai.Bean.TenderImpl;
import com.masai.Bean.Vendor;
import com.masai.Bean.VendorImpl;
import com.masai.Dao.AdministratorDao;
import com.masai.Exception.EmailException;
import com.masai.Exception.MobileNumberException;
import com.masai.Exception.TenderNotFoundException;
import com.masai.Exception.UserNameException;
import com.masai.Exception.VendorNotFoundException;
import com.masai.utility.DBUtils;
import com.masai.utility.IdNaming;

public class AdministratorDaoImpl implements AdministratorDao{

	@Override
	public boolean adminLogin(String username, String password) {
		// TODO Auto-generated method stub
		
		if(username.equalsIgnoreCase("admin") && password.equalsIgnoreCase("admin")) {
			return true;
		}
		return false;
	}

	
	@Override
	public String registerNewVendor(Vendor vendor) throws UserNameException, EmailException, MobileNumberException {
		// TODO Auto-generated method stub
		Connection con = null;
		String ans = "Registration of new Vendor Failed!";
		
		con = DBUtils.connectToDatabase();
		
		try {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM Vendor WHERE userName = '"+ vendor.getUserName() +"'");
			
			ResultSet userNameResult = ps.executeQuery();
			if(userNameResult.next()) {
				System.err.println(ans);
				throw new UserNameException("Username already taken, Please enter a different one");
			}
			
			ps = con.prepareStatement("SELECT * FROM Vendor WHERE email = '"+ vendor.getEmail() +"'");
			
			ResultSet emailResult = ps.executeQuery();
			if(emailResult.next()) {
				System.err.println(ans);
				throw new EmailException("Username already taken, Please enter a different one");
			}
			
			ps = con.prepareStatement("SELECT * FROM Vendor WHERE mobileNumber = '"+ vendor.getMobileNumber() +"'");
			
			ResultSet mobileResult = ps.executeQuery();
			if(mobileResult.next()) {
				System.err.println(ans);
				throw new MobileNumberException("Username already taken, Please enter a different one");
			}
			
			
			ps = con.prepareStatement("INSERT INTO Vendor (userName, email, mobileNumber, address, companyName, password) VALUES (?,?,?,?,?,?)");
			
			ps.setString(1, vendor.getUserName());
			ps.setString(2, vendor.getEmail());
			ps.setString(3, vendor.getMobileNumber());
			ps.setString(4, vendor.getAddress());
			ps.setString(5, vendor.getCompanyName());
			ps.setString(6, vendor.getPassword());
			
			int rowsAffected = ps.executeUpdate();
			
			if(rowsAffected > 0) {
				
				ps = con.prepareStatement("SELECT Vid FROM Vendor ORDER BY Vid DESC LIMIT 1");
				
				ResultSet justRegisteredVendorId = ps.executeQuery();
				
				justRegisteredVendorId.next();
				String vendorId = IdNaming.generateId(justRegisteredVendorId.getInt("Vid"), "V");
				ans = "Registration successful! Your vendorId is "+ vendorId +"";
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtils.closeConnection();
		}
		
		return ans;
	}

	@Override
	public List<Vendor> getAllVendors() {
		// TODO Auto-generated method stub
		Connection con = null;
		List<Vendor> list = null;
		con = DBUtils.connectToDatabase();
		
		try {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM Vendor");
			
			ResultSet resultset = ps.executeQuery();
			
			if(!isResultSetEmpty(resultset)) {
				
				list = getListOfVendorsFromResultSet(resultset);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtils.closeConnection();
		}
		
		return list;
	}

	@Override
	public String createNewTender(Tender tender) {
		// TODO Auto-generated method stub
//TenderImpl(String name, String type, double price, String description, String deadline,String location, String status)
		String status = "new Tender creation Failed!";
		try(Connection con = DBUtils.connectToDatabase()){
			
			PreparedStatement ps = con.prepareStatement("INSERT INTO Tender (tender_name, tender_type, tender_desc, tender_price, tender_deadline, tender_location) VALUES (?, ?, ?, ?, ?, ?)");
			
			ps.setString(1, tender.getName());
			ps.setString(2, tender.getType());
			ps.setString(3, tender.getDescription());
			ps.setInt(4, (int)tender.getPrice());
			ps.setDate(5, Date.valueOf(String.valueOf(tender.getDeadline())));
			ps.setString(6, tender.getLocation());
			
			int affected = ps.executeUpdate();
			
			ps = con.prepareStatement("SELECT * FROM Tender ORDER BY Tid DESC LIMIT 1");
			
			ResultSet r = ps.executeQuery();
			int id = r.getInt("Tid");
			
			String tenderId = IdNaming.generateId(id, "T");
			
			if(affected > 0) {
				status = "Successfully added the Tender, Your TenderId is "+ tenderId +"";
			}
			
		}catch(SQLException ex) {
			System.out.println(status);
			ex.printStackTrace();
		}
		return status;
	}

	@Override
	public List<Tender> getAllTenders() {
		// TODO Auto-generated method stub
		Connection con = null;
		List<Tender> list = null;
		con = DBUtils.connectToDatabase();
		
		try {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM Tender");
			
			ResultSet resultset = ps.executeQuery();
			
			if(!isResultSetEmpty(resultset)) {
				
				list = getListOfTendersFromResultSet(resultset);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtils.closeConnection();
		}
		
		return list;
	}

	@Override
	public List<Bidder> getAllBiddsOfThisTender(String tenderId) {
		// TODO Auto-generated method stub
		Connection con = null;
		List<Bidder> list = null;
		con = DBUtils.connectToDatabase();
		
		try {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM Bidder WHERE Tender_id = ?");
			
			int tender_id = IdNaming.extractIdNumber(tenderId);
			
			ps.setInt(1, tender_id);
			
			ResultSet resultset = ps.executeQuery();
			
			if(!isResultSetEmpty(resultset)) {
				
				list = getListOfBiddersFromResultSet(resultset);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtils.closeConnection();
		}
		
		return list;
	}

	@Override
	public String assignTenderToVendor(String tenderId, String vendorId) throws TenderNotFoundException, VendorNotFoundException{
		// TODO Auto-generated method stub
		String str = "Something went wrong";
		int t_id = IdNaming.extractIdNumber(tenderId);
		int v_id = IdNaming.extractIdNumber(vendorId);
		
		try(Connection con = DBUtils.connectToDatabase()){
			
			//Checking tenderId and vendorId are correct or not
			PreparedStatement ps = con.prepareStatement("SELECT * FROM Tender WHERE Tid = ?");
			
			ps.setInt(1, t_id);
			ResultSet r = ps.executeQuery();
			
			if(isResultSetEmpty(r)) {
				throw new TenderNotFoundException("No Tender Found for TenderId = "+ tenderId +"");
			}
			
			ps = con.prepareStatement("SELECT * FROM Vendor WHERE Vid = ?");
			
			ps.setInt(1, v_id);
			r = ps.executeQuery();
			
			if(isResultSetEmpty(r)) {
				throw new VendorNotFoundException("No vendor exists for TenderId = "+ vendorId +"");
			}
			
			
			//Checking tender is already closed or not
			ps = con.prepareStatement("SELECT * FROM Tender WHERE Tid = ?");
			ps.setInt(1, t_id);
			
			ResultSet tender_info = ps.executeQuery();
			if(tender_info.getString("tender_status").equals("Closed")) {
				return "Tender is already Closed! , cannot assign to any other vendor";
			}
			
			
			//Assigning tender to the vendor by updating the status of bid(Selected) and status of tender(Closed)
			ps = con.prepareStatement("UPDATE Bidder SET Bid_status = 'Selected' WHERE tender_id = ? and vendor_id = ?");
		
			ps.setInt(1, t_id);
			ps.setInt(2, v_id);
			
			int rowsAffected = ps.executeUpdate();
			
			if(rowsAffected > 0) {
				str = "Successfully assingned Tender to Vendor";
			}
			
			ps = con.prepareStatement("UPDATE Tender SET tender_status = 'Closed' WHERE Tid = ?");
			ps.setInt(1, t_id);
			
			ps.executeUpdate();
			
			
			//Updating every other bidder status for this tender to Rejected
			ps = con.prepareStatement("UPDATE Bidder SET Bid_status = 'Rejected' WHERE Tender_id = ? AND Bid_status = 'Pending'");
			ps.setInt(1, t_id);
			
			ps.executeUpdate();
			
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
		return str;
	}
	
	
	
	
	
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= Some supporting methods-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
	
	/**
	 * 
	 * @param r ResultSet
	 * @return true if set is empty else false
	 */
	public static boolean isResultSetEmpty(ResultSet r) {
		
		try {
			if(!r.isBeforeFirst() && r.getRow()==0) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	
	
	/**
	 * 
	 * @param ResultSet
	 * @return List<Vendor>
	 */
	public static List<Vendor> getListOfVendorsFromResultSet(ResultSet r){
		List<Vendor> list = new ArrayList<>();
		
		try {
			while(r.next()) {
				
				int id = r.getInt("Vid");
				String username = r.getString("userName");
				String email = r.getString("email");
				String mobileNumber = r.getString("mobileNumber");
				String address = r.getString("address");
				String companyName = r.getString("companyName");
				
				list.add(new VendorImpl(id, username, email, mobileNumber, address,	companyName));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	
	
	/**
	 * 
	 * @param ResultSet
	 * @return List<Tender> 
	 */
	public static List<Tender> getListOfTendersFromResultSet(ResultSet r){
		List<Tender> list = new ArrayList<>();
		
		try {
			while(r.next()) {
				
				int id = r.getInt("Tid");
				String name = r.getString("tender_name");
				String type = r.getString("tender_type");
				String desc = r.getString("tender_desc");
				double price = r.getInt("tender_price");
				Date deadline = r.getDate("deadline");
				String location = r.getString("tender_location");
				String status = r.getString("tender_status");
				
				list.add(new TenderImpl(id, name, type, price, desc, deadline, location, status));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	
	/**
	 * 
	 * @param ResultSet
	 * @return List<Bidder>
	 */
	public static List<Bidder> getListOfBiddersFromResultSet(ResultSet r){
		List<Bidder> list = new ArrayList<>();
		
		try {
			while(r.next()) {
				
				int b_Id = r.getInt("Bid_id");
				int v_Id = r.getInt("Vendor_id");
				int t_Id = r.getInt("Tender_id");
				int bidAmount = r.getInt("Bid_Amount");
				String bidDate = String.valueOf(r.getDate("Bid_Date"));
				String bidStatus = r.getString("Bid_status");
				
				list.add(new BidderImpl(b_Id, v_Id, t_Id, bidAmount, bidDate, bidStatus));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}

}
