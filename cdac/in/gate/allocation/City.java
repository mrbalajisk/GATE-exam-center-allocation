package cdac.in.gate.allocation;

import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.LinkedHashMap;

public class City{

	String code;
	Map<String, Centre> centreMap;
	Map<String, Session> sessionMap;
	
	City(String code){

		this.code = code;
		this.centreMap = new LinkedHashMap<String, Centre>();
		this.sessionMap = new LinkedHashMap<String, Session>();
	}

	void print(String zone){

		Set<String> centres = centreMap.keySet();
		for(String centre: centres ){
			centreMap.get( centre ).print( zone, code );
		}
	}
} 

