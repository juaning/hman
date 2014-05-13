package edu.unsw.comp9321.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import edu.unsw.comp9321.common.ServiceLocatorException;
import edu.unsw.comp9321.exception.EmptyResultException;


public class RoomDAO {
	static Logger logger = Logger.getLogger(RoomDAO.class.getName());
	private Connection connection;
	DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
	private ArrayList<ArrayList<Date>> peakDatePrices = null;
	
	
	public RoomDAO() throws ServiceLocatorException, SQLException, ParseException {
		connection = DBConnectionFactory.getConnection();
		logger.info("Got connection");
		initializePeakDates();
	}
	
	private void initializePeakDates() throws ParseException {
		ArrayList<Date> peakDate = new ArrayList<Date>();
		peakDatePrices = new ArrayList<ArrayList<Date>>();
		java.util.Date d = fmt.parse("2014-12-15");
		peakDate.add(d);
		d = fmt.parse("2015-02-15");
		peakDate.add(d);
		peakDatePrices.add(peakDate);
		peakDate = null;
		// New date 
		peakDate = new ArrayList<Date>();
		d = fmt.parse("2013-12-15");
		peakDate.add(d);
		d = fmt.parse("2014-02-15");
		peakDate.add(d);
		peakDatePrices.add(peakDate);
		peakDate = null;
		// New date 
		peakDate = new ArrayList<Date>();
		d = fmt.parse("2014-03-25");
		peakDate.add(d);
		d = fmt.parse("2014-04-14");
		peakDate.add(d);
		peakDatePrices.add(peakDate);
		peakDate = null;
		// New date 
		peakDate = new ArrayList<Date>();
		d = fmt.parse("2015-03-25");
		peakDate.add(d);
		d = fmt.parse("2015-04-14");
		peakDate.add(d);
		peakDatePrices.add(peakDate);
		peakDate = null;
		// New date 
		peakDate = new ArrayList<Date>();
		d = fmt.parse("2014-07-01");
		peakDate.add(d);
		d = fmt.parse("2014-07-20");
		peakDate.add(d);
		peakDatePrices.add(peakDate);
		peakDate = null;
		// New date 
		peakDate = new ArrayList<Date>();
		d = fmt.parse("2014-09-20");
		peakDate.add(d);
		d = fmt.parse("2014-10-10");
		peakDate.add(d);
		peakDatePrices.add(peakDate);
	}
	
	private RoomDTO setRoomDTOFromResult(ResultSet res) throws SQLException {
		RoomDTO room = null;
		int id = res.getInt("ID");
		int hotel_id = res.getInt("HOTEL_ID");
		String room_type = res.getString("room_type");
		String status = res.getString("status");
		boolean extra_bed = res.getBoolean("extra_bed");
		int extra_bed_price = res.getInt("extra_bed_price");
		int num_double_bed = res.getInt("num_double_bed");
		int num_single_bed = res.getInt("num_single_bed");
		int price_per_night = res.getInt("price_per_night");
		String description = res.getString("description");
		room = new RoomDTO(id,room_type,extra_bed,status,extra_bed_price,num_double_bed,num_single_bed,price_per_night,description,hotel_id);
		return room;
	}
	
	private java.sql.Date getDateFromStr(String date) throws ParseException {
		java.util.Date d;
		d = fmt.parse(date);
		return new java.sql.Date(d.getTime());
	}
	
	private boolean isPeakPrice(Date date) {
		for(int i=0; i<peakDatePrices.size(); i++){
			ArrayList<Date> peakDate = peakDatePrices.get(i);
			if(date.after(peakDate.get(0)) && date.before(peakDate.get(1))) return true;
		}
		return false;
	}
	
	public RoomDTO getRoomById(int id) throws EmptyResultException {
		RoomDTO room = null;
		
		String query = "SELECT * FROM ROOM WHERE id=?";
		PreparedStatement stmnt;
		try {
			stmnt = connection.prepareStatement(query);
			stmnt.setInt(1, id);
			ResultSet res = stmnt.executeQuery();
			res.next();
			room = setRoomDTOFromResult(res);
		} catch (SQLException e) {
			System.out.println("Caught Exception");
			e.printStackTrace();
			throw new EmptyResultException();
		}
		return room;
	}
	
