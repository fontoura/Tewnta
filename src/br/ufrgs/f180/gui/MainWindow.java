package br.ufrgs.f180.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
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

import br.ufrgs.f180.server.Game;
import br.ufrgs.f180.server.Server;

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

	private Menu menu1;
	private Button ToggleServer;
	private Canvas FootballField;
	private Label labelPlayers;
	private List listPlayers;
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
	private Server server;

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}

	public MainWindow(Composite parent, int style) {
		super(parent, style);
		initGUI();
		server = new Server();
	}
	
	/**
	* Initializes the GUI.
	*/
	private void initGUI() {
		try {
			this.setSize(750, 430);
			this.setBackground(SWTResourceManager.getColor(192, 192, 192));
			FormLayout thisLayout = new FormLayout();
			this.setLayout(thisLayout);
			{
				ToggleServer = new Button(this, SWT.TOGGLE | SWT.CENTER);
				ToggleServer.setText("Start Server");
				FormData ToggleServerLData = new FormData();
				ToggleServerLData.width = 82;
				ToggleServerLData.height = 23;
				ToggleServerLData.left =  new FormAttachment(0, 1000, 623);
				ToggleServerLData.top =  new FormAttachment(0, 1000, 238);
				ToggleServer.setLayoutData(ToggleServerLData);
				ToggleServer.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						ToggleServerWidgetSelected(evt);
					}
				});
			}
			{
				FootballField = new Canvas(this, SWT.DOUBLE_BUFFERED | SWT.NO_BACKGROUND);
				FormData FootballFieldLData = new FormData(600, 400);
				FootballFieldLData.width = 600;
				FootballFieldLData.height = 400;
				FootballFieldLData.left =  new FormAttachment(0, 1000, 11);
				FootballFieldLData.top =  new FormAttachment(0, 1000, 16);
				FootballField.setLayoutData(FootballFieldLData);
				FootballField.setBackground(SWTResourceManager.getColor(0, 0, 0));
				FootballField.setForeground(SWTResourceManager.getColor(0, 255, 128));
				FootballField.setSize(600, 400);
				FootballField.addPaintListener(new PaintListener() {
					public void paintControl(PaintEvent evt) {

				        // Draw the background
						evt.gc.setForeground(evt.gc.getForeground());
						evt.gc.setBackground(evt.gc.getBackground());
						evt.gc.fillRectangle(((Canvas)evt.getSource()).getClientArea());

				        Game.getInstance().draw(evt.gc);

					}
				});
			}
			{
				labelPlayers = new Label(this, SWT.NONE);
				labelPlayers.setText("Connected Players:");
				FormData labelPlayersLData = new FormData();
				labelPlayersLData.width = 114;
				labelPlayersLData.height = 13;
				labelPlayersLData.left =  new FormAttachment(0, 1000, 624);
				labelPlayersLData.top =  new FormAttachment(0, 1000, 16);
				labelPlayers.setLayoutData(labelPlayersLData);
			}
			{
				listPlayers = new List(this, SWT.NONE);
				FormData listPlayersLData = new FormData();
				listPlayersLData.width = 111;
				listPlayersLData.height = 194;
				listPlayersLData.left =  new FormAttachment(0, 1000, 624);
				listPlayersLData.top =  new FormAttachment(0, 1000, 32);
				listPlayers.setLayoutData(listPlayersLData);
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

			//Set up the game
			Game.getInstance().setUpField(FootballField);			
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
				inst.gameLoop(Game.GAME_LOOP_INTERVAL / 1000d);
				display.timerExec(Game.GAME_LOOP_INTERVAL, this);
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
	}
	
	public void gameLoop(double interval){
		Game.getInstance().updateState(interval);
	}

	public void repaintLoop(){
		if(FootballField != null) FootballField.redraw();
	}
	
	private void ToggleServerWidgetSelected(SelectionEvent evt) {
		System.out.println("ToggleServer.widgetSelected, event="+evt);
		if(ToggleServer.getSelection()){
			try {
				if(!server.isStarted()){
					server.startServer();
				}
				ToggleServer.setText("Stop Server");
			} catch (Exception e) {
				e.printStackTrace();
				ToggleServer.setSelection(false);
			}
		}
		else{
			try {
				if(server.isStarted()){
					server.stopServer();
				}
				ToggleServer.setText("Start Server");
			} catch (Exception e) {
				e.printStackTrace();
				ToggleServer.setSelection(true);
			}
		}
	}

}
