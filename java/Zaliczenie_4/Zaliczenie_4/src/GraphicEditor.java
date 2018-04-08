/*
 *  Program EdytorGraficzny - aplikacja z graficznym interfejsem
 *   - obsługa zdarzeń od klawiatury, myszki i innych elementów GUI.
 *
 *  Autor: Dawid Siuda, ...
 *   Data: 19. 11, 2017 r.
 */

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


abstract class Figure{

	static Random random = new Random();

	private boolean selected = false;

	public boolean isSelected() { return selected; }
	public void select() {	selected = true; }
	public void select(boolean z) { selected = z; }
	public void unselect() { selected = false; }

	protected void setColor(Graphics g) {
		if (selected) g.setColor(Color.RED);
		           else g.setColor(Color.BLACK);
	}

	public abstract boolean isInside(float px, float py);
	public boolean isInside(int px, int py) {
		return isInside((float) px, (float) py);
	}

	protected String properties() {
		String s = String.format("  Pole: %.0f  Obwod: %.0f", computeArea(), computePerimeter());
		if (isSelected()) s = s + "   [SELECTED]";
		return s;
	}

	abstract String getName();
	abstract float  getX();
	abstract float  getY();

    abstract float computeArea();
    abstract float computePerimeter();

    abstract void move(float dx, float dy);
    abstract void scale(float s);

    abstract void draw(Graphics g);

    @Override
    public String toString(){
        return getName();
    }

}

class Point extends Figure{

	protected float x, y;

	Point()
	{ this.x=random.nextFloat()*400;
	  this.y=random.nextFloat()*400;
	}

	Point(float x, float y)
	{ this.x=x;
	  this.y=y;
	}

	@Override
	public boolean isInside(float px, float py) {
		// by umo�liwi� zaznaczanie punktu myszk�
		// miejsca odleg�e nie wi�cej ni� 6 le�� wewn�trz
		return (Math.sqrt((x - px) * (x - px) + (y - py) * (y - py)) <= 6);
	}


    @Override
	String getName() {
		return "Point(" + x + ", " + y + ")";
	}

	@Override
	float getX() {
		return x;
	}

	@Override
	float getY() {
		return y;
	}

	@Override
    float computeArea(){ return 0; }

	@Override
	float computePerimeter(){ return 0; }

	@Override
    void move(float dx, float dy){ x+=dx; y+=dy; }

	@Override
    void scale(float s){ }

	@Override
    void draw(Graphics g){
		setColor(g);
		g.fillOval((int)(x-3),(int)(y-3), 6,6);
	}

    String toStringXY(){ return "(" + x + " , " + y + ")"; }

}
class Circle extends Point{
    float r;

    Circle(){
        super();
        r=random.nextFloat()*100;
    }

    Circle(float px, float py, float pr){
        super(px,py);
        r=pr;
    }

    @Override
	public boolean isInside(float px, float py) {
		return (Math.sqrt((x - px) * (x - px) + (y - py) * (y - py)) <= r);
	}

    @Override
   	String getName() {
   		return "Circle(" + x + ", " + y + ")";
   	}

    @Override
    float computeArea(){ return (float)Math.PI*r*r; }

    @Override
    float computePerimeter(){ return (float)Math.PI*r*2; }

    @Override
    void scale(float s){ r*=s; }

    @Override
    void draw(Graphics g){
    	setColor(g);
        g.drawOval((int)(x-r), (int)(y-r), (int)(2*r), (int)(2*r));
    }

}
class Triangle extends Figure{
    Point point1, point2, point3;

    Triangle(){
    	point1 = new Point();
    	point2 = new Point();
    	point3 = new Point();
    }

    Triangle(Point p1, Point p2, Point p3){
        point1=p1; point2=p2; point3=p3;
    }

