import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Exdb expo = new Exdb();
		Wyb wy = new Wyb();
		Scanner scan = new Scanner(System.in);


		System.out.println("Wybierz typ przepisu:");
		expo.printType();


		int a = scan.nextInt();


		ArrayList<Przepis> wynik = wy.choT(a);


		if (wynik != null && !wynik.isEmpty()) {
			for (Przepis p : wynik) {
				p.wypiszPrzepis();
			}
		} else {
			System.out.println("Brak przepisów w wybranej kategorii.");
		}
	}
}