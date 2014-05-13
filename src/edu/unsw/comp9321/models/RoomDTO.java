package edu.unsw.comp9321.models;

public class RoomDTO {
	
	public RoomDTO(int id, String room_type, boolean extra_bed, String status, int extra_bed_price, int num_double_bed, int num_single_bed, int price_per_night, String description, int hotel_id) {
		this.id = id;
		this.room_type = room_type;
		this.extra_bed = extra_bed;
		this.status = status;
		this.extra_bed_price = extra_bed_price;
		this.num_double_bed = num_double_bed;
		this.num_single_bed = num_single_bed;
		this.price_per_night = price_per_night;
		this.description = description;
		this.hotel_id = hotel_id;
	}
	
	private int id;
	private String room_type;
	private boolean extra_bed;
	private String status;
	private int extra_bed_price;
	private int num_double_bed;
	private int num_single_bed;
	private int price_per_night;
	private String description;
	private int hotel_id;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRoom_type() {
		return room_type;
	}
	public void setRoom_type(String room_type) {
		this.room_type = room_type;
	}
	public boolean isExtra_bed() {
		return extra_bed;
	}
	public void setExtra_bed(boolean extra_bed) {
		this.extra_bed = extra_bed;
	}
	public String getStatus() {
		return this.status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getExtra_bed_price() {
		return extra_bed_price;
	}
	public void setExtra_bed_price(int extra_bed_price) {
		this.extra_bed_price = extra_bed_price;
	}
	public int getNum_double_bed() {
		return num_double_bed;
	}
	public void setNum_double_bed(int num_double_bed) {
		this.num_double_bed = num_double_bed;
	}
	public int getNum_single_bed() {
		return num_single_bed;
	}
	public void setNum_single_bed(int num_single_bed) {
		this.num_single_bed = num_single_bed;
	}
	public int getPrice_per_night() {
		return price_per_night;
	}
	public void setPrice_per_night(int price_per_night) {
		this.price_per_night = price_per_night;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getHotel_id() {
		return hotel_id;
	}
	public void setHotel_id(int hotel_id) {
		this.hotel_id = hotel_id;
	}
}
