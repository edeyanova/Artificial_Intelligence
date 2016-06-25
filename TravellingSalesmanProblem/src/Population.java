
public class Population {

	
	Route[] routes;
	
	//Creates a population
	public Population(int populationSize, boolean initialise) {
		routes = new Route[populationSize];
		
		//if we need to initialise a population
		if (initialise) {
			for (int i = 0; i < populationSize(); i++) {
                Route newTour = new Route();
                newTour.generateIndividual();
                saveTour(i, newTour);
            }
		}
	}
	
	// Saves a tour
    public void saveTour(int index, Route route) {
        routes[index] = route;
    }
    
    // Gets a tour from population
    public Route getTour(int index) {
        return routes[index];
    }

    // Gets the best tour in the population
    public Route getFittest() {
        Route fittest = routes[0];
        // Loop through individuals to find fittest
        for (int i = 1; i < populationSize(); i++) {
            if (fittest.getFitness() <= getTour(i).getFitness()) {
                fittest = getTour(i);
            }
        }
        return fittest;
    }

    // Gets population size
   

	public int populationSize() {
		 return routes.length;
	}
	
}
