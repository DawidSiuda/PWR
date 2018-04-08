/*
 * Program: Prosta biblioteka metod do realizacji dialogu z u¿ytkownikiem
 *          w prostych aplikacjach bez graficznego interfejsu u¿ytkownika.
 *    Plik: ConsoleUserDialog.java
 *          
 *   Autor: Pawe³ Rogalinski
 *    Data: pazdziernik 2017 r.
 *
 */

import java.util.Scanner;

/**
 * Klasa <code> ConsoleUserDialog </code> implementuje pomocnicze
 * metody do tworzenia w programie tekstowego interfejsu u¿ytkownika.
 * 
 * Do realizacji dialogu z u¿ytkownikiem wykorzystywane s¹ standardowe
 * strumienie wejœcia <code>System.in</code>, wyjœcia <code> System.out </code> 
 * oraz b³êdów <code> System.err </code>. 
 * 
 * Program demonstruje nastêpuj¹ce zagadnienia:
 * <ul>
 *  <li> wyœwietlanie komunikatów tekstowych w oknie konsoli, </li>
 *  <li> czytanie w oknie konsoli danych ró¿nych typów ze standardowego
 *       strumienia wejsciowego za pomoc¹ klasy <code> Scanner </code></li>
 *  <li> konwersjê obiektów klasy <code>String</code> na znaki lub liczby
 *       typu <code>char, int, double</code>,
 *  <li> obs³ugê wyj¹tków przy konwersji danych</li>
 * </ul>
 *
 * @author Pawel Rogaliñski
 * @version 1 paŸdziernik 2017
 */

public class ConsoleUserDialog {
	
	/** Komunikat o b³êdnym formacie wprowadzonych danych. */
	private final String ERROR_MESSAGE = "Nieprawid³owe dane!\nSpróbuj jeszcze raz.";

	/** Pomocniczy obiekt klasy <code> Scanner </code> 
	 *  do czytania danych w oknie konsoli.
	 *  
	 *  Domyœlnie <code>scanner</code> pod³¹czony jest do standardowego 
	 *  strumienia wejœciowego.
	 */
	private Scanner scanner = new Scanner(System.in);

		
	/**
	 * Metoda drukuje komunikat do standardowego strumienia wyjœciowego.
	 * 
	 * @param message tekst komunikatu.
	 */
	public void printMessage(String message) {
			System.out.println(message);
		}
		
		
	/**
	 * Metoda drukuje komunikat do standardowego strumienia wyjœciowego
	 * i czeka na potwierdzenie od u¿ytkownika.
	 * 
	 * Po wydrukowaniu komunikatu program jest wstrzymywany do momentu
	 * naciœniêcia klawisza ENTER.
	 * 
	 * @param message tekst komunikatu.
	 */	
	public void printInfoMessage(String message) {
			System.out.println(message);
			enterString("Nacisnij ENTER");
		}
		
		
	/**
	 * Metoda drukuje komunikat do standardowego strumienia b³êdów
	 * i czeka na potwierdzenie od u¿ytkownika.
	 * 
	 * Po wydrukowaniu komunikatu program jest wstrzymywany do momentu
	 * naciœniêcia klawisza ENTER.
	 * @param message tekst komunikatu.
	 */	
	public void printErrorMessage(String message) {
			System.err.println(message);
			System.err.println("Nacisnij ENTER");
			enterString("");
		}
		
		/**
	     * Metoda czyœci konsolê tekstow¹.
	     * 
	     * Metoda faktycznie drukuje trzy puste linie, które
	     * tworz¹ dodatkowy odstêp przed kolejnymi komunikatami. 
	     */
		public void clearConsole(){
			System.out.println("\n\n");
		}

		/**
	     * Metoda umo¿liwia u¿ytkownikowi wprowadzenie ³añcucha znaków.
	     *
	     * @param prompt tekst zachêty do wprowadzania danych.
	     * @return obiekt reprezentuj¹cy wprowadzony ci¹g znaków.
	     */
		public String enterString(String prompt) {
			System.out.print(prompt);
			return scanner.nextLine();
		}
		
		/**
	     * Metoda umo¿liwia u¿ytkownikowi wprowadzenie pojedyñczego znaku.
	     *
	     * Metoda faktycznie czyta ca³y ³añcuch znaków, z którego
	     * wybierany jest tylko pierwszy znak.
	     * @param prompt tekst zachêty do wprowadzania danych.
	     * @return wprowadzony znak.
	     */ 
		public char enterChar(String prompt) {
			boolean isError;
			char c = ' ';
			do {
				isError = false;
				try {
					c = enterString(prompt).charAt(0);
				} catch (IndexOutOfBoundsException e) {
					System.err.println(ERROR_MESSAGE);
					isError = true;
				}
			} while (isError);
			return c;
		}

		/**
	     * Metoda umo¿liwia u¿ytkownikowi wprowadzenie liczby ca³kowitej.
	     *
	     * Metoda faktycznie czyta ca³y ³añcuch znaków, który
	     * nastêpnie jest kowertowany na liczbê ca³kowit¹.
	     * @param prompt tekst zachêty do wprowadzania danych.
	     * @return wprowadzona liczba.
	     */
		public int enterInt(String prompt) {
	        boolean isError;
	        int i = 0;
	        do{
	            isError = false;
	            try{ 
	                i = Integer.parseInt(enterString(prompt));
	            } catch(NumberFormatException e){
	            	System.err.println(ERROR_MESSAGE);
	            	isError = true;
	            }
	        }while(isError);
	        return i;
	    }
		
		/**
	     * Metoda umo¿liwia u¿ytkownikowi wprowadzenie liczby rzeczywistej.
	     *
	     * Metoda faktycznie czyta ca³y ³añcuch znaków, który
	     * nastêpnie jest kowertowany na liczbê rzeczywist¹.
	     * @param prompt tekst zachêty do wprowadzania danych.
	     * @return wprowadzona liczba.
	     */
		public float enterFloat(String prompt) {
	        boolean isError;
	        float d = 0;
	        do{
	            isError = false;
	            try{
	                d = Float.parseFloat(enterString(prompt));
	            } catch(NumberFormatException e){
	            	System.err.println(ERROR_MESSAGE);
	                isError = true;
	            }
	        } while(isError);
	        return d;
	    }   
		
		/**
	     * Metoda umo¿liwia u¿ytkownikowi wprowadzenie liczby 
	     * rzeczywistej podwójnej precyzji.
	     *
	     * Metoda faktycznie czyta ca³y ³añcuch znaków, który
	     * nastêpnie jest kowertowany na liczbê rzeczywist¹ 
	     * podwójnej precyzji.
	     * @param prompt tekst zachêty do wprowadzania danych.
	     * @return wprowadzona liczba.
	     */
		public double enterDouble(String prompt) {
	        boolean isError;
	        double d = 0;
	        do{
	            isError = false;
	            try{
	                d = Double.parseDouble(enterString(prompt));
	            } catch(NumberFormatException e){
	            	System.err.println(ERROR_MESSAGE);
	                isError = true;
	            }
	        }while(isError);
	        return d;
	    }   
		
} // koniec kasy ConsoleUserDialog



