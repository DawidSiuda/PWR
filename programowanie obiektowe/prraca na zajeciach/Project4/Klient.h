#pragma once

#include <string> 

#include "Osoba.h"


class Klient : public Osoba
{
	int wiek;
	string zainteresowania;
public:
	Klient(int = 0, string = "brak", string = "Jan", string = "Kowalski", int = 44);
	~Klient();
};

