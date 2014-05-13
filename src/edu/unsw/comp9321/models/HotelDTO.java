package edu.unsw.comp9321.models;

public class HotelDTO {
	public HotelDTO(int id, int city_id, String name, String city_name) {
		this.id = id;
		this.city_id = city_id;
		this.name = name;
		this.city_name = city_name;
	}
	
	private int id;
	private int city_id;
	private String name;
	private String city_name;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCity_id() {
		return city_id;
	}
	public void setCity_id(int city_id) {
		this.city_id = city_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCity_name() {
		return city_name;
	}
	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}
}
