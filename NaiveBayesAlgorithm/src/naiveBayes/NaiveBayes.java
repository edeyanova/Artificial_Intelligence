package naiveBayes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import votes.Vote;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class NaiveBayes {

	
	public static List<Vote> getInstances(String path){
		DataSource source = null;
		try {
			source = new DataSource(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Instances instances = null;
		try {
			instances = source.getDataSet();
		} catch (Exception e) {
			e.printStackTrace();
		}
		instances.setClassIndex(instances.numAttributes() - 1);
		
		int numberOfInstances = instances.numInstances();
		int numberOfAttributes = instances.numAttributes();

		List<Vote> list = new ArrayList<Vote>();
		for (int i = 0; i < numberOfInstances; i++) {
			Instance instance = instances.instance(i);
			String[] attributes = new String[numberOfAttributes - 1];
			for (int j = 0; j < attributes.length; j++) {
				attributes[j] = instance.stringValue(j);								
			}
			String className = instance.stringValue(numberOfAttributes - 1);
			Vote vote = new Vote(attributes,className);
			list.add(vote);
		}
		
		return list;
	}
	private static List<Vote> getSubset(List<Vote> list, int startIndex, int endIndex){
		List<Vote> subset = new ArrayList<Vote>();
		
		for (int i = startIndex; i <= endIndex; i++) {
			subset.add(list.get(i));
		}
		
		return subset;
	}
	public static List<List<Vote>> getSubsets(List<Vote> list, int numberOfSubsets){
		List<List<Vote>> subsets = new ArrayList<List<Vote>>(numberOfSubsets);
		
		int length = list.size()/numberOfSubsets;
		int startIndex = 0;
		for (int i = 0; i < numberOfSubsets - 1; i++) {
			subsets.add(getSubset(list,startIndex,startIndex + length - 1));
			startIndex+=length;
		}
		subsets.add(getSubset(list, startIndex, list.size() - 1));
		
		return subsets;
	}
	
	public static List<Vote> getTestData(int index, List<List<Vote>> list){
		List<Vote> testData = list.get(index);
		list.remove(index);
		return testData;
	}
	
	public static List<Vote> getTrainData(List<List<Vote>> list){
		List<Vote> result = new ArrayList<Vote>();
		
		for (int i = 0; i < list.size(); i++) {
			for (int j = 0; j < list.get(i).size(); j++) {
				result.add(list.get(i).get(j));
			}
		}
		
		return result;
	}
	
	
	public static String[] getClassNames(List<Vote> list){
		Set<String> uniqueClassValues = new HashSet<String>();
		for (int i = 0; i < list.size(); i++) {
			uniqueClassValues.add(list.get(i).getClassName());
		}
		String[] classValues = uniqueClassValues.toArray(new String[0]);
		
		return classValues;
	}
	
	private static String[] getAllValuesOfCurrentAttribute(int indexOfAttribute, List<Vote> list){
		String[] allValues = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			allValues[i] = list.get(i).getAttributes()[indexOfAttribute];
		}
		
		return allValues;
	}
	
	
	private static int getClassCount(String className, List<Vote> list){
		int count = 0;
		
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getClassName().equals(className)) {
				count++;
			}
		}
		
		return count;
	}
	
	private static int getAttributeCount(int indexOfAttribute, String value, 
			String classValue, List<Vote> list){
		int count = 0;
		String[] values = getAllValuesOfCurrentAttribute(indexOfAttribute, list);
		for (int i = 0; i < list.size(); i++) {
			if (values[i].equals(value) && list.get(i).getClassName().equals(classValue)) {
				count++;
			}
		}
		
		return count;
	}
	private static double getClassProbability(String className, List<Vote> list){
		int classCount = getClassCount(className, list);
		
		return (double)classCount/list.size();
	}
	private static double getAttributeProbability(int indexOfAttribute, String value, String classValue, List<Vote> list){
		int classNameCount = getClassCount(classValue, list);
		int attributeCount = getAttributeCount(indexOfAttribute, value, classValue, list);
		
		double probability = (double)attributeCount/classNameCount;
		
		return probability;
	}
	
	private static double getProbability(String[] attributes, String className, List<Vote> list){
		double res = 1;
		for (int i = 0; i < attributes.length; i++) {
			res*=getAttributeProbability(i, attributes[i], className, list);
		}
		res*=getClassProbability(className, list);
		return res;
	}
	
	private static double getFullProbability(String[] attributes, String[] classNames, List<Vote> list){
		double result = 0;
		for (int i = 0; i < classNames.length; i++) {
			result+=getProbability(attributes, classNames[i], list);
		}
		
		return result;
	}
	
	private static double getProbability(String[] attributes, String[] classNames,
			String className, List<Vote> list){
		double probability = getProbability(attributes,className, list)/
				getFullProbability(attributes,classNames,list);
		
		return probability;
	}
	
	private static List<String> getClassName(List<Vote> testData, List<Vote> trainData,
			String[] classNames){
		double maxProbability = Double.MIN_VALUE;
		String className = "";
		List<String> result = new ArrayList<String>();
		
		for (int i = 0; i < testData.size(); i++) {
			for (int j = 0; j < classNames.length; j++) {
				double probability = getProbability(testData.get(i).getAttributes(), classNames,
						classNames[j], trainData);
				if(probability>maxProbability){
					maxProbability=probability;
					className=classNames[j];
				}
			}
			result.add(className);
		}
		
		return result;
	}
	private static List<String> getClassName(List<Vote> testData, List<Vote> trainData){
		String[] classNames = getClassNames(trainData);
		return getClassName(testData,trainData,classNames);
	}
	private static int countCorrectClassifiedClasses(List<Vote> testData, List<Vote> trainData){
		int count = 0;
		
		List<String> classNames = getClassName(testData,trainData);
		for (int i = 0; i < testData.size(); i++) {
			if (classNames.get(i).equals(testData.get(i).getClassName())) {
				count++;
			}
		}
		
		return count;
	}
	
	public static double calculateAccuracy(List<Vote> testData, List<Vote> trainData){
		int correctClassifiedData = countCorrectClassifiedClasses(testData, trainData);
		
		return ((double)correctClassifiedData/testData.size())*100;
	}
	
	
}
