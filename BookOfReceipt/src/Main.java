import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Exdb expo = new Exdb();
		Wyb wy = new Wyb();
		UserManager userManager = new UserManager();
		Scanner scan = new Scanner(System.in);
		User zalogowany = null;
		Connection conn = Database.connect();

		ArrayList<String> opcje = new ArrayList<>();
		opcje.add("2. Wyświetl przepisy");
		opcje.add("3. Dodaj przepis do ulubionych");
		opcje.add("4. Pokaż ulubione przepisy");
		opcje.add("5. Dodaj nowy przepis");
		opcje.add("6. Wyloguj");
		opcje.add("7. Wyjdź");

		boolean dziala = true;

		while (dziala) {
			while (userManager.getZalogowanyUserId() == -1) {
				System.out.println("=== MENU ===");
				System.out.println("1. Zaloguj się");
				System.out.println("2. Zarejestruj się");
				System.out.println("3. Wyjdź");
				System.out.print("Wybierz opcję: ");

				int wybor = scan.nextInt();
				scan.nextLine();

				switch (wybor) {
					case 1:
						if (userManager.getZalogowanyUserId() == -1) {
							userManager.zaloguj();
						}
						break;
					case 2:
						UserManager.zarejestruj(conn, scan);
						break;
					case 3:
						System.out.println("Zamykanie programu...");
						return;
					default:
						System.out.println("Niepoprawny wybór.");
				}
			}

			System.out.println("\n===== MENU =====");
			for (String opcja : opcje) {
				System.out.println(opcja);
			}
			System.out.print("Wybierz opcję: ");
			int wybor = scan.nextInt();
			scan.nextLine();

			switch (wybor) {
				case 2 -> {
					System.out.println("\n===== LISTA PRZEPISÓW =====");
					expo.getAll();
				}
				case 3 -> {
					if (userManager.getZalogowanyUserId() == -1) {
						System.out.println("Musisz się najpierw zalogować!");
						break;
					}
					System.out.println("\n===== LISTA PRZEPISÓW =====");
					expo.getAll();
					System.out.print("Podaj ID przepisu do dodania do ulubionych: ");
					int przepisId = scan.nextInt();
					expo.dodajDoUlubionych(userManager.getZalogowanyUserId(), przepisId);
				}
				case 4 -> {
					if (userManager.getZalogowanyUserId() == -1) {
						System.out.println("Musisz się najpierw zalogować!");
						break;
					}
					System.out.println("\n===== TWOJE ULUBIONE PRZEPISY =====");
					expo.wyswietlUlubione(userManager.getZalogowanyUserId());
				}
				case 5 -> {
					if (userManager.getZalogowanyUserId() == -1) {
						System.out.println("Musisz się najpierw zalogować!");
						break;
					}
					DodajPrzepis dodajPrzepis = new DodajPrzepis(conn, userManager.getZalogowanyUserId());
					dodajPrzepis.dodajNowyPrzepis();
				}
				case 6 -> {
					if (userManager.getZalogowanyUserId() == -1) {
						System.out.println("Nie jesteś zalogowany!");
					} else {
						userManager.wyloguj();
					}
				}
				case 7 -> {
					dziala = false;
					System.out.println("Zamykanie programu...");
				}
				default -> System.out.println("Niepoprawna opcja. Spróbuj ponownie.");
			}
		}
	}
}
