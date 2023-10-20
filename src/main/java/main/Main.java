package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dataset.models.FootballMatch;
import dataset.utils.Utils;
import neuralnetwork.enums.ActivationFunctionEnum;
import neuralnetwork.models.ArtificialNeuron;
import neuralnetwork.models.ArtificialNeuronInput;
import neuralnetwork.models.NetworkLayerParam;
import neuralnetwork.models.NeuralNetwork;
import neuralnetwork.models.NeuralNetworkParams;

public class Main {
	static List<Integer> layersNeurons = Arrays.asList(12, 6, 3);
	static Double bias = 0.0;
	static Double momentum = 0.9;
	static Double learningRate = 0.2;

	public static void main(String[] args) {
		try {
			List<FootballMatch> matches = Utils.readCSV("/dataset_football.csv", ';');
			NeuralNetwork nn = createFootballMatchNeuralNetwork(matches.get(0), layersNeurons, bias);
			for (int i = 0; i < matches.size(); i++) {
				nn.propagateToNextLayer();
				nn.propagateToNextLayer();
				nn.propagateToNextLayer();
				nn.backPropagation();
				if ((i + 1) < matches.size()) {
					nextMatch(nn, matches.get(i + 1));
					nn.resetCurrentLayer();
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * This method gets all necessary inputs from the FootballMatch and converts
	 * them into Double (if necessary).
	 * 
	 * @param fm - Football Match
	 * @return List of Double values
	 */
	public static List<Double> getInputs(FootballMatch fm) {
//		Double v1 = fm.getFullTimeHomeGoals() != null ? fm.getFullTimeHomeGoals() : 0.0;
//		Double v2 = fm.getFullTimeAwayGoals() != null ? fm.getFullTimeAwayGoals() : 0.0;
//		Double v4 = fm.getHalfTimeHomeGoals() != null ? fm.getHalfTimeHomeGoals() : 0.0;
//		Double v5 = fm.getHalfTimeAwayGoals() != null ? fm.getHalfTimeAwayGoals() : 0.0;
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
		List<ArtificialNeuronInput> inputsAndWeightsFirstLayer = new ArrayList<>();
		for (Double i : getInputs(fm)) {
			inputsAndWeightsFirstLayer.add(new ArtificialNeuronInput("0", i, 0.0));
		}
		List<NetworkLayerParam> layersParams = new ArrayList<>();
		for (Integer numberOfNeurons : layersNeurons) {
			layersParams.add(new NetworkLayerParam(numberOfNeurons, bias));
		}
		NeuralNetworkParams networkParams = new NeuralNetworkParams(layersParams, inputsAndWeightsFirstLayer,
				ActivationFunctionEnum.Sigmoid, momentum, learningRate);
		return new NeuralNetwork(networkParams);
	}

	public static NeuralNetwork nextMatch(NeuralNetwork nn, FootballMatch fm) {
		List<Double> newInputs = getInputs(fm);
		if (nn.getLayers().get(0) != null) {
			for (ArtificialNeuron an : nn.getLayers().get(0).getNeurons()) {
				for (int i = 0; i < an.inputs.size(); i++) {
					an.inputs.set(i, new ArtificialNeuronInput("0", newInputs.get(i), an.inputs.get(i).weight));
				}
			}
		}
		return nn;
	}

}
