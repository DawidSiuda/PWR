import javax.swing.*;
import java.awt.*;
import java.io.*;

/*
 *  Program: Operacje na obiektach klasy Person
 *     Plik: Person.java
 *           definicja typu wyliczeniowego Gender
 *           definicja publicznej klasy Person
 *
 *    Autor: Dawid Siuda
 *     Data:  październik 2017 r.
 */

enum Gender
{
    UNKNOWN(1, "-------"),
    MALE(2, "MEZCZYZNA"),
    FEMALE(3, "KOBIETA");

    private int numer;
    private String genderName;

    private Gender(int num, String gender_Name)
    {
        genderName = gender_Name;
        numer = num;
    }

    @Override
    public String toString()
    {
        return genderName;
    }
}

public class Person
{
    private String firstname;
    private String secondname;
    private int age;
    Gender gender;

    public String getFirstname()
    {
        return this.firstname;
    }
    public String getSecondname()
    {
        return this.secondname;
    }
    public String getGender(){
        String gen = gender.toString();
        return gen;};
    public String getAge()
    {
        return String.valueOf(age);
    }

    public void setFirstname(String name)
    {
        this.firstname = name;
    }
    public void setSecondname(String secName)
    {
        this.secondname = secName;
    }
    public void setAge(int age)
    {
        this.age = age;
    }
    public void setGender(Gender typeOfGender)
    {
        gender = typeOfGender;
    }
    public static Person readFromFile(String patch, Window parent) throws IOException {
       try {
           BufferedReader reader = new BufferedReader(new FileReader(new File(patch)));
           String line = reader.readLine();
           String[] txt = line.split("<==>");
           Person person = new Person(txt[0], txt[1], txt[2], txt[3]);
           return person;
       }
       catch (Exception e)
       {
           if(parent != null) {
               JOptionPane.showMessageDialog(parent,
                       "Błędna zawartość pliku",
                       "Błąd",
                       JOptionPane.ERROR_MESSAGE);
               return null;
           }
           else
           {
               return null;
           }
       }
    }
    public static void writeToFile(String patch, Window parent, Person person) throws Exception
    {
        try (PrintWriter writer = new PrintWriter(patch))
        {
            writer.println(person.getFirstname() + "<==>" + person.getSecondname() +
                    "<==>" + person.getAge() + "<==>" + person.getGender());
        } catch (Exception e){
            if(parent != null) {
                JOptionPane.showMessageDialog(parent,
                        "Błąd zapisu pliku",
                        "Błąd",
                        JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                throw new Exception("Wystąpił błąd podczas zzapisu pliku");
            }
        }
    }

    Person(String imie , String nazwisko , int wiek, Gender plec)
    {
        setFirstname(imie);
        setSecondname(nazwisko);
        setAge(wiek);
        setGender(plec);

    }
    Person()
    {
        setFirstname("UNKNOWN");
        setSecondname("UNKNOWN");
        setAge(0);
        setGender(Gender.UNKNOWN);

    }
    Person(String imie , String nazwisko , String  wiek, String plec)
    {
        setFirstname(imie);
        setSecondname(nazwisko);
        setAge(Integer.parseInt(wiek));
        switch(plec)
        {
            case "MEZCZYZNA":
                setGender(Gender.MALE);
                break;
            case "KOBIETA":
                setGender(Gender.FEMALE);
                break;
            case "-------":
                setGender(Gender.UNKNOWN);
                break;

        }


    }
}
