package dataset.models;

import java.util.List;

public class Statistics {

	private Integer[][] confusionMatrix;

	public Statistics(List<List<Integer>> matrix) {
		this.setMatrix(matrix);
	}
	
	public void setMatrix(List<List<Integer>> matrix) {
		this.confusionMatrix = this.convertListToArray(matrix);
	}
	
	public Integer[][] getMatrix() {
		return this.confusionMatrix;
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

		return (double) correctPredictions / totalPredictions;
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

		return recall;
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

	    return precision;
	}
	
	public double[] calculateFMeasure(double[] recall, double[] precision) {
	    double[] fMeasure = new double[this.confusionMatrix.length];

	    for (int i = 0; i < this.confusionMatrix.length; i++) {
	        fMeasure[i] = 2 * (recall[i] * precision[i]) / (recall[i] + precision[i]);
	    }

	    return fMeasure;
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