    @Override
    public boolean isInside(float px, float py)
    { float d1, d2, d3;
      d1 = px*(point1.y-point2.y) + py*(point2.x-point1.x) +
           (point1.x*point2.y-point1.y*point2.x);
      d2 = px*(point2.y-point3.y) + py*(point3.x-point2.x) +
           (point2.x*point3.y-point2.y*point3.x);
      d3 = px*(point3.y-point1.y) + py*(point1.x-point3.x) +
           (point3.x*point1.y-point3.y*point1.x);
      return ((d1<=0)&&(d2<=0)&&(d3<=0)) || ((d1>=0)&&(d2>=0)&&(d3>=0));
    }

    @Override
	String getName() {
    	return "Triangle{"+point1.toStringXY()+
                point2.toStringXY()+
                point3.toStringXY()+"}";
	}

	@Override
	float getX() {
		return (point1.x+point2.x+point3.x)/3;
	}

	@Override
	float getY() {
		return (point1.y+point2.y+point3.y)/3;
	}

	@Override
	float computeArea(){
        float a = (float)Math.sqrt( (point1.x-point2.x)*(point1.x-point2.x)+
                                    (point1.y-point2.y)*(point1.y-point2.y));
        float b = (float)Math.sqrt( (point2.x-point3.x)*(point2.x-point3.x)+
                                    (point2.y-point3.y)*(point2.y-point3.y));
        float c = (float)Math.sqrt( (point1.x-point3.x)*(point1.x-point3.x)+
                                    (point1.y-point3.y)*(point1.y-point3.y));
        float p=(a+b+c)/2;
        return (float)Math.sqrt(p*(p-a)*(p-b)*(p-c));
    }

	@Override
    float computePerimeter(){
        float a = (float)Math.sqrt( (point1.x-point2.x)*(point1.x-point2.x)+
                                    (point1.y-point2.y)*(point1.y-point2.y));
        float b = (float)Math.sqrt( (point2.x-point3.x)*(point2.x-point3.x)+
                                    (point2.y-point3.y)*(point2.y-point3.y));
        float c = (float)Math.sqrt( (point1.x-point3.x)*(point1.x-point3.x)+
                                    (point1.y-point3.y)*(point1.y-point3.y));
        return a+b+c;
    }

	@Override
    void move(float dx, float dy){
        point1.move(dx,dy);
        point2.move(dx,dy);
        point3.move(dx,dy);
    }

	@Override
    void scale(float s){
        Point sr1 = new Point((point1.x+point2.x+point3.x)/3,
                              (point1.y+point2.y+point3.y)/3);
        point1.x*=s; point1.y*=s;
        point2.x*=s; point2.y*=s;
        point3.x*=s; point3.y*=s;
        Point sr2 = new Point((point1.x+point2.x+point3.x)/3,
                              (point1.y+point2.y+point3.y)/3);
        float dx=sr1.x-sr2.x;
        float dy=sr1.y-sr2.y;
        point1.move(dx,dy);
        point2.move(dx,dy);
        point3.move(dx,dy);
    }

	@Override
    void draw(Graphics g){
		setColor(g);
        g.drawLine((int)point1.x, (int)point1.y,
                   (int)point2.x, (int)point2.y);
        g.drawLine((int)point2.x, (int)point2.y,
                   (int)point3.x, (int)point3.y);
        g.drawLine((int)point3.x, (int)point3.y,
                   (int)point1.x, (int)point1.y);
    }

}
class Square extends Figure {
	Point point1, point2, point3, point4;

	Square(){
		point1 = new Point();
		float length=random.nextFloat()*(400-point1.x);
		point2 = new Point(point1.x+length,point1.y);
		point3 = new Point(point1.x+length, point1.y+length);
		point4 = new Point(point1.x ,point1.y+length);
	}

	@Override
	public boolean isInside(float px, float py) {

		if(point1.x-px<=0 && point2.x-px>=0 && point1.y-py<=0 && point4.y-py>=0)
			return true;
		else
			return false;
	}

	@Override
	String getName() {
		return "Squere{"+point1.toStringXY()+
				point2.toStringXY()+
				point3.toStringXY()+
				point4.toStringXY()+"}";
	}

	@Override
	float getX() {
		return ((point1.x+point2.x)/2);
	}

