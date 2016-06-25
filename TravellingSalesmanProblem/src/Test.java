import java.util.Scanner;

public class Test {

	public static void main(String[] args) {

		System.out.println("Enter the number of cities ");
		Scanner input = new Scanner(System.in);
		int n = input.nextInt();

		// City city = new City(5, 6);
		// Destinations.addCity(city);

		// Create randomly situated cities
		for (int i = 0; i < n; i++) {
			City city = new City();
			Destinations.addCity(city);
		}

		// Initialize population
		Population pop = new Population(100, true);
		 System.out.println("Initial distance: " +
		 pop.getFittest().getDistance());

		// Evolve population for 100 generations
		pop = Genetic_Algorithm.evolvePopulation(pop);
		for (int i = 0; i < 100; i++) {
			pop = Genetic_Algorithm.evolvePopulation(pop);
			System.out.println(pop.getFittest().getDistance());
		}

		// Print final results
		System.out.println("Distance: " + pop.getFittest().getDistance());
		System.out.println("Solution's steps:");
		System.out.println(pop.getFittest());
	}

}
