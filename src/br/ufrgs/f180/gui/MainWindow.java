package br.ufrgs.f180.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

import br.ufrgs.f180.elements.Ball;
import br.ufrgs.f180.elements.GameField;
import br.ufrgs.f180.elements.Robot;
import br.ufrgs.f180.math.Vector;

import com.cloudgarden.resource.SWTResourceManager;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class MainWindow extends org.eclipse.swt.widgets.Composite {

	private GameField gameField;
	private Menu menu1;
	private Canvas FootballField;
	private Label labelPlayers;
	private List listPlayers;
	private Button buttonStart;
	private MenuItem aboutMenuItem;
	private MenuItem contentsMenuItem;
	private Menu helpMenu;
	private MenuItem helpMenuItem;
	private MenuItem exitMenuItem;
	private MenuItem closeFileMenuItem;
	private MenuItem saveFileMenuItem;
	private MenuItem newFileMenuItem;
	private MenuItem openFileMenuItem;
	private Menu fileMenu;
	private MenuItem fileMenuItem;

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}

	public MainWindow(Composite parent, int style) {
		super(parent, style);
		initGUI();
	}
	
	/**
	* Initializes the GUI.
	*/
	private void initGUI() {
		try {
			this.setSize(743, 373);
			this.setBackground(SWTResourceManager.getColor(192, 192, 192));
			FormLayout thisLayout = new FormLayout();
			this.setLayout(thisLayout);
			{
				FootballField = new Canvas(this, SWT.NONE);
				FormData FootballFieldLData = new FormData();
				FootballFieldLData.width = 593;
				FootballFieldLData.height = 349;
				FootballFieldLData.left =  new FormAttachment(0, 1000, 11);
				FootballFieldLData.top =  new FormAttachment(0, 1000, 16);
				FootballField.setLayoutData(FootballFieldLData);
				FootballField.setBackground(SWTResourceManager.getColor(0, 0, 0));
				FootballField.setForeground(SWTResourceManager.getColor(0, 255, 128));
				FootballField.addPaintListener(new PaintListener() {
					//Double buffered
					public void paintControl(PaintEvent evt) {
						// Create the image to fill the canvas
				        Image image = new Image(evt.display, ((Canvas)evt.getSource()).getBounds());

				        // Set up the offscreen gc
				        GC gcImage = new GC(image);

				        // Draw the background
				        gcImage.setForeground(evt.gc.getForeground());
				        gcImage.setBackground(evt.gc.getBackground());
				        gcImage.fillRectangle(image.getBounds());

				        if(gameField != null) gameField.draw(gcImage);

				        // Draw the offscreen buffer to the screen
				        evt.gc.drawImage(image, 0, 0);

				        // Clean up
				        image.dispose();
				        gcImage.dispose();
					}
				});
			}
			{
				labelPlayers = new Label(this, SWT.NONE);
				labelPlayers.setText("Jogadores Conectados:");
				FormData labelPlayersLData = new FormData();
				labelPlayersLData.width = 114;
				labelPlayersLData.height = 13;
				labelPlayersLData.left =  new FormAttachment(0, 1000, 617);
				labelPlayersLData.top =  new FormAttachment(0, 1000, 15);
				labelPlayers.setLayoutData(labelPlayersLData);
			}
			{
				listPlayers = new List(this, SWT.NONE);
				FormData listPlayersLData = new FormData();
				listPlayersLData.width = 111;
				listPlayersLData.height = 194;
				listPlayersLData.left =  new FormAttachment(0, 1000, 617);
				listPlayersLData.top =  new FormAttachment(0, 1000, 31);
				listPlayers.setLayoutData(listPlayersLData);
			}
			{
				buttonStart = new Button(this, SWT.PUSH | SWT.CENTER);
				buttonStart.setText("Iniciar");
				FormData buttonStartLData = new FormData();
				buttonStartLData.width = 41;
				buttonStartLData.height = 23;
				buttonStartLData.left =  new FormAttachment(0, 1000, 617);
				buttonStartLData.top =  new FormAttachment(0, 1000, 237);
				buttonStart.setLayoutData(buttonStartLData);
				buttonStart.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						buttonStartWidgetSelected(evt);
					}
				});
			}
			{
				menu1 = new Menu(getShell(), SWT.BAR);
				getShell().setMenuBar(menu1);
				{
					fileMenuItem = new MenuItem(menu1, SWT.CASCADE);
					fileMenuItem.setText("File");
					{
						fileMenu = new Menu(fileMenuItem);
						{
							openFileMenuItem = new MenuItem(fileMenu, SWT.CASCADE);
							openFileMenuItem.setText("Open");
						}
						{
							newFileMenuItem = new MenuItem(fileMenu, SWT.CASCADE);
							newFileMenuItem.setText("New");
						}
						{
							saveFileMenuItem = new MenuItem(fileMenu, SWT.CASCADE);
							saveFileMenuItem.setText("Save");
						}
						{
							closeFileMenuItem = new MenuItem(fileMenu, SWT.CASCADE);
							closeFileMenuItem.setText("Close");
						}
						{
							exitMenuItem = new MenuItem(fileMenu, SWT.CASCADE);
							exitMenuItem.setText("Exit");
							exitMenuItem.addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent evt) {
									System.out.println("exitMenuItem.widgetSelected, event="+evt);
									System.exit(0);
								}
							});
						}
						fileMenuItem.setMenu(fileMenu);
					}
				}
				{
					helpMenuItem = new MenuItem(menu1, SWT.CASCADE);
					helpMenuItem.setText("Help");
					{
						helpMenu = new Menu(helpMenuItem);
						{
							contentsMenuItem = new MenuItem(helpMenu, SWT.CASCADE);
							contentsMenuItem.setText("Contents");
						}
						{
							aboutMenuItem = new MenuItem(helpMenu, SWT.CASCADE);
							aboutMenuItem.setText("About");
						}
						helpMenuItem.setMenu(helpMenu);
					}
				}
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	* Auto-generated main method to display this 
	* org.eclipse.swt.widgets.Composite inside a new Shell.
	*/
	public static void main(String[] args) {
		final Display display = Display.getDefault();
		final Shell shell = new Shell(display);
		final MainWindow inst = new MainWindow(shell, SWT.NULL);
		Point size = inst.getSize();
		shell.setLayout(new FillLayout());
		shell.layout();
		if(size.x == 0 && size.y == 0) {
			inst.pack();
			shell.pack();
		} else {
			Rectangle shellBounds = shell.computeTrim(0, 0, size.x, size.y);
			shell.setSize(shellBounds.width, shellBounds.height);
		}
		shell.open();
	
		display.timerExec(0, new Runnable(){
			@Override
			public void run() {
				inst.gameLoop();
				display.timerExec(5, this);
			}
		}
		);
		
		display.timerExec(0, new Runnable(){
			@Override
			public void run() {
				if(!shell.isDisposed()){
					inst.repaintLoop();
					display.timerExec(40, this);
				}
			}
		}
		);
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	private void buttonStartWidgetSelected(SelectionEvent evt) {
		System.out.println("buttonStart.widgetSelected, event="+evt);
		//Initiate the game Field
		gameField = new GameField(FootballField, 593, 349);
		gameField.addElement(new Robot(200, 100));
		gameField.addElement(new Robot(200, 200));
		gameField.addElement(new Robot(200, 300));
		gameField.addElement(new Ball(280, 100));
		gameField.addElement(new Ball(140, 71));
		gameField.addElement(new Ball(160, 72));
		gameField.addElement(new Ball(180, 73));
		gameField.addElement(new Ball(210, 74));
		gameField.addElement(new Ball(230, 75));
		gameField.addElement(new Ball(250, 76));
		gameField.addElement(new Ball(300, 77));
		gameField.addElement(new Ball(320, 78));
		gameField.addElement(new Ball(340, 79));
		gameField.addElement(new Ball(360, 71));
		gameField.addElement(new Ball(380, 71));
		gameField.addElement(new Ball(410, 71));
		gameField.addElement(new Ball(430, 71));
		gameField.addElement(new Ball(450, 71));
		gameField.addElement(new Ball(100, 171));
		gameField.addElement(new Ball(120, 172));
		gameField.addElement(new Ball(140, 173));
		gameField.addElement(new Ball(160, 174));
		gameField.addElement(new Ball(180, 175));
		gameField.addElement(new Ball(210, 176));
		gameField.addElement(new Ball(230, 177));
		gameField.addElement(new Ball(250, 178));
		gameField.addElement(new Ball(300, 178));
		gameField.addElement(new Ball(320, 171));
		gameField.addElement(new Ball(340, 171));
		gameField.addElement(new Ball(360, 171));
		gameField.addElement(new Ball(380, 171));
		gameField.addElement(new Ball(410, 171));
		gameField.addElement(new Ball(430, 171));
		gameField.addElement(new Ball(450, 171));
	}
	
	public void gameLoop(){
		if(gameField != null){
			gameField.updateElementsState(0.005);
		}
	}

	public void repaintLoop(){
		if(FootballField != null) FootballField.redraw();
	}
}