	@Override
	float getY() {
		return ((point1.y+point4.y)/2);
	}

	@Override
	float computeArea() {
		float a =point1.x-point2.x;
		float b =point1.y-point4.y;
		return a*b;
	}

	@Override
	float computePerimeter() {
		float a =point1.x-point2.x;
		float b =point1.y-point4.y;

		return 2*(a+b);
	}

	@Override
	void move(float dx, float dy) {
		point1.move(dx,dy);
		point2.move(dx,dy);
		point3.move(dx,dy);
		point4.move(dx,dy);
	}

	@Override
	void scale(float s) {
		Point sr1 = new Point((point1.x+point2.x)/2,
					(point1.y+point2.y)/2);
		point1.x*=s; point1.y*=s;
		point2.x*=s; point2.y*=s;
		point3.x*=s; point3.y*=s;
		point4.x*=s; point4.y*=s;

		Point sr2 = new Point((point1.x+point2.x)/2,
				(point1.y+point2.y)/2);

		float dx=sr1.x-sr2.x;
		float dy=sr1.y-sr2.y;
		point1.move(dx,dy);
		point2.move(dx,dy);
		point3.move(dx,dy);
		point4.move(dx,dy);
	}

	@Override
	void draw(Graphics g) {
		setColor(g);
		g.drawLine((int)point1.x, (int)point1.y,
				(int)point2.x, (int)point2.y);
		g.drawLine((int)point2.x, (int)point2.y,
				(int)point3.x, (int)point3.y);
		g.drawLine((int)point3.x, (int)point3.y,
				(int)point4.x, (int)point4.y);
		g.drawLine((int)point4.x, (int)point4.y,
				(int)point1.x, (int)point1.y);
	}
}
class Cross extends Figure {
	Vector<Point> vertex;

	Cross()
	{
		vertex = new Vector<>(12);
		Point point1 = new Point();
		float length=random.nextFloat()*(400-3*point1.x);
		vertex.add(point1);

		Point point2 = new Point(point1.x+length, point1.y);
		vertex.add(point2);
		Point point3 = new Point(point1.x+length, point1.y-length);
		vertex.add(point3);
		Point point4 = new Point(point1.x+2*length, point1.y-length);
		vertex.add(point4);
		Point point5 = new Point(point1.x+2*length, point1.y);
		vertex.add(point5);
		Point point6 = new Point(point1.x+3*length, point1.y);
		vertex.add(point6);
		Point point7 = new Point(point1.x+3*length, point1.y+length);
		vertex.add(point7);
		Point point8 = new Point(point1.x+2*length, point1.y+length);
		vertex.add(point8);
		Point point9 = new Point(point1.x+2*length, point1.y+2*length);
		vertex.add(point9);
		Point point10 = new Point(point1.x+length, point1.y+2*length);
		vertex.add(point10);
		Point point11 = new Point(point1.x+length, point1.y+length);
		vertex.add(point11);
		Point point12 = new Point(point1.x, point1.y+length);
		vertex.add(point12);

	}

	@Override
	public boolean isInside(float px, float py) {
		if(px>vertex.elementAt(0).x&&
				px<vertex.elementAt(5).x&&
				py>vertex.elementAt(0).y&&
				py<vertex.elementAt(11).y)
		{
			return true;
		}


		if (px>vertex.elementAt(2).x&&
				px<vertex.elementAt(3).x&&
				py>vertex.elementAt(2).y&&
				py<vertex.elementAt(9).y)
		{
			return true;
		}
			return false;
	}

	@Override
	String getName()
	{
		return "Cross";
	}

	@Override
	float getX() {
		return (vertex.elementAt(0).x + vertex.elementAt(5).x)/2;
	}

	@Override
	float getY() {
		return (vertex.elementAt(2).y + vertex.elementAt(9).y)/2;
	}

	@Override
	float computeArea() {
		float sum =0;
		sum += (vertex.elementAt(5).x -vertex.elementAt(0).x)*(vertex.elementAt(6).y -vertex.elementAt(5).y);
		sum +=(vertex.elementAt(3).x -vertex.elementAt(2).x)*(vertex.elementAt(8).y -vertex.elementAt(3).y);
		sum -= (vertex.elementAt(4).x -vertex.elementAt(1).x)*(vertex.elementAt(7).y -vertex.elementAt(4).y);
		return sum;
	}

	@Override
	float computePerimeter() {
		return 12*(vertex.elementAt(1).x -vertex.elementAt(0).x);
	}

	@Override
	void move(float dx, float dy) {
		for(Point p:vertex)
			p.move(dx,dy);
	}

	@Override
	void scale(float s) {
		Point sr1 = new Point((vertex.elementAt(0).x),(vertex.elementAt(0).y));

		for(Point p:vertex) {
			p.x *= s;
			p.y *= s;
		}

		Point sr2 = new Point((vertex.elementAt(0).x),(vertex.elementAt(0).y));
		float dx=sr1.x-sr2.x;
		float dy=sr1.y-sr2.y;

		for(Point p:vertex) {
			p.move(dx,dy);
		}
	}

	@Override
	void draw(Graphics g)
	{
		setColor(g);

		for(int i=0; i< vertex.size()-1; i++) {

				g.drawLine((int) vertex.elementAt(i).x, (int) vertex.elementAt(i).y, (int) vertex.elementAt(i + 1).x, (int) vertex.elementAt(i + 1).y);
		}
		g.drawLine((int) vertex.elementAt(vertex.size()-1).x, (int) vertex.elementAt(vertex.size()-1).y, (int) vertex.elementAt(0).x, (int) vertex.elementAt(0).y);
	}
}
class Trapezium extends Figure{

	Point point1,point2,point3,point4;

	Trapezium(){
	point1 = new Point();
	float length = length=random.nextFloat()*(250);
	float height = random.nextFloat()*100;
	point2 = new Point(point1.x+length, point1.y);
	point3 = new Point(point2.x+length,point2.y+length);
	point4 = new Point(point1.x - length, point1.y+length);

	}

	@Override
	public boolean isInside(float px, float py) {

		try
		{
			if (py >= functionWithTrapezoidalGraph(px) && py <= point4.y)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		catch (Exception e)
		{
			return false;
		}


	}

	@Override
	String getName() {
		return "Trapezium{"+point1.toStringXY()+
				point2.toStringXY()+
				point3.toStringXY()+
				point4.toStringXY()+"}";
	}

	@Override
	float getX() {
		return ((point1.x+point2.x)/2);
	}

	@Override
	float getY() {
		return ((point1.y+point4.y)/2);
	}

	@Override
	float computeArea() {
		float widthOfTopSide= point2.x-point4.x;
		return ((4*widthOfTopSide)* widthOfTopSide)/2;
	}

	@Override
	float computePerimeter() {
		float widthOfTopSide= point2.x-point4.x;
		float lengthOfLeftAndRightSide =(float)Math.sqrt(2*(widthOfTopSide*widthOfTopSide));
		return (4*widthOfTopSide)+(2*lengthOfLeftAndRightSide);
	}

	@Override
	void move(float dx, float dy) {
		point1.move(dx,dy);
		point2.move(dx,dy);
		point3.move(dx,dy);
		point4.move(dx,dy);
	}

	@Override
	void scale(float s) {
		Point sr1 = new Point((point1.x+point2.x)/2,
				(point1.y+point2.y)/2);
		point1.x*=s; point1.y*=s;
		point2.x*=s; point2.y*=s;
		point3.x*=s; point3.y*=s;
		point4.x*=s; point4.y*=s;

		Point sr2 = new Point((point1.x+point2.x)/2,
				(point1.y+point2.y)/2);

		float dx=sr1.x-sr2.x;
		float dy=sr1.y-sr2.y;
		point1.move(dx,dy);
		point2.move(dx,dy);
		point3.move(dx,dy);
		point4.move(dx,dy);
	}

	@Override
	void draw(Graphics g) {
		setColor(g);
		g.drawLine((int)point1.x, (int)point1.y,
				(int)point2.x, (int)point2.y);
		g.drawLine((int)point2.x, (int)point2.y,
				(int)point3.x, (int)point3.y);
		g.drawLine((int)point3.x, (int)point3.y,
				(int)point4.x, (int)point4.y);
		g.drawLine((int)point4.x, (int)point4.y,
				(int)point1.x, (int)point1.y);
	}

	private float functionWithTrapezoidalGraph(float x) throws Exception
	{
		float y =0;

		if(x>=point4.x && x<=point1.x)
		{
			y = point4.y-(point1.x-point4.x);
		}else
		if(x>=point1.x && x<=point2.x)
		{
			y = point1.y;
		}else
		if(x>=point2.x && x<=point3.x)
		{
			y = point4.y-(point3.x-point2.x);
		}
		else
		{
			throw new Exception("Argument is out of domain ");
		}
		return y;
	}
}


class Picture extends JPanel implements KeyListener, MouseListener, MouseMotionListener {

	private static final long serialVersionUID = 1L;
	private float lastPositionOfMouse_X;
	private float lastPositionOfMouse_Y;

	Vector<Figure> figures = new Vector<Figure>();



	/*
	 * UWAGA: ta metoda b�dzie wywo�ywana automatycznie przy ka�dej potrzebie
	 * odrysowania na ekranie zawarto�ci panelu
	 *
	 * W tej metodzie NIE WOLNO !!! wywo�ywa� metody repaint()
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Figure f : figures)
			f.draw(g);
	}


    void addFigure(Figure fig)
    { for (Figure f : figures){ f.unselect(); }
      fig.select();
      figures.add(fig);
      repaint();
    }


    void moveAllFigures(float dx, float dy){
    	for (Figure f : figures){
    		if (f.isSelected()) f.move(dx,dy);
    	}
        repaint();
    }

    void scaleAllFigures(float s){
    	for (Figure f : figures)
        	{ if (f.isSelected()) f.scale(s);
        	}
          repaint();
    }

    public String toString(){
        String str = "Rysunek{ ";
        for(Figure f : figures)
            str+=f.toString() +"\n         ";
        str+="}";
        return str;
    }


    /*
     *  Impelentacja interfejsu KeyListener - obs�uga zdarze� generowanych
     *  przez klawiaturę gdy focus jest ustawiony na ten obiekt.
     */
    public void keyPressed (KeyEvent evt)
    //Virtual keys (arrow keys, function keys, etc) - handled with keyPressed() listener.
    {  int dist;
       if (evt.isShiftDown()) dist = 10;
                         else dist = 1;
		switch (evt.getKeyCode()) {
		case KeyEvent.VK_UP:
			moveAllFigures(0, -dist);
			break;
		case KeyEvent.VK_DOWN:
			moveAllFigures(0, dist);
			break;
		case KeyEvent.VK_LEFT:
			moveAllFigures(-dist,0 );
			break;
		case KeyEvent.VK_RIGHT:
			moveAllFigures(dist, 0);
			break;
		case KeyEvent.VK_DELETE:
			Iterator<Figure> i = figures.iterator();
			while (i.hasNext()) {
				Figure f = i.next();
				if (f.isSelected()) {
					i.remove();
				}
			}
			repaint();
			break;
		}
    }

   public void keyReleased (KeyEvent evt)
   {  }

   public void keyTyped (KeyEvent evt)
   //Characters (a, A, #, ...) - handled in the keyTyped() listener.
   {
     char znak=evt.getKeyChar(); //reakcja na przycisku na naci�ni�cie klawisza
		switch (znak) {
		case 'p':
			addFigure(new Point());
			break;
		case 'c':
			addFigure(new Circle());
			break;
		case 't':
			addFigure(new Triangle());
			break;
		case 'k':
			addFigure(new Square());
			break;
		case 'q':
			addFigure(new Cross());
			break;
		case 'w':
			addFigure(new Trapezium());
			break;
		case '+':
			scaleAllFigures(1.1f);
			break;
		case '-':
			scaleAllFigures(0.9f);
			break;
		}
   }


