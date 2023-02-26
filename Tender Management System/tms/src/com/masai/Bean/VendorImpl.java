package com.masai.Bean;

import java.util.regex.Pattern;

import com.masai.Exception.EmailException;
import com.masai.Exception.MobileNumberException;
import com.masai.Exception.PasswordException;
import com.masai.utility.IdNaming;

public class VendorImpl implements Vendor{
	
	private String vendorId;
	private String userName;
	private String email;
	private String mobileNumber;
	private String address;
	private String companyName;
	private String password;
	
	public VendorImpl() {}
	
	//constructor to get details from Administrator while registering a new vendor 
	public VendorImpl(String userName, String email, String mobileNumber, String address,
			String companyName, String password) throws MobileNumberException, PasswordException, EmailException{
		
		this.userName = userName;
		if(emailValidation(email)) {
			this.email = email;
		}
		else {
			throw new EmailException("Please enter a valid Email id");
		}
		
		if(mobileNumberValidation(mobileNumber)) {
			this.mobileNumber = mobileNumber;
		}
		else {
			throw new MobileNumberException("Mobile Number is not valid");
		}
		
		this.address = address;
		this.companyName = companyName;
		
		if(passwordValidation(password)) {
			this.password = password;
		}
		else {
			throw new PasswordException("Weak Password! password should contain at least (1 Capital Letter, 1 Small Letter, 1 Special Character, 1 Integer)");
		}
		
	}
	
	
	//constructor to get the details from database
	public VendorImpl(int v_id, String userName, String email, String mobileNumber, String address,
			String companyName) {
		
		super();
		int n = v_id;
		this.vendorId = IdNaming.generateId(n, "V");
		this.userName = userName;
		this.email = email;
		this.mobileNumber = mobileNumber;
		this.address = address;
		this.companyName = companyName;
		
	}
	
	
	
	
	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String fullName) {
		this.userName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	

	
	
	
	
	
	/**
	 * 
	 * @param email String
	 * @return boolean Indicating the input email is valid or not
	 */
	static boolean emailValidation(String email) {
		
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
		
		Pattern p = Pattern.compile(emailRegex);
		if(email == null) {
			return false;
		}
		
		return p.matcher(email).matches();
	}
	
	
	
	
	/**
	 * 
	 * @param mobileNumber String
	 * @return boolean Indicating the input mobile number is valid or not
	 */
	static boolean mobileNumberValidation(String mobileNumber) {
		
		Pattern p = Pattern.compile("[6-9]\\d{9}$");
		
		return p.matcher(mobileNumber).matches();
	}
	
	
	
	
	/**
	 * 
	 * @param password String
	 * @return boolean Indicating the input password is valid or not
	 */
	static boolean passwordValidation(String password) {
		// for checking if password length
        // is between 8 and 15
        if (!((password.length() >= 8)
              && (password.length() <= 15))) {
            return false;
        }
 
        // to check space
        if (password.contains(" ")) {
            return false;
        }
        if (true) {
            int count = 0;
 
            // check digits from 0 to 9
            for (int i = 0; i <= 9; i++) {
 
                // to convert int to string
                String str1 = Integer.toString(i);
 
                if (password.contains(str1)) {
                    count = 1;
                }
            }
            if (count == 0) {
                return false;
            }
        }
 
        // for special characters
        if (!(password.contains("@") || password.contains("#")
              || password.contains("!") || password.contains("~")
              || password.contains("$") || password.contains("%")
              || password.contains("^") || password.contains("&")
              || password.contains("*") || password.contains("(")
              || password.contains(")") || password.contains("-")
              || password.contains("+") || password.contains("/")
              || password.contains(":") || password.contains(".")
              || password.contains(", ") || password.contains("<")
              || password.contains(">") || password.contains("?")
              || password.contains("|"))) {
            return false;
        }
 
        if (true) {
            int count = 0;
 
            // checking capital letters
            for (int i = 65; i <= 90; i++) {
 
                // type casting
                char c = (char)i;
 
                String str1 = Character.toString(c);
                if (password.contains(str1)) {
                    count = 1;
                }
            }
            if (count == 0) {
                return false;
            }
        }
 
        if (true) {
            int count = 0;
 
            // checking small letters
            for (int i = 97; i <= 122; i++) {
 
                // type casting
                char c = (char)i;
                String str1 = Character.toString(c);
 
                if (password.contains(str1)) {
                    count = 1;
                }
            }
            if (count == 0) {
                return false;
            }
        }
 
        // if all conditions fails
        return true;
	}
}