	public List<RoomDTO> getRoomsByHotelId(int hotel_id) throws EmptyResultException {
		ArrayList<RoomDTO> rooms = new ArrayList<RoomDTO>();
		String query = "SELECT * FROM ROOM WHERE hotel_id=?";
		PreparedStatement stmnt;
		try {
			stmnt = connection.prepareStatement(query);
			stmnt.setInt(1, hotel_id);
			ResultSet res = stmnt.executeQuery();
			while(res.next()){
				rooms.add(setRoomDTOFromResult(res));
			}
		} catch (SQLException e) {
			System.out.println("Caught Exception");
			e.printStackTrace();
			throw new EmptyResultException();
		}
		return rooms;
	}
	
	public String getCityById(int id) throws EmptyResultException {
		String city = "";
		
		String query = "SELECT name FROM CITY WHERE id=?";
		PreparedStatement stmnt;
		try {
			stmnt = connection.prepareStatement(query);
			stmnt.setInt(1, id);
			ResultSet res = stmnt.executeQuery();
			res.next();
			city = res.getString("NAME");
		} catch (SQLException e) {
			System.out.println("Caught Exception");
			e.printStackTrace();
			throw new EmptyResultException();
		}
		return city.substring(0, 1).toUpperCase() + city.substring(1);
	}
	
	public List<HashMap<String,String>> getRoomsByConsumerSearch(String checkin, String checkout, int city_id, int price) throws EmptyResultException {
		ArrayList<HashMap<String,String>> rooms = new ArrayList<HashMap<String,String>>();
		String query = "SELECT Type, SUM(Availables) as Availables, Price, Hotel, City "
						+ "FROM ( "
						+ "SELECT r.room_type as Type, COUNT(r.ROOM_TYPE) as Availables, r.PRICE_PER_NIGHT as Price, h.NAME as hotel, c.NAME as city "
						+ "FROM ROOM r, ROOM_AVAILABILITY ra, HOTEL h, CITY c "
						+ "WHERE r.ID = ra.ROOM_ID  "
						+ "AND ((? < ra.DATE_START) "
						+ "	OR (? > ra.DATE_END)) "
						+ "AND r.HOTEL_ID = h.ID "
						+ "AND h.CITY_ID = c.ID "
						+ "AND c.ID = ? "
						+ "AND r.PRICE_PER_NIGHT <= ? "
						+ "GROUP BY r.ROOM_TYPE, r.PRICE_PER_NIGHT, h.NAME, c.NAME "
						+ "UNION "
						+ "SELECT r.ROOM_TYPE as Type, COUNT(r.ROOM_TYPE) as Availables, r.PRICE_PER_NIGHT as Price, h.NAME as hotel, c.Name as city "
						+ "FROM ROOM r "
						+ "LEFT JOIN HOTEL h ON h.ID = r.HOTEL_ID "
						+ "LEFT JOIN CITY c ON c.ID = h.CITY_ID "
						+ "WHERE r.ID NOT IN (SELECT ID FROM ROOM_AVAILABILITY) "
						+ "AND c.ID = ? "
						+ "AND r.PRICE_PER_NIGHT <= ? "
						+ "GROUP BY r.ROOM_TYPE, r.PRICE_PER_NIGHT, h.NAME, c.NAME "
						+ ") as avail "
						+ "GROUP BY Type, Price, Hotel, City "
						+ "ORDER BY Price";
		PreparedStatement stmnt;
		try {
			stmnt = connection.prepareStatement(query);
			stmnt.setDate(1, getDateFromStr(checkout));
			stmnt.setDate(2, getDateFromStr(checkin));
			stmnt.setInt(3, city_id);
			stmnt.setInt(4, price);
			stmnt.setInt(5, city_id);
			stmnt.setInt(6, price);
			ResultSet res = stmnt.executeQuery();
			while(res.next()){
				HashMap<String,String> tmp = new HashMap<String,String>();
				tmp.put("type", res.getString("TYPE"));
				tmp.put("availables", res.getString("AVAILABLES"));
				tmp.put("price", res.getString("PRICE"));
				tmp.put("hotel", res.getString("HOTEL"));
				tmp.put("city", res.getString("CITY"));
				rooms.add(tmp);
				tmp = null;
			}
		} catch (SQLException e) {
			System.out.println("Caught Exception");
			e.printStackTrace();
			throw new EmptyResultException();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rooms;
	}

}
