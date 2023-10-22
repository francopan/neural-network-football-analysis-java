package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dataset.enums.MatchResult;
import dataset.models.FootballMatch;
import dataset.utils.Utils;
import neuralnetwork.enums.ActivationFunctionEnum;
import neuralnetwork.models.ArtificialNeuron;
import neuralnetwork.models.NetworkLayerParam;
import neuralnetwork.models.NeuralNetwork;
import neuralnetwork.models.NeuralNetworkParams;

public class Main {
	static List<Integer> layersNeurons = Arrays.asList(12, 12, 6, 3);
	static Double bias = 0.0;
	static Double momentum = 0.8;
	static Double learningRate = 0.2;

	public static void main(String[] args) {

		Integer epochs = 100;
		Double trainPercentage = 0.8;

		try {
			List<FootballMatch> matches = Utils.readCSV("/dataset_football.csv", ';');
			NeuralNetwork nn = createFootballMatchNeuralNetwork(matches.get(0), layersNeurons, bias);
			int trainSize = (int) (matches.size() * trainPercentage);
			train(epochs, matches.subList(0, trainSize), nn);
			test(matches.subList(trainSize, matches.size()), nn);
			System.out.println(nn);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static void test(List<FootballMatch> data, NeuralNetwork nn) {
		for (int i = 0; i < data.size() - 1; i++) {
			List<Double> expectedResult = getExpectedResult(data.get(i).fullTimeResult);
			nextMatch(nn, data.get(i));
			for (int l = 0; l < nn.getLayers().size(); l++) {
				nn.propagateToNextLayer();
			}
			System.out.println(expectedResult + " - " + nn.getLayers().get(layersNeurons.size() - 1).getOutputs());
		}
	}

	private static void train(Integer epochs, List<FootballMatch> data, NeuralNetwork nn) {
		for (int k = 0; k < epochs; k++) {
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
			System.out.println("Epoch: " + k);
		}
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
