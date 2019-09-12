package cs2114.minesweeper;

import java.util.Random;

/**
 * 
 * @author hannahleland
 * @version 2018.09.21
 */
public class MineSweeperBoard extends MineSweeperBoardBase {
    private int height;
    private int width;
    private MineSweeperCell[][] b;
    
    /**
     * constructs a 2d array
     * @param h the height of the board
     * @param w the width of the board
     * @param nm is the nummber of mines
     */
    public MineSweeperBoard(int w, int h, int nm) {
        height = h;
        width = w;
        b = new MineSweeperCell[width][height];
        Random r = new Random();
        
        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                b[i][j] = MineSweeperCell.COVERED_CELL;
            }
        }
     
        int count = 0;
        while (nm > count) {
            int x = r.nextInt(w);
            int y = r.nextInt(h);
            if (getCell(x, y) != MineSweeperCell.MINE)
            {
                b[x][y] = MineSweeperCell.MINE;
                count++;
            }
        }
    }
    
    /**
     * @return the width
     */
    public int width() {
        return width;
    }

    /**
     * @return the height
     */
    public int height() {
        
        return height;
    }

    /**
     * @return the value inside the cell
     * @param x the row value 
     * @param y the col value
     */
    public MineSweeperCell getCell(int x, int y) {
        
        if (x < 0 || x > width - 1 || y < 0 || y > height - 1)
        {
            return MineSweeperCell.INVALID_CELL;
        }
        return b[x][y];
    }

    /**
     * Uncover the specified cell. If the cell already contains a flag it
     * should not be uncovered. If there is not a mine under the specified
     * cell then the value in that cell is changed to the number of mines
     * that appear in adjacent cells. If there is a mine  under the specified
     * cell the game is over and the player has lost.  If the specified cell
     * is already uncovered or is invalid, no change  is made to the board.
     *
     * @param x the column of the cell to be uncovered.
     * @param y the row of the cell to be uncovered.
     */
    public void uncoverCell(int x, int y) {
        
        if (getCell(x, y) == (MineSweeperCell.COVERED_CELL))
        {
            //if (getCell(x, y) != (MineSweeperCell.FLAG))
            //{
            int count = numberOfAdjacentMines(x, y);
            setCell(x, y, MineSweeperCell.adjacentTo(count));
            //}
        }
        
        if (b[x][y].equals(MineSweeperCell.MINE)) {
            b[x][y] = MineSweeperCell.UNCOVERED_MINE;
        }
        
    }

    /**
     * Place or remove a flag from the specified cell. If the cell currently
     * covered then place a flag on the cell.  If the cell currently contains
     * a flag, remove that flag but do not uncover the cell. If the cell has
     * already been uncovered or is invalid, no change is made to the board.
     *
     * @param x the column of the cell to be flagged/unflagged
     * @param y the row of the cell to be flagged/unflagged
     */
    public void flagCell(int x, int y) {
        
        if (getCell(x, y) == MineSweeperCell.COVERED_CELL) {
            b[x][y] = MineSweeperCell.FLAG;
        }
        
        else if (getCell(x, y) == (MineSweeperCell.MINE)) {
            b[x][y] = MineSweeperCell.FLAGGED_MINE;
        }
        
        else if (getCell(x, y) == (MineSweeperCell.FLAG)) {
            b[x][y] = MineSweeperCell.COVERED_CELL;
        }
        
        else if (getCell(x, y) == MineSweeperCell.FLAGGED_MINE) {
            b[x][y] = MineSweeperCell.MINE;
        }
        //flagged mine to regular mine
        
    }

    /**
     * Determine if the player has lost the current game. The game is lost if
     * the player has uncovered a mine.
     *
     * @return true if the current game has been lost and false otherwise
     */
    public boolean isGameLost() {
        for (int i = 0; i < b.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                if (b[i][j].equals(MineSweeperCell.UNCOVERED_MINE)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Determine if the player has won the current game. The game is won when
     * three conditions are met:
     *
     * <ol>
     * <li>Flags have been placed on all of the mines.</li>
     * <li>No flags have been placed incorrectly.</li>
     * <li>All non-flagged cells have been uncovered.</li>
     * </ol>
     *
     * @return true if the current game has been won and false otherwise.
     */
    public boolean isGameWon() {
        
        for (int i = 0; i < b.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                
                if (b[i][j].equals(MineSweeperCell.UNCOVERED_MINE)) {
                    return false;
                }
                
                if (b[i][j].equals(MineSweeperCell.COVERED_CELL)) {
                    return false;
                }
                
                if (b[i][j].equals(MineSweeperCell.FLAG)) {
                    return false;
                }
                
            }
        }

        
        return true;
    }

    /**
     * Count the number of mines that appear in cells that are adjacent to
     * the specified cell.
     *
     * @param x the column of the cell.
     * @param y the row of the cell.
     * @return the number of mines adjacent to the specified cell.
     */
    public int numberOfAdjacentMines(int x, int y) {
        int count = 0;
        
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                
                if (getCell(i, j) == MineSweeperCell.FLAGGED_MINE || 
                        getCell(i, j) == MineSweeperCell.UNCOVERED_MINE ||
                        getCell(i, j) == MineSweeperCell.MINE) {
                    count++;
                    
                }
            }
        }
        
        return count;
    }

    /**
     * Uncover all of the cells on the board.
     */
    public void revealBoard() {
        
        for (int i = 0; i < b.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                uncoverCell(i, j);
            }
        }
        
    }

    /**
     * Set the contents of the specified cell on this MineSweeperBoard. The
     * value passed in should be one of the defined constants in the
     * {@link MineSweeperCell} enumerated type.
     *
     * @param x the column containing the cell
     * @param y the row containing the cell
     * @param value the value to place in the cell
     */
    protected void setCell(int x, int y, MineSweeperCell value) {
        
        if (getCell(x, y) != MineSweeperCell.INVALID_CELL)
        {
            b[x][y] = value;
        }
        
    }
}
