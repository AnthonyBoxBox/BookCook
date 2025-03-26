
public class Przepis {
	protected int _iloscSkladnikow;


	protected String _nazwaPrzepisu;
	protected String _instrukcja;



	public Przepis(int iloscSkladnikow, String instrukcja, String nazwaPrzepisu) {
		this._iloscSkladnikow = iloscSkladnikow;
		this._instrukcja = instrukcja;
		this._nazwaPrzepisu = nazwaPrzepisu;
	}

	public void wypiszPrzepis() {
        System.out.println(_nazwaPrzepisu );
        System.out.println("Ilosc potrzebnych skladnikow:" + _iloscSkladnikow);
        System.out.println(_instrukcja);
		System.out.println("");
	}



}
