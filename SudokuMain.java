import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Random;

public class SudokuMain extends JFrame {
	private static final long serialVersionUID = 1L; // to prevent serial warning

	// private variables
	public static GameBoardPanel board = new GameBoardPanel();
	public static PauseScreen pauseScreen = new PauseScreen();
	private MenuBar menuBar = new MenuBar(board);
	public static ProgressBar progressBar = new ProgressBar();
	public static JPanel cardPanel = new JPanel(new CardLayout());
	public static CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
	public static PointTimer pointTimer = new PointTimer();
	public static JTextField username = new JTextField("Enter Username (e.g. Player1)");
	public static boolean WelcomeScreenState = true;
	Random random = new Random();
	Timer timer;

	public SudokuMain() {

		// Creating welcome screen
		JWindow w = new JWindow(this);
		w.setLayout(new BorderLayout());
		w.setSize(600, 600);
		w.setVisible(true);

		JPanel imgPanel = new JPanel();
		imgPanel.setBackground(Color.BLACK);
		w.add(imgPanel, BorderLayout.CENTER);
		ImageIcon icon = new ImageIcon("BackgroundImageSudoku.jpg");
		Image img = icon.getImage();
		JLabel image = new JLabel("");
		imgPanel.add(image);
		Image modifiedimage = img.getScaledInstance(600, 600, java.awt.Image.SCALE_SMOOTH);
		icon = new ImageIcon(modifiedimage);
		image.setIcon(icon);

		JPanel buttonPanel = new JPanel(new GridLayout(3, 1));
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 150, 10, 150));
		w.add(buttonPanel, BorderLayout.SOUTH);
		buttonPanel.setBackground(new Color(255, 172, 92));

		// Username TextField
		username.setEditable(true);
		username.setBackground(Color.WHITE);
		username.setForeground(Color.gray);
		username.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (username.getText().equals("Enter Username (e.g. Player1)")) {
                    username.setText("");
                    username.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (username.getText().isEmpty()) {
                    username.setForeground(Color.GRAY);
                    username.setText("Enter Username (e.g. Player1)");
                }
            }
        });

		// Start Button
		JButton btnStart = new JButton("START GAME");
		btnStart.setPreferredSize(new Dimension(200, 40));
		btnStart.setForeground(Color.BLACK);
		btnStart.setFont(new Font("Helvetica", Font.BOLD, 26));
		btnStart.setFocusPainted(false);


		// Difficulty Selection
		String[] diff = { "Easy", "Intermediate", "Difficult" };
		JComboBox<String> difficulty = new JComboBox<String>(diff);
		final String[] selectedDifficulty = { "" };
		difficulty.addActionListener(e -> {
			selectedDifficulty[0] = (String) difficulty.getSelectedItem();
			System.out.println("Selected difficulty: " + selectedDifficulty[0]);
		});

		buttonPanel.add(username);
		buttonPanel.add(difficulty);
		buttonPanel.add(btnStart);


		btnStart.addActionListener(e -> {
			w.dispose();
			pointTimer.start();
			if (selectedDifficulty[0].equals("Difficult")) {
				int difficultNum = random.nextInt(36, 65);
				board.newGame(difficultNum);
			} else if (selectedDifficulty[0].equals("Intermediate")) {
				int intermediateNum = random.nextInt(11, 36);
				board.newGame(intermediateNum);
			} else {
				int easyNum = random.nextInt(1, 11);
				board.newGame(easyNum);
			}
		
		});
		btnStart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnStart.setForeground(new Color(169, 121, 255));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnStart.setForeground((Color.BLACK));
			}
		});
		// Creating Container for Sudoku
		JPanel panelDisplay = new JPanel(new FlowLayout());
		
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		cp.add(panelDisplay, BorderLayout.SOUTH);
		cp.add(cardPanel);
		cardPanel.add(board, "board");
		cardPanel.add(pauseScreen, "pauseScreen");
		cp.add(menuBar.getMenuBar(), BorderLayout.NORTH);

		panelDisplay.add(progressBar);
		panelDisplay.add(pointTimer);

		pack(); // Pack the UI components, instead of using setSize()
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // to handle window-closing
		cardLayout.show(cardPanel, "board");
		setTitle("Sudoku");
		setSize(600, 600);
		setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new SudokuMain();

			}
		});
	}

}