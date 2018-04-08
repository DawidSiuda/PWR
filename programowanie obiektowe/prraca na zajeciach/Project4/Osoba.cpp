#include "Osoba.h"

int Osoba::ile_jest_osob = 0;

void Osoba::dodaj_ksiazke(Ksiazka *ksiazka, int ktora)
{
	spis_ksiazek[ktora] = *ksiazka;
}

void Osoba::podaj_ile_jest_osob() 
{
	cout << "Zdefiniowanych zostalo " << ile_jest_osob << " osob." << endl;
}

Osoba::Osoba(string name, string surname, int nr_but):numer_buta(nr_but)
{
	imie = name;
	nazwisko = surname;

	ile_jest_osob++;
	coss = new int;
}

Osoba::Osoba(Osoba & rodzic):numer_buta(rodzic.numer_buta)
{
	imie = rodzic.imie;
	nazwisko = rodzic.nazwisko;
	
	coss = new int;
}

Osoba::~Osoba()
{
	ile_jest_osob--;
	delete coss;
}
