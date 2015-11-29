package cdac.in.gate.allocation;

import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.LinkedHashMap;

public class City{

	String cityCode;
	String cityName;

	List<Centre> centres;
	Map<String, Session> sessions;
	
	City(String code, String cityName){

		this.cityCode = code;
		this.cityName = cityName;
		this.centres = new ArrayList<Centre>();
		this.sessions = new LinkedHashMap<String, Session>();
	}

	void print(String zone){
		for(Centre centre: centres ){
			centre.print( zone, cityCode );
		}
	}
} 

