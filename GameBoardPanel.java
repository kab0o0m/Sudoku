
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameBoardPanel extends JPanel {
	private static final long serialVersionUID = 1L; // to prevent serial warning

	// Define named constants for the game board properties
	public static final int GRID_SIZE = 9; // Size of the board
	public static final int SUBGRID_SIZE = 3; // Size of the sub-grid
	// Define named constants for UI sizes
	public static final int CELL_SIZE = 60;
	public static final int BOARD_WIDTH = CELL_SIZE * GRID_SIZE;
	public static final int BOARD_HEIGHT = CELL_SIZE * GRID_SIZE;

	// Define properties
	/** The game board composes of 9x9 Cells (customized JTextFields) */
	private Cell[][] cells = new Cell[GRID_SIZE][GRID_SIZE];
	/** It also contains a Puzzle with array numbers and isGiven */
	private Puzzle puzzle = new Puzzle();
	private int[][] copyNumbers = new int[GRID_SIZE][GRID_SIZE];
	private boolean[][] copyBoolean = new boolean[GRID_SIZE][GRID_SIZE];
	

	/** Constructor */
	public GameBoardPanel() {
		super.setLayout(new GridLayout(GRID_SIZE, GRID_SIZE)); // JPanel
		// Allocate the 2D array of Cell, and added into JPanel.
		for (int row = 0; row < GRID_SIZE; ++row) {
			for (int col = 0; col < GRID_SIZE; ++col) {
				cells[row][col] = new Cell(row, col);
				super.add(cells[row][col]); // JPanel
			}
		}

		CellInputListener lis = new CellInputListener();
		for (int row = 0; row < GRID_SIZE; ++row) {
			for (int col = 0; col < GRID_SIZE; ++col) {
				if (cells[row][col].isEditable()) {
					cells[row][col].addActionListener(lis);
				}
			}
		}
		
		super.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));

	}
	
	/**
	 * Generate a new puzzle; and reset the gameboard of cells based on the puzzle.
	 * You can call this method to start a new game.
	 */
	public void newGame(int cellsToGuess) {
		// Generate a new puzzle
		puzzle.newPuzzle(cellsToGuess);
		// Initialize all the 9x9 cells, based on the puzzle.
		for (int row = 0; row < GRID_SIZE; ++row) {
			for (int col = 0; col < GRID_SIZE; ++col) {
				cells[row][col].newGame(puzzle.numbers[row][col], puzzle.isGiven[row][col], row, col);
			}
		}
		copyNumbers = puzzle.getPuzzleNumbers();
		copyBoolean = puzzle.getPuzzleBoolean();
		
	}
	
	public void resetGame() {
		for (int row = 0; row < GRID_SIZE; ++row) {
			for (int col = 0; col < GRID_SIZE; ++col) {
				cells[row][col].newGame(copyNumbers[row][col], copyBoolean[row][col], row, col);
			}
		}
	}

	/**
	 * Return true if the puzzle is solved i.e., none of the cell have status of
	 * TO_GUESS or WRONG_GUESS
	 */
	public boolean isSolved() {
		for (int row = 0; row < GRID_SIZE; ++row) {
			for (int col = 0; col < GRID_SIZE; ++col) {
				if (cells[row][col].status == CellStatus.TO_GUESS || cells[row][col].status == CellStatus.WRONG_GUESS) {
					return false;
				}
			}
		}
		return true;
	}

	private class CellInputListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			Cell sourceCell = (Cell) e.getSource();
			int numberIn = Integer.parseInt(sourceCell.getText());
			int currentCellRow = sourceCell.row, currentCellCol = sourceCell.col;
			int[][] conflictCells = new int[9][9];
			System.out.println("You entered " + numberIn);

			for (int col = 0; col < GRID_SIZE; col++) {
				if (col != currentCellCol && cells[currentCellRow][col].getText().equals(sourceCell.getText())) {
					conflictCells[currentCellRow][col] = 1;
				}
			}

			for (int row = 0; row < GRID_SIZE; row++) {
				if (row != currentCellRow && cells[row][currentCellCol].getText().equals(sourceCell.getText())) {
					conflictCells[row][currentCellCol] = 1;
				}
			}
			int boxRowStart = (currentCellRow / 3) * 3;
			int boxColStart = (currentCellCol / 3) * 3;
			for (int row = boxRowStart; row < boxRowStart + 3; row++) {
				for (int col = boxColStart; col < boxColStart + 3; col++) {
					if (row != currentCellRow && col != currentCellCol
							&& cells[row][col].getText().equals(sourceCell.getText())) {
						conflictCells[row][col] = 1;
					}
				}
			}
			for (int row = 0; row < GRID_SIZE; ++row) {
				for (int col = 0; col < GRID_SIZE; ++col) {
					System.out.print(conflictCells[row][col] + " ");
					if (conflictCells[row][col] == 1) {
						if (cells[row][col].isEditable()) {
							cells[row][col].status = CellStatus.WRONG_GUESS;
						} else if (!cells[row][col].isEditable()) {
							cells[row][col].status = CellStatus.IMMUTABLE_CONFLICT;
						}
						cells[row][col].paint();
						sourceCell.status = CellStatus.WRONG_GUESS;
					}
					final int finalRow = row;
					final int finalCol = col;
					Timer timer = new Timer(1000, new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							if (!cells[finalRow][finalCol].isEditable()) {
								cells[finalRow][finalCol].status = CellStatus.GIVEN;
								if((finalRow>=3&&finalRow<=5||finalCol>=3&&finalCol<=5)){
									cells[finalRow][finalCol].setBackground(Cell.BG_GIVEN_LIGHT);
								 }else{
									cells[finalRow][finalCol].setBackground(Cell.BG_GIVEN_DARK);
								 }
								 if((finalRow >= 3 && finalRow <= 5) && (finalCol >= 3 && finalCol <= 5)){
									cells[finalRow][finalCol].setBackground(Cell.BG_GIVEN_DARK);
								 }
							}
						}
					});
					timer.setRepeats(false);
					timer.start();
				}

				System.out.println();
			}

			boolean noConflicts = true;
			for (int row = 0; row < GRID_SIZE; ++row) {
				for (int col = 0; col < GRID_SIZE; ++col) {
					if (conflictCells[row][col] == 1 && !(row == currentCellRow && col == currentCellCol)) {
						noConflicts = false;
						break;
					}
				}
				if (!noConflicts) {
					sourceCell.status = CellStatus.WRONG_GUESS;
					break;
				}
			}
			if (noConflicts) {
				sourceCell.status = CellStatus.CORRECT_GUESS;
			}
			sourceCell.paint();

			//updates the progress bar

			int totalToGuess = 0, totalCorrectGuess=0;

			for (int row = 0; row < GRID_SIZE; ++row) {
				for (int col = 0; col < GRID_SIZE; ++col) {
					if(cells[row][col].isEditable()){
						totalToGuess++;
						if(cells[row][col].status == CellStatus.CORRECT_GUESS){
							totalCorrectGuess++;
						}
					};
			
				}
			}
			SudokuMain.progressBar.setNewValue((double)totalCorrectGuess * 100.0/(double)totalToGuess);



			//if the puzzle is solved
			if (isSolved()) {
				SudokuMain.pointTimer.stop();
				PointTimer.stateOfTimer = false;
				if(SudokuMain.username.getText().equals("Enter Username (e.g. Player1)")){
					JOptionPane.showMessageDialog(null, "Congratulations! Your time was "+SudokuMain.pointTimer.getText());
				}else{
					JOptionPane.showMessageDialog(null, "Congratulations! "+SudokuMain.username.getText()+"'s time was "+SudokuMain.pointTimer.getText());
				}
			}
		}
	}
}
