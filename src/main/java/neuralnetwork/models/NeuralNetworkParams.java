package neuralnetwork.models;

import java.util.ArrayList;
import java.util.List;

import neuralnetwork.enums.ActivationFunctionEnum;

public class NeuralNetworkParams implements Cloneable {
	private List<NetworkLayerParam> layersParams;
	private List<Double> inputs;
	private ActivationFunctionEnum function;
	private Double momentum;
	private Double learningRate;

	public NeuralNetworkParams(List<NetworkLayerParam> layersParams, List<Double> inputs,
			ActivationFunctionEnum function, Double momentum, Double learningRate) {
		this.layersParams = layersParams;
		this.inputs = inputs;
		this.function = function;
		this.momentum = momentum;
		this.learningRate = learningRate;
	}

	public ActivationFunctionEnum getFunction() {
		return this.function;
	}

	public List<NetworkLayerParam> getLayersParams() {
		return layersParams;
	}

	public List<Double> getInputs() {
		return this.inputs;
	}

	public Double getMomentum() {
		return momentum;
	}

	public void setMomentum(Double momentum) {
		this.momentum = momentum;
	}

	public Double getLearningRate() {
		return learningRate;
	}

	public void setLearningRate(Double learning_rate) {
		this.learningRate = learning_rate;
	}

	public NeuralNetworkParams clone() {
		List<NetworkLayerParam> newLayersParams = new ArrayList<>();
		List<Double> newInputs = new ArrayList<>();

		for (NetworkLayerParam param : this.layersParams) {
			newLayersParams.add(param.clone());
		}

		for (Double input : this.inputs) {
			newInputs.add(input);
		}
		return new NeuralNetworkParams(newLayersParams, newInputs, this.function, this.momentum, this.learningRate);
	}
}