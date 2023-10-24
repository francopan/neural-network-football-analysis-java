package neuralnetwork.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import neuralnetwork.enums.ActivationFunctionEnum;

public class NetworkLayer implements Cloneable {
	private List<ArtificialNeuron> neurons;
	private double bias;
	private Random rng;
	private Double minWeight;
	private Double maxWeight;

	public NetworkLayer(List<Double> inputs, int numberOfNeurons, double bias, double minWeight, double maxWeight,
			boolean isFirstLayer) {
		this.rng = new Random();
		this.neurons = new ArrayList<>();
		this.minWeight = minWeight;
		this.maxWeight = maxWeight;
		for (int i = 0; i < numberOfNeurons; i++) {
			List<ArtificialNeuronInput> neuronInputs = new ArrayList<>(inputs.size());
			if (isFirstLayer) {
				neuronInputs.add(new ArtificialNeuronInput(String.valueOf(i), inputs.get(i), 1.0));
			}
			this.neurons.add(new ArtificialNeuron(neuronInputs));
		}
		this.bias = bias;
	}
	
	public List<ArtificialNeuron> getNeurons() {
		return this.neurons;
	}

	public Random getRng() {
		return rng;
	}

	public void setRng(Random rng) {
		this.rng = rng;
	}

	public Double getMinWeight() {
		return minWeight;
	}

	public void setMinWeight(Double minWeight) {
		this.minWeight = minWeight;
	}

	public Double getMaxWeight() {
		return maxWeight;
	}

	public void setMaxWeight(Double maxWeight) {
		this.maxWeight = maxWeight;
	}

	public void setNeurons(List<ArtificialNeuron> neurons) {
		this.neurons = neurons;
	}

	public void setBias(double bias) {
		this.bias = bias;
	}

	public double generateRandom() {
		return this.minWeight + (this.maxWeight - this.minWeight) * this.rng.nextDouble();
	}

	public Double getOutputFromIndex(int index) {
		return this.neurons.get(index).getOutput();
	}

	public List<Double> getOutputs() {
		return this.neurons.stream().map(n -> n.getOutput()).collect(Collectors.toList());
	}

	public void activate(ActivationFunctionEnum activationFunctionEnum) {
		for (ArtificialNeuron neuron : neurons) {
			neuron.setOutput(ActivationFunction.apply(activationFunctionEnum, neuron.getSummation()));
		}
	}

	public void summate() {
		for (ArtificialNeuron neuron : neurons) {
			neuron.setSummation(neuron.getWeightedSum() + this.bias);
		}
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
	
	
	public NetworkLayer clone() {
		List<ArtificialNeuron> newNeurons = new ArrayList<>();
		for (ArtificialNeuron an : this.neurons) {
			newNeurons.add(an.clone());
		}		
		
		
		List<Double> newInputs = new ArrayList<>(this.neurons.size());
		for (int i = 0; i < this.neurons.size(); i++) {
			newInputs.add(0.0);
		}
		NetworkLayer nl = new NetworkLayer(newInputs, this.neurons.size(), this.bias, this.minWeight, this.maxWeight, true);

		nl.setBias(this.bias);
		nl.setRng(this.rng);
		nl.setMinWeight(this.minWeight);
		nl.setMaxWeight(this.maxWeight);
		nl.setNeurons(newNeurons);
		return nl;
	}

	
}
