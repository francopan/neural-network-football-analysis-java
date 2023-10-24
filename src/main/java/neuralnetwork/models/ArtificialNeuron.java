package neuralnetwork.models;

import java.util.ArrayList;
import java.util.List;

public class ArtificialNeuron implements Cloneable {
	private String id;
	private List<ArtificialNeuronInput> inputs;
	private Double summation;
	private Double output;
	private Double error;

	public ArtificialNeuron(List<ArtificialNeuronInput> inputs) {
		this.inputs = inputs;
		this.output = 0.0;
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

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<ArtificialNeuronInput> getInputs() {
		return this.inputs;
	}

	public void addInput(Integer index, String id, Double input, Double weight) {
		ArtificialNeuronInput ani = new ArtificialNeuronInput(id, input, weight);
		this.inputs.add(ani);
	}

	public void updateInput(Integer index, Double input, Double weight) {
		ArtificialNeuronInput ani = this.inputs.get(index);
		if (ani != null) {
			ani.input = input;
			ani.weight = weight;
			this.inputs.set(index, ani);
		}
	}

	public Double getError() {
		return this.error;
	}

	public void setError(Double error) {
		this.error = error;
	}

	public Double getSummation() {
		return this.summation;
	}

	public void setSummation(double d) {
		this.summation = d;

	}

	public Double getOutput() {
		return this.output;
	}

	public void setOutput(Double output) {
		this.output = output;

	}

	@Override
	public String toString() {
		return "ArtificialNeuron { Inputs : " + inputs.toString() + ", Summation: " + this.getSummation() + ", Output: "
				+ this.getOutput() + ", Error:" + this.getError() + "}";
	}

	public ArtificialNeuron clone() {

		List<ArtificialNeuronInput> inputs = new ArrayList<>();

		for (ArtificialNeuronInput input : this.inputs) {
			ArtificialNeuronInput newInput = input.clone();
			inputs.add(newInput);
		}

		ArtificialNeuron newArtificialNeuron = new ArtificialNeuron(inputs);
		newArtificialNeuron.setId(this.id);
		newArtificialNeuron.setSummation(this.summation != null ? this.summation : 0.0);
		newArtificialNeuron.setError(this.error != null ? this.error : 0.0);
		newArtificialNeuron.setOutput(this.output != null ? this.output : 0.0);

		return newArtificialNeuron;
	}
}