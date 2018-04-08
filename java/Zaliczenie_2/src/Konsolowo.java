import java.io.IOException;
import java.util.Scanner;

/*
 * Program: Aplikacja działąjąca w oknie konsoli, która umożliwia testowanie
 *          operacji wykonywanych na obiektach klasy Person.
 *    Plik: Konsolowo.java
 *
 *   Autor: Dawid Siuda
 *    Data: październik 2017 r.
 */


public class Konsolowo
{
    private static final String GEETING_MESSAGE =
                    "Autor: Dawid Siuda\n" +
                    "Data:  pażdziernik 2017 r.\n";

    private static final String MENU =
            "    M E N U   G £ Ó W N E  \n" +
                    "1 - Podaj dane nowej osoby \n" +
                    "2 - Usun dane osoby        \n" +
                    "3 - Modyfikuj dane osoby   \n" +
                    "4 - Wczytaj dane z pliku   \n" +
                    "5 - Zapisz dane do pliku   \n" +
                    "6 - Pokaż dane osoby      \n" +
                    "0 - Zakoncz program        \n";

    Person person;

    public static void main(String[] arg) throws IOException
    {
        Konsolowo currentInstance = new Konsolowo();
    }
    private Konsolowo() throws IOException {
        char pointer;
        String error = null;
        Scanner s = new Scanner(System.in);

        do
        {
            System.out.println(GEETING_MESSAGE);
            System.out.println("\n" + MENU);
            if(error == null || error.equals(""))
            {
                System.out.println("Wybierz opcje:");
            }
            else
            {
                System.out.println(error);
                System.out.println("Wybierz opcje:");
            }
            error = null;

            String str = s.nextLine();
            if(str == null || str.equals(""))
               continue;

            pointer =  str.charAt(0);
            switch(pointer)
            {
                case '1':
                    person = createPerson();
                    break;
                case '2':
                    clearScreen();
                    person = null;
                    error = "OSOBA ZOSTAŁA USUNIĘTA";
                    break;
                case '3':
                    person = this.editPerson(person);
                    break;
                case '4':
                        clearScreen();
                        System.out.println("Podaj ścieszke do pliku:");
                        str = s.nextLine();
                        person = Person.readFromFile(str,null);
                        if(person == null)
                        {
                            error = " BŁĄD: Błąd podczas wczytywania,\nsprawdź poprawność adresu pliku";
                        }
                        else
                        {
                            error = "PLIK WCZYTANO POMYŚLNIE";
                        }

                    break;
                case '5':
                    clearScreen();
                    System.out.println("Podaj ścieszke do pliku:");
                    str = s.nextLine();
                    try
                    {
                        Person.writeToFile(str, null, person);
                    }
                    catch(Exception e)
                    {
                        error = e.getMessage();
                    }
                    break;
                case '6':this.showPerson(person);
                    break;
                case '0':
                    return;
                default: error = "NIEZNANA AKCJA";

            }
            Konsolowo.clearScreen();
        }while(true);
    }

    private static void clearScreen()
    {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }
    private Person createPerson()
    {
        Person person = new Person();
        person = editPerson(person);
        return person;
    }
    private Person editPerson(Person person)
    {
        Scanner s = new Scanner(System.in);
        String str;
        if(person == null)
        {
            clearScreen();
            System.out.println("Musisz najpierw stworzyc osobe aby móc ją edytować. " +
                               "\n Naciśnij Enter aby przejsc do głównego menu");
            str = s.nextLine();
            return null;
        }
        else
        {
            clearScreen();
            System.out.println("Podaj Imie:");
            str = s.nextLine();
            person.setFirstname(str);

            clearScreen();
            System.out.println("Podaj Nazwisko:");
            str = s.nextLine();
            person.setSecondname(str);

            clearScreen();
            do
            {
                int wiek;
                System.out.println("Podaj wiek:");
                str = s.nextLine();
                try{
                    wiek = Integer.parseInt(str);
                }
                catch(Exception e)
                {
                    clearScreen();
                    System.out.println("Zły format wieku:");
                    continue;
                }
                person.setAge(wiek);
                break;
            }while(true);

            clearScreen();
            do
            {
                char plec;
                System.out.println("Podaj płeć: \nm -Mężczyzna\nk -Kobieta\nn -Nieznana");
                str = s.nextLine();
                if(str.equals("n"))
                {
                    person.setGender(Gender.UNKNOWN);
                    break;
                }
                else if(str.equals("m"))
                {
                    person.setGender(Gender.MALE);
                    break;
                }else if(str.equals("k"))
                {
                    person.setGender(Gender.FEMALE);
                    break;
                }else {
                    clearScreen();
                    System.out.println("PODANO BŁEDNĄ WARTOŚĆ");
                }

            }while(true);

            return person;
        }

    }
    private void showPerson(Person person)
    {
        String error = null;
        Scanner z = new Scanner(System.in);
        if(person != null)
        {
            Konsolowo.clearScreen();
            System.out.println("Imie:     "+person.getFirstname()+
                             "\nNazwisko: "+person.getSecondname()+
                             "\nWiek:     "+person.getAge()+
                             "\nPłeć:     "+person.getGender());

            System.out.println("\n Naciśnij Enter kontynuować ");
            String str = z.nextLine();
        }
        else
        {
            Konsolowo.clearScreen();
            System.out.println("Brak zdefiniowanej osoby \n Naciśnij Enter kontynuować ");
            String str = z.nextLine();

        }

    }



}
