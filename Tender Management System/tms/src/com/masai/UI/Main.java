package com.masai.UI;

import java.util.Scanner;

import com.masai.Dao.AdministratorDao;
import com.masai.Dao.VendorDao;
import com.masai.DaoImplementation.VendorDaoImpl;
import com.masai.useCases.AdministratorOptions;
import com.masai.useCases.UserOperations;
import com.masai.DaoImplementation.AdministratorDaoImpl;

public class Main {
	static Scanner sc;

	static {
		System.out.println("Welcome! Please Enter your choice");
		sc = new Scanner(System.in);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AdministratorDao admin = new AdministratorDaoImpl(); 
		VendorDao user = new VendorDaoImpl();
		int choice = 0;
		
		do {
			
			System.out.println();
			System.out.println("1. Administrator Login");
			System.out.println("2. Vendor Login");
			System.out.println("0. Exit");
			System.out.println();
			System.out.print("Enter your Choice: ");
			choice = sc.nextInt();
			
			switch(choice) {
			
			case 1:
				
				System.out.print("Enter Username: ");
				String username = sc.next();
				System.out.print("Enter password: ");
				String password = sc.next();
				
				if(admin.adminLogin(username, password)) {
					
					System.out.println("\nLogedIn Successfully 😀");
					AdministratorOptions.adminOperations(sc, admin);
					
				}
				else {
					
					System.err.println("\nUsername or password is incorrect");
					
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				break;
				
			case 2:
				
				System.out.print("Enter Username: ");
				String userName = sc.next();
				System.out.print("Enter password: ");
				String pass = sc.next();
				
				if(user.userLogin(userName, pass)) {
					System.out.println("\nSuccessfully LogedIn! 😀");
					UserOperations.userOpr(sc, user);
				}
				else {
					System.err.println("\nusername or password is wrong");
					
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				break;
			
				
			case 0:
				
				System.out.println("Thank you, visit again!");
				System.exit(1);
				break;
				
			default:
				
				System.err.println("\nInvalid input");
			
			}
			
			
		}while(choice != 0);
	}

}
