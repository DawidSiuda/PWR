#include "Pracownik.h"



Pracownik::Pracownik(int staw, string name,string surname, int nr_but ) 
	:Osoba::Osoba( name, surname, nr_but)
{
	stawka_na_godz = staw;
}


Pracownik::~Pracownik()
{
}
