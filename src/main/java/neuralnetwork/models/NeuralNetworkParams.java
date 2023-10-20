package neuralnetwork.models;

import java.util.List;

import neuralnetwork.enums.ActivationFunctionEnum;

public class NeuralNetworkParams {
	private List<NetworkLayerParam> layersParams;
	private List<ArtificialNeuronInput> inputsAndWeightsFirstLayer;
	private ActivationFunctionEnum function;
	private Double momentum;
	private Double learningRate;

	public NeuralNetworkParams(List<NetworkLayerParam> layersParams,
			List<ArtificialNeuronInput> inputsAndWeightsFirstLayer, ActivationFunctionEnum function, Double momentum,
			Double learningRate) {
		this.layersParams = layersParams;
		this.inputsAndWeightsFirstLayer = inputsAndWeightsFirstLayer;
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

	public List<ArtificialNeuronInput> getInputsAndWeightsFirstLayer() {
		return inputsAndWeightsFirstLayer;
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
}