#include <iostream>	
#include <windows.h>

#include "Osoba.h"
#include "Ksiazka.h"

using namespace std;

Ksiazka Biblioteka[10];
Osoba Jan("Jan", "Kowalski");
Osoba Stanislaw("Stanislaw", "Nowak");

void wypisz_prywatne_dane(Ksiazka, Osoba);

void main()
{
	Stanislaw.dodaj_ksiazke(&Biblioteka[2], 1);
	Stanislaw.dodaj_ksiazke(&Biblioteka[4], 0);

	Jan.dodaj_ksiazke(&Biblioteka[5], 1);
	Jan.dodaj_ksiazke(&Biblioteka[1], 0);

	Ksiazka::podaj_ile_jest_ksiazek();
	Osoba::podaj_ile_jest_osob();

	wypisz_prywatne_dane(Jan);

	wypisz_prywatne_dane(Stanislaw);
	
	//////////////////////////////////////////////////////////
	//konstruktor kopiujacy
	Osoba harry_poter(Jan);

	system("pause");

	return;
}

void wypisz_prywatne_dane(Osoba osoba) 
{
	cout << endl;
	cout << osoba.imie << " " << osoba.nazwisko << " wypozyczyl: \n-"
		<< Biblioteka[1].tytul << " autor: "
		<< Biblioteka[1].imie_autora << " "
		<< Biblioteka[1].nazwisko_autora << endl;

	cout <<"-"<< Biblioteka[1].tytul << " autor: "
		<< Biblioteka[1].imie_autora << " "
		<< Biblioteka[1].nazwisko_autora << endl;
	cout << endl;
	return;
}