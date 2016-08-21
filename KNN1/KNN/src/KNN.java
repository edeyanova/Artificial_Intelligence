import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

//import items.Kind;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class KNN {
	
	public static HashMap<Double,String> mapKind = 
			new HashMap<Double,String>();
	
	public static void initializeMap(){
		mapKind.put(0.0, "Iris-setosa");
		mapKind.put(1.0, "Iris-versicolor");
		mapKind.put(2.0, "Iris-virginica");
	}
	
	private static ArrayList<Kind> getInstances(String path){
		initializeMap();
		//to read arff file
		DataSource source = null;
		try {
			source = new DataSource(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Instances instances = null;
		try {
			instances = source.getDataSet(); //getDataSet takes all elements of the file
		} catch (Exception e) {
			e.printStackTrace();
		}
		instances.setClassIndex(instances.numAttributes() - 1);//the class is set to be the last attribute
		
		ArrayList<Kind> list = new ArrayList<Kind>();
		
		int numberOfInstances = instances.numInstances();
		int numberOfAttributes = instances.numAttributes();
		
		for (int i = 0; i < numberOfInstances; i++) {
			Instance instance = instances.instance(i);
			//takes the numbers without the class
			double[] attributes = new double[numberOfAttributes - 1];
			for (int j = 0; j < attributes.length; j++) {
				//takes the attribute on the position j
				attributes[j] = instance.value(j);
			}
			double key = instance.value(numberOfAttributes - 1);
			//takes the name of the class
			String kindName = mapKind.get(key);
			//takes the array of attributes and the name of the last position each
			Kind kind = new Kind(attributes, kindName);
			//each line is added to the kind list
			list.add(kind);
		}
		
		return list;
	}
	

	public static List<Kind> getTrainData(int k, List<Kind> data){ //k is the %
		ArrayList<Kind> trainData = new ArrayList<>();
		//k is %. k*numberOfTrainData and divide by 100 
		int numberOfTrainData = (int)(k*data.size()/100.0);
		for (int i = 0; i < numberOfTrainData; i++) {
			trainData.add(data.get(i));
		}
		
		return trainData;
	}
	
	//takes the rest of data
	public static List<Kind> getTestData(int k, List<Kind> data){
		ArrayList<Kind> testData = new ArrayList<>();
		int numberOfTrainData = (int)(k*data.size()/100.0);
		//int numberOfTestData = data.size() - numberOfTrainData;
		for (int i = numberOfTrainData; i < data.size(); i++) {
			testData.add(data.get(i));
		}
		
		return testData;
	}
	private static String findMajorityClass(String[] array) {
		// adds the string array to a hashset to get unique string values
		Set<String> h = new HashSet<String>(Arrays.asList(array));
		//adds unique values
		String[] uniqueValues = h.toArray(new String[0]);
		//how often each element from uniqueValues is repeated in the array
		//3 elements - {50, 50, 50}
		int[] counts = new int[uniqueValues.length];
		for (int i = 0; i < uniqueValues.length; i++) {
			for (int j = 0; j < array.length; j++) {
				if (array[j].equals(uniqueValues[i])) {
					counts[i]++;
				}
			}
		}
			//returns the maximum repeated value from the classes; the most repeated class
			int max = counts[0];
			for (int counter = 1; counter < counts.length; counter++) {
				if (counts[counter] > max) {
					max = counts[counter];
				}

			}
			//finds the number of maximum class
			//how many times the most frequent classes appear
			int freq = 0;
			for (int counter = 0; counter < counts.length; counter++) {
				if (counts[counter] == max) {
					freq++;
				}
			}

			//finds the index of maximum class
			int index = -1;
			if (freq == 1) {
				for (int counter = 0; counter < counts.length; counter++) {
					if (counts[counter] == max) {
						index = counter;
						break;

					}
				}
				//returns the element of that index in the array uniqueValues
				return uniqueValues[index];
			} else {
				//we have multiple majority classes
				//create an array with a size of freq
				int[] ix = new int[freq];
				int ixi = 0;
				//add the indexes of max in the counts array
				for (int counter = 0; counter < counts.length; counter++) {
					if (counts[counter] == max) {
						ix[ixi] = counter;
						ixi++;
					}
				}
				//choose one max class by random
				Random generator = new Random();
				int rIndex = generator.nextInt(ix.length);
				int nIndex = ix[rIndex];
				return uniqueValues[nIndex];
			}
		}
	//finds the data's class by given attributes
	//k is the number of neighbours
	public static String findMajorityClass(double[] query, List<Kind> trainData, int k){
		List<Result> resultList = new ArrayList<Result>();
		
		for(Kind data : trainData) {
			double dist = 0.0;
			for(int j=0; j < data.getKindAttributes().length; j++) { //kindAttributes takes all attributes without the class
				dist+=Math.pow(data.getKindAttributes()[j] - query[j], 2 );
			}
			double distance = Math.sqrt(dist);
			resultList.add(new Result(distance, data.getKindName()));
		}
		Collections.sort(resultList, new DistanceComparator());
		String[] ss = new String[k];
		for(int x=0; x<k; x++) {
			ss[x] = resultList.get(x).getKindName();
		}
		String majClass = KNN.findMajorityClass(ss);
		return majClass;
	}
	
	//k is the number of neighbours
	public static String[] test(List<Kind> testData,List<Kind> trainData, int k){
		String[] majorClasses = new String[testData.size()];
		for (int i = 0; i < testData.size(); i++) {
			//for each element of test data finds the majority class
			majorClasses[i] = findMajorityClass(testData.get(i).getKindAttributes(), trainData,
					k);
		}
		return majorClasses;
	}
	
	public static int getNumberOfCorrectClassifiedData(String[] data, List<Kind> testData){
		int count = 0;
		for (int i = 0; i < data.length; i++) {
			if (data[i].equals(testData.get(i).getKindName())) {
				count++;
			}
		}
		return count;
	}
	
	public static double calculateAccuracy(String[] data,List<Kind> testData){
		int numberOfCorrectClassifiedData = getNumberOfCorrectClassifiedData(data, testData);
		return (((double)numberOfCorrectClassifiedData/data.length)*100);
	}

	public static void main(String[] args) {
		int k=5;
		int percent = 5;
		List<Kind> kindList = KNN.getInstances("C:\\Program Files\\Weka-3-6\\data\\iris.arff");
		List<Result> resultList = new ArrayList<Result>();
		
		Collections.shuffle(kindList);
		
		List<Kind> trainData = KNN.getTrainData(percent, kindList);
		List<Kind> testData = KNN.getTestData(percent, kindList);
		
		String[] data = KNN.test(testData, trainData, k);
		double accuracy = KNN.calculateAccuracy(data, testData);
		
		for(int i=0; i<data.length; i++) {
			System.out.println(data[i]);
		}
		
		System.out.println("Accuracy "+ accuracy);
	}
	
	static class Kind {
		double[] kindAttributes;
		String kindName;//which is the class
		public Kind(double[] kindAttributes, String kindName) {
			this.kindAttributes = kindAttributes;
			this.kindName = kindName;
		}	

		public double[] getKindAttributes() {
			return kindAttributes;
		}

		public void setKindAttributes(double[] kindAttributes) {
			this.kindAttributes = kindAttributes;
		}

		public String getKindName() {
			return kindName;
		}

		public void setKindName(String kindName) {
			this.kindName = kindName;
		}
	}
	
	static class Result {
		double distance;
		String kindName; //which is the class
		public Result(double distance, String kindName) {
			this.distance = distance;
			this.kindName = kindName;
		}
		
		public double getDistance() {
			return distance;
		}

		public void setDistance(double distance) {
			this.distance = distance;
		}

		public String getKindName() {
			return kindName;
		}

		public void setKindName(String kindName) {
			this.kindName = kindName;
		}
	}
	
	static class DistanceComparator implements Comparator<Result> {

		@Override
		public int compare(Result a, Result b) {
			return a.distance < b.distance ? -1 : a.distance == b.distance ? 0 : 1;
		}
		
	}
	

}
