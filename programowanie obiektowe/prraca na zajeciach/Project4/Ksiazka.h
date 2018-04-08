#pragma once

#include <iostream>
#include <string>
 
extern class Osoba;// mowi ze dana klasa gdzies istnieje w programie

using namespace std;

class Ksiazka
{
private:
	string tytul;
	string imie_autora, nazwisko_autora;
	int rok_wydania, nr_wydania;
	
	static int ile_jest_ksiazek;
	

public:

	static const string nazwa_biblioteki;
	static void podaj_ile_jest_ksiazek();
	void wczytaj();
	void wypisz();

	Ksiazka(string Tytul = "nieznany",
		string Imie_autora = "nieznany",
		string Nazwisko_autora = "nieznane",
		int Rok_wydania = 0,
		int Nr_wydania = 0);
	~Ksiazka();

	friend void wypisz_prywatne_dane(Osoba);
};
