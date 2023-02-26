package com.masai.Bean;

import java.time.LocalDate;

public interface Tender {
	
	public String getTenderId();

	public void setTenderId(String tenderId);

	public String getName();
	
	public void setName(String name);

	public String getType();

	public void setType(String type);

	public int getPrice();

	public void setPrice(int price);

	public String getDescription();

	public void setDescription(String description);
	
	public LocalDate getDeadline();
	
	public void setDeadline(LocalDate deadline);

	public String getLocation();
	
	public void setLocation(String location);

	public String getStatus();

	public void setStatus(String status);
}
