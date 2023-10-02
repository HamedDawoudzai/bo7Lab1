import java.io.*;
import java.util.*;
import java.util.Arrays;

public class Polynomial {
    public double[] coefficients;
    public int[] exponents;

    public Polynomial() {
        coefficients = new double[0];
        exponents = new int[0];
    }

    public Polynomial(double[] coefficients, int[] exponents) {
    	this.coefficients = coefficients;
    	this.exponents = exponents;
    }

    public Polynomial add(Polynomial other) {
    	int length_of_coeff1 = this.coefficients.length;
        int length_of_coeff2 = other.coefficients.length;
        int totalLength = length_of_coeff1 + length_of_coeff2;

        double[] list_of_updatedCoeffs = new double[totalLength];
        int[] updatedExponents = new int[totalLength];

        int i = 0;
        int k = 0; 
        int j = 0;

        while (i < length_of_coeff1 && j < length_of_coeff2) {
            if (this.exponents[i] == other.exponents[j]) {
                list_of_updatedCoeffs[k] = this.coefficients[i] + other.coefficients[j];
                updatedExponents[k] = this.exponents[i];
                i = i + 1;
                j++;
            } else if (this.exponents[i] < other.exponents[j]) {
                list_of_updatedCoeffs[k] = this.coefficients[i];
                updatedExponents[k] = this.exponents[i];
                i = i + 1;
            } else {
                list_of_updatedCoeffs[k] = other.coefficients[j];
                updatedExponents[k] = other.exponents[j];
                j++;
            }
            k++;
        }
        while (j < length_of_coeff2) {
            list_of_updatedCoeffs[k] = other.coefficients[j];
            updatedExponents[k] = other.exponents[j];
            j++;
            k++;
        }
        while (i < length_of_coeff1) {
            list_of_updatedCoeffs[k] = this.coefficients[i];
            updatedExponents[k] = this.exponents[i];
            i = i + 1;
            k++;
        }
        int[] Fitted_exponents = Arrays.copyOf(updatedExponents, k);
        double[] Fitted_coeffs = Arrays.copyOf(list_of_updatedCoeffs, k);

        return new Polynomial(Fitted_coeffs, Fitted_exponents);
    }

    public double evaluate(double x) {
        double result = 0;
        for (int i = 0; i < coefficients.length; i = i + 1) {
            result += this.coefficients[i] * Math.pow(x, exponents[i]);
        }
        return result;
    }

    public boolean hasRoot(double x) {
        return evaluate(x) == 0;
    }
    
    private int combine_x(double[] coefficients, int[] exponents) {
        int length_of_coeffs = coefficients.length;
        for (int i = 0; i < length_of_coeffs - 1; i++) {
            for (int j = i + 1; j < length_of_coeffs; j++) {
                if (exponents[i] == exponents[j]) {
                    coefficients[i] += coefficients[j];
                    coefficients[j] = 0; 
                }
            }
        }
        int resultLength = 0;
        for (int i = 0; i < length_of_coeffs; i++) {
            if (coefficients[i] != 0) {
                resultLength++;
            }
        }
        double[] newCoefficients = new double[resultLength];
        int[] newExponents = new int[resultLength];
        int newIndex = 0;

        for (int i = 0; i < length_of_coeffs; i++) {
            if (coefficients[i] != 0) {
                newCoefficients[newIndex] = coefficients[i];
                newExponents[newIndex] = exponents[i];
                newIndex++;
            }
        }
        System.arraycopy(newCoefficients, 0, coefficients, 0, resultLength);
        System.arraycopy(newExponents, 0, exponents, 0, resultLength);
        return resultLength;
    }

    private void sort(double[] coefficients, int[] exponents, int length) {
        for (int i = 0; i < length - 1; i++) {
            for (int j = 0; j < length - i - 1; j++) {
                if (exponents[j] > exponents[j + 1]) {
                	int temp1 = exponents[j];
                    exponents[j] = exponents[j + 1];
                    exponents[j + 1] = temp1;
                    
                    double temp = coefficients[j];
                    coefficients[j] = coefficients[j + 1];
                    coefficients[j + 1] = temp;
                }
            }
        }
    }
    
    public Polynomial multiply(Polynomial other) {
    	int length_of_coeffs1 = this.coefficients.length;
        int length_of_coeffs2 = other.coefficients.length;
        int total = length_of_coeffs1 * length_of_coeffs2;
        double[] updated_coeffs = new double[total];
        int[] updated_exponents = new int[total];

        int k = 0;
        int i = 0;
        int j = 0;
        for (i = 0; i < length_of_coeffs1; i++) {
            for (j = 0; j < length_of_coeffs2; j++) {
                updated_coeffs[k] = this.coefficients[i] * other.coefficients[j];
                updated_exponents[k] = this.exponents[i] + other.exponents[j];
                k = k + 1;
            }
        }
        int result = combine_x(updated_coeffs, updated_exponents);
        sort(updated_coeffs, updated_exponents, result);
        double[] trimmedCoefficients = Arrays.copyOf(updated_coeffs, result);
        int[] trimmedExponents = Arrays.copyOf(updated_exponents, result);

        return new Polynomial(trimmedCoefficients, trimmedExponents);
    }

    public Polynomial(File file) throws IOException {
        ArrayList<Double> coeffs_list = new ArrayList<>();
        ArrayList<Integer> exponent_list = new ArrayList<>();

        Scanner scanner = new Scanner(file);
        scanner.useDelimiter("\\s*[+\\-]\\s*");

        while (scanner.hasNext()) {
            String term = scanner.next().trim();
            if (!term.isEmpty()) {
                double coefficient = Double.parseDouble(term);
                int exponent = 0;
                if (scanner.hasNextInt()) {
                    exponent = scanner.nextInt();
                }
                coeffs_list.add(coefficient);
                exponent_list.add(exponent);
            }
        }

        coefficients = new double[coeffs_list.size()];
        exponents = new int[exponent_list.size()];

        for (int i = 0; i < coeffs_list.size(); i = i + 1) {
            coefficients[i] = coeffs_list.get(i);
            exponents[i] = exponent_list.get(i);
        }

        scanner.close();
     }

    public void saveToFile(String fileName) throws IOException {
        FileWriter writer = new FileWriter(fileName);
        for (int i = 0; i < coefficients.length; i = i + 1) {
            double coefficient = coefficients[i];
            int exponent = exponents[i];
            if (i != 0 && coefficient >= 0) {
                writer.write("+");
            }
            writer.write(String.format("%.1f", coefficient));
            if (exponent > 0) {
                writer.write("x");
                if (exponent > 1) {
                    writer.write("" + exponent);
                }
            }
        }
        writer.close();
    }
}