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
			boolean isFirstLayer = layerIndex == 0;
			int numberOfNeurons = layerParam.getNumberOfNeurons();
			List<Double> inputs = isFirstLayer ? params.getInputs() : new ArrayList<>(previousNumberOfNeurons);
			NetworkLayerParam layerParams = params.getLayersParams().get(layerIndex);

			try {
				NetworkLayer newLayer = new NetworkLayer(inputs, numberOfNeurons, layerParams.getBias(), -1.0, 1.0,
						isFirstLayer);
				layers.add(newLayer);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

			previousNumberOfNeurons = numberOfNeurons;
		}
	}

	public void resetCurrentLayerCounter() {
		this.currentLayerStep = 0;
	}

	public List<NetworkLayer> getLayers() {
		return this.layers;
	}

	public List<Double> getResults() {
		return this.layers.get(this.layers.size() - 1).getNeurons().stream().map(n -> n.getOutput())
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
		NetworkLayer currentLayer = this.layers.get(this.currentLayerStep);
		NetworkLayer nextLayer = this.layers.size() == this.currentLayerStep + 1 ? null
				: this.layers.get(this.currentLayerStep + 1);

		if (currentLayer != null) {
			currentLayer.summate();
			currentLayer.activate(this.currentLayerStep == 0 ? ActivationFunctionEnum.Copy : this.function);
			setInputsFromOutputs(currentLayer, nextLayer);
			this.currentLayerStep++;
		}
	}

	private void setInputsFromOutputs(NetworkLayer currentLayer, NetworkLayer nextLayer) {
		if (nextLayer != null) {
			for (int i = 0; i < currentLayer.getNeurons().size(); i++) {
				double currentOutput = currentLayer.getNeurons().get(i).getOutput();
				for (ArtificialNeuron nextNeuron : nextLayer.getNeurons()) {
					boolean neuronInputExists = (i + 1) <= nextNeuron.getInputs().size();
					if (neuronInputExists) {
						nextNeuron.getInputs().get(i).input = currentOutput;
					} else {
						nextNeuron.getInputs().add(new ArtificialNeuronInput(String.valueOf(i), currentOutput,
								nextLayer.generateRandom()));
					}
				}

			}
		}
	}

	public void backPropagation(List<Double> expectedResult) {
		for (this.currentLayerStep = this.layers.size() - 1; this.currentLayerStep > 0; this.currentLayerStep--) {
			NetworkLayer currentLayer = this.getLayers().get(this.currentLayerStep);
			for (int neuronIndex = 0; neuronIndex < currentLayer.getNeurons().size(); neuronIndex++) {
				ArtificialNeuron neuronCurrentLayer = currentLayer.getNeurons().get(neuronIndex);
				Double error = setNeuronError(expectedResult, neuronIndex, neuronCurrentLayer);

				List<ArtificialNeuronInput> inputsNeuronCurrentLayer = neuronCurrentLayer.getInputs();
				for (int j = 0; j < inputsNeuronCurrentLayer.size(); j++) {
					ArtificialNeuronInput inputAndWeight = inputsNeuronCurrentLayer.get(j);
					Double previousWeight = inputAndWeight.weight;
					Double previousOutput = inputAndWeight.input;
					inputAndWeight.weight = previousWeight + this.learningRate * previousOutput * error;
					neuronCurrentLayer.updateInput(currentLayerStep, inputAndWeight.input, inputAndWeight.weight);
				}
			}
		}
	}

	private Double setNeuronError(List<Double> expectedResult, int neuronIndex, ArtificialNeuron neuronCurrentLayer) {
		Double followingError, currentOutput = neuronCurrentLayer.getOutput();
		boolean isLastLayer = this.currentLayerStep == this.layers.size() - 1;
		if (isLastLayer) { // Last Layer
			Double errorFactor = expectedResult.get(neuronIndex) - currentOutput;
			followingError = currentOutput * (1 - currentOutput) * errorFactor;
			neuronCurrentLayer.setError(followingError);
		} else { // Intermediate Layers
			Double errorFactor = 0.0;
			followingError = 0.0;
			NetworkLayer nextLayer = this.getLayers().get(this.currentLayerStep + 1);
			for (ArtificialNeuron neuronNextLayer : nextLayer.getNeurons()) {
				Double w = neuronNextLayer.getInputs().stream()
						.filter((input) -> input.id.equals(String.valueOf(neuronIndex))).collect(Collectors.toList())
						.get(0).weight;
				errorFactor = errorFactor + (neuronNextLayer.getError() * w);
			}
			followingError = neuronCurrentLayer.getOutput() * (1 - neuronCurrentLayer.getOutput()) * errorFactor;
			neuronCurrentLayer.setError(followingError);
		}
		return followingError;
	}

}
