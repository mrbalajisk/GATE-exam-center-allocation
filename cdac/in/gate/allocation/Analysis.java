package cdac.in.gate.allocation;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


class PaperWise{
	String paperCode;
	int count;	
	
	PaperWise(String paperCode){
		this.paperCode = paperCode;
		this.count = 0;	
	}	
		

}

class CityWise{
	
	String cityCode;
	Map<String, PaperWise> paperWise;

	CityWise(String cityCode){
		this.cityCode = cityCode;
		this.paperWise = new TreeMap<String, PaperWise>();	

	}

}

public class Analysis{

	String zoneId;
	Map<String, CityWise> cityWise;

	Analysis(String zoneId){
		this.zoneId = zoneId;
		this.cityWise = new TreeMap<String, CityWise>();
	}

	void print(){
		Set<String> cityCodes = cityWise.keySet();
		for(String cityCode: cityCodes){
			CityWise city = cityWise.get( cityCode );
			System.out.print(zoneId+", "+cityCode+"("+Allocator.codeCities.get( cityCode )+") " );
			Set<String> paperCodes = city.paperWise.keySet();
			for(String paperCode: paperCodes){
				System.out.print(", "+paperCode+":"+city.paperWise.get( paperCode ).count);
			}
			System.out.println();
		}

	}

}