   /*
    * Implementacja interfejsu MouseListener - obs�uga zdarze� generowanych przez myszk�
    * gdy kursor myszki jest na tym panelu
    */
   public void mouseClicked(MouseEvent e)
   // Invoked when the mouse button has been clicked (pressed and released) on a component.
   { int px = e.getX();
     int py = e.getY();
     for (Figure f : figures)
       { if (e.isAltDown()==false) f.unselect();
         if (f.isInside(px,py)) f.select( !f.isSelected() );
       }
     repaint();
   }

   public void mouseEntered(MouseEvent e)
   //Invoked when the mouse enters a component.
   { }

   public void mouseExited(MouseEvent e)
   //Invoked when the mouse exits a component.
   { }


	public void mousePressed(MouseEvent e)
	// Invoked when a mouse button has been pressed on a component.
	{
		lastPositionOfMouse_X = e.getX();
		lastPositionOfMouse_Y = e.getY();
	}

   public void mouseReleased(MouseEvent e)
   //Invoked when a mouse button has been released on a component.
   { }

	@Override
	public void mouseDragged(MouseEvent e) {
		float willMoveX = e.getX() - lastPositionOfMouse_X ;
		float willMoveY = e.getY() - lastPositionOfMouse_Y ;

		moveAllFigures(willMoveX, willMoveY);

		lastPositionOfMouse_X = e.getX();
		lastPositionOfMouse_Y = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
//		System.out.println(e.getX());
//		System.out.println(e.getY());
	}


};



public class GraphicEditor extends JFrame implements ActionListener{


	private static final long serialVersionUID = 3727471814914970170L;


	private final String DESCRIPTION = "OPIS PROGRAMU\n\n" + "Aktywna klawisze:\n"
			+ "   strzalki ==> przesuwanie figur\n"
			+ "   SHIFT + strzalki ==> szybkie przesuwanie figur\n"
			+ "   +,-  ==> powiekszanie, pomniejszanie\n"
			+ "   DEL  ==> kasowanie figur\n"
			+ "   p  ==> dodanie nowego punktu\n"
			+ "   c  ==> dodanie nowego kola\n"
			+ "   t  ==> dodanie nowego trojkata\n"
			+ "   k  ==> dodanie nowego kwadratu\n"
			+ "   q  ==> dodanie nowego krzyza\n"
			+ "   w  ==> dodanie nowego trapezu\n"
			+ "\nOperacje myszka:\n" + "   klik ==> zaznaczanie figur\n"
			+ "   ALT + klik ==> zmiana zaznaczenia figur\n"
			+ "   przeciaganie ==> przesuwanie figur";
	private final String ABOUT_ME ="Autor: Dawid Siuda\nData: 18 listopad 2017";

	protected Picture picture;

	private JMenu[] menu = { new JMenu("Figury"),
			                 new JMenu("Edytuj"),
								new JMenu("Pomoc")};

	private JMenuItem[] items = { new JMenuItem("Punkt"),
			                      new JMenuItem("Kolo"),
			                      new JMenuItem("Trojkat"),
			                      new JMenuItem("Wypisz wszystkie"),
			                      new JMenuItem("Przesun w gore"),
			                      new JMenuItem("Przesun w dol"),
			                      new JMenuItem("Powieksz"),
			                      new JMenuItem("Pomniejsz"),
			new JMenuItem("Przesun w lewo"),
			new JMenuItem("Przesun w prawo"),
			new JMenuItem("Kwadrat"),
			new JMenuItem("Krzyz"),
			new JMenuItem("Trapez"),
			new JMenuItem("O autorze"),
			new JMenuItem("Opis programu"),
			                      };

	private JButton buttonPoint = new JButton("Punkt");
	private JButton buttonCircle = new JButton("Kolo");
	private JButton buttonTriangle = new JButton("Trojkat");
	private JButton buttonSquare = new JButton("Kwadrat");
	private JButton buttonCross = new JButton("Krzyz");
	private JButton buttonTrapezium = new JButton("Trapez");


