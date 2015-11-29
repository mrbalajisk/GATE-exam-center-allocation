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

	Map<String, Centre> centres;
	Map<String, Session> sessions;
	
	City(String code, String cityName){
		this.cityCode = code;
		this.cityName = cityName;
		this.centres = new LinkedHashMap<String, Centre>();
		this.sessions = new LinkedHashMap<String, Session>();
	}

	void print(String zone){

		Set<String> centreCodes = centres.keySet();
		for(String centreCode: centreCodes ){
			centres.get( centreCode ).print( zone, cityCode );
		}
	}
} 

