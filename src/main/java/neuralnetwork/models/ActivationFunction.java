package neuralnetwork.models;

import neuralnetwork.enums.ActivationFunctionEnum;

public class ActivationFunction {

	public static double apply(ActivationFunctionEnum function, Double value) {
		switch (function) {
		case ReLu: {
			return rectifiedLinearFunction(value);
		}
		case Sigmoid: {
			return sigmoid(value);
		}
		case Copy:
		default: {
			return copy(value);
		}
		}
	}

	private static double rectifiedLinearFunction(double x) {
		return Math.max(0.0, x);
	}

	private static double sigmoid(double x) {
		return 1 / (1 + Math.exp(-x));
	}

	private static double copy(double x) {
		return x;
	}

}
