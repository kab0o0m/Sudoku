import javax.swing.*;

public class ProgressBar extends JPanel{
    private JProgressBar progressBar;
    public ProgressBar(){
        progressBar = new JProgressBar(JProgressBar.HORIZONTAL, 0, 100);
        progressBar.setValue(0);
        super.add(progressBar);
        setVisible(true);
        
    }
    public void setNewValue(double value) {
        progressBar.setValue((int)value);
    }
}
