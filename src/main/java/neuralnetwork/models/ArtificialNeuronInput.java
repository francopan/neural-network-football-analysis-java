package neuralnetwork.models;

public class ArtificialNeuronInput implements Cloneable {
	public String id;
	public Double input;
	public Double weight;

	public ArtificialNeuronInput(String id, Double input, Double weight) {
		this.id = id;
		this.input = input;
		this.weight = weight;
	}

	@Override
	public String toString() {
		return "{ Input : { Value: " + input + " }, Weight: " + weight + " }";
	}

	public Double getInput() {
		return input;
	}

	public ArtificialNeuronInput clone() {
		return new ArtificialNeuronInput(this.id,this.input,this.weight);
	}
}