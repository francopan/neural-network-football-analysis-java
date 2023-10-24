package dataset.utils;

import java.util.List;

public class Statistics {

	private Integer[][] confusionMatrix;
	private double accuracy = 0.0;
	private double[] recall = {};
	private double[] precision = {};
	private double[] fMeasure = {};

	public Statistics(List<List<Integer>> matrix) {
		this.setMatrix(matrix);
	}

	public Integer[][] getConfusionMatrix() {
		return confusionMatrix;
	}

	public double getAccuracy() {
		return accuracy;
	}

	public double[] getRecall() {
		return recall;
	}

	public Integer[][] getMatrix() {
		return this.confusionMatrix;
	}

	public double[] getPrecision() {
		return precision;
	}

	public double[] getfMeasure() {
		return fMeasure;
	}

	public void setMatrix(List<List<Integer>> matrix) {
		this.confusionMatrix = this.convertListToArray(matrix);
	}

	public double calculateAccuracy() {

		int correctPredictions = 0;
		int totalPredictions = 0;

		for (int i = 0; i < this.confusionMatrix.length; i++) {
			for (int j = 0; j < this.confusionMatrix[i].length; j++) {
				totalPredictions += this.confusionMatrix[i][j];
				if (i == j) {
					correctPredictions += this.confusionMatrix[i][j];
				}
			}
		}
		this.accuracy = (double) correctPredictions / totalPredictions;
		return this.accuracy;
	}

	public double[] calculateRecall() {
		double[] recall = new double[this.confusionMatrix.length];

		for (int i = 0; i < this.confusionMatrix.length; i++) {
			int truePositives = this.confusionMatrix[i][i];
			int falseNegatives = 0;

			for (int j = 0; j < this.confusionMatrix[i].length; j++) {
				if (i != j) {
					falseNegatives += this.confusionMatrix[i][j];
				}
			}

			recall[i] = (double) truePositives / (truePositives + falseNegatives);
		}
		this.recall = recall;
		return this.recall;
	}

	public double[] calculatePrecision() {
		double[] precision = new double[this.confusionMatrix.length];

		for (int i = 0; i < this.confusionMatrix.length; i++) {
			int truePositives = this.confusionMatrix[i][i];
			int falsePositives = 0;

			for (int j = 0; j < this.confusionMatrix.length; j++) {
				if (i != j) {
					falsePositives += this.confusionMatrix[j][i];
				}
			}

			precision[i] = (double) truePositives / (truePositives + falsePositives);
		}
		this.precision = precision;
		return this.precision;
	}

	public double[] calculateFMeasure(double[] recall, double[] precision) {
		double[] fMeasure = new double[this.confusionMatrix.length];

		for (int i = 0; i < this.confusionMatrix.length; i++) {
			fMeasure[i] = 2 * (recall[i] * precision[i]) / (recall[i] + precision[i]);
		}
		this.fMeasure = fMeasure;
		return this.fMeasure;
	}

	public void printConfusionMatrix() {
		Integer trueValues = 0;
		Integer total = 0;
		for (int i = 0; i < this.confusionMatrix.length; i++) {
			Integer[] row = this.confusionMatrix[i];
			for (int j = 0; j < this.confusionMatrix.length; j++) {
				System.out.print("\t" + row[j]);
				if (j == i) {
					trueValues += row[j];
				}
				total += row[j];
			}
			System.out.print("\n");
		}
	}
	
	public void printStatistics() {
		System.out.println("Accuracy: " + this.getAccuracy());
		System.out.println("Recall HomeWin: " + this.getRecall()[0]);
		System.out.println("Recall Draw: " + this.getRecall()[1]);
		System.out.println("Recall AwayWin: " + this.getRecall()[2]);
		System.out.println("Precision HomeWin: " + this.getPrecision()[0]);
		System.out.println("Precision Draw: " + this.getPrecision()[1]);
		System.out.println("Precision AwayWin: " + this.getPrecision()[2]);
		System.out.println("F-Measure HomeWin: " + this.getfMeasure()[0]);
		System.out.println("F-Measure Draw: " + this.getfMeasure()[1]);
		System.out.println("F-Measure AwayWin: " + this.getfMeasure()[2]);
	}

	private Integer[][] convertListToArray(List<List<Integer>> listMatrix) {
		int rows = listMatrix.size();
		int cols = listMatrix.get(0).size();

		Integer[][] arrayMatrix = new Integer[rows][cols];

		for (int i = 0; i < rows; i++) {
			arrayMatrix[i] = listMatrix.get(i).toArray(new Integer[cols]);
		}

		return arrayMatrix;
	}

}
