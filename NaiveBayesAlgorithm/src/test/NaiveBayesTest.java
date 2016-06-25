package test;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import naiveBayes.NaiveBayes;
import votes.Vote;

public class NaiveBayesTest {
	public static void main(String[] args) {
		Random rand = new Random(System.currentTimeMillis());
		int numberOfSubsets = 10;
		
		List<Vote> data = NaiveBayes.getInstances("C:\\Program Files\\Weka-3-6\\data\\vote.arff");
		Collections.shuffle(data);
		List<List<Vote>> subsets = NaiveBayes.getSubsets(data, numberOfSubsets);
		
		int index = rand.nextInt(subsets.size());
		List<Vote> testData = NaiveBayes.getTestData(index, subsets);
		
		List<Vote> trainData = NaiveBayes.getTrainData(subsets);
		
		
		double accuracy = NaiveBayes.calculateAccuracy(testData,trainData);
		System.out.println("Accuracy: "+accuracy+"%");
	}
}
