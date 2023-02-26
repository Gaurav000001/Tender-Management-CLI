package com.masai.useCases;

import java.util.List;
import java.util.Scanner;

import com.masai.Bean.Bidder;
import com.masai.Bean.Tender;
import com.masai.Bean.TenderImpl;
import com.masai.Bean.Vendor;
import com.masai.Bean.VendorImpl;
import com.masai.Dao.AdministratorDao;
import com.masai.Exception.EmailException;
import com.masai.Exception.MobileNumberException;
import com.masai.Exception.PasswordException;
import com.masai.Exception.TenderNotFoundException;
import com.masai.Exception.UserNameException;
import com.masai.Exception.VendorNotFoundException;
import com.masai.UI.Main;

public class AdministratorOptions {
	
	public static void adminOperations(Scanner sc, AdministratorDao adm) {
		AdministratorDao admin = adm;
		int choice = 0;
		
		do {
			System.out.println();
			System.out.println("1. Register new Vendor");
			System.out.println("2. View all the Vendors");
			System.out.println("3. Create new Tender");
			System.out.println("4. View all the Tenders");
			System.out.println("5. View all the bids of a Tender");
			System.out.println("6. Assign Tender to a Vendor");
			System.out.println("7. Logout!");
			System.out.println("0. Exit");
			System.out.println();
			System.out.print("Enter your choice: ");
			
			
			choice = sc.nextInt();
			
			switch(choice) {
			
			case 0:
				System.out.println("Thank you, visit again!");
				System.exit(1);
				break;
			
			case 1: 
				
				System.out.println();
				System.out.print("Enter Username: ");
				String userName = sc.next();
				
				System.out.print("Enter email: ");
				String email = sc.next();

				System.out.print("Enter mobile Number: ");
				String mobileNumber = sc.next();

				System.out.print("Enter address: ");
				sc.nextLine();
				String address = sc.nextLine();

				System.out.print("Enter Company Name: ");
				String companyName = sc.nextLine();

				System.out.print("Enter Password: ");
				String password = sc.next();
				
				
				try {
					
					Vendor newVendor = new VendorImpl(userName, email, mobileNumber, address, companyName, password);
					String vendorId = admin.registerNewVendor(newVendor);
					System.out.println(vendorId);
					
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
				} catch (UserNameException ex) {
					// TODO Auto-generated catch block
					choice = 1;
					System.err.println(ex.getLocalizedMessage());
				}
				
				break;
				
			case 2:
				List<Vendor> vendors = admin.getAllVendors();
				
				if(vendors != null) {
					for(int i=0;i<vendors.size();i++) {
						Vendor v = vendors.get(i);
						
						System.out.println("\nVendorId:       "+v.getVendorId()+"\nUsername:       "+v.getUserName()+"\nMobile no:      "+v.getMobileNumber()+"\nEmail:          "+v.getEmail()+"\nCompany Name:   "+v.getCompanyName()+"\nAddress:        "+v.getAddress());
					}
				}
				else {
					System.out.println("\nNo Vendor Found!");
				}
				break;
				
			case 3:
				
				System.out.println();
				System.out.print("Enter Name: ");
				String name = sc.next();
				
				System.out.print("Enter Type: ");
				String type = sc.next();
				
				System.out.print("Enter Price: ");
				int price = sc.nextInt();
				
				System.out.print("Enter Description: ");
				sc.nextLine();
				String desc = sc.nextLine();
				
				System.out.print("Enter Deadline Year: ");
				String year = sc.next();
				
				System.out.print("Enter Deadline Month: ");
				String month = sc.next();
				
				System.out.print("Enter Deadline Date: ");
				String date = sc.next();
				
				String deadline = year+"-"+month+"-"+date;
				
				System.out.print("Enter Location: ");
				sc.nextLine();
				String location = sc.nextLine();
				
				Tender tender = new TenderImpl(name, type, price, desc, deadline, location);
				
				String str = admin.createNewTender(tender);
				System.out.println(str);
				
				break;
				
			case 4:
				
				List<Tender> tenders = admin.getAllTenders();
				
				if(tenders != null) {
					for(int i=0;i<tenders.size();i++) {
						Tender t = tenders.get(i);
						
						System.out.println("\nTenderId:       "+t.getTenderId()+"\nName:           "+t.getName()+"\nType:           "+t.getType()+"\nPrice:          "+t.getPrice()+"\nDescription:    "+t.getDescription()+"\nDeadline:       "+t.getDeadline()+"\nLocation:       "+t.getLocation()+"\nStatus:         "+t.getStatus());
					}
				}
				else {
					System.out.println("\nNo Tender Found!");
				}
				break;
				
			case 5:

				System.out.println();
				System.out.print("Enter TenderId: ");
				String tenderId = sc.next();
				
				try {
					List<Bidder> Bidds = admin.getAllBiddsOfThisTender(tenderId);
					
					if(Bidds != null) {
						
						for(int i=0;i<Bidds.size();i++) {
							Bidder b = Bidds.get(i);
							
							System.out.println("\nBid Id:         "+b.getBidderId()+"\nVendorId:       "+b.getVendorId()+"\nTenderId:       "+b.getTenderId()+"\nBid Amount:     "+b.getBidAmount()+"\nBid Date:       "+b.getBidDate()+"\nStatus:         "+b.getBidStatus());
						}
					}
					else{
						System.out.println("\nNo Bidds Found for this TenderId!");
					}
					
				} catch (TenderNotFoundException ex) {
					// TODO Auto-generated catch block
					System.err.println(ex.getLocalizedMessage());
				}
				break;
				
			case 6:
				
				System.out.println();
				System.out.print("Enter TenderId: ");
				String tender_id = sc.next();
				System.out.println();
				System.out.print("Enter VendorId: ");
				String vendor_id = sc.next();
				
				try {
					admin.assignTenderToVendor(tender_id, vendor_id);
				} catch (TenderNotFoundException ex) {
					// TODO Auto-generated catch block
					System.err.println(ex.getLocalizedMessage());
				} catch (VendorNotFoundException ex) {
					// TODO Auto-generated catch block
					System.err.println(ex.getLocalizedMessage());
				}
				
				break;
				
			case 7:
				
				Main.main(null);
				
				break;
				
			default:
				
				System.err.println("\nInvalid Input");
					
			}
			
			//forcing thread to sleep for 2 seconds
			if(choice==1 || choice==2 || choice==3 || choice==4 || choice==5 || choice==6 || choice==7) {
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
