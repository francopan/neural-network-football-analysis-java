package neuralnetwork.models;

import java.util.ArrayList;
import java.util.List;

public class ArtificialNeuron {
	public String id;
	public List<ArtificialNeuronInput> inputs;
	public Double summation;
	public List<Double> outputs;
	public Double error;

	public ArtificialNeuron(List<ArtificialNeuronInput> inputs) {
		this.inputs = inputs;
		this.outputs = new ArrayList<>();
		this.outputs.stream().map(e -> e = 0.0);
		this.error = 0.0;
	}

	public Double getWeightedSum() {
		Double sum = 0.0;
		for (ArtificialNeuronInput input : this.inputs) {

			Double auxInput = input.input != null ? input.input.doubleValue() : 0.0;
			Double auxWeight = input.weight != null ? input.weight.doubleValue() : 0.0;

			Double product = auxInput * auxWeight;
			sum += product;
		}
		return sum;
	}
	
	public Double getError() {
		return this.error;
	}
	
	public void setError(Double error) {
		this.error = error;
	}
	
	@Override
	public String toString() {
		return "ArtificialNeuron { Inputs : " + inputs.toString() + ", Summation: " + summation + ", Output: " + outputs.toString()
				+ "}";
	}

	public List<Double> getOutputs() {
		return this.outputs;
	}

	public void setSummation(double d) {
		this.summation = d;

	}

	public Double getSummation() {
		return this.summation;
	}
}