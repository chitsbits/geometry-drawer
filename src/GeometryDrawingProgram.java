
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Geometry Drawing Program - ISC4UE
 * This class contains the main method
 * and static variables for the program
 * @author Sunny Jiao
 */
class GeometryDrawingProgram {
	
	private static JFrame frame;
	private static ArrayList<Shape> shapeList;	// ArrayList containing all shapes
	private static String shapeToAdd = "";		
	private static Shape currentShape;
	private static Scanner input;
	private static boolean isAdding = false;	// Flag to indicate adding process

	/**
	 * Main method
	 * @param args Command line arguments
	 */
	public static void main(String[] args) {
		
		shapeList = new ArrayList<Shape>();
		
		GeometryScreen gs = new GeometryScreen();
		input = new Scanner(System.in);

		// Main loop
		do {
			printMenu();
			int select = getIntegerInput();
			switch(select) {
				case 1 :	// Add shape
					isAdding = true;
					printAddChoice();
					int select2 = getIntegerInput();
					System.out.println("Click to add a point.");
					switch(select2) {
						case 1 :
							shapeToAdd = "Trapezoid";
							shapeList.add(new Trapezoid());
							break;
						case 2 :
							shapeToAdd = "Parallelogram";
							shapeList.add(new Parallelogram());
							break;
						case 3 :
							shapeToAdd = "Rhombus";
							shapeList.add(new Rhombus());
							break;
						case 4 :
							shapeToAdd = "Rectangle";
							shapeList.add(new Rectangle());
							break;
						case 5 :
							shapeToAdd = "Square";
							shapeList.add(new Square());
							break;
						case 6 :
							shapeToAdd = "Scalene Triangle";
							shapeList.add(new ScaleneTriangle());
							break;
						case 7 :
							shapeToAdd = "Iscosceles Triangle";
							shapeList.add(new IsoscelesTriangle());
							break;
						case 8 :
							shapeToAdd = "Equilateral Triangle";
							shapeList.add(new EquilateralTriangle());
							break;
						case 9 :
							shapeToAdd = "Ellipse";
							shapeList.add(new Ellipse());
							break;
						case 10 :
							shapeToAdd = "Circle";
							shapeList.add(new Circle());
							break;
						default :
							shapeToAdd = "None";
							isAdding = false;
					}
					System.out.println("Shape selected: " + shapeToAdd);
					if(isAdding) {
						// Create reference to the shape; used by draw method
						currentShape = shapeList.get(shapeList.size()-1);
					}				
					// Refresh graphics panel until shape is made with mouse
					while(isAdding) {
						frame.repaint();
					}
					break;				
				case 2 : // Remove shape
					removeShape();
					break;
				case 3 : // Translate single shape
					translateShape();
					break;				
				case 4 : // Translate drawing
					translateDrawing();
					break;				
				case 5 : // Display list of shapes and their data
					printShapeData();
					break;					
				case 6 : // Save drawing to a file
					saveDrawing();
					break;					
				case 7 : // Load drawing from a file
					loadDrawing();
					break;
				case 8 : // Quit program
					frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
					break;
				default :
					System.out.println("Invalid option");
					break;
			}
			frame.repaint(); // Update the screen
		} while(true);

	}
	
	/**
	 * Print the menu
	 */
	private static void printMenu() {
		System.out.println("\n1. Add Shape");
		System.out.println("2. Remove Shape");
		System.out.println("3. Translate Shape");
		System.out.println("4. Translate Drawing");
		System.out.println("5. Display Shape Data");
		System.out.println("6. Save Drawing");
		System.out.println("7. Load Drawing");
		System.out.print("8. Quit\n\n> ");
	}
	
	/**
	 * Print the choices for adding shapes
	 */
	private static void printAddChoice() {
		System.out.println("\nSelect shape to add:");
		System.out.println("1. Trapezoid\t\t2. Parallelogram");
		System.out.println("3. Rhombus\t\t4. Rectangle");
		System.out.println("5. Square\t\t6. Scalene Triangle");
		System.out.println("7. Iscosceles Triangle\t8. Equilateral Triangle");
		System.out.println("9. Ellipse\t\t10. Circle");
		System.out.print("11. Back\n\n> ");
	}
	
	/**
	 * Print the choices for removing shapes
	 */
	private static void removeShape() {
		if(shapeList.size() > 0) {
			printShapeData();
			System.out.print("Select a shape to remove\n\n> ");
			int select = input.nextInt();
			if(select < shapeList.size() && select >= 0) {
				System.out.println(shapeList.get(select).getClass().getSimpleName()+ " removed.");
				shapeList.remove(select);
			}
			else {
				System.out.println("Invalid number.");
			}
		}
		else {
			System.out.println("No shapes to remove.");
		}
	}
	
	/**
	 * Translate a specific shape
	 */
	private static void translateShape() {
		if(shapeList.size() > 0) {
			printShapeData();
			System.out.print("Select a shape to translate\n\n> ");
			int select = input.nextInt();
			if(select < shapeList.size() && select >= 0) {
				System.out.print("Input dx and dy\n\n> ");
				int dx = input.nextInt();
				int dy = input.nextInt();
				shapeList.get(select).translate(dx,dy);
				System.out.println(shapeList.get(select).getClass()+ " has been translated.");
			}
			else {
				System.out.println("Invalid number.");
			}
		}
		else {
			System.out.println("No shapes to translate.");
		}
	}
	
