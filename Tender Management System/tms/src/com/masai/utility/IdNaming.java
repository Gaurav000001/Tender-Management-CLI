package com.masai.utility;

public class IdNaming {
	/**
	 * 
	 * @param n int Indicating auto_generated Id in database
	 * @param id String Indicating the Uniquely identified letter for Id verification
	 * @return String
	 */
	public static String generateId(int n, String id) {
		StringBuilder sb = new StringBuilder();
		
		String s = String.valueOf(n);
		String[] arr = new String[5];
		arr[0] = id;
		int j = arr.length-1;
		
		for(int i=s.length()-1;i>=0;i--) {
			arr[j] = String.valueOf(s.charAt(i));
			j--;
		}
		for(int i=0;i<arr.length;i++) {
			if(arr[i] == null) {
				arr[i] = "0";
			}
			
			sb.append(arr[i]);
		}
		String generatedId = String.valueOf(sb);

		return generatedId;
	}
	
	
	
	
	/**
	 * 
	 * @param id String
	 * @return int Indicating the id(int) which is stored in database for a perticular record
	 */
	public static int extractIdNumber(String id) {
		int a = 0;
		
		for(int i=1;i<id.length();i++) {
			if(Integer.parseInt(String.valueOf(id.charAt(i))) != 0) {
				a = i;
				break;
			}
		}
		
		StringBuilder sb = new StringBuilder();
		for(int i=a;i<id.length();i++) {
			sb.append(Integer.parseInt(String.valueOf(id.charAt(i))));
		}
		
		return Integer.parseInt(String.valueOf(sb));
	}
	
}
