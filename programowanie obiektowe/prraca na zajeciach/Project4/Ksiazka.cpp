#include "Ksiazka.h"

int Ksiazka::ile_jest_ksiazek = 0;
const string Ksiazka::nazwa_biblioteki = "Biblioteka Glowna";

void Ksiazka::wczytaj()
{
	cout << "----------" << endl;
	cout << " podaj tytul ksiazki: ";
	cin >> tytul;
	cout << " podaj imie autora ksiazki: ";
	cin >> imie_autora;
	cout << " podaj nazwisko autora ksiazki: ";
	cin >> nazwisko_autora;
	cout << " podaj rok wydania ksiazki: ";
	cin >> rok_wydania;
	cout << " podaj nr wydania ksiazki: ";
	cin >> nr_wydania;

}

void Ksiazka::wypisz()
{
	cout << "----------" << endl;
	cout << " tytul: " << tytul << endl;
	cout << " imie autora : " << imie_autora << endl;
	cout << " nazwisko autora ksiazki: " << nazwisko_autora << endl;
	cout << " rok wydania ksiazki: " << rok_wydania << endl;
	cout << " nr wydania ksiazki: " << nr_wydania << endl;
}

void Ksiazka::podaj_ile_jest_ksiazek() 
{
	cout << "Zdefiniowanych zostalo " << ile_jest_ksiazek << " ksiazek." << endl;
}

Ksiazka::Ksiazka(string Tytul, string Imie_autora, string Nazwisko_autora, int Rok_wydania, int Nr_wydania)
{
	tytul = Tytul;
	imie_autora = Imie_autora;
	nazwisko_autora = Nazwisko_autora;
	rok_wydania = Rok_wydania;
	nr_wydania = Nr_wydania;

	ile_jest_ksiazek++;
}


Ksiazka::~Ksiazka()
{	
	ile_jest_ksiazek--;
}
