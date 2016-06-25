import java.util.ArrayList;


//This class represents all the cities for the current tour
public class Destinations {
	
	public static ArrayList destinationCity = new ArrayList<City>();
	
	public static void addCity(City city) {
		destinationCity.add(city);
	}
	
	
	public static int getNumberOfCities() {
		return destinationCity.size();
	}

	public static City getCity(int i) {
		return (City) destinationCity.get(i);
	}

}
