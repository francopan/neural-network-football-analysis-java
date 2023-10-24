package neuralnetwork.models;

public class NetworkLayerParam implements Cloneable {
	private int numberOfNeurons;
	private Double bias;

	public NetworkLayerParam(int numberOfNeurons, Double bias) {
		this.numberOfNeurons = numberOfNeurons;
		this.bias = bias;
	}

	public int getNumberOfNeurons() {
		return numberOfNeurons;
	}

	public Double getBias() {
		return bias;
	}

	public NetworkLayerParam clone() {
		return new NetworkLayerParam(this.numberOfNeurons, this.bias);
	}
}
