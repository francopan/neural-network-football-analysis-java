package neuralnetwork.models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import neuralnetwork.enums.ActivationFunctionEnum;

public class NeuralNetwork {
	private List<NetworkLayer> layers;
	private Double momentum = 0.0;
	private Double learningRate = 0.0;
	private Integer currentLayerStep = 0;
	private ActivationFunctionEnum function;

	public NeuralNetwork(NeuralNetworkParams params) {
		this.layers = new ArrayList<>();
		this.function = params.getFunction();
		this.momentum = params.getMomentum();
		this.learningRate = params.getLearningRate();
		int previousNumberOfNeurons = 0;

		for (int layerIndex = 0; layerIndex < params.getLayersParams().size(); layerIndex++) {
			NetworkLayerParam layerParam = params.getLayersParams().get(layerIndex);
			int numberOfNeurons = layerParam.getNumberOfNeurons();
			List<Double> inputs;
			if (layerIndex == 0) {
				inputs = new ArrayList<>();
				for (ArtificialNeuronInput iaw : params.getInputsAndWeightsFirstLayer()) {
					inputs.add(iaw.getInput());
				}
			} else {
				inputs = new ArrayList<>(previousNumberOfNeurons);
			}
			List<Double> weights = new ArrayList<>(previousNumberOfNeurons);
			NetworkLayerParam layerParams = params.getLayersParams().get(layerIndex);

			try {
				NetworkLayer newLayer = new NetworkLayer(inputs, numberOfNeurons, layerParams.getBias(), weights, -1.0,
						1.0);
				layers.add(newLayer);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			previousNumberOfNeurons = numberOfNeurons;
		}
	}

	public void resetCurrentLayer() {
		this.currentLayerStep = 0;
	}

	public List<NetworkLayer> getLayers() {
		return this.layers;
	}

	public List<List<Double>> getResults() {
		return this.layers.get(this.layers.size() - 1).getNeurons().stream().map(n -> n.getOutputs())
				.collect(Collectors.toList());
	}

	public void setFunction(ActivationFunctionEnum function) {
		this.function = function;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		this.layers.stream().forEach(l -> {
			sb.append("\n\t");
			sb.append(l.toString());
			sb.append(",");
		});
		return "{NeuralNetwork: {" + "layers: [" + sb.toString() + "\n]}}";
	}

	public void propagateToNextLayer() {
		// Get the current and next layer
		NetworkLayer currentLayer = this.layers.get(this.currentLayerStep);
		NetworkLayer nextLayer = this.layers.size() == this.currentLayerStep + 1 ? null
				: this.layers.get(this.currentLayerStep + 1);

		// Check if the current layer is not null
		if (currentLayer != null) {
			// Perform summation and activation on the current layer
			currentLayer.summate();
			currentLayer.activate(this.function);

			// Set inputs of next layer from currentLayer outputs
			setInputsFromOutputs(currentLayer, nextLayer);

			this.currentLayerStep++;
		}
	}

	private void setInputsFromOutputs(NetworkLayer currentLayer, NetworkLayer nextLayer) {
		// Check if the next layer is not null
		if (nextLayer != null) {
			// For each neuron in the current layer
			for (int i = 0; i < currentLayer.getNeurons().size(); i++) {
				ArtificialNeuron neuron = currentLayer.getNeurons().get(i);

				// Get the current output
				double currentOutput = neuron.outputs.get(0);

				// For each neuron in the next layer
				for (ArtificialNeuron nextNeuron : nextLayer.getNeurons()) {
					// Set the corresponding input of the next neuron to the current output
					if ((i + 1) < nextNeuron.inputs.size()) {
						nextNeuron.inputs.get(i).input = currentOutput;
					} else {
						StringBuilder sb = new StringBuilder();
						sb.append(i);
						nextNeuron.inputs.add(
								new ArtificialNeuronInput(sb.toString(), currentOutput, nextLayer.generateRandom()));
					}
				}

			}
		}
	}

	public void backPropagation(List<Double> expectedResult) {
		this.currentLayerStep = this.layers.size() - 1;
		for (this.currentLayerStep = this.layers.size() - 1; this.currentLayerStep > 0; this.currentLayerStep--) {
			NetworkLayer nl = this.getLayers().get(this.currentLayerStep);
			for (int i = 0; i < nl.getNeurons().size(); i++) {
				ArtificialNeuron an = nl.getNeurons().get(i);
				Double result = an.outputs.get(0);

				if (i == this.layers.size() - 1) { // Last Layer
					Double errorFactor = expectedResult.get(i) - result;
					Double error = result * (1 - result) * errorFactor;
					an.setError(error);
				} else { // Intermediate Layers
					Double error = 0.0;
					NetworkLayer nextLayer = this.getLayers().get(this.currentLayerStep + 1);
					for (ArtificialNeuron ai : nextLayer.getNeurons()) {
						StringBuilder sb = new StringBuilder();
						sb.append(i);
						Double w = ai.inputs.stream().filter((input) -> input.id.equals(sb.toString()))
								.collect(Collectors.toList()).get(0).weight;
						error = error + (ai.getError() * w);
					}
				}
				
				// Novo_peso=Peso_anterior*momentum+Taxa_aprendizagem*Saída_neurônio_anterior*Erro_neuronio_posterior
				
				//Double newWeight = an.error * this.momentum + this.learning_rate * 
				
			}
		}
	}

}
