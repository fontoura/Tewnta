package br.ufrgs.wumpus.gui;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
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
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import br.ufrgs.wumpus.elements.GameField;
import br.ufrgs.wumpus.server.Game;
import br.ufrgs.wumpus.server.Server;

import com.cloudgarden.resource.SWTResourceManager;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class MainWindow extends org.eclipse.swt.widgets.Composite {
	private static final Display display = Display.getDefault();
	private static Logger logger = Logger.getLogger(MainWindow.class);

	private Menu menu1;
	private Label labelScore;
	private Group groupScore;
	private Label label1;
	private Scale scaleTimeSpeed;
	private Group groupPlayers;
	private Button ToggleServer;
	private Group groupCommands;
	private Button buttonMarks;
	private Group groupDisplayOptions;
	private Button buttonResetGame;
	private Button buttonStartGame;
	private Text PlayerDetails;
	private Canvas WumpusField;
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
	private GameField field;

	/**
	 * Tells the engine how many real time millis represent one millisecond in
	 * the simulation.
	 */
	private double elapsedTimePerCycle = 1d;

	{
		// Register as a resource user - SWTResourceManager will
		// handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}

	public MainWindow(Composite parent, int style) throws Exception {
		super(parent, style);
		initGUI();
		// Create the game fixture
		setUpGame();
	}

	public void setUpGame() throws Exception {
		server = new Server();

		try {
			if (!server.isStarted()) {
				server.startServer();
			}
			ToggleServer.setText("Stop Server");
			ToggleServer.setSelection(true);
		} catch (Exception e) {
			logger.error("ERROR: ", e);
			;
			ToggleServer.setSelection(false);
		}

		// Set up the game
		Game.getInstance().setUp(this);
	}

	/**
	 * Initializes the GUI.
	 */
	private void initGUI() {
		try {
			this.setSize(660, 434);
			this.setBackground(SWTResourceManager.getColor(192, 192, 192));
			FormLayout thisLayout = new FormLayout();
			this.setLayout(thisLayout);
			{
				groupScore = new Group(this, SWT.NONE);
				GridLayout groupScoreLayout = new GridLayout();
				groupScoreLayout.makeColumnsEqualWidth = true;
				groupScore.setLayout(groupScoreLayout);
				FormData groupScoreLData = new FormData();
				groupScoreLData.width = 148;
				groupScoreLData.height = 30;
				groupScoreLData.left =  new FormAttachment(0, 1000, 0);
				groupScoreLData.top =  new FormAttachment(0, 1000, 0);
				groupScore.setLayoutData(groupScoreLData);
				groupScore.setText("Score");
				{
					labelScore = new Label(groupScore, SWT.NONE);
					GridData labelScoreLData = new GridData();
					labelScoreLData.widthHint = 141;
					labelScoreLData.heightHint = 18;
					labelScore.setLayoutData(labelScoreLData);
					labelScore.setText("Score");
					labelScore.setFont(SWTResourceManager.getFont("Tahoma", 10, 1, false, false));
				}
			}
			{
				groupPlayers = new Group(this, SWT.NONE);
				groupPlayers.setLayout(null);
				FormData groupPlayersLData = new FormData();
				groupPlayersLData.width = 148;
				groupPlayersLData.height = 211;
				groupPlayersLData.left =  new FormAttachment(0, 1000, 0);
				groupPlayersLData.top =  new FormAttachment(0, 1000, 207);
				groupPlayers.setLayoutData(groupPlayersLData);
				groupPlayers.setText("Cell information");
				{
					PlayerDetails = new Text(groupPlayers, SWT.MULTI | SWT.WRAP);
					PlayerDetails.setEditable(false);
					PlayerDetails.setBounds(12, 25, 132, 152);
				}
			}
			{
				groupCommands = new Group(this, SWT.NONE);
				groupCommands.setLayout(null);
				FormData groupCommandsLData = new FormData();
				groupCommandsLData.width = 148;
				groupCommandsLData.height = 104;
				groupCommandsLData.left =  new FormAttachment(0, 1000, 0);
				groupCommandsLData.top =  new FormAttachment(0, 1000, 46);
				groupCommands.setLayoutData(groupCommandsLData);
				groupCommands.setText("Commands");
				{
					ToggleServer = new Button(groupCommands, SWT.TOGGLE
							| SWT.CENTER);
					ToggleServer.setBounds(8, 18, 69, 23);
					ToggleServer.setText("Start Server");
					FormData ToggleServerLData = new FormData();
					ToggleServer.setText("Stop Server");
					ToggleServer.setSelection(true);
					ToggleServer.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {
							ToggleServerWidgetSelected(evt);
						}
					});
				}
				{
					buttonResetGame = new Button(groupCommands, SWT.PUSH
							| SWT.CENTER);
					buttonResetGame.setText("Reset");
					buttonResetGame.setBounds(95, 18, 40, 23);
					buttonResetGame
							.addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent evt) {
									buttonResetGameWidgetSelected(evt);
								}
							});
				}
				{
					buttonStartGame = new Button(groupCommands, SWT.PUSH
							| SWT.CENTER);
					buttonStartGame.setBounds(95, 47, 41, 23);
					buttonStartGame.setText("Play");
					buttonStartGame
							.addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent evt) {
									buttonStartGameWidgetSelected(evt);
								}
							});
				}
				{
					scaleTimeSpeed = new Scale(groupCommands, SWT.NONE);
					scaleTimeSpeed.setBounds(12, 78, 60, 30);
					scaleTimeSpeed.setSelection(100);
					scaleTimeSpeed.setMinimum(1);
					scaleTimeSpeed.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {
							scaleTimeSpeedWidgetSelected(evt);
						}
					});
				}
				{
					label1 = new Label(groupCommands, SWT.NONE);
					label1.setText("Time Speed:");
					label1.setBounds(12, 64, 60, 16);
				}
			}
			{
				groupDisplayOptions = new Group(this, SWT.NONE);
				groupDisplayOptions.setLayout(null);
				FormData groupDisplayOptionsLData = new FormData();
				groupDisplayOptionsLData.width = 148;
				groupDisplayOptionsLData.height = 25;
				groupDisplayOptionsLData.left =  new FormAttachment(0, 1000, 0);
				groupDisplayOptionsLData.top =  new FormAttachment(0, 1000, 166);
				groupDisplayOptions.setLayoutData(groupDisplayOptionsLData);
				groupDisplayOptions.setText("View");
				{
					buttonMarks = new Button(groupDisplayOptions, SWT.CHECK
							| SWT.LEFT);
					buttonMarks.setText("Display hidden cells");
					buttonMarks.setBounds(12, 19, 117, 17);
					buttonMarks.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {
							buttonMarksWidgetSelected(evt);
						}
					});
				}
			}
			this.addDisposeListener(new DisposeListener() {
				public void widgetDisposed(DisposeEvent evt) {
					thisWidgetDisposed(evt);
				}
			});
			{
				WumpusField = new Canvas(this, SWT.DOUBLE_BUFFERED
						| SWT.NO_BACKGROUND);
				FormData FootballFieldLData = new FormData();
				FootballFieldLData.width = 506;
				FootballFieldLData.height = 435;
				FootballFieldLData.left = new FormAttachment(0, 1000, 154);
				FootballFieldLData.top = new FormAttachment(0, 1000, -1);
				FootballFieldLData.bottom = new FormAttachment(1001, 1000, 0);
				FootballFieldLData.right = new FormAttachment(1000, 1000, 0);
				WumpusField.setLayoutData(FootballFieldLData);
				WumpusField
						.setBackground(SWTResourceManager.getColor(0, 80, 0));
				WumpusField.setForeground(SWTResourceManager.getColor(0, 0, 0));
				WumpusField.addMouseMoveListener(new MouseMoveListener() {
					public void mouseMove(MouseEvent evt) {
						WumpusFieldMouseMove(evt);
					}
				});
				WumpusField.addControlListener(new ControlAdapter() {
					public void controlResized(ControlEvent evt) {
						WumpusFieldControlResized(evt);
					}
				});
				WumpusField.addPaintListener(new PaintListener() {
					public void paintControl(PaintEvent evt) {

						// Draw the background
						evt.gc.setForeground(evt.gc.getForeground());
						evt.gc.setBackground(evt.gc.getBackground());
						evt.gc.fillRectangle(((Canvas) evt.getSource())
								.getClientArea());

						if (getField() != null)
							getField().draw(evt.gc);

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
							exitMenuItem = new MenuItem(fileMenu, SWT.CASCADE);
							exitMenuItem.setText("Exit");
							exitMenuItem
									.addSelectionListener(new SelectionAdapter() {
										public void widgetSelected(
												SelectionEvent evt) {
											System.out
													.println("exitMenuItem.widgetSelected, event="
															+ evt);
											MainWindow.this.getShell()
													.dispose();
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
							contentsMenuItem = new MenuItem(helpMenu,
									SWT.CASCADE);
							contentsMenuItem.setText("Contents");
						}
						{
							aboutMenuItem = new MenuItem(helpMenu, SWT.CASCADE);
							aboutMenuItem.setText("About");
							aboutMenuItem
									.addSelectionListener(new SelectionAdapter() {
										public void widgetSelected(
												SelectionEvent evt) {
											System.out
													.println("aboutMenuItem.widgetSelected, event="
															+ evt);

											AboutDialog about = new AboutDialog(
													getShell(), SWT.DIALOG_TRIM);
											about.open();
										}
									});
						}
						helpMenuItem.setMenu(helpMenu);
					}
				}
			}
			this.layout();
		} catch (Exception e) {
			logger.error("ERROR: ", e);
			;
		}
	}

	/**
	 * Auto-generated main method to display this
	 * org.eclipse.swt.widgets.Composite inside a new Shell.
	 * 
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		final Shell shell = new Shell(display);
		final MainWindow inst = new MainWindow(shell, SWT.NULL);
		Point size = inst.getSize();
		shell.setLayout(new FillLayout());
		shell.setText("Hunt the Wumpus");
		shell.setImage(SWTResourceManager.getImage("br/ufrgs/wumpus/icon.bmp"));
		shell.layout();
		if (size.x == 0 && size.y == 0) {
			inst.pack();
			shell.pack();
		} else {
			Rectangle shellBounds = shell.computeTrim(0, 0, size.x, size.y);
			shell.setSize(shellBounds.width, shellBounds.height);
		}
		shell.open();

		display.timerExec(0, new Runnable() {
			@Override
			public void run() {
				inst.gameLoop(Game.GAME_LOOP_INTERVAL / 1000d);
				display.timerExec(Game.GAME_LOOP_INTERVAL, this);
			}
		});

		display.timerExec(0, new Runnable() {
			@Override
			public void run() {
				if (!shell.isDisposed()) {
					inst.repaintLoop();
					display.timerExec(100, this);
				}
			}
		});

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		System.exit(0);
	}

	/**
	 * Update game state by increasing game time with the provided interval. The
	 * interval is fixed as to control the thread activity and keep it real
	 * time. But the game state will be updated according to the
	 * 
	 * @param interval
	 */
	public void gameLoop(double interval) {

		// calculates the time elapsed in the simulation
		double intervalInSimulation = interval * elapsedTimePerCycle;
		Game.getInstance().updateState(intervalInSimulation);
	}

	public void repaintLoop() {
		updateButtons();

		if (WumpusField != null) {
			WumpusField.redraw();
		}
	}

	/**
	 * Update the buttons text that changes according to the game
	 */
	private void updateButtons() {
		if (Game.getInstance().isGameRunning()) {
			buttonStartGame.setText("Pause");
		} else {
			buttonStartGame.setText("Play");
		}
	}

	private void ToggleServerWidgetSelected(SelectionEvent evt) {
		logger.debug("ToggleServer.widgetSelected, event=" + evt);
		if (ToggleServer.getSelection()) {
			try {
				if (!server.isStarted()) {
					server.startServer();
				}
				ToggleServer.setText("Stop Server");
			} catch (Exception e) {
				logger.error("ERROR: ", e);
				;
				ToggleServer.setSelection(false);
			}
		} else {
			try {
				if (server.isStarted()) {
					server.stopServer();
				}
				ToggleServer.setText("Start Server");
			} catch (Exception e) {
				logger.error("ERROR: ", e);
				;
				ToggleServer.setSelection(true);
			}
		}
	}

	public Canvas getFootballFieldCanvas() {
		return WumpusField;
	}

	public GameField getField() {
		return field;
	}

	public void setField(GameField field) {
		this.field = field;
	}

	private void buttonStartGameWidgetSelected(SelectionEvent evt) {
		logger.debug("buttonStartGame.widgetSelected, event=" + evt);
		if (Game.getInstance().isGameRunning()) {
			Game.getInstance().setGameRunning(false);
		} else {
			Game.getInstance().setGameRunning(true);
		}
	}

	private void buttonResetGameWidgetSelected(SelectionEvent evt) {
		logger.debug("buttonResetGame.widgetSelected, event=" + evt);
		Game.getInstance().resetGame();
	}

	/**
	 * Removes the robots from a team from the game
	 * 
	 * @param team
	 */

	private void thisWidgetDisposed(DisposeEvent evt) {
		logger.debug("this.widgetDisposed, event=" + evt);
		if (server != null && server.isStarted()) {
			try {
				server.stopServer();
			} catch (Exception e) {
				logger.error("ERROR: ", e);
				;
			}
		}
	}

	private void WumpusFieldControlResized(ControlEvent evt) {
		logger.debug("WumpusField.controlResized, event=" + evt);
		if (getField() != null)
			getField().updateProportions(WumpusField);
		if (WumpusField != null)
			WumpusField.redraw();
	}

	private void WumpusFieldMouseMove(MouseEvent evt) {
		field.setMousePosition(evt.x, evt.y);
	}

	private void scaleTimeSpeedWidgetSelected(SelectionEvent evt) {
		logger.debug("scaleTimeSpeed.widgetSelected, event=" + evt);
		double value = scaleTimeSpeed.getSelection();
		elapsedTimePerCycle = 0.01 * value;
	}
	
	private void buttonMarksWidgetSelected(SelectionEvent evt) {
		logger.debug("buttonMarks.widgetSelected, event="+evt);
		field.setDisplayHiddenCells(buttonMarks.getSelection());
	}
}
