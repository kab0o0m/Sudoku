import java.util.Random;

/**
 * The Sudoku number puzzle to be solved
 */
public class Puzzle {

   int[][] numbers = new int[GameBoardPanel.GRID_SIZE][GameBoardPanel.GRID_SIZE];
   boolean[][] isGiven = new boolean[GameBoardPanel.GRID_SIZE][GameBoardPanel.GRID_SIZE];

   
   public Puzzle() {
      super();
   }

   public void newPuzzle(int cellsToGuess) {
      int count = 0;
      int[][] ModelAnswer =
         {{5, 3, 4, 6, 7, 8, 9, 1, 2},
          {6, 7, 2, 1, 9, 5, 3, 4, 8},
          {1, 9, 8, 3, 4, 2, 5, 6, 7},
          {8, 5, 9, 7, 6, 1, 4, 2, 3},
          {4, 2, 6, 8, 5, 3, 7, 9, 1},
          {7, 1, 3, 9, 2, 4, 8, 5, 6},
          {9, 6, 1, 5, 3, 7, 2, 8, 4},
          {2, 8, 7, 4, 1, 9, 6, 3, 5},
          {3, 4, 5, 2, 8, 6, 1, 7, 9}};

    
      Random random = new Random();
      int rowShuffles = random.nextInt(20);
      int colShuffles = random.nextInt(20);
      int randomMargin = random.nextInt(9);
      System.out.println(rowShuffles+" "+colShuffles+" "+randomMargin);
      for(int i = 0;i<2;++i){
         swapRow(ModelAnswer);
      }
      for(int j=0;j<2;++j){
         swapCol(ModelAnswer);
      }
      
      for (int row = 0; row < GameBoardPanel.GRID_SIZE; ++row) {
         for (int col = 0; col < GameBoardPanel.GRID_SIZE; ++col) {
            numbers[row][col] = (ModelAnswer[row][col]+randomMargin)%9+1;
         }
      }

      boolean[][] IsGiven =
         {{true, true, true, true, true, true, true, true, true},
          {true, true, true, true, true, true, true, true, true},
          {true, true, true, true, true, true, true, true, true},
          {true, true, true, true, true, true, true, true, true},
          {true, true, true, true, true, true, true, true, true},
          {true, true, true, true, true, true, true, true, true},
          {true, true, true, true, true, true, true, true, true},
          {true, true, true, true, true, true, true, true, true},
          {true, true, true, true, true, true, true, true, true}};

      short visited[][] = new short[9][9];
      while(count<cellsToGuess){
         int randomRow = random.nextInt(9);
         int randomCol = random.nextInt(9);
         if(visited[randomRow][randomCol]==1){
            continue;
         }
         IsGiven[randomRow][randomCol] = false;
         visited[randomRow][randomCol] = 1;
         count++;
      }
         
      for (int row = 0; row < GameBoardPanel.GRID_SIZE; ++row) {
         for (int col = 0; col < GameBoardPanel.GRID_SIZE; ++col) {
            isGiven[row][col] = IsGiven[row][col];
         }
      }
      
   }
   public int[][] getPuzzleNumbers() {
	   return this.numbers;
   }
   public boolean[][] getPuzzleBoolean() {
	   return this.isGiven;
   }
   
   public static void swapRow(int[][] ModelAnswer){
      Random random = new Random();
      int box = random.nextInt(3)*3;
      int[] sampleArr = RandomSample.randomSample(box, box + 3, 2);
      int row1 = sampleArr[0], row2 = sampleArr[1];
      int[] temp = ModelAnswer[row1];
      ModelAnswer[row1] = ModelAnswer[row2];
      ModelAnswer[row2] = temp;
   }

   public static void swapCol(int[][] ModelAnswer){
      Random random = new Random();
      int box = random.nextInt(3)*3;
      int[] sampleArr = RandomSample.randomSample(box, box + 3, 2);
      int col1 = sampleArr[0], col2 = sampleArr[1];
      for(int i = 0;i<9;++i){
         int temp = ModelAnswer[i][col1];
         ModelAnswer[i][col1] = ModelAnswer[i][col2];
         ModelAnswer[i][col2] = temp;
      }
   }

   public class RandomSample {
      public static int[] randomSample(int start, int end, int k) {
         int[] result = new int[k];
         int[] range = new int[3];
         for(int i = start; i < end; i++) {
            range[i - start] = i;
         }
         Random rand = new Random();  
         for(int i = 0; i < k; i++) {
            int index = rand.nextInt(end - start - i);
            result[i] = range[index]; 
            range[index] = range[end - start - i - 1];
         }
         return result;
      }
  }
}