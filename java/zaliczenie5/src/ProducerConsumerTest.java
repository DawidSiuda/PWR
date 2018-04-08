import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/* 
 *  Problem producenta i konsumenta
 *
 *  Autor: Dawid Siuda
 *   Data: 26 listopada 2017 r.
 */


abstract class  Worker extends Thread {

	private ArrayList<ProducerConsumerTest> observerList;

	public void observerRegister(ProducerConsumerTest var)
	{
		observerList.add(var);
	}

	public void observerUnregister(ProducerConsumerTest var)
	{
		observerList.remove(var);
	}

	public void observerNotify(String var)
	{
		for(ProducerConsumerTest o: observerList)
		{
			o.update(var);
		}
	}

	Worker()
	{
		observerList = new ArrayList<>();
	}

	// Metoda usypia w¹tek na podany czas w milisekundach
	public static void sleep(int millis){
		try {
			Thread.sleep(millis);
			} catch (InterruptedException e) { }
	}
	
	// Metoda usypia w¹tek na losowo dobrany czas z przedzia³u [min, max) milsekund
	public static void sleep(int min_millis, int max_milis){
		sleep(ThreadLocalRandom.current().nextInt(min_millis, max_milis));
	}


	// Unikalny identyfikator przedmiotu wyprodukowanego
	// przez producenta i zu¿ytego przez konsumenta
	// Ten identyfikator jest wspólny dla wszystkich producentów
	// i bêdzie zwiêkszany przy produkcji ka¿dego nowego przedmiotu
	static int itemID = 0;
	
	// Minimalny i maksymalny czas produkcji przedmiotu
	public volatile static int MIN_PRODUCER_TIME = 100;
	public volatile static int MAX_PRODUCER_TIME = 1000;
	
	// Minimalny i maksymalny czas konsumpcji (zu¿ycia) przedmiotu
	public volatile static int MIN_CONSUMER_TIME = 100;
	public volatile static int MAX_CONSUMER_TIME = 1000;

	 static void changeTime(
			 int nMIN_PRODUCER_TIME,
			 int nMAX_PRODUCER_TIME,
			 int nMIN_CONSUMER_TIME,
			 int nMAX_CONSUMER_TIME)
	 {
		 MIN_PRODUCER_TIME = nMIN_PRODUCER_TIME;
		 MAX_PRODUCER_TIME = nMAX_PRODUCER_TIME;
		 MIN_CONSUMER_TIME = nMIN_CONSUMER_TIME;
		 MAX_CONSUMER_TIME = nMAX_CONSUMER_TIME;
	 }

	String name;
	Buffer buffer;

	public abstract void pause();
	public abstract void breakPause();

	@Override
	public abstract void run();
}


class Producer extends Worker {

	volatile boolean setPause;

	public Producer(String name , Buffer buffer, ProducerConsumerTest window){
		super();
		this.name = name;
		this.buffer = buffer;
		observerRegister(window);
		setPause = false;
	}

	@Override
	public void pause()
	{
		setPause = true;
	}

	@Override
	public void breakPause()
	{
		setPause = false;
	}

	@Override
	public void run(){ 
		int item;
		while(true){
			if(setPause == false)
			{
				// Producent "produkuje" nowy przedmiot.
				item = itemID++;
				observerNotify("Producent <" + name + ">   produkuje: " + item);
				sleep(MIN_PRODUCER_TIME, MAX_PRODUCER_TIME);

				// Producent umieszcza przedmiot w buforze.
				buffer.put(this, item);
			}
			else
			{
				sleep(10);
			}
//			while (setPause != false)
//			{
//				try { observerNotify("++");
//					wait();
//				} catch (InterruptedException e) { }
//			}
//			setPause = false;
//				// Producent "produkuje" nowy przedmiot.
//				item = itemID++;
//				observerNotify("Producent <" + name + ">   produkuje: " + item);
//				sleep(MIN_PRODUCER_TIME, MAX_PRODUCER_TIME);
//
//				// Producent umieszcza przedmiot w buforze.
//				buffer.put(this, item);
//				//notifyAll();

		}
	}
	
} // koniec klasy Producer


class Consumer extends Worker {

	private volatile boolean setPause;
	public Consumer(String name , Buffer buffer, ProducerConsumerTest window){
		this.name = name;
		this.buffer = buffer;
		setPause = false;
		observerRegister(window);
	}

	@Override
	public void pause()
	{
		setPause = true;
	}

	@Override
	public void breakPause()
	{
		setPause = false;
	}

