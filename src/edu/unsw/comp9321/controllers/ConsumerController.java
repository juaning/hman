package edu.unsw.comp9321.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.paukov.combinatorics.Factory;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;

import edu.unsw.comp9321.common.ServiceLocatorException;
import edu.unsw.comp9321.exception.EmptyResultException;
import edu.unsw.comp9321.models.RoomDAO;

/**
 * Servlet implementation class ConsumerController
 */
@WebServlet("/ConsumerController")
public class ConsumerController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(ConsumerController.class.getName());
	private RoomDAO room;
       
    /**
     * @throws ServletException
     * @see HttpServlet#HttpServlet()
     */
    public ConsumerController() throws ServletException {
        super();
        try {
			room = new RoomDAO();
		} catch (ServiceLocatorException e) {
			logger.severe("Trouble connecting to database "+e.getStackTrace());
			throw new ServletException();
		} catch (SQLException e) {
			logger.severe("Trouble connecting to database "+e.getStackTrace());
			throw new ServletException();
		} catch (ParseException e) {
			logger.severe("Trouble parsing date value " + e.getStackTrace());
		}
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request,response);
	}
	
	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String forwardPage = "";
		boolean error = false;
		String checkin = "";
		String checkout = "";
		int cityId = 0;
		int maxPrice = 0;
		int noRooms = 0;
		if(request.getParameter("checkin") == null) {
			String errMsg = "<li>Checkin date can not be empty.</li>";
			request.setAttribute("errMsg", errMsg);
			forwardPage = "welcome.jsp";
			error = true;
		} else {
			checkin = request.getParameter("checkin");
		}
		if(request.getParameter("checkout") == null) {
			String errMsg = "<li>Checkout date can not be empty.</li>";
			request.setAttribute("errMsg", errMsg);
			forwardPage = "welcome.jsp";
			error = true;
		} else {
			checkout = request.getParameter("checkout");
		}
		if(request.getParameter("city") == null) {
			String errMsg = "<li>You need to pick a city.</li>";
			request.setAttribute("errMsg", errMsg);
			forwardPage = "welcome.jsp";
			error = true;
		} else {
			cityId = Integer.parseInt(request.getParameter("city"));
		}
		if(request.getParameter("selMaxPrice") == null) {
			String errMsg = "<li>You need to pick a Maximum Price.</li>";
			request.setAttribute("errMsg", errMsg);
			forwardPage = "welcome.jsp";
			error = true;
		} else {
			maxPrice = Integer.parseInt(request.getParameter("selMaxPrice"));
		}
		if(request.getParameter("rooms") == null) {
			String errMsg = "<li>You need to pick the number of rooms.</li>";
			request.setAttribute("errMsg", errMsg);
			forwardPage = "welcome.jsp";
			error = true;
		} else {
			noRooms = Integer.parseInt(request.getParameter("rooms"));
		}
		if(!error) {
			try {
				ArrayList<HashMap<String,String>> result = (ArrayList<HashMap<String,String>>) room.getRoomsByConsumerSearch(checkin, checkout, cityId, maxPrice);
				request.setAttribute("city", room.getCityById(cityId));
				ArrayList<HashMap<String,String>> returnList = new ArrayList<HashMap<String,String>>(); 
				ArrayList<Integer> indexes = new ArrayList<Integer>();
				int indexesCC = 0;
				for(int i=0; i<result.size(); i++) {
					HashMap<String,String> tmp = result.get(i);
					if(Integer.parseInt(tmp.get("availables")) >= noRooms) {
						// Add up to noRooms to list
						for(int noRoomsCC = 0; noRoomsCC<noRooms; noRoomsCC++) {
							returnList.add(tmp);
							indexes.add(indexesCC);
							indexesCC++;
						}
					} else {
						// Add all to list
						int availables = Integer.parseInt(tmp.get("availables"));
						for(int noRoomsCC = 0; noRoomsCC<availables; noRoomsCC++) {
							returnList.add(tmp);
							indexes.add(indexesCC);
							indexesCC++;
						}
					}
				}
				/**
				 * Test combinations
				 */
				// Create vector
				ICombinatoricsVector<Integer> initialVector = Factory.createVector(indexes);
				
				// Create a generator
				Generator<Integer> gen = Factory.createSimpleCombinationGenerator(initialVector, noRooms);
				
				// Print all possible combinations
				ArrayList<ArrayList<HashMap<String,String>>> finalResult = new ArrayList<ArrayList<HashMap<String,String>>>();
				for (ICombinatoricsVector<Integer> combination : gen) {
					ArrayList<HashMap<String,String>> tmpList = new ArrayList<HashMap<String,String>>();
					List<?> combV = combination.getVector();
					for(int i=0; i<combV.size(); i++) {
						HashMap<String,String> tmpHash = returnList.get((Integer) combV.get(i));
						tmpList.add(tmpHash);
					}
					finalResult.add(tmpList);
				}
				returnList = null;
				HashSet<ArrayList<HashMap<String, String>>> hs = new HashSet<ArrayList<HashMap<String, String>>>();
				hs.addAll(finalResult);
				finalResult.clear();
				finalResult.addAll(hs);
//				for(int i=0; i<finalResult.size(); i++) {
//					System.out.println(finalResult.get(i));
//				}
				
				/**
				 * End test
				 */
				request.setAttribute("resultList", finalResult);
				forwardPage = "searchResult.jsp";
			} catch (EmptyResultException e) {
				// No results redirect to proper page
				String errMsg = "<li><b>No results for search criteria. Please try with different values</b></li>";
				request.setAttribute("errMsg", errMsg);
				forwardPage = "welcome.jsp";
			}
		}
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/"+forwardPage);
		dispatcher.forward(request, response);
	}
	
	private ArrayList<HashMap<String,String>> generateRoomCombinations(ArrayList<HashMap<String,String>> items) {
		ArrayList<HashMap<String,String>> result = new ArrayList<HashMap<String,String>>();
		return result;
	}
}
