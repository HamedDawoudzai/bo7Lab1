public class Polynomial {
    double[] list_of_coefficients;
    public Polynomial() {
        list_of_coefficients = new double[]{0};
    }
    public Polynomial(double[] coefficients) {
        this.list_of_coefficients = coefficients;
    }
    public Polynomial add(Polynomial other) {
        int maxLength = Math.max(this.list_of_coefficients.length, other.list_of_coefficients.length);
        double[] updated_list_of_coeff = new double[maxLength];
        for (int i = 0; i < other.list_of_coefficients.length; i++) {
            updated_list_of_coeff[i] = updated_list_of_coeff[i] + other.list_of_coefficients[i];
        }

        for (int i = 0; i < this.list_of_coefficients.length; i++) {
            updated_list_of_coeff[i] = updated_list_of_coeff[i] + this.list_of_coefficients[i];
        }
	return new Polynomial(updated_list_of_coeff);
    }
    
    public double evaluate(double x) {
        double result_of_total_sum = 0;
        for (int i = 0; i < list_of_coefficients.length; i++) {
            result_of_total_sum = result_of_total_sum + list_of_coefficients[i] * Math.pow(x, i);
        }
        return result_of_total_sum;
    }
    
    public boolean hasRoot(double x) {
        return evaluate(x) == 0;
    }
}