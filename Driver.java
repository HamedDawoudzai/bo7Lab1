import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
public class Driver {
    public static void main(String[] args)throws IOException{
        
        double[] A1 = {1, 2, 3};
        int[] exp1 = {1, 2, 3};
        Polynomial poly1 = new Polynomial(A1, exp1);
        
        double[] A2 = {2, 3, 1};
        int[] exp2 = {1, 2, 3};
        Polynomial poly2 = new Polynomial(A2, exp2);

        double[] A3 = {3, 4, 5};
        int[] exp3 = {1, 2, 3};
        Polynomial poly3 = new Polynomial(A3, exp3);

        double[] A4 = {1, 2, 3};
        int[] exp4 = {0, 1, 2};
        Polynomial poly4 = new Polynomial(A4, exp4);

        double[] A5 = {2, 3, 1};
        int[] exp5 = {0, 1, 2};
        Polynomial poly5 = new Polynomial(A5, exp5);

        double[] A6 = {3, 4, 5};
        int[] exp6 = {0, 1, 2};
        Polynomial poly6 = new Polynomial(A6, exp6);

        System.out.println("Test Add:");
        Polynomial added1 = poly1.add(poly2);
        System.out.println("Our output: " + Arrays.toString(added1.coefficients));
        System.out.println("Expected output: [3.0, 5.0, 4.0]");
        System.out.println();

        Polynomial added3 = poly5.add(poly6);
        System.out.println("Our output: " + Arrays.toString(added3.coefficients));
        System.out.println("Expected output: [5.0, 7.0, 6.0]");
        System.out.println();

        System.out.println("Test Multiply:");
        Polynomial multiplied1 = poly1.multiply(poly2);
        System.out.println("Our output: " + Arrays.toString(multiplied1.coefficients));
        System.out.println("Expected output: [2.0, 7.0, 13.0, 11.0, 3.0]");
        System.out.println();

        Polynomial multiplied2 = poly3.multiply(poly4);
        System.out.println("Our output: " + Arrays.toString(multiplied2.coefficients));
        System.out.println("Expected output: [3.0, 10.0, 22.0, 22.0, 15.0]");
        System.out.println();

        Polynomial multiplied3 = poly5.multiply(poly6);
        System.out.println("Our output: " + Arrays.toString(multiplied3.coefficients));
        System.out.println("Expected output: [6.0, 17.0, 25.0, 19.0, 5.0]");
        System.out.println();
        
        System.out.println("Test files:");
        poly1.saveToFile("output.txt");
		System.out.println("Successfully written to output.txt");
		System.out.println();
		System.out.println("Reading from output.txt");
		try (BufferedReader reader = new BufferedReader(new FileReader("output.txt"))) {
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println("Our output: " + line);
			}
		} catch (IOException e) {
			System.err.println("Error reading the file: " + e.getMessage());
		}
		System.out.println("Expected output: 1.0x+2.0x2+3.0x3");
    }
}