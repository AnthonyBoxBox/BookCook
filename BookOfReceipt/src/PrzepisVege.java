public class PrzepisVege extends Przepis {

	public PrzepisVege(int iloscSkladnikow, String instrukcja, String nazwaPrzepisu) {
		super(iloscSkladnikow, instrukcja, nazwaPrzepisu);
	}

	private static final String ansi_green = "\u001B[32m";
	private static final String ansi_reset = "\u001B[0m";
	public void wypiszPrzepis() {
		System.out.println(ansi_green + _nazwaPrzepisu + "\uD83C\uDF32" + ansi_reset  );
		System.out.println("Ilosc potrzebnych skladnikow:" + _iloscSkladnikow );
		System.out.println(_instrukcja );
		System.out.println("");
	}
}

