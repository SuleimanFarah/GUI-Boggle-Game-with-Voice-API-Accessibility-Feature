package boggle;

/**
 * Class to hold Position information associated with a BoggleGrid
 */
public class Position {
    /**
     * row
     */    
    private int row;
    /**
     * column
     */    
    private int col;

    /**
     * A grid Position.
     * Sets row and column to 0, by default
     */
    public Position() {
        this.row = 0;
        this.col = 0;
    }

    /**
     * A grid Position.
     * 
     * @param row row
     * @param col column
     */
    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

   /*
     * Useful getter and setter method for class attributes
     */
    public void setRow(int row) { this.row = row; }
    public void setCol(int col) { this.col = col; }
<<<<<<< HEAD
    public int getRow() { return this.row; }
    public int getCol() { return this.col; }
=======
    public int getRow(int row) { return this.row; }
    public int getCol(int col) { return this.col; }
>>>>>>> 7649895676de94dd3a4043b0221c31fe0c509cba
}
