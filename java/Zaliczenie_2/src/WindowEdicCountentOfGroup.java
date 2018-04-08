import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class WindowEdicCountentOfGroup  extends JFrame implements ActionListener
{
    JButton b_newPerson, b_delPerson, b_modPerson, b_readPerson, b_wriPerson, b_exit;
    JTable table;
    final String columnsNames[]= {"Imię", "Nazwisko", "Wiek", "Płeć"};
    Object[][] data;
    Collection<Person> collectionOfPeople;

    private JScrollPane refresh(Collection<Person> collect, String[] columnNames)
    {
        System.out.println("--> Odswierzam");


        Iterator j = collect.iterator();
        int sum = 0;
        while(j.hasNext()){sum++;j.next();};
        JTable currentTable;

        String[][] countentOfTablesGroup = new String[sum][4];
        Iterator i = collect.iterator();
        int numberOfRecord = 1;
        System.out.println("Liczba wierszy w tabeli osób: "+sum);


        while (i.hasNext())
        {
            Person person = (Person) i.next();

            countentOfTablesGroup[numberOfRecord-1][0] = person.getFirstname();
            countentOfTablesGroup[numberOfRecord-1][1] = person.getSecondname();
            countentOfTablesGroup[numberOfRecord-1][2] = person.getAge();
            countentOfTablesGroup[numberOfRecord-1][3] = person.getGender();


//            System.out.println("-->"+countentOfTablesGroup[numberOfRecord-1][0]+ " "+
//            countentOfTablesGroup[numberOfRecord-1][1] + " "+
//            countentOfTablesGroup[numberOfRecord-1][2] + " "+
//            countentOfTablesGroup[numberOfRecord-1][3]);

            numberOfRecord++;
        }

        currentTable = new JTable(countentOfTablesGroup,columnNames);

        JScrollPane scrollPane= new JScrollPane(currentTable);
        scrollPane.setBounds(30,40,410,250);


        //ustawiamy rozmiar tabeli
        currentTable.setBounds(30,30,440,270);

        return scrollPane;


    }

    WindowEdicCountentOfGroup(Collection actuallyCollection)
    {
        collectionOfPeople = actuallyCollection;
        if(collectionOfPeople == null)
        {
            collectionOfPeople = new ArrayList<Person>();
        }
        setSize(490,500);

        setLayout(null);
        setLocationRelativeTo(null);
        setTitle("Edit Group");

        data = new Object[][] {{null, null, null, null}};

        table = new JTable(data, columnsNames);
        table.setPreferredScrollableViewportSize(new Dimension(300,100));
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane= new JScrollPane(table);
        scrollPane.setBounds(30,40,410,250);
        add(scrollPane);

        //table.setRow

        // person = new Person();

//        // Wypisanie danych
//        lname = new JLabel("imię:");
//        lname.setBounds(LEFT_MARGIN_JLAEBL, 20, 80,20);
//        add(lname);
//
//        lsecondname = new JLabel("nazwisko:");
//        lsecondname.setBounds(LEFT_MARGIN_JLAEBL, 40, 80,20);
//        add(lsecondname);
//
//        lage = new JLabel("wiek:");
//        lage.setBounds(LEFT_MARGIN_JLAEBL, 60, 80,20);
//        add(lage);
//
//        lgender = new JLabel("płeć:");
//        lgender.setBounds(LEFT_MARGIN_JLAEBL, 80, 80,20);
//        add(lgender);
//
//
//        if(person == null)
//        {
//            tname = new JLabel("");
//            tname.setBounds(LEFT__SECOND_MARGIN_JLAEBL, 20, 80,20);
//            add(tname);
//
//            tsecondname = new JLabel("");
//            tsecondname.setBounds(LEFT__SECOND_MARGIN_JLAEBL, 40, 80,20);
//            add(tsecondname);
//
//            tage = new JLabel("");
//            tage.setBounds(LEFT__SECOND_MARGIN_JLAEBL, 60, 80,20);
//            add(tage);
//
//            tgender = new JLabel("");
//            tgender.setBounds(LEFT__SECOND_MARGIN_JLAEBL, 80, 80,20);
//            add(tgender);
//        }
//        else
//        {
//            tname = new JLabel(person.getFirstname());
//            tname.setBounds(LEFT__SECOND_MARGIN_JLAEBL, 20, 80,20);
//            add(tname);
//
//            tsecondname = new JLabel(person.getSecondname());
//            tsecondname.setBounds(LEFT__SECOND_MARGIN_JLAEBL, 40, 80,20);
//            add(tsecondname);
//
//            tage = new JLabel(person.getAge());
//            tage.setBounds(LEFT__SECOND_MARGIN_JLAEBL, 60, 80,20);
//            add(tage);
//
//            tgender = new JLabel(person.getGender());
//            tgender.setBounds(LEFT__SECOND_MARGIN_JLAEBL, 80, 80,20);
//            add(tgender);
//        }

        b_newPerson = new JButton("Nowa Osoba");
        b_newPerson.setBounds(30, 320, 130, 30);
        b_newPerson.addActionListener(this);
        add(b_newPerson);

        b_delPerson = new JButton("Usuń Osobe");
        b_delPerson.setBounds(170, 320, 130, 30);
        b_delPerson.addActionListener(this);
        add(b_delPerson);

        b_modPerson = new JButton("Edytuj Dane");
        b_modPerson.setBounds(310, 320, 130, 30);
        b_modPerson.addActionListener(this);
        add(b_modPerson);

        b_readPerson = new JButton("Wczytaj Dane");
        b_readPerson.setBounds(30, 360, 130, 30);
        b_readPerson.addActionListener(this);
        add(b_readPerson);

        b_wriPerson = new JButton("Zapisz Dane");
        b_wriPerson.setBounds(170, 360, 130, 30);
        b_wriPerson.addActionListener(this);
        add(b_wriPerson);

        b_exit = new JButton("Wyjście");
        b_exit.setBounds(310, 360, 130, 30);
        b_exit.addActionListener(this);
        add(b_exit);

    }


    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object source = e.getSource();
        Person person = null;
        try {
            if (source == b_newPerson) {
                person = ChangeDataWindow.createNewPerson(this);
                System.out.println(person.getSecondname());
                collectionOfPeople.add(person);
                add(refresh(collectionOfPeople, columnsNames));
                setVisible(true);
            } else if (source == b_delPerson) {
                person = null;
                add(refresh(collectionOfPeople, columnsNames));
                setVisible(true);
            } else if (source == b_modPerson) {
                ChangeDataWindow.setData(person, this);
                add(refresh(collectionOfPeople, columnsNames));
                setVisible(true);
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
                add(refresh(collectionOfPeople, columnsNames));
                setVisible(true);
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

                add(refresh(collectionOfPeople, columnsNames));
                setVisible(true);
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
            //System.out.println();
            err.printStackTrace();
        }
    }

    public static void main(String[] arg)
    {
        WindowEdicCountentOfGroup okno = new WindowEdicCountentOfGroup(null );
        okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        okno.setVisible(true);
    }
}
