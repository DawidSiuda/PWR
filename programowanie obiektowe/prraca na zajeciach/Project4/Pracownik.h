#pragma once
#include "Osoba.h"



class Pracownik : public Osoba
{
	int stawka_na_godz;
public:
	Pracownik(int = 10, string = "Jan", string = "Kowalski", int = 44);
	~Pracownik();
};

