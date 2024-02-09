import javax.swing.*;
import java.awt.event.*;
import java.awt.Component;


public class PointTimer extends JPanel {
    private int second, minute, hour;
    private Timer timer;
    private ActionListener listener;
    public static boolean stateOfTimer = false;
    private JTextField TimerField;
    public static JButton pause;
    public static Component SudokuPauseScreen;
    public static Component GameBoardComponent;
	
    public PointTimer() {
        TimerField = new JTextField(6);
        super.add(TimerField);
 
        pause = new JButton("Pause");
        pause.addActionListener(e-> {
        	
            GameBoardComponent = SudokuMain.board;
            SudokuPauseScreen = SudokuMain.pauseScreen;
            // SudokuPauseScreen.setVisible(!SudokuPauseScreen.isVisible());
            GameBoardComponent.setVisible(!GameBoardComponent.isVisible());
        	if(pause.getText().equals("Pause")){
                timer.stop();
                pause.setText("Resume");
                SudokuMain.cardLayout.show(SudokuMain.cardPanel, "pauseScreen");
            }else{
                timer.start();
                pause.setText("Pause");
                SudokuMain.cardLayout.show(SudokuMain.cardPanel, "screen");
            }
        });
        super.add(pause);
        TimerField.setEditable(false);
        second = 0;minute = 0;hour = 0;
        listener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                second++;
                if(second==60){
                    second=0;
                    minute++;
                    if(minute==60){
                        minute=0;
                        hour++;
                    }
                }
                TimerField.setText(String.format("%02d:%02d:%02d", hour, minute, second));
                
            }
        };
        timer = new Timer(1000, listener);
    }

    public void start() {
        timer.start();
        stateOfTimer = true;
    }

    public void stop() {
        timer.stop();
        stateOfTimer = false;
    }
    public void reset(){
        timer.stop();
        second = 0;minute = 0;hour = 0;
        TimerField.setText("00:00:00");
    }
    public String getText(){
        return TimerField.getText();
    }
    
    
    
}



