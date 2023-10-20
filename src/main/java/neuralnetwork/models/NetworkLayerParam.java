package neuralnetwork.models;

public class NetworkLayerParam {
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
}
