import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FileDialog;
import java.io.IOException;

/*
 * Program: Aplikacja okienkowa z GUI, która umożliwia testowanie
 *          operacji wykonywanych na obiektach klasy Person.
 *    Plik: WindowApp.java
 *
 *   Autor: Dawid Siuda
 *    Data: październik 2017 r.
 */

public class WindowApp extends JFrame implements ActionListener
{
    final int LEFT_MARGIN_JLAEBL = 50;
    final int LEFT__SECOND_MARGIN_JLAEBL = 200;
    final int LEFT_MARGIN_JBUTTON = 100;


    Person person;
    JLabel lname, lsecondname, lage, lgender,
            tname, tsecondname, tage, tgender;
    JButton b_newPerson, b_delPerson, b_modPerson, b_readPerson, b_wriPerson, b_exit;

    private void refresh()
    {
        if(person == null)
        {
            tname.setText("");
            tsecondname.setText("");
            tage.setText("");
            tgender.setText("");
        }
        else
        {
            tname.setText(person.getFirstname());
            tsecondname.setText(person.getSecondname());
            tage.setText(person.getAge());
            tgender.setText(person.getGender());
        }
    }

    WindowApp()
    {
        setSize(350,450);
        setLayout(null);
        setLocationRelativeTo(null);
        setTitle("WindowApplication");

       // person = new Person();

        // Wypisanie danych
        lname = new JLabel("imię:");
        lname.setBounds(LEFT_MARGIN_JLAEBL, 20, 80,20);
        add(lname);

        lsecondname = new JLabel("nazwisko:");
        lsecondname.setBounds(LEFT_MARGIN_JLAEBL, 40, 80,20);
        add(lsecondname);

        lage = new JLabel("wiek:");
        lage.setBounds(LEFT_MARGIN_JLAEBL, 60, 80,20);
        add(lage);

        lgender = new JLabel("płeć:");
        lgender.setBounds(LEFT_MARGIN_JLAEBL, 80, 80,20);
        add(lgender);


        if(person == null)
        {
            tname = new JLabel("");
            tname.setBounds(LEFT__SECOND_MARGIN_JLAEBL, 20, 80,20);
            add(tname);

            tsecondname = new JLabel("");
            tsecondname.setBounds(LEFT__SECOND_MARGIN_JLAEBL, 40, 80,20);
            add(tsecondname);

            tage = new JLabel("");
            tage.setBounds(LEFT__SECOND_MARGIN_JLAEBL, 60, 80,20);
            add(tage);

            tgender = new JLabel("");
            tgender.setBounds(LEFT__SECOND_MARGIN_JLAEBL, 80, 80,20);
            add(tgender);
        }
        else
        {
            tname = new JLabel(person.getFirstname());
            tname.setBounds(LEFT__SECOND_MARGIN_JLAEBL, 20, 80,20);
            add(tname);

            tsecondname = new JLabel(person.getSecondname());
            tsecondname.setBounds(LEFT__SECOND_MARGIN_JLAEBL, 40, 80,20);
            add(tsecondname);

            tage = new JLabel(person.getAge());
            tage.setBounds(LEFT__SECOND_MARGIN_JLAEBL, 60, 80,20);
            add(tage);

            tgender = new JLabel(person.getGender());
            tgender.setBounds(LEFT__SECOND_MARGIN_JLAEBL, 80, 80,20);
            add(tgender);
        }

        b_newPerson = new JButton("Nowa Osoba");
        b_newPerson.setBounds(LEFT_MARGIN_JBUTTON, 120, 150, 30);
        b_newPerson.addActionListener(this);
        add(b_newPerson);

        b_delPerson = new JButton("Usuń Osobe");
        b_delPerson.setBounds(LEFT_MARGIN_JBUTTON, 160, 150, 30);
        b_delPerson.addActionListener(this);
        add(b_delPerson);

        b_modPerson = new JButton("Edytuj Dane");
        b_modPerson.setBounds(LEFT_MARGIN_JBUTTON, 200, 150, 30);
        b_modPerson.addActionListener(this);
        add(b_modPerson);

        b_readPerson = new JButton("Wczytaj Dane");
        b_readPerson.setBounds(LEFT_MARGIN_JBUTTON, 240, 150, 30);
        b_readPerson.addActionListener(this);
        add(b_readPerson);

        b_wriPerson = new JButton("Zapisz Dane");
        b_wriPerson.setBounds(LEFT_MARGIN_JBUTTON, 280, 150, 30);
        b_wriPerson.addActionListener(this);
        add(b_wriPerson);

        b_exit = new JButton("Wyjście");
        b_exit.setBounds(LEFT_MARGIN_JBUTTON, 320, 150, 30);
        b_exit.addActionListener(this);
        add(b_exit);

    }


    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object source = e.getSource();
        try {
            if (source == b_newPerson) {
                person = ChangeDataWindow.createNewPerson(this);
                refresh();
            } else if (source == b_delPerson) {
                person = null;
                refresh();
            } else if (source == b_modPerson) {
                ChangeDataWindow.setData(person, this);
                refresh();
            } else if (source == b_readPerson) {
                try {
                    FileDialog fd = new FileDialog(this, "Wczytaj", FileDialog.LOAD);
                    fd.setVisible(true);
                    String katalog = fd.getDirectory();
                    String plik = fd.getFile();
                    System.out.println(katalog + plik);

                    if(katalog != null && plik != null)
                    {
                        person = Person.readFromFile(katalog + plik, this);
                    }
                } catch (IOException e1) {

                    JOptionPane.showMessageDialog(this,
                            "Nie odnaleziono pliku",
                            "Błąd",
                            JOptionPane.ERROR_MESSAGE);
                }
                refresh();
            }
            else if (source == b_wriPerson)
            {
                try {
                        FileDialog fd = new FileDialog(this, "Zapisz", FileDialog.LOAD);
                        fd.setVisible(true);
                        String katalog = fd.getDirectory();
                        String plik = fd.getFile();

                        if(katalog != null && plik != null)
                        {
                            Person.writeToFile(katalog + plik, this, person);
                            //person = Person.readFromFile(katalog + plik, this);
                        }
                    }
                catch (Exception e2)
                    {

                        JOptionPane.showMessageDialog(this,
                                "Nie odnaleziono pliku",
                                "Błąd",
                                JOptionPane.ERROR_MESSAGE);
                    }

                refresh();
            }
            else if (source == b_exit)
            {
                dispose();
            }
        }
        catch(Exception err)
        {
            JOptionPane.showMessageDialog(this,
                    "Upss.. Coś poszło nie tak",
                    "Błąd",
                    JOptionPane.ERROR_MESSAGE);

        }
    }

    public static void main(String[] arg)
    {
        WindowApp okno = new WindowApp();
        okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        okno.setVisible(true);
    }
}