    public GraphicEditor()
    { super ("Edytor graficzny");
      setSize(400,400);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      for (int i = 0; i < items.length; i++)
      	items[i].addActionListener(this);

      // dodanie opcji do menu "Figury"
      menu[0].add(items[0]);
      menu[0].add(items[1]);
      menu[0].add(items[2]);
	menu[0].add(items[10]);
	menu[0].add(items[11]);
		menu[0].add(items[12]);
      menu[0].addSeparator();
      menu[0].add(items[3]);

      // dodanie opcji do menu "Edytuj"
      menu[1].add(items[4]);
      menu[1].add(items[5]);
		menu[1].add(items[8]);
		menu[1].add(items[9]);
      menu[1].addSeparator();
      menu[1].add(items[6]);
      menu[1].add(items[7]);

		// dodanie opcji do menu "Pomoc"
		menu[2].add(items[13]);
		menu[2].add(items[14]);

      // dodanie do okna paska menu
      JMenuBar menubar = new JMenuBar();
      for (int i = 0; i < menu.length; i++)
      	menubar.add(menu[i]);
      setJMenuBar(menubar);

      picture=new Picture();
      picture.addKeyListener(picture);
      picture.setFocusable(true);
      picture.addMouseListener(picture);
      picture.addMouseMotionListener(picture);
      picture.setLayout(new FlowLayout());

      buttonPoint.addActionListener(this);
      buttonCircle.addActionListener(this);
      buttonTriangle.addActionListener(this);
	buttonSquare.addActionListener(this);
	buttonCross.addActionListener(this);
	buttonTrapezium.addActionListener(this);

      picture.add(buttonPoint);
      picture.add(buttonCircle);
      picture.add(buttonTriangle);
		picture.add(buttonSquare);
		picture.add(buttonCross);
		picture.add(buttonTrapezium);

      setContentPane(picture);
      setVisible(true);
    }

	public void actionPerformed(ActionEvent evt) {
		Object zrodlo = evt.getSource();

		if (zrodlo == buttonPoint)
			picture.addFigure(new Point());
		if (zrodlo == buttonCircle)
			picture.addFigure(new Circle());
		if (zrodlo == buttonTriangle)
			picture.addFigure(new Triangle());
		if (zrodlo == buttonSquare)
			picture.addFigure(new Square());
		if (zrodlo == buttonCross)
			picture.addFigure(new Cross());
		if (zrodlo == buttonTrapezium)
			picture.addFigure(new Trapezium());

		if (zrodlo == items[0])
			picture.addFigure(new Point());
		if (zrodlo == items[1])
			picture.addFigure(new Circle());
		if (zrodlo == items[2])
			picture.addFigure(new Triangle());
		if (zrodlo == items[3])
			JOptionPane.showMessageDialog(null, picture.toString());

		if (zrodlo == items[4])
			picture.moveAllFigures(0, -10);
		if (zrodlo == items[5])
			picture.moveAllFigures(0, 10);
		if (zrodlo == items[6])
			picture.scaleAllFigures(1.1f);
		if (zrodlo == items[7])
			picture.scaleAllFigures(0.9f);
		if (zrodlo == items[8])
			picture.moveAllFigures(-10, 0);
		if (zrodlo == items[9])
			picture.moveAllFigures(10, 0);
		if (zrodlo == items[10])
			picture.addFigure(new Square());
		if (zrodlo == items[11])
			picture.addFigure(new Cross());
		if (zrodlo == items[12])
			picture.addFigure(new Trapezium());
		if (zrodlo == items[13])
			JOptionPane.showMessageDialog(null, ABOUT_ME);
		if (zrodlo == items[14])
			JOptionPane.showMessageDialog(null, DESCRIPTION);
//
		picture.requestFocus(); // przywrocenie ogniskowania w celu przywrocenia
								// obslugi zadarez� pd klawiatury
		repaint();
	}

    public static void main(String[] args)
    { new GraphicEditor();
    }

};

