package com.masai.useCases;

import java.util.List;
import java.util.Scanner;

import com.masai.Bean.Bidder;
import com.masai.Bean.BidderImpl;
import com.masai.Bean.Tender;
import com.masai.Bean.Vendor;
import com.masai.Bean.VendorImpl;
import com.masai.Dao.VendorDao;
import com.masai.Exception.BidNotFoundException;
import com.masai.Exception.EmailException;
import com.masai.Exception.MobileNumberException;
import com.masai.Exception.PasswordException;
import com.masai.Exception.TenderNotFoundException;
import com.masai.UI.Main;
import com.masai.utility.LogedinUser;

public class UserOperations {
	
	public static void userOpr(Scanner sc, VendorDao vendor) {
		VendorDao user = vendor;
		int choice = 0;
		
		
		do {
			
			System.out.println();
			System.out.println("1. Show All current Tenders");
			System.out.println("2. Place a Bid against a Tender");
			System.out.println("3. Show Status of a Bid");
			System.out.println("4. Show Bid History");
			System.out.println("5. Update Profile");
			System.out.println("6. Change Password");
			System.out.println("7. Delete a Bid");
			System.out.println("9. Logout!");
			System.out.println("0. Exit");
			
			System.out.println();
			System.out.print("Enter your choice: ");
			choice = sc.nextInt();
			System.out.println();
			
			switch(choice) {
			
			case 0:
				
				System.out.println("Thank you, visit again!");
				System.exit(1);
				break;
			
			case 1:
				
				List<Tender> tender = user.getAllCurrentTenders();
				
				if(tender != null) {
					
					for(int i=0;i<tender.size();i++) {
						Tender t = tender.get(i);
						
						System.out.println("TenderId:       "+t.getTenderId()+"\nName:           "+t.getName()+"\nType:           "+t.getType()+"\nPrice:          "+t.getPrice()+"\nDescription:    "+t.getDescription()+"\nDeadline:       "+t.getDeadline()+"\nLocation:       "+t.getLocation());
						System.out.println();
					}
				}
				else{
					System.out.println("\nNothing Fount!");
				}
				
				
				break;
				
			case 2:
				
				System.out.print("Enter Tender-ID: ");
				String tenderId = sc.next();
				

				System.out.print("Enter Bid-Amount: ");
				int amount = sc.nextInt();
				
				Bidder bid = new BidderImpl(tenderId, amount);
				
				try {
					System.out.println(user.bidForTender(bid));
				} catch (TenderNotFoundException ex) {
					// TODO Auto-generated catch block
					System.err.println(ex.getLocalizedMessage());
				}
				
				break;
				
			case 3:
				
				System.out.print("Enter Bid-ID: ");
				String bidID = sc.next();
				
				try {
					Bidder b = user.getStatusOfABid(bidID);
					
					if(b == null) {
						System.err.println("Invalid Bid-ID!");
					}
					else {
						System.out.println("Bid Id:         "+b.getBidderId()+"\nVendorId:       "+b.getVendorId()+"\nTenderId:       "+b.getTenderId()+"\nBid Amount:     "+b.getBidAmount()+"\nBid Date:       "+b.getBidDate()+"\nStatus:         "+b.getBidStatus());
					}
					
				} catch (BidNotFoundException ex) {
					// TODO Auto-generated catch block
					System.err.println(ex.getLocalizedMessage());
				}
				
				break;
				
			case 4:
				
				System.out.println();
				
				List<Bidder> bids = user.getBidHistory();
				
				if(bids != null) {
					
					for(int i=0;i<bids.size();i++) {
						Bidder b = bids.get(i);
						
						System.out.println("Bid Id:         "+b.getBidderId()+"\nTenderId:       "+b.getTenderId()+"\nBid Amount:     "+b.getBidAmount()+"\nBid Date:       "+b.getBidDate()+"\nStatus:         "+b.getBidStatus());
						System.out.println();
					}
				}
				else {
					System.err.println("You haven't Bided for any Tender yet!");
				}
				
				break;
				
			case 5:
				
				user.printProfileDetails();
				
				System.out.println();
				System.out.println("Please Fill out your details below");
				
				System.out.print("Enter Username: ");
				String name = sc.next();
				
				System.out.print("Enter Email: ");
				String email = sc.next();
				
				System.out.print("Enter Mobile no: ");
				String mobile = sc.next();
				
				sc.nextLine();
				System.out.print("Enter Address: ");
				String address = sc.nextLine();
				
				System.out.print("Enter Company name: ");
				String company = sc.nextLine();
				
				try {
					
					Vendor update = new VendorImpl(name, email, mobile, address, company, LogedinUser.password);
					
					boolean status = user.updateProfileDetails(update);
					
					if(status) {
						System.out.println("Successfully Updated the Details!");
					}
					else {
						System.err.println("Something went wrong");
					}
					
				} catch (PasswordException ex) {
					// TODO Auto-generated catch block
					choice = 1;
					System.err.println(ex.getLocalizedMessage());
				}catch(EmailException ex) {
					choice = 1;
					System.err.println(ex.getLocalizedMessage());
				}catch(MobileNumberException ex) {
					choice = 1;
					System.err.println(ex.getLocalizedMessage());
				}
				
				break;
				
			case 6:
				
				System.out.println();
				System.out.print("Enter Old Password: ");
				String old = sc.next();
				
				if(old.equals(LogedinUser.password)) {
					
					System.out.print("Enter new Password: ");
					String newPass = sc.next();
					
					if(user.changePassword(newPass)) {
						System.out.println("Password Updated Successfully😀");
					}
					else {
						System.err.println("Something went wrong, Please try again!");
					}
					
				}
				else {
					System.err.println("Wrong Password Entered!");
				}
				
				break;
				
			case 7:
				
				System.out.println();
				System.out.print("Enter Bid-ID: ");
				String bidid = sc.next();
				
				try {
					
					System.out.println(user.deleteABid(bidid));
					
					
				} catch (BidNotFoundException ex) {
					// TODO Auto-generated catch block
					System.err.println(ex.getLocalizedMessage());
				}
				
				break;
				
			case 9:
				
				Main.main(null);
				
				break;
				
			default:
				
				System.err.println("\nInvalid Input!");
				
			}
			
			if(choice>=0 || choice<=9) {
				try {
					Thread.sleep(4000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
		}while(choice != 0);
	}
}
