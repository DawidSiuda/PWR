/*
 *  Program do obliczania pierwiastków
 *  równania kwadratowego
 *
 *  Autor: Pawe³ Rogalinski
 *   Data: 1 paŸdziernika 2017 r.
 */

/**
 * Klasa reprezentuje równania kwadratowe postaci: <br>
 * <center>A x^2 + B x + C = 0.</center>
 *
 * Program demonstruje nastêpuj¹ce zagadnienia:
 * <ul>
 * <li>definiowanie prostej klasy zawieraj¹cej kilka atrybutów i metood,
 * <li>wyœwietlanie danych ró¿nych typów w oknie konsoli,</li>
 * <li>wykorzystywanie klasy <code>ConsoleUserDialog</code> 
 *     do wprowadzania danych.</li>
 * </ul>
 *
 * @author Pawe³ Rogaliñski
 * @version 1 paŸdziernika 2017 r.
 */
public class QuadraticEquation {

	/**
     * Metoda uruchamia program, który prosi u¿ytkownika o wprowadzenie
     * parametrów równania kwadratowego, a nastêpnie oblicza i wyœwietla 
     * pierwiastki tego równania.
     *
     * @param args tablica parametrów z lini poleceñ, przekazanych 
     * przy uruchamianiu programu do wirtualnej maszyny javy.
     */
    public static void main(String[] args) {
    	ConsoleUserDialog user_dialog = new ConsoleUserDialog();
        user_dialog.printInfoMessage("Demonstracja\n"+
        		             "Program oblicza pierwiastki równania kwadratowego\n" +
                             "     A x^2 + B x + C = 0\n" +
        		             "\nAutor: Pawe³ Rogaliñski");
    	QuadraticEquation equation = new QuadraticEquation();
        equation.enterEquation(user_dialog);
        try{
        	 equation.calculateRoots();
           } catch(IllegalStateException e)
           {
               user_dialog.printErrorMessage("UWAGA:\n Wspó³czynnik 'A' równania kwadratowego musi byc ró¿ny od zera!");
               System.exit(1);
           }
        equation.printRoots(user_dialog);
        user_dialog.printInfoMessage("KONIEC PROGRAMU");
        System.exit(0);
    }

    
	/**
	 * wspó³czynnik A.
	 *
	 * <p>
	 * <b>Uwaga:</b> Wspó³czynnik A musi byæ ró¿ny od 0.
	 * </p>
	 */
	private double a;

	/** wspó³czynnik B. */
	private double b;
	
	/** wspó³czynnik C. */
	private double c;

	/**
	 * wyró¿nik równania kwadratowego.
	 *
	 * <br><b>Uwaga:</b> Od wartoœci wyró¿nika zale¿y liczba pierwiastków równania
	 * kwadratowego:
	 * <ul>
	 * <li><code> delta &gt; 0 </code> - istniej¹ dwa pierwiastki
	 * <code> x1, x2.</code></li>
	 * <li><code> delta = 0 </code> - istnieje jeden (podwójny) pierwiastek
	 * <code> x1, </code></li>
	 * <li><code> delta &lt; 0 </code> - brak pierwiastków.</li>
	 * </ul>
	 */
	private double delta;

	/**
	 * pierwszy pierwiastek.
	 *
	 * <p><b>Uwaga:</b> Pierwiastek istnieje tylko jeœli 
	 * <code>delta</code> jest wiêksza lub równa zero.</p>
	 */
	private double x1;

	/**
	 * drugi pierwiastek.
	 *
	 * <p><b>Uwaga:</b> Pierwiastek istnieje tylko jeœli 
	 * <code>delta</code> jest wiêksza od zera.</p>
	 */
	private double x2;

	/**
	 * Metoda zwraca postaæ równania kwadratowego w formie tekstowej.
	 *
	 * <p><b>Uwaga:</b> Metoda jest wywo³ywana niejawnie, jeœli zachodzi potrzeba
	 * przedstawienia równania w formie tekstowej.
	 * @return tekstowa forma równania kwadratowego.
	 */
	public String toString() {
		String equation = a + " x^2 + " + b + " x + " + c + " = 0";
		return equation;
	}

	/**
	 * Metoda oblicza pierwiastki równania kwadratowego.
	 *
	 * @exception IllegalStateException
	 *            wyj¹tek zg³aszany gdy wspó³czynnik A jest równy zero.
	 */
	public void calculateRoots() throws IllegalStateException {
		if (a == 0)
			throw new IllegalStateException("Parametr a nie mo¿e byc równy zero.");
		delta = calculateDiscriminant ();
		if (delta > 0) {
			x1 = (-b - Math.sqrt(delta)) / (2 * a);
			x2 = (-b + Math.sqrt(delta)) / (2 * a);
		} else if (delta == 0) {
			x1 = -b / (2 * a);
		}
	}

	/**
	 * Metoda oblicza wyró¿nik równania kwadratowego.
	 *
	 * @return Wyró¿nik.
	 */
	public double calculateDiscriminant() {
		return b * b - 4 * a * c;
	}

	/**
	 * Metoda umo¿liwia wprowadzenie wspó³czynników równania.
	 * 
	 * @param dialog referencja do obiektu klasy <code>ConsoleUserDialog</code>
	 *        wykorzystywanego do realizacji dialogu z u¿ytkownikiem.
	 */
	public void enterEquation(ConsoleUserDialog dialog) {
		a = dialog.enterDouble("WprowadŸ wspó³czynniki równania\n Podaj A: ");
		b = dialog.enterDouble("WprowadŸ wspó³czynniki równania\n Podaj B: ");
		c = dialog.enterDouble("WprowadŸ wspó³czynniki równania\n Podaj C: ");
	}

	/**
	 * Metoda wyœwietla pierwiastki.
	 *
	 * @param dialog referencja do obiektu klasy <code>ConsoleUserDialog</code>
	 *        wykorzystywanego do realizacji dialogu z u¿ytkownikiem.
	 */
	public void printRoots(ConsoleUserDialog dialog) {
		String message;
		message = "Równanie: " + this + "\n    Delta = " + delta;
		if (delta > 0) {
			message = message + "\nRównanie ma dwa pierwiastki:" + "\n     x1 = " + x1 + "\n     x2 = " + x2;
		} else if (delta == 0) {
			
			message = message + "\nRównanie ma jeden pierwiastek:" + "\n     x1 = " + x1;
		} else
			message = message + "\nRównanie nie ma pierwiastków.";
		dialog.printInfoMessage("Rozwi¹zanie:\n" + message);
	}

} // koniec klasy QuadraticEquation
