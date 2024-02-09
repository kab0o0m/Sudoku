

import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
/**
 * The Cell class model the cells of the Sudoku puzzle, by customizing (subclass)
 * the javax.swing.JTextField to include row/column, puzzle number and status.
 */
public class Cell extends JTextField {
   private static final long serialVersionUID = 1L;  // to prevent serial warning

   // Define named constants for JTextField's colors and fonts
   //  to be chosen based on CellStatus
   public static final Color BG_GIVEN_LIGHT = new Color(255, 210, 156); // RGB
   public static final Color BG_GIVEN_DARK = new Color(255, 172, 135); 
   public static final Color FG_GIVEN = Color.BLACK;
   public static final Color FG_NOT_GIVEN = Color.GRAY;
   public static final Color BG_TO_GUESS  = new Color(255, 248, 216);
   public static final Color BG_CORRECT_GUESS = new Color(170, 234, 170);
   public static final Color BG_WRONG_GUESS   = new Color(255, 115, 71);
   public static final Color BG_IMMUTABLE_CONFLICT = new Color(169, 121, 255);
   public static final Font FONT_NUMBERS = new Font("Helvetica", Font.BOLD, 28);

   // Define properties (package-visible)
   /** The row and column number [0-8] of this cell */
   int row, col;
   /** The puzzle number [1-9] for this cell */
   int number;
   /** The status of this cell defined in enum CellStatus */
   CellStatus status;

   /** Constructor */
   public Cell(int row, int col) {
      super();   // JTextField
      this.row = row;
      this.col = col;
      // Inherited from JTextField: Beautify all the cells once for all
      super.setHorizontalAlignment(JTextField.CENTER);
      super.setFont(FONT_NUMBERS);
   }

   /** Reset this cell for a new game, given the puzzle number and isGiven */
   public void newGame(int number, boolean isGiven, int row, int col) {
      this.number = number;
      status = isGiven ? CellStatus.GIVEN : CellStatus.TO_GUESS;
      paint();
      if(status == CellStatus.GIVEN){
         if((row>=3&&row<=5||col>=3&&col<=5)){
            super.setBackground(BG_GIVEN_LIGHT);
         }else{
            super.setBackground(BG_GIVEN_DARK);
         }
         if((row >= 3 && row <= 5) && (col >= 3 && col <= 5)){
            super.setBackground(BG_GIVEN_DARK);
         }
      }

   }

   /** This Cell (JTextField) paints itself based on its status */
   public void paint() {
      if (status == CellStatus.GIVEN) {
         // Inherited from JTextField: Set display properties
         super.setText(number + "");
         super.setEditable(false);
         super.setForeground(FG_GIVEN);
      } else if (status == CellStatus.TO_GUESS) {
         // Inherited from JTextField: Set display properties
         super.setText("");
         super.setEditable(true);
         super.setBackground(BG_TO_GUESS);
         super.setForeground(FG_NOT_GIVEN);
      } else if (status == CellStatus.CORRECT_GUESS) {  // from TO_GUESS
         super.setBackground(BG_CORRECT_GUESS);
      } else if (status == CellStatus.WRONG_GUESS) {    // from TO_GUESS
         super.setBackground(BG_WRONG_GUESS);
      } else if (status == CellStatus.IMMUTABLE_CONFLICT){
         super.setBackground(BG_IMMUTABLE_CONFLICT);
      }
   }
}