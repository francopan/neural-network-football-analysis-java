package neuralnetwork.models;

import neuralnetwork.enums.ActivationFunctionEnum;

public class ActivationFunction {

	public static double apply(ActivationFunctionEnum function, Double value) {
		switch (function) {
		case ReLu: {
			return rectifiedLinearFunction(value);
		}
		case Sigmoid:
		default: {
			return sigmoid(value);
		}
		}
	}

	private static double rectifiedLinearFunction(double x) {
		return Math.max(0, x);
	}

	private static double sigmoid(double x) {
		return 1 / (1 + Math.exp(-x));
	}

}
