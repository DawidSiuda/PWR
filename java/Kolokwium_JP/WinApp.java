package scondProjectKolokium;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class WinApp extends JFrame implements ActionListener
{
	Canvas canvas;
	//JOptionPane optionPane;
	JMenuItem jMenuItem, reset;
	JMenuBar jMenuBar;
	JLabel jLabel;
	Timer timer;
	
	
	public static void main(String[] arg)
	{
		WinApp winApp = new WinApp();
		winApp.setDefaultCloseOperation(EXIT_ON_CLOSE);
		winApp.setVisible(true);
	}
	
	public WinApp() 
	{
		setSize(400,500);
		setLayout(null);
		setLocationRelativeTo(null);
		setTitle("tytu³");
		
		jMenuBar = new JMenuBar();
		setJMenuBar(jMenuBar);
	
		jMenuItem = new JMenuItem("Autor");
		jMenuItem.addActionListener(this);
		jMenuBar.add(jMenuItem);
		
		reset = new JMenuItem("Reset");
		reset.addActionListener(this);
		jMenuBar.add(reset);
		
		jLabel = new JLabel();
		jMenuBar.add(jLabel);
				
	
		canvas = new Canvas();
		canvas.setBounds(0, 0, this.getWidth(), this.getHeight());
		add(canvas);
		
		timer = new Timer(this);
		timer.start();
		// TODO Auto-generated constructor stub
	}
	
	public synchronized void observerNotify(int time)
	{
		jLabel.setText(Integer.toString(time));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object source = e.getSource();
		
		if(source == jMenuItem)
		{
			JOptionPane.showMessageDialog(null, "Dawid Siuda 235069");
		}else
		if(source == reset)
		{
			timer.reset();
		}
	}
}
