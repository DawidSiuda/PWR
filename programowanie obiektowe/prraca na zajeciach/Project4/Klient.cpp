#include "Klient.h"



Klient::Klient(int Wiek, string zaint, string name, string surname, int nr_but)
	:Osoba::Osoba(name, surname, nr_but)
{
	wiek = Wiek;
	zainteresowania = zaint;
}


Klient::~Klient()
{
}