	/**
	 * Translate entire drawing
	 */
	private static void translateDrawing() {
		if(shapeList.size() > 0) {
			System.out.print("Input dx and dy\n\n> ");
			int dx2 = input.nextInt();
			int dy2 = input.nextInt();
			for(Shape s : shapeList) {
				s.translate(dx2,dy2);
			}
			System.out.println(shapeList.size() + " shapes have been translated.");
		}
		else {
			System.out.println("No shapes to translate.");
		}
	}
	
	/**
	 * Print data about each shape
	 */
	private static void printShapeData() {
		if(shapeList.size() > 0) {
			for(int i = 0; i < shapeList.size(); i++) {
				System.out.println(i + ": " + shapeList.get(i) + "\n");
			}
		}
		else {
			System.out.println("No shapes to display.");
		}
	}
	
	/**
	 * Create a serialization of the shape ArrayList
	 * and save it to a .ser file
	 */
	private static void saveDrawing() {
		System.out.print("Enter drawing name:\n\n> ");
		input.nextLine();
		String name = input.nextLine();
		try {
			FileOutputStream file = new FileOutputStream(name + ".ser");
			ObjectOutputStream out = new ObjectOutputStream(file);
			out.writeObject(shapeList);
	        out.close();
	        file.close();
	        System.out.println("Drawing saved as " + name + ".ser");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Read a serialized ArrayList from the working directory
	 * and reinitialize the shape array
	 */
	private static void loadDrawing() {
		System.out.print("Enter drawing name:\n\n> ");
		input.nextLine();
		String name = input.nextLine();
		try {
	         FileInputStream file = new FileInputStream(name + ".ser");
	         ObjectInputStream in = new ObjectInputStream(file);
	         shapeList = (ArrayList<Shape>) in.readObject();
	         in.close();
	         file.close();
	         System.out.println(name + ".ser has been loaded.");
	    }
		catch (IOException e) {
			System.out.println("Invalid file.");
	    }
		catch (ClassNotFoundException c) {
			System.out.println("Class not found.");
	    }
	}
	
	/**
	 * Repeatedly ask for an integer input 
	 * until one is properly given
	 * @return An integer from the user
	 */
	private static int getIntegerInput() {
		int select;
		while(true) {
			try {
				select = Integer.parseInt(input.next());
				break;
			}
			catch (NumberFormatException e) {
				System.out.println("Invalid input.");
			}
		}
		return select;
	}

	/**
	 * Contains and initializes the main JPanel
	 * @author Sunny Jiao
	 */
	public static class GeometryScreen {

		/**
		 * Constructor - Initializes the JFrame
		 */
		GeometryScreen() {
			frame = new JFrame("Geometry Drawing Program 2.0");
			// Create a new "custom" panel for graphics based on the inner class below
			JPanel graphicsPanel = new GraphicsPanel();
			// Add the panel and the frame to the window
			frame.getContentPane().add(graphicsPanel);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(1080,720);
			frame.setUndecorated(false);  //Set to true to remove title bar
			frame.setVisible(true);
		} 

		/**
		 * Main panel for the graphics display
		 * @author Sunny Jiao
		 */
		public static class GraphicsPanel extends JPanel implements MouseListener {
			
			private Point cursor;	// Mouse location
			
			/**
			 * Initialize graphics panel and
			 * add MouseListener
			 */
			public GraphicsPanel() {
				addMouseListener(this);
				cursor = new Point();
			}

			/**
			 * Draw on the JPanel
			 * @param g Graphics component to paint
			 */
			public void paintComponent(Graphics g) {     
				
				setDoubleBuffered(true);   
				g.setColor(Color.BLACK);

				// Set cursor coordinates
				cursor.x = MouseInfo.getPointerInfo().getLocation().x - getLocationOnScreen().x;
				cursor.y = MouseInfo.getPointerInfo().getLocation().y - getLocationOnScreen().y;
				
				if (isAdding) {			
					currentShape.drawGhost(g, cursor); // Draw the ghost of the shape to be added
				}
				// Draw all shapes
				if (!shapeList.isEmpty()) {
					for (Shape shape : shapeList) {
						if(shape != null && shape != currentShape) {
							shape.draw(g);
						}
					}
				}
			}

			/**
			 * Add a point to the shape currently being added
			 * using the cursor as input
			 * @param e MouseEvent from user's mouse
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
				// Append a point on the shape being added
				if(isAdding && currentShape != null) {
					if(currentShape.addPoint(cursor)) {
						isAdding = false;
						currentShape.initialize();
						currentShape = null;	// Delete reference
					}
				}
			}

			/* 
			 * The rest of the class are unused overriden
			 * methods from the MouseListener interface
			 */
			
			/**
			 * Unused method implementing MouseListener
			 * @param e MouseEvent
			 */
			@Override
			public void mouseEntered(MouseEvent e) {}

			/**
			 * Unused method implementing MouseListener
			 * @param e MouseEvent
			 */
			@Override
			public void mouseExited(MouseEvent e) {}

			/**
			 * Unused method implementing MouseListener
			 * @param e MouseEvent
			 */
			@Override
			public void mousePressed(MouseEvent e) {}

			/**
			 * Unused method implementing MouseListener
			 * @param e MouseEvent
			 */
			@Override
			public void mouseReleased(MouseEvent e) {}
		}
	}
}