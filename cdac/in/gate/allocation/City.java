package cdac.in.gate.allocation;

import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.LinkedHashMap;

public class City{

	Integer cityCode;
	String cityName;

	List<Centre> centres;
	Map<Integer, Session> sessions;
	
	City(Integer code, String cityName){

		this.cityCode = code;
		this.cityName = cityName;
		this.centres = new ArrayList<Centre>();
		this.sessions = new LinkedHashMap<Integer, Session>();
	}

	void print(Zone zone, boolean iiscformat){
		for(Centre centre: centres ){
			centre.print( zone, cityCode, iiscformat );
		}
	}
} 

