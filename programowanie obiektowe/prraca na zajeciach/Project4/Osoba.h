#pragma once

#include <iostream>
#include <string> 

#include "ksiazka.h"

using namespace std;

class Osoba
{
private:
	string imie;
	string nazwisko;
	Ksiazka spis_ksiazek[2];
	static int ile_jest_osob; 
	int *coss;
	const int numer_buta;
public:

	void dodaj_ksiazke(Ksiazka *ksiazka, int ktora);
	static void podaj_ile_jest_osob();

	Osoba(string name = "Jan", string surname = "Kowalski", int nr_but = 44);
	Osoba(Osoba&);
	~Osoba();

	friend void wypisz_prywatne_dane(Osoba);
};