	@Override
	public void run(){ 
		int item;
		while(true){
			if(setPause == false)
			{
				// Konsument pobiera przedmiot z bufora
				item = buffer.get(this);

				// Konsument zu¿ywa popraany przedmiot.
				sleep(MIN_CONSUMER_TIME, MAX_CONSUMER_TIME);
				observerNotify("Konsument <" + name + ">       zuzyl: " + item);
			}
			else
			{
				sleep(10);
			}
		}
	}
	
} // koniec klasy Consumer

class Buffer {

	private ArrayList<ProducerConsumerTest> observerList;
	private ArrayList<Integer> spaceOfBufor;
	int buforSize;

	Buffer(ProducerConsumerTest window, int setSize)
	{
		observerList = new ArrayList<>();
		observerRegister(window);
		buforSize = setSize;
		spaceOfBufor = new ArrayList<>();
	}

	//private int contents;
	private boolean available = false;

	public synchronized int get(Consumer consumer){
		observerNotify("Konsument <" + consumer.name + "> chce zabrac");
		while (spaceOfBufor.size()== 0){
			try { observerNotify("Konsument <" + consumer.name + ">   bufor pusty - czekam");
				  wait();
				} catch (InterruptedException e) { }
		}
		int item = spaceOfBufor.get(0);
		spaceOfBufor.remove(0);
		observerNotify("Konsument <" + consumer.name + ">      zabral: " + item);
		notifyAll();
		return item;
	}

	public synchronized void put(Producer producer, int item){
		observerNotify("Producent <" + producer.name + ">  chce oddac: " + item);
		while (spaceOfBufor.size()>=buforSize){
			try { observerNotify("Producent <" + producer.name + ">   bufor zajety - czekam");
				  wait();
				} catch (InterruptedException e) { }
		}
		spaceOfBufor.add(item);
		observerNotify("Producent <" + producer.name + ">       oddal: " + item);
		notifyAll();
	}

	public void observerRegister(ProducerConsumerTest var)
	{
		observerList.add(var);
	}

	public void observerUnregister(ProducerConsumerTest var)
	{
		observerList.remove(var);
	}

	public void observerNotify(String var)
	{
		for(ProducerConsumerTest o: observerList)
		{
			o.update(var);
		}
	}
	
} // koniec klasy Buffer

public class ProducerConsumerTest extends JFrame implements ActionListener, ChangeListener
{
	//ProducerConsumerTest okno;
	Buffer buffer;
	JButton bStart, bPause;
	JTextArea textArea;
	JScrollPane scrollPane;
	JComboBox<Integer> buforSize, numberOfProducer, numberOfConsumer;
	JLabel labelBuforSize, labelNumberOfProducer, labelNumberOfConsumer;
	JSlider miProTime,mxProTime,miCosTime,mxCosTime;
	JLabel labelMiProTime, labelMxProTime,labelMiCosTime,labelMxCosTime;
	final Integer selectNumber[] = {1,2,3,4,5,6,7};
	ArrayList<Worker> workerList;
	boolean startWasPress=false;
	boolean pauseWasPressed=false;


