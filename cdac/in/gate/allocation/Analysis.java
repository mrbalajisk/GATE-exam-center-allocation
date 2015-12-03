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
	
	Integer cityCode;
	Map<String, PaperWise> paperWise;

	CityWise(Integer cityCode){
		this.cityCode = cityCode;
		this.paperWise = new TreeMap<String, PaperWise>();	

	}

}

public class Analysis{

	Integer zoneId;
	Map<Integer, CityWise> cityWise;

	Analysis(Integer zoneId){
		this.zoneId = zoneId;
		this.cityWise = new TreeMap<Integer, CityWise>();
	}

	void print(){

		Set<Integer> cityCodes = cityWise.keySet();

		for(Integer cityCode: cityCodes){

			CityWise city = cityWise.get( cityCode );
			System.out.print(zoneId+", "+cityCode+"( "+Allocator.codeCities.get( cityCode )+" )" );

			Set<String> paperCodes = city.paperWise.keySet();
			for(String paperCode: paperCodes){
				System.out.print(", "+paperCode+":"+city.paperWise.get( paperCode ).count);
			}
			System.out.println();
		}

	}

}

