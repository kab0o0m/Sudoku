
import java.awt.event.*;
import java.io.IOException;
import java.awt.*;
import javax.swing.*;
import java.util.logging.Logger;
import java.awt.Desktop;
import java.util.Random;

public class MenuBar extends JPanel {
	private Desktop desktop = Desktop.getDesktop();
	private JMenuBar menuBar = new JMenuBar();

	public MenuBar(GameBoardPanel board) {
		JMenu file = new JMenu("File");
		JMenu options = new JMenu("Options");
		JMenu help = new JMenu("Help");

		JMenuItem newGame = new JMenuItem("New Game");
		JMenuItem resetGame = new JMenuItem("Reset Game");
		JMenuItem exit = new JMenuItem("Exit");
		JMenuItem easy = new JMenuItem("Easy");
		JMenuItem intermediate = new JMenuItem("Intermediate");
		JMenuItem difficult = new JMenuItem("Difficult");
		Random random = new Random();
    	
		file.add(newGame);
		file.add(resetGame);
		file.add(exit);
		options.add(easy);
		options.add(intermediate);
		options.add(difficult);

		menuBar.add(file);
		menuBar.add(options);
		menuBar.add(help);

		help.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					desktop.browse(java.net.URI
							.create("https://www3.ntu.edu.sg/home/ehchua/programming/java/JavaGame_Sudoku.html"));
				} catch (IOException ex) {
					Logger.getLogger("test");
				}
			}
			// Main.class.getName()).log(Level.SEVERE, null, ex
			@Override
			public void mousePressed(MouseEvent e) {}	
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
		});
		
		//File
		//New Game
		newGame.addActionListener(e->{
			int ranGenNumber = random.nextInt(30);
			board.newGame(ranGenNumber);
			resetGameFunction();
		});
		//Exit game
		exit.addActionListener(e->System.exit(0));
		//Reset game
		resetGame.addActionListener(e->{
			board.resetGame();
			PointTimer.stateOfTimer = false;
			resetGameFunction();
		});

		easy.addActionListener(e->{
			int easyLevel = random.nextInt(1, 11);
			board.newGame(easyLevel);
			resetGameFunction();
		});
		intermediate.addActionListener(e->{
			int intermediateLevel = random.nextInt(11, 36);
			board.newGame(intermediateLevel);
			resetGameFunction();
		});
		difficult.addActionListener(e->{
			int hardLevel = random.nextInt(36, 65);
			board.newGame(hardLevel);
			resetGameFunction();
		});

	}

	public Component getMenuBar() {
		JRootPane rootPane = new JRootPane();
		rootPane.setJMenuBar(menuBar);
		return rootPane;
	}

	public void resetGameFunction(){
		PointTimer.GameBoardComponent.setVisible(true);
		PointTimer.pause.setText("Pause");
		PointTimer.stateOfTimer = false;
		SudokuMain.pointTimer.reset();
		SudokuMain.pointTimer.start();
		SudokuMain.progressBar.setNewValue(0);
	}

}

