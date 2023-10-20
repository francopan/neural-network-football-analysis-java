package neuralnetwork.models;

public class ArtificialNeuronInput {
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
}