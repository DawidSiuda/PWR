import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * Program: Aplikacja okienkowa z GUI, która umożliwia testowanie
 *          operacji wykonywanych na obiektach klasy Person.
 *    Plik: ChangeDataWindow.java
 *
 *   Autor: Dawid Siuda
 *    Data: październik 2017 r.
 *
 *
 * Klasa ChangeDataWindow implementuje pomocnicze metody pozwalające
 * łatwiejszą modyfikacje obiektów klasy person za pośrednictwem WinApi.
 */


public class ChangeDataWindow extends JDialog implements ActionListener
{
    final int LEFT_MARGIN_JLAEBL = 10;
    final int LEFT__SECOND_MARGIN_JLAEBL = 100;

    JLabel lname, lsecondname, lage, lgender;
    JTextField tname, tsecondname, tage;
    JButton b_ok, b_cancel;
    JComboBox<Gender> genderBox = new JComboBox<Gender>(Gender.values());

    private void setNewPerson()
    {
        newPerson = new Person(tname.getText(),tsecondname.getText(), Integer.parseInt(tage.getText()),genderBox.getPrototypeDisplayValue());
    }

    Person newPerson;
    ChangeDataWindow(Window parent ,Person oldPerson)
    {
        super(parent, Dialog.ModalityType.DOCUMENT_MODAL);
        newPerson = oldPerson;

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(250,200);
        setLayout(null);
        setLocationRelativeTo(null);

        b_cancel = new JButton("Anuluj");
        b_cancel.setBounds(30,110,80,30);
        b_cancel.addActionListener(this);
        add(b_cancel);

        b_ok = new JButton("OK");
        b_ok.setBounds(120,110,80,30);
        b_ok.addActionListener(this);
        add(b_ok);

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

        lgender = new JLabel("Płeć:");
        lgender.setBounds(LEFT_MARGIN_JLAEBL, 80, 80,20);
        add(lgender);


        if(oldPerson == null)
        {
            tname = new  JTextField("");
            tname.setBounds(LEFT__SECOND_MARGIN_JLAEBL, 20, 80,20);
            add(tname);

            tsecondname = new  JTextField("");
            tsecondname.setBounds(LEFT__SECOND_MARGIN_JLAEBL, 40, 80,20);
            add(tsecondname);

            tage = new  JTextField("");
            tage.setBounds(LEFT__SECOND_MARGIN_JLAEBL, 60, 80,20);
            add(tage);

            genderBox.setBounds(LEFT__SECOND_MARGIN_JLAEBL, 80, 80,20);
            add(genderBox);
        }
        else
        {
            tname = new  JTextField(oldPerson.getFirstname());
            tname.setBounds(LEFT__SECOND_MARGIN_JLAEBL, 20, 80,20);
            add(tname);

            tsecondname = new  JTextField(oldPerson.getSecondname());
            tsecondname.setBounds(LEFT__SECOND_MARGIN_JLAEBL, 40, 80,20);
            add(tsecondname);

            tage = new  JTextField(oldPerson.getAge());
            tage.setBounds(LEFT__SECOND_MARGIN_JLAEBL, 60, 80,20);
            add(tage);

            genderBox.setBounds(LEFT__SECOND_MARGIN_JLAEBL, 80, 80,20);
            add(genderBox);
        }

        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == b_ok)
        {
            try
            {
                if (newPerson == null) {
                    newPerson = new Person(tname.getText(), tsecondname.getText(), Integer.parseInt(tage.getText()), (Gender) genderBox.getSelectedItem());
                } else {
                    newPerson.setFirstname(tname.getText());
                    newPerson.setSecondname(tsecondname.getText());
                    newPerson.setAge(Integer.parseInt(tage.getText()));
                    newPerson.setGender((Gender) genderBox.getSelectedItem());
                }
            }
            catch(Exception er)
            {
                JOptionPane.showMessageDialog(this,
                        "Wprowadzono błędne dane",
                        "Błąd",
                        JOptionPane.ERROR_MESSAGE);

            }
            dispose();
        }
        else if (source == b_cancel) {
            dispose();
        }

    }

    static void setData(Person oldPerson, Window window)
    {
        ChangeDataWindow newWindow = new ChangeDataWindow(window, oldPerson);
    }

    static Person createNewPerson(Window window)
    {
        ChangeDataWindow newWindow = new ChangeDataWindow(window, null);
        return newWindow.newPerson;
    }


}
