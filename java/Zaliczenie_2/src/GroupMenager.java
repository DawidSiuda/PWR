import org.omg.CORBA.Object;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.xml.bind.annotation.XmlType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;

import static javax.swing.JOptionPane.DEFAULT_OPTION;
import static javax.swing.JOptionPane.showInputDialog;




public class GroupMenager extends JFrame implements ActionListener
{
    private final static int INDEX_IDX = 0;
    private final static int NAME_IDX = 1;
    private final static int DESCRIBE_IDX = 2;
    private final static int SIZE_IDX = 3;
    private final static String[] columnNames = {"Lp.", "Nazwa", "Typ", "Liczba osób"};

    String[][] countentOfTablesGroup;
    LinkedList<MyGroup> mainListOfGroup;

    JMenuBar menuBar;
    JMenu menuGroup, menuSpecialGroup,menuAboutMe;
    JMenuItem itemCreate, itemEdit, itemDelete, itemLoadFromFile, itemSaveToFile,
              itemCombination, itemPartOfCommon, itemDifference,itemSymetricalDifference,
              itemAuthor;
    JButton bCreate,bEdit, bDelete, bOpen, bSave, bSum, bProduct, bDifference, bSimetricalDifference;
    JTable table;
    //private DefaultTableModel somethink;


    private void messageBox(String message)
    {
        JOptionPane.showMessageDialog(this,
                message,
                "Informacja",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void create(LinkedList<MyGroup> mainListOfGroup)
    {
        TypeCollection typeOfGroup;
        String name;
        try
            {
                name = showInputDialog("Podaj nazwę grupy");
                if (name == null || name == "" || name.equals("")) return;


                typeOfGroup = (TypeCollection)JOptionPane.showInputDialog(this,
                        "Wybierz typ kolekcji",
                        "utwórz grupe",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        TypeCollection.values(),
                        TypeCollection.COLLECTION_HASHSET);

                        MyGroup newGroup = new MyGroup(name, typeOfGroup);
                        mainListOfGroup.add(newGroup);

            }

        catch(Exception exc)
            {
                System.out.println(exc.getMessage());
                messageBox("Błąd podczas tworzenia grupy");
                return;
            }
    }
    private void create(LinkedList<MyGroup> mainListOfGroup, String name, TypeCollection coll)
    {
        try {
            MyGroup newGroup = new MyGroup(name, coll);
            mainListOfGroup.add(newGroup);
        }catch(Exception ext)
        {
            System.out.println("automatyczne tworzenie grup wysypało się");
        }
    }
    private void edit()
    {}
    private void del()
    {}
    private void open( Collection<MyGroup> listOfGroup)
    {}
    private void save()
    {}
    private void sum()
    {}
    private void product()// iloczyn
    {}
    private void difference()
    {}
    private void symmetricalDifference()
    {}
    private void showAuthor()
    {
        messageBox("Autor: Dawid Siuda\nData: październik 2017");
    }

    private JTable refreshTable(Collection<MyGroup> collect, String[] collumnsName, JTable myTable)
    {
        Iterator j = collect.iterator();
        int sum = 0;
        while(j.hasNext()){sum++;j.next();};
        JTable currentTable;

        String[][] countentOfTablesGroup = new String[sum][4];
        Iterator i = collect.iterator();
        int numberOfRecord = 1;
        System.out.println("Liczba wierszy w tabeli grup: "+sum);


        while (i.hasNext())
        {
            MyGroup myGroup = (MyGroup)i.next();

            countentOfTablesGroup[numberOfRecord-1][0] = Integer.toString(numberOfRecord);
            countentOfTablesGroup[numberOfRecord-1][1] = myGroup.getName();
            countentOfTablesGroup[numberOfRecord-1][2] = myGroup.getTypeOfGroup();
            countentOfTablesGroup[numberOfRecord-1][3] = Integer.toString(myGroup.getSize());

//            System.out.println("-->"+countentOfTablesGroup[numberOfRecord-1][0]+ " "+
//            countentOfTablesGroup[numberOfRecord-1][1] + " "+
//            countentOfTablesGroup[numberOfRecord-1][2] + " "+
//            countentOfTablesGroup[numberOfRecord-1][3]);

            numberOfRecord++;
        }

        currentTable = new JTable(countentOfTablesGroup,columnNames);

        // ustawiamy szerokosc pierwszej kolumny
        TableColumn tColumn = currentTable.getColumnModel().getColumn(0);
        tColumn.setMaxWidth(30);

        //ustawiamy rozmiar tabeli
        currentTable.setBounds(30,30,440,270);
        //currentTable.setBounds(100,100,100,100);

        return currentTable;
    }
    void menuBarCreator()
    {
        // tworzymy menuBar
        menuBar = new JMenuBar();

        // tworzymy podkatalogi menuBr
        menuGroup = new JMenu("Grupy");
        menuSpecialGroup = new JMenu("Grupy Specjalne");
        menuAboutMe = new JMenu("O programie");

        //tworzymy itemy menu
        itemCreate = new JMenuItem("Utwórz grupe");
            itemCreate.addActionListener(this);
        itemEdit = new JMenuItem("Edytuj grupe");
            itemEdit.addActionListener(this);
        itemDelete = new JMenuItem("Usuń grupe");
            itemDelete.addActionListener(this);
        itemLoadFromFile = new JMenuItem("Wczytaj dane z pliku");
            itemLoadFromFile.addActionListener(this);
        itemSaveToFile = new JMenuItem("Zapisz dane do pliku");
            itemSaveToFile.addActionListener(this);

        itemCombination = new JMenuItem("Połączenie grup");
            itemCombination.addActionListener(this);
        itemPartOfCommon = new JMenuItem("Część wspólna grup");
            itemPartOfCommon.addActionListener(this);
        itemDifference = new JMenuItem("Różnica grup");
            itemDifference.addActionListener(this);
        itemSymetricalDifference = new JMenuItem("Rónica symetryczna grup");
            itemSymetricalDifference.addActionListener(this);

        itemAuthor  = new JMenuItem("Autor");;
            itemAuthor.addActionListener(this);

        //dadajemy JMenuBar do ramki
        setJMenuBar(menuBar);

        //dodajemy "podfoldery" menu
        menuBar.add(menuGroup);
        menuBar.add(menuSpecialGroup);
        menuBar.add(menuAboutMe);

        //Dodajemy itemy do menu
        menuGroup.add(itemCreate);
        menuGroup.add(itemEdit);
        menuGroup.add(itemDelete);
        menuGroup.addSeparator();
        menuGroup.add(itemLoadFromFile);
        menuGroup.add(itemSaveToFile);

        menuSpecialGroup.add(itemCombination);
        menuSpecialGroup.add(itemPartOfCommon);
        menuSpecialGroup.add(itemDifference);
        menuSpecialGroup.add(itemSymetricalDifference);

        menuAboutMe.add(itemAuthor);
        //  menuGroup.a
    }

    GroupMenager()
    {
        mainListOfGroup = new LinkedList<MyGroup>();

        setSize(510,500);
        setLayout(null);
        setLocationRelativeTo(null);


        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        create(mainListOfGroup, "Pierwsza", TypeCollection.COLLECTION_HASHSET);
        create(mainListOfGroup, "Druga", TypeCollection.LIST_ARRAYLIST);
        create(mainListOfGroup, "Trzecia", TypeCollection.LIST_VECTOR);
        create(mainListOfGroup, "Czwarta", TypeCollection.COLLECTION_TREESET);
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        table = refreshTable(mainListOfGroup, columnNames, table);

        add(table);


        menuBarCreator();

        //przyciski
        bCreate = new JButton("Utwórz");
        bCreate.setBounds(30,330,80,30);
        bCreate.addActionListener(this);
        add(bCreate);

        bEdit = new JButton("Edytuj");
        bEdit.setBounds(120,330,80,30);
        bEdit.addActionListener(this);
        add(bEdit);

        bDelete = new JButton("Usuń");
        bDelete.setBounds(210,330,80,30);
        bDelete.addActionListener(this);
        add(bDelete);


        bOpen = new JButton("Otwórz");
        bOpen.setBounds(300,330,80,30);
        bOpen.addActionListener(this);
        add(bOpen);


        bSave = new JButton("Zapisz");
        bSave.setBounds(390,330,80,30);
        bSave.addActionListener(this);
        add(bSave);


        bSum = new JButton("Suma");
        bSum.setBounds(30,370,80,30);
        bSum.addActionListener(this);
        add(bSum);


        bProduct = new JButton("Iloczyn");
        bProduct.setBounds(120,370,80,30);
        bProduct.addActionListener(this);
        add(bProduct);


        bDifference = new JButton("Różnica");
        bDifference.setBounds(210,370,80,30);
        bDifference.addActionListener(this);
        add( bDifference);


        bSimetricalDifference = new JButton("Różnica symetryczna");
        bSimetricalDifference.setBounds(300,370,170,30);
        bSimetricalDifference.addActionListener(this);
        add(bSimetricalDifference);

    }



    @Override
    public void actionPerformed(ActionEvent e)
    {
        java.lang.Object source = e.getSource();

        if(source == itemCreate  || source == bCreate)
        {
            create(mainListOfGroup);
            table = refreshTable(mainListOfGroup, columnNames, table);
            add(table);
            setVisible(true);
        }else
        if(source == itemEdit || source == bEdit)
        {
           edit();
        }else
        if(source == itemDelete || source == bDelete)
        {
            del();
        }else
        if(source == itemLoadFromFile || source == bOpen)
        {
            //open();
        }else
        if(source == itemSaveToFile || source == bSave)
        {
            save();
        }else
        if(source == itemCombination || source == bSum)
        {
            sum();
        }else
        if(source == itemPartOfCommon || source == bProduct)
        {
            product();
        }else
        if(source == itemDifference || source == bDifference)
        {
            difference();
        }else
        if(source == itemSymetricalDifference || source == bSimetricalDifference)
        {
            symmetricalDifference();
        }else
        if(source == itemAuthor)
        {
            showAuthor();
        }

    }

    public static void main(String[] arg)
    {
        GroupMenager windowsFrame = new GroupMenager();
        windowsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        windowsFrame.setName("Zażądzanie grupami");
        windowsFrame.setVisible(true);
    }
}