	ProducerConsumerTest()
	{

		setSize(700,900);
		setLayout(null);
		setLocationRelativeTo(null);
		setTitle("WindowApplication");

		bStart = new JButton("Start");
		bStart.setBounds(200, 500, 80, 30);
		bStart.addActionListener(this);
		add(bStart);

		bPause = new JButton("Wstrzymaj Symulacje");
		bPause.setBounds(300, 500, 200, 30);
		bPause.addActionListener(this);
		add(bPause);

		textArea = new JTextArea();
		add(textArea);

		scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(50,80,590,400);
		add(scrollPane);

		buforSize = new JComboBox<>(selectNumber);
		buforSize.setBounds(210,20,40,35);
		add(buforSize);

		labelBuforSize = new JLabel("Rozmiar bufora");
		labelBuforSize.setBounds(120,20,100,35);
		add(labelBuforSize);

		numberOfProducer = new JComboBox<>(selectNumber);
		numberOfProducer.setBounds(380,20,40,35);
		add(numberOfProducer);

		labelNumberOfProducer = new JLabel("Ilosc producentow");
		labelNumberOfProducer.setBounds(270,20,115,35);
		add(labelNumberOfProducer);

		numberOfConsumer = new JComboBox<>(selectNumber);
		numberOfConsumer.setBounds(550,20,40,35);
		add(numberOfConsumer);

		labelNumberOfConsumer = new JLabel("Ilosc konsumentow");
		labelNumberOfConsumer.setBounds(435,20,115,35);
		add(labelNumberOfConsumer);

		labelMiProTime = new JLabel("Minimalny czas produkcji");
		labelMiProTime.setBounds(60,540,400,30);
		add(labelMiProTime);

		miProTime = new JSlider(0,600,100);
		miProTime.setBounds(80,565,400,45);
		miProTime.setMajorTickSpacing(100);
		miProTime.setMinorTickSpacing(50);
		miProTime.setPaintTicks(true);
		miProTime.setPaintLabels(true);
		miProTime.setPaintTrack(true);
		miProTime.addChangeListener(this);
		add(miProTime);

		labelMxProTime = new JLabel("Maksymalny czas produkcji");
		labelMxProTime.setBounds(60,610,400,30);
		add(labelMxProTime);

		mxProTime = new JSlider(700,1300,1000);
		mxProTime.setBounds(80,635,400,45);
		mxProTime.setMajorTickSpacing(100);
		mxProTime.setMinorTickSpacing(50);
		mxProTime.setPaintTicks(true);
		mxProTime.setPaintLabels(true);
		mxProTime.setPaintTrack(true);
		mxProTime.addChangeListener(this);
		add(mxProTime);

		labelMiCosTime = new JLabel("Minimalny czas konsumpcji");
		labelMiCosTime.setBounds(60,680,400,30);
		add(labelMiCosTime);

		miCosTime = new JSlider(0,600,100);
		miCosTime.setBounds(80,705,400,45);
		miCosTime.setMajorTickSpacing(100);
		miCosTime.setMinorTickSpacing(50);
		miCosTime.setPaintTicks(true);
		miCosTime.setPaintLabels(true);
		miCosTime.setPaintTrack(true);
		miCosTime.addChangeListener(this);
		add(miCosTime);

		labelMxCosTime = new JLabel("Maksymalny czas konsumpcji");
		labelMxCosTime.setBounds(60,750,400,30);
		add(labelMxCosTime);

		mxCosTime = new JSlider(700,1300,1000);
		mxCosTime.setBounds(80,775,400,45);
		mxCosTime.setMajorTickSpacing(100);
		mxCosTime.setMinorTickSpacing(50);
		mxCosTime.setPaintTicks(true);
		mxCosTime.setPaintLabels(true);
		mxCosTime.setPaintTrack(true);
		mxCosTime.addChangeListener(this);
		add(mxCosTime);

	}

	public static void main(String[] args)
	{
		ProducerConsumerTest okno = new ProducerConsumerTest();
		okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		okno.setVisible(true);
	}

	public void update(String message)
	{
		textArea.append( message+ "\n");
		textArea.setCaretPosition(textArea.getDocument().getLength());
		//textArea.scrollTop = textArea.scrollHeight;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();

		if(source == bStart)
		{
			if(startWasPress == false)
			{
				workerList = new ArrayList<>();
				int numberC = (int)numberOfConsumer.getSelectedItem();
					System.out.println("ilosc konsumentow: "+ numberC);
				int numberP = (int)numberOfProducer.getSelectedItem();
					System.out.println("ilosc producentow: "+ numberP);
				int numberB = (int)buforSize.getSelectedItem();
					System.out.println("Wielkosc buforu: "+ numberB);

				buffer = new Buffer(this, numberB);

				for(int i =1; i<= numberC; i++)
					workerList.add(new Consumer("K"+i,buffer,this));

				for(int i =1; i<= numberP; i++)
					workerList.add(new Producer("P"+i,buffer,this));

				for(Worker w: workerList)
						w.start();

				startWasPress = true;
			}
			else
			{
				System.out.println("Start was pressed");
			}
		}
		else
		if(source == bPause )
		{
			if(pauseWasPressed == false && startWasPress)
			{
				for(Worker w: workerList)
					w.pause();

				pauseWasPressed = true;
				bPause.setText("Kontynuuj Symulacje ") ;
			}
			else if(pauseWasPressed != false && startWasPress )
			{
				for(Worker w: workerList)
					w.breakPause();

				pauseWasPressed = false;
				bPause.setText("Wstrzymaj Symulacje ") ;
			}
		}
	}

	@Override
	public void stateChanged(ChangeEvent e)
	{
		Worker.changeTime(
				miProTime.getValue(),
				mxProTime.getValue(),
				miCosTime.getValue(),
				mxCosTime.getValue()
		);
	}

} // koniec klasy ProducerConsumerTest


