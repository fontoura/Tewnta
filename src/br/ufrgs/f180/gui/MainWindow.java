package br.ufrgs.f180.gui;

import java.util.ArrayList;

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
import org.eclipse.swt.widgets.Text;

import br.ufrgs.f180.elements.GameField;
import br.ufrgs.f180.elements.MovingElement;
import br.ufrgs.f180.elements.Robot;
import br.ufrgs.f180.elements.Robot.Team;
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
	private Button buttonStartGame;
	private Label labelScoreTeamB;
	private Label labelX;
	private Label labelScoreTeamA;
	private Label labelTeamB;
	private Label labelTeamA;
	private Label label1;
	private Label labelElapsedTimeCount;
	private Label labelElapsedTime;
	private Label DetailsLabel;
	private Text PlayerDetails;
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
	
	private boolean invalidPlayers = false;
	private ArrayList<String> playerNames;
	private Server server;
	private GameField field;

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}

	public MainWindow(Composite parent, int style) {
		super(parent, style);
		initGUI();
		server = new Server();
		playerNames = new ArrayList<String>();
	}
	
	/**
	* Initializes the GUI.
	*/
	private void initGUI() {
		try {
			this.setSize(969, 436);
			this.setBackground(SWTResourceManager.getColor(192, 192, 192));
			FormLayout thisLayout = new FormLayout();
			this.setLayout(thisLayout);
			{
				buttonStartGame = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData buttonStartGameLData = new FormData();
				buttonStartGameLData.width = 114;
				buttonStartGameLData.height = 23;
				buttonStartGameLData.left =  new FormAttachment(0, 1000, 723);
				buttonStartGameLData.top =  new FormAttachment(0, 1000, 94);
				buttonStartGame.setLayoutData(buttonStartGameLData);
				buttonStartGame.setText("Play");
				buttonStartGame.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						buttonStartGameWidgetSelected(evt);
					}
				});
			}
			{
				labelScoreTeamB = new Label(this, SWT.NONE);
				FormData labelScoreTeamBLData = new FormData();
				labelScoreTeamBLData.width = 102;
				labelScoreTeamBLData.height = 25;
				labelScoreTeamBLData.left =  new FormAttachment(0, 1000, 855);
				labelScoreTeamBLData.top =  new FormAttachment(0, 1000, 35);
				labelScoreTeamB.setLayoutData(labelScoreTeamBLData);
				labelScoreTeamB.setText("0");
				labelScoreTeamB.setAlignment(SWT.CENTER);
				labelScoreTeamB.setFont(SWTResourceManager.getFont("Tahoma", 14, 1, false, false));
				labelScoreTeamB.setForeground(SWTResourceManager.getColor(255, 0, 0));
			}
			{
				labelX = new Label(this, SWT.NONE);
				FormData labelXLData = new FormData();
				labelXLData.width = 18;
				labelXLData.height = 19;
				labelXLData.left =  new FormAttachment(0, 1000, 831);
				labelXLData.top =  new FormAttachment(0, 1000, 41);
				labelX.setLayoutData(labelXLData);
				labelX.setText("X");
				labelX.setAlignment(SWT.CENTER);
				labelX.setFont(SWTResourceManager.getFont("Tahoma", 12, 0, false, false));
			}
			{
				labelScoreTeamA = new Label(this, SWT.NONE);
				FormData labelScoreTeamALData = new FormData();
				labelScoreTeamALData.width = 102;
				labelScoreTeamALData.height = 25;
				labelScoreTeamALData.left =  new FormAttachment(0, 1000, 723);
				labelScoreTeamALData.top =  new FormAttachment(0, 1000, 35);
				labelScoreTeamA.setLayoutData(labelScoreTeamALData);
				labelScoreTeamA.setText("0");
				labelScoreTeamA.setAlignment(SWT.CENTER);
				labelScoreTeamA.setForeground(SWTResourceManager.getColor(0, 0, 255));
				labelScoreTeamA.setFont(SWTResourceManager.getFont("Tahoma", 14, 1, false, false));
			}
			{
				labelTeamB = new Label(this, SWT.NONE);
				FormData labelTeamBLData = new FormData();
				labelTeamBLData.width = 114;
				labelTeamBLData.height = 13;
				labelTeamBLData.left =  new FormAttachment(0, 1000, 841);
				labelTeamBLData.top =  new FormAttachment(0, 1000, 16);
				labelTeamB.setLayoutData(labelTeamBLData);
				labelTeamB.setText("Team B");
				labelTeamB.setAlignment(SWT.CENTER);
				labelTeamB.setForeground(SWTResourceManager.getColor(255, 0, 0));
			}
			{
				labelTeamA = new Label(this, SWT.NONE);
				FormData labelTeamALData = new FormData();
				labelTeamALData.width = 114;
				labelTeamALData.height = 13;
				labelTeamALData.left =  new FormAttachment(0, 1000, 723);
				labelTeamALData.top =  new FormAttachment(0, 1000, 16);
				labelTeamA.setLayoutData(labelTeamALData);
				labelTeamA.setText("Team A");
				labelTeamA.setAlignment(SWT.CENTER);
				labelTeamA.setForeground(SWTResourceManager.getColor(0, 0, 255));
			}
			{
				FormData labelElapsedTimeCountLData = new FormData();
				labelElapsedTimeCountLData.width = 114;
				labelElapsedTimeCountLData.height = 22;
				labelElapsedTimeCountLData.left =  new FormAttachment(0, 1000, 843);
				labelElapsedTimeCountLData.top =  new FormAttachment(0, 1000, 65);
				labelElapsedTimeCount = new Label(this, SWT.NONE);
				labelElapsedTimeCount.setLayoutData(labelElapsedTimeCountLData);
				labelElapsedTimeCount.setFont(SWTResourceManager.getFont("Tahoma", 12, 0, false, false));
			}
			{
				labelElapsedTime = new Label(this, SWT.NONE);
				FormData labelElapsedTimeLData = new FormData();
				labelElapsedTimeLData.width = 114;
				labelElapsedTimeLData.height = 22;
				labelElapsedTimeLData.left =  new FormAttachment(0, 1000, 723);
				labelElapsedTimeLData.top =  new FormAttachment(0, 1000, 66);
				labelElapsedTime.setLayoutData(labelElapsedTimeLData);
				labelElapsedTime.setText("Elapsed Time:");
				labelElapsedTime.setFont(SWTResourceManager.getFont("Tahoma", 12, 0, false, false));
			}
			{
				DetailsLabel = new Label(this, SWT.NONE);
				FormData DetailsLabelLData = new FormData();
				DetailsLabelLData.width = 114;
				DetailsLabelLData.height = 13;
				DetailsLabelLData.left =  new FormAttachment(0, 1000, 843);
				DetailsLabelLData.top =  new FormAttachment(0, 1000, 206);
				DetailsLabel.setLayoutData(DetailsLabelLData);
				DetailsLabel.setText("Player Details:");
			}
			{
				FormData PlayerDetailsLData = new FormData();
				PlayerDetailsLData.width = 108;
				PlayerDetailsLData.height = 194;
				PlayerDetailsLData.left =  new FormAttachment(0, 1000, 843);
				PlayerDetailsLData.top =  new FormAttachment(0, 1000, 222);
				PlayerDetails = new Text(this, SWT.MULTI | SWT.WRAP);
				PlayerDetails.setLayoutData(PlayerDetailsLData);
				PlayerDetails.setEditable(false);
			}
			{
				ToggleServer = new Button(this, SWT.TOGGLE | SWT.CENTER);
				ToggleServer.setText("Start Server");
				FormData ToggleServerLData = new FormData();
				ToggleServerLData.width = 114;
				ToggleServerLData.height = 23;
				ToggleServerLData.left =  new FormAttachment(0, 1000, 843);
				ToggleServerLData.top =  new FormAttachment(0, 1000, 94);
				ToggleServer.setLayoutData(ToggleServerLData);
				ToggleServer.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						ToggleServerWidgetSelected(evt);
					}
				});
			}
			{
				FootballField = new Canvas(this, SWT.DOUBLE_BUFFERED | SWT.NO_BACKGROUND);
				FormData FootballFieldLData = new FormData(700, 400);
				FootballFieldLData.width = 700;
				FootballFieldLData.height = 400;
				FootballFieldLData.left =  new FormAttachment(0, 1000, 11);
				FootballFieldLData.top =  new FormAttachment(0, 1000, 16);
				FootballField.setLayoutData(FootballFieldLData);
				FootballField.setBackground(SWTResourceManager.getColor(0, 0, 0));
				FootballField.setForeground(SWTResourceManager.getColor(0, 255, 128));
				FootballField.setSize(700, 400);
				FootballField.addPaintListener(new PaintListener() {
					public void paintControl(PaintEvent evt) {

				        // Draw the background
						evt.gc.setForeground(evt.gc.getForeground());
						evt.gc.setBackground(evt.gc.getBackground());
						evt.gc.fillRectangle(((Canvas)evt.getSource()).getClientArea());

						if(getField() != null) getField().draw(evt.gc);

					}
				});
			}
			{
				labelPlayers = new Label(this, SWT.NONE);
				labelPlayers.setText("Connected Players:");
				FormData labelPlayersLData = new FormData();
				labelPlayersLData.width = 114;
				labelPlayersLData.height = 13;
				labelPlayersLData.left =  new FormAttachment(0, 1000, 723);
				labelPlayersLData.top =  new FormAttachment(0, 1000, 206);
				labelPlayers.setLayoutData(labelPlayersLData);
			}
			{
				listPlayers = new List(this, SWT.NONE);
				FormData listPlayersLData = new FormData();
				listPlayersLData.width = 111;
				listPlayersLData.height = 194;
				listPlayersLData.left =  new FormAttachment(0, 1000, 723);
				listPlayersLData.top =  new FormAttachment(0, 1000, 222);
				listPlayers.setLayoutData(listPlayersLData);
				listPlayers.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						listPlayersWidgetSelected(evt);
					}
				});
			}
			{
				label1 = new Label(this, SWT.NONE);
				FormData label1LData = new FormData();
				label1LData.width = 114;
				label1LData.height = 13;
				label1LData.left =  new FormAttachment(0, 1000, 139);
				label1LData.top =  new FormAttachment(0, 1000, 388);
				label1.setLayoutData(label1LData);
				label1.setText("Connected Players:");
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
							exitMenuItem = new MenuItem(fileMenu, SWT.CASCADE);
							exitMenuItem.setText("Exit");
							exitMenuItem.addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent evt) {
									System.out.println("exitMenuItem.widgetSelected, event="+evt);
									MainWindow.this.getShell().dispose();
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
			Game.getInstance().setUp(this);			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	* Auto-generated main method to display this 
	* org.eclipse.swt.widgets.Composite inside a new Shell.
	 * @throws Exception 
	*/
	public static void main(String[] args) throws Exception {
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
		if(inst.server != null && inst.server.isStarted()){
			inst.server.stopServer();
		}
		System.exit(0);
	}

	public void gameLoop(double interval){
		Game.getInstance().updateState(interval);
	}

	public void repaintLoop(){
		if(invalidPlayers) updatePlayers();
		
		updateSelectedPlayer();
		updateElapsedTime();
		updateScores();
		updateButtons();

		if(FootballField != null) FootballField.redraw();
	}
	
	/**
	 * Update the buttons text that changes according to the game
	 */
	private void updateButtons() {
		if(Game.getInstance().getGameRunning()){
			buttonStartGame.setText("Pause");
		}
		else{
			buttonStartGame.setText("Play");
		}
	}
	
	private void updateScores() {
		String nameA = Game.getInstance().getNameTeamA();
		labelTeamA.setText(nameA != null ? nameA : "Team A");
		String nameB = Game.getInstance().getNameTeamB();
		labelTeamB.setText(nameB != null ? nameB : "Team B");
		labelScoreTeamA.setText(String.valueOf(Game.getInstance().getScoreTeamA()));
		labelScoreTeamB.setText(String.valueOf(Game.getInstance().getScoreTeamB()));
	}

	private void updateElapsedTime() {
		long time = Game.getInstance().getElapsedTime();
		labelElapsedTimeCount.setText(String.format("%tM:%tS:%tL", time, time, time));
	}

	private void updatePlayers(){
		listPlayers.removeAll();		
		for (String id : playerNames) {
			listPlayers.add(id);	
		}
		updateSelectedPlayer();		
		invalidPlayers = false;
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

	public Canvas getFootballFieldCanvas() {
		return FootballField;
	}

	public GameField getField() {
		return field;
	}

	public void setField(GameField field) {
		this.field = field;
	}

	public void addRobot(String id, double x, double y, Team team) throws Exception {
		if(getField() != null){
			Robot r = new Robot(x, y, team);
			getField().addElement(id, r);
			if(playerNames.indexOf(id) < 0){
				playerNames.add(id);
			}
			invalidPlayers = true;
		}
		else {
			throw new Exception("Cannot add element. Configure the field first");
		}
	}

	private void updateSelectedPlayer(){
		if(listPlayers.getSelection().length == 1){
			String selectedId = listPlayers.getSelection()[0];
			Robot e = (Robot) field.getElement(selectedId);
			StringBuffer details = new StringBuffer();
			details.append("Team: ");
			details.append(Game.getInstance().getTeamName(e.getTeam()));
			details.append("\n");
			details.append("Velocity:\n");
			details.append("  x:");
			details.append(String.format("%.2f", e.getVelocity().getX()));
			details.append("\n");
			details.append("  y:");
			details.append(String.format("%.2f", e.getVelocity().getY()));
			details.append("\n");
			details.append("Force:\n");
			details.append("  x:");
			details.append(String.format("%.2f", e.getForce().getX()));
			details.append("\n");
			details.append("  y:");
			details.append(String.format("%.2f", e.getForce().getY()));
			PlayerDetails.setText(details.toString());
		}
		else{
			PlayerDetails.setText("");
		}
	}
	
	private void listPlayersWidgetSelected(SelectionEvent evt) {
		System.out.println("listPlayers.widgetSelected, event="+evt);
		updateSelectedPlayer();
	}
	
	private void buttonStartGameWidgetSelected(SelectionEvent evt) {
		System.out.println("buttonStartGame.widgetSelected, event="+evt);
		if(Game.getInstance().getGameRunning()){
			Game.getInstance().setGameRunning(false);
		}
		else{
			Game.getInstance().setGameRunning(true);
		}
	}

}
