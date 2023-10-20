package neuralnetwork.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import neuralnetwork.enums.ActivationFunctionEnum;

public class NetworkLayer {
	private List<ArtificialNeuron> neurons;
	private double bias;
	private Random rng;
	private Double minWeight;
	private Double maxWeight;

	public NetworkLayer(List<Double> inputs, int numberOfNeurons, double bias, List<Double> weights, double minWeight,
			double maxWeight) {
		this.rng = new Random();
		this.neurons = new ArrayList<>();
		this.minWeight = minWeight;
		this.maxWeight = maxWeight;

		for (int i = 0; i < numberOfNeurons; i++) {
			List<Double> actualWeights = getWeights(inputs.size());
			List<ArtificialNeuronInput> neuronInputs = new ArrayList<>();
			for (int j = 0; j < inputs.size(); j++) {

				StringBuilder id = new StringBuilder();
				id.append(i);
				neuronInputs.add(new ArtificialNeuronInput(id.toString(), inputs.get(j), actualWeights.get(j)));
			}
			this.neurons.add(new ArtificialNeuron(neuronInputs));
		}
		this.bias = bias;
	}

	public List<ArtificialNeuron> getNeurons() {
		return this.neurons;
	}

	public double getBias() {
		return this.bias;
	}

	public double generateRandom() {
		return this.minWeight + (this.maxWeight - this.minWeight) * this.rng.nextDouble();
	}

	public List<Double> getOutputsFromIndex(int index) {
		return this.neurons.get(index).outputs;
	}

	public void activate(ActivationFunctionEnum activationFunctionEnum) {
		for (ArtificialNeuron neuron : neurons) {
			neuron.getOutputs().add(ActivationFunction.apply(activationFunctionEnum, neuron.getSummation()));
		}
	}

	public void summate() {
		for (ArtificialNeuron neuron : neurons) {
			neuron.setSummation(neuron.getWeightedSum() + this.bias);
		}
	}

	private List<Double> getWeights(int numberOfInputs) {
		List<Double> generatedWeights = new ArrayList<>();
		for (int i = 0; i < numberOfInputs; i++) {
			generatedWeights.add(this.generateRandom());
		}
		return generatedWeights;

	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		this.neurons.stream().forEach(l -> {
			sb.append("\n\t");
			sb.append(l.toString());
			sb.append(",");
		});
		return "NetworkLayer{" + "neurons=[\t" + sb.toString() + "]}";
	}
}
