package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import dataset.enums.MatchResult;
import dataset.models.FootballMatch;
import dataset.models.Statistics;
import dataset.utils.Utils;
import neuralnetwork.enums.ActivationFunctionEnum;
import neuralnetwork.models.ArtificialNeuron;
import neuralnetwork.models.NetworkLayerParam;
import neuralnetwork.models.NeuralNetwork;
import neuralnetwork.models.NeuralNetworkParams;

public class Main {
	static Integer maxEpochs = 1000;
	static Double bias = 0.000;
	static Double momentum = 1.0;
	static Double learningRate = 0.005;
	static Double trainPercentage = 0.8;
	static Double errorStop = 0.01;
	static List<Integer> layersNeurons = Arrays.asList(12, 12, 6, 3);
	static Integer numberOfOutputs = layersNeurons.get(layersNeurons.size() - 1);
	
	public static void main(String[] args) {
		
		try {
			List<FootballMatch> matches = Utils.readCSV("/dataset_football.csv", ';');
			NeuralNetwork nn = createFootballMatchNeuralNetwork(matches.get(0), layersNeurons, bias);
			int trainSize = (int) (matches.size() * trainPercentage);
			train(maxEpochs, matches.subList(0, trainSize), nn);
			List<List<Integer>> confusionMatrix = createConfusionMatrixStructure();
			test(matches.subList(trainSize, matches.size()), nn, confusionMatrix);
			printConfusionMatrix(confusionMatrix);
			printStatistics(confusionMatrix);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}


	private static List<List<Integer>> createConfusionMatrixStructure() {
		List<List<Integer>> confusionMatrix = new ArrayList<>();
		for (int i = 0; i < numberOfOutputs; i++) {
			confusionMatrix.add(new ArrayList<Integer>(Arrays.asList(0, 0, 0)));
		}
		return confusionMatrix;
	}


	private static void printConfusionMatrix(List<List<Integer>> confusionMatrix) {
		Integer trueValues = 0;
		Integer total = 0;
		for (int i = 0; i < numberOfOutputs; i++) {
			List<Integer> row = confusionMatrix.get(i);
			for (int j = 0; j < numberOfOutputs; j++) {
				System.out.print("\t" + row.get(j));
				if (j == i) {
					trueValues += row.get(j);
				}
				total += row.get(j);
			}
			System.out.print("\n");
		}
	}


	private static void printStatistics(List<List<Integer>> confusionMatrix) {
		Statistics stats = new Statistics(confusionMatrix);
		double accuracy = stats.calculateAccuracy();
		double[] recall = stats.calculateRecall();
		double[] precision = stats.calculatePrecision();
		double[] fMeasure = stats.calculateFMeasure(recall, precision);
		System.out.println("Accuracy: " + accuracy);
		System.out.println("Recall HomeWin: " + recall[0]);
		System.out.println("Recall Draw: " + recall[1]);
		System.out.println("Recall AwayWin: " + recall[2]);
		System.out.println("Precision HomeWin: " + precision[0]);
		System.out.println("Precision Draw: " + precision[1]);
		System.out.println("Precision AwayWin: " + precision[2]);
		System.out.println("F-Measure HomeWin: " + fMeasure[0]);
		System.out.println("F-Measure Draw: " + fMeasure[1]);
		System.out.println("F-Measure AwayWin: " + fMeasure[2]);
	}


	private static void test(List<FootballMatch> data, NeuralNetwork nn, List<List<Integer>> confusionMatrix) {
		for (int i = 0; i < data.size() - 1; i++) {
			List<Double> expectedResult = getExpectedResult(data.get(i).fullTimeResult);
			nextMatch(nn, data.get(i));
			for (int l = 0; l < nn.getLayers().size(); l++) {
				nn.propagateToNextLayer();
			}

			Integer expectedPosition = getBiggestPosition(expectedResult);
			Integer actualPosition = getBiggestPosition(nn.getLayers().get(layersNeurons.size() - 1).getOutputs());

			List<Integer> row = confusionMatrix.get(expectedPosition);
			row.set(actualPosition, row.get(actualPosition) != null ? row.get(actualPosition) + 1 : 1);
		}
	}

	private static Integer getBiggestPosition(List<Double> val) {
		Integer pos = 0;
		Double biggestValue = val.get(0);
		for (int i = 1; i < val.size(); i++) {
			if (Math.abs(val.get(i)) > Math.abs(biggestValue)) {
				biggestValue = val.get(i);
				pos = i;
			}
		}
		return pos;
	}

	private static Integer train(Integer maxEpochs, List<FootballMatch> data, NeuralNetwork nn) {
		List<Double> outputs;
		Integer k = 0, bestErrorPos = 0;
		Double bestError = 999.9;
		boolean continueTraining = true;

		while (continueTraining) {
			for (int i = 0; i < data.size(); i++) {
				for (int l = 0; l < nn.getLayers().size(); l++) {
					nn.propagateToNextLayer();
				}
				List<Double> expectedResult = getExpectedResult(data.get(i).fullTimeResult);
				nn.backPropagation(expectedResult);
				if ((i + 1) < data.size()) {
					nextMatch(nn, data.get(i + 1));
				}
			}

			outputs = nn.getLayers().get(nn.getLayers().size() - 1).getNeurons().stream().map(n -> n.getError())
					.collect(Collectors.toList());

			Double networkError = Math.abs(outputs.get(getBiggestPosition(outputs)));

			if (bestError != null && networkError < bestError) {
				bestError = networkError;
				bestErrorPos = k;
			}

			continueTraining = k < maxEpochs && networkError > errorStop;
			
				System.out.println("Epoch: " + k + ", error: " + Math.abs(outputs.get(getBiggestPosition(outputs))));				
		
			k++;
		}
		return bestErrorPos;

	}

	private static List<Double> getExpectedResult(MatchResult matchResult) {
		switch (matchResult) {
		case HomeWin:
			return List.of(1.0, 0.0, 0.0);
		case Draw:
			return List.of(0.0, 1.0, 0.0);
		case AwayWin:
			return List.of(0.0, 0.0, 1.0);
		}
		return null;
	}

	/**
	 * This method gets all necessary inputs from the FootballMatch and converts
	 * them into Double (if necessary).
	 * 
	 * @param fm - Football Match
	 * @return List of Double values
	 */
	public static List<Double> getInputs(FootballMatch fm) {
		Double v7 = fm.getHomeShots() != null ? fm.getHomeShots() : 0.0;
		Double v8 = fm.getAwayShots() != null ? fm.getAwayShots() : 0.0;
		Double v9 = fm.getHomeShotsOnTarget() != null ? fm.getHomeShotsOnTarget() : 0.0;
		Double v10 = fm.getAwayShotsOnTarget() != null ? fm.getAwayShotsOnTarget() : 0.0;
		Double v11 = fm.getHomeCorners() != null ? fm.getHomeCorners() : 0.0;
		Double v12 = fm.getAwayCorners() != null ? fm.getAwayCorners() : 0.0;
		Double v13 = fm.getHomeFoulsCommitted() != null ? fm.getHomeFoulsCommitted() : 0.0;
		Double v14 = fm.getAwayFoulsCommitted() != null ? fm.getAwayFoulsCommitted() : 0.0;
		Double v15 = fm.getHomeYellowCards() != null ? fm.getHomeYellowCards() : 0.0;
		Double v16 = fm.getAwayYellowCards() != null ? fm.getAwayYellowCards() : 0.0;
		Double v17 = fm.getHomeRedCards() != null ? fm.getHomeRedCards() : 0.0;
		Double v18 = fm.getAwayRedCards() != null ? fm.getAwayRedCards() : 0.0;
		return Arrays.asList(v7, v8, v9, v10, v11, v12, v13, v14, v15, v16, v17, v18);
	}

	/**
	 * Creates a NeuralNetwork for FootballMatch
	 * 
	 * @param fm            FootballMatch
	 * @param layersNeurons List of how many neurons each layer has. Eg.: (10,5,2)
	 *                      -> First layer has 10 neurons, second layer has 5 and
	 *                      last layer has 2
	 * @param bias          Bias
	 * @return NeuralNetwork
	 */
	public static NeuralNetwork createFootballMatchNeuralNetwork(FootballMatch fm, List<Integer> layersNeurons,
			Double bias) {
		List<Double> inputs = getInputs(fm);
		List<NetworkLayerParam> layersParams = new ArrayList<>();
		for (Integer numberOfNeurons : layersNeurons) {
			layersParams.add(new NetworkLayerParam(numberOfNeurons, bias));
		}
		NeuralNetworkParams networkParams = new NeuralNetworkParams(layersParams, inputs,
				ActivationFunctionEnum.Sigmoid, momentum, learningRate);
		return new NeuralNetwork(networkParams);
	}

	public static NeuralNetwork nextMatch(NeuralNetwork nn, FootballMatch fm) {
		List<Double> newInputs = getInputs(fm);
		int j = 0;
		if (nn.getLayers().get(0) != null) {
			for (ArtificialNeuron an : nn.getLayers().get(0).getNeurons()) {
				for (int i = 0; i < an.getInputs().size(); i++) {
					an.updateInput(i, newInputs.get(j), an.getInputs().get(i).weight);
				}
				j++;
			}
		}
		nn.resetCurrentLayerCounter();
		return nn;
	}

}
