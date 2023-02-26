package com.masai.Bean;

import java.sql.Date;
import java.time.LocalDate;

import com.masai.utility.IdNaming;

public class TenderImpl implements Tender {
	
	private String tenderId;
	private String name;
	private String type;
	private int price;
	private String description;
	private LocalDate deadline;
	private String location;
	private String status;
	
	public TenderImpl() {}
	
	//constructor to get details of Tender from database
	public TenderImpl(int t_id, String name, String type, int price, String description, Date deadline,
			String location, String status) {

		int n = t_id;
		this.tenderId = IdNaming.generateId(n, "T");
		this.name = name;
		this.type = type;
		this.price = price;
		this.description = description;
		this.deadline = LocalDate.parse(String.valueOf(deadline));
		this.location = location;
		this.status = status;
	}
	
	//constructor to get details from Administrator
	public TenderImpl(String name, String type, int price, String description, String deadline,
			String location) {
		this.name = name;
		this.type = type;
		this.price = price;
		this.description = description;
		this.deadline = LocalDate.parse(deadline);
		this.location = location;
	}
	
	public String getTenderId() {
		return tenderId;
	}

	public void setTenderId(String tenderId) {
		this.tenderId = tenderId;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public LocalDate getDeadline() {
		return deadline;
	}
	
	public void setDeadline(LocalDate deadline) {
		this.deadline = deadline;
	}

	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
