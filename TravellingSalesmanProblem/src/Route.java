import java.util.ArrayList;
import java.util.Collections;

//Stores a candidate for a tour
public class Route {
	
	public ArrayList route = new ArrayList<City>();
	public int distance = 0;
	
	//Constructs a blank route
	public Route() {
		for(int i=0; i<Destinations.getNumberOfCities(); i++) {
			route.add(null);
		}
	}
	
	public Route(ArrayList route) {
		this.route = route;
	}
	
	//Creates a random individual
	public void generateIndividual() {
		for (int i=0; i<Destinations.getNumberOfCities(); i++) {
			setCity(i, Destinations.getCity(i));
        }
        // Randomly reorder the tour
        Collections.shuffle(route);
	}
	
	// Gets a city from the tour
    public City getCity(int tourPosition) {
        return (City)route.get(tourPosition);
    }

    // Sets a city in a certain position within a tour
    public void setCity(int tourPosition, City city) {
        route.set(tourPosition, city);
        // If the tours been changed we need to reset the distance
        distance = 0;
    }
    
 // Gets the tours fitness
    public double getFitness() {
        return 1.0/getDistance();
    }
    
 // Gets the total distance of the tour
    public int getDistance(){
        if (distance == 0) {
            int tourDistance = 0;
            // Loop through our tour's cities
            for (int cityIndex=0; cityIndex < tourSize(); cityIndex++) {
                // Get city we're travelling from
                City fromCity = getCity(cityIndex);
                // City we're travelling to
                City destinationCity;
                // Check we're not on our tour's last city, if we have set our 
                // tour's final destination city to our starting city
                if(cityIndex+1 < tourSize()){
                    destinationCity = getCity(cityIndex+1);
                }
                else{
                    destinationCity = getCity(0);
                }
                // Get the distance between the two cities
                tourDistance += fromCity.distanceTo(destinationCity);
            }
            distance = tourDistance;
        }
        return distance;
    }

    // Get number of cities on our tour
    public int tourSize() {
        return route.size();
    }
    
    // Check if the tour contains a city
    public boolean containsCity(City city){
        return route.contains(city);
    }
    
    @Override
    public String toString() {
        String geneString = "|";
        for (int i = 0; i < tourSize(); i++) {
            geneString += getCity(i)+"|";
        }
        return geneString;
    }

}
