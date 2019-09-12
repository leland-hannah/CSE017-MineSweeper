package cs2114.minesweeper;

/**
 * 
 * @author hannahleland
 * @version 2018.09.19
 */
public class MineSweeperBoardTest extends student.TestCase {

    private MineSweeperBoard mb;
    private MineSweeperBoard board;
    
    /**
     * this creates the board
     */
    public void setUp()
    {
        mb = new MineSweeperBoard(4, 4, 2);
        board = new MineSweeperBoard(4, 4, 2);
    }
    
    /**
     * 
     * @param theBoard the old board
     * @param expected the new board
     */
    public void assertBoard(MineSweeperBoard theBoard, String... expected)
    {
        MineSweeperBoard expectedBoard =
            new MineSweeperBoard(expected[0].length(), expected.length, 0);
        
        expectedBoard.loadBoardState(expected);
        
        assertEquals(expectedBoard, theBoard);
    }
    
    /**
     * this tests the width method
     */
    public void testWidth() {
        
        assertEquals(4, mb.width());
    }
    
    /**
     * this tests the height
     */
    public void testHeight() {
        
        assertEquals(4, mb.height());
    }
    
    /**
     * this tests the getCell method 
     */
    public void testGetCell()
    {
        mb.loadBoardState("    ",
                "O+OO",
                "+OOO",
                "OOOO");
        
        assertEquals(MineSweeperCell.INVALID_CELL, mb.getCell(5, 7));
        
        assertEquals(MineSweeperCell.COVERED_CELL, mb.getCell(3, 3));
    }
    
    /**
     * this tests flagged cell
     */
    public void testNumberOfAdjacentMines() {
        mb.loadBoardState("OOOO",
                "O*OO",
                "MOOO",
                "OOOO");
        
        assertEquals(2, mb.numberOfAdjacentMines(0, 1));
        
    }
    
    /**
     * this tests setCell
     */
    public void testSetCell() {
        mb.loadBoardState("    ",
                "OOOO",
                "O++O",
                "OOOO");
        
        mb.setCell(1, 2, MineSweeperCell.FLAGGED_MINE);
        assertBoard(mb, "    ", "OOOO", "OM+O", "OOOO");
        mb.setCell(5, 7, MineSweeperCell.FLAGGED_MINE);
        assertBoard(mb, "    ", "OOOO", "OM+O", "OOOO");
        
    }
    
    /**
     * this tests the uncoverCell
     */
    public void testUncoverCell()
    {
        mb.loadBoardState("    ", "OOOO", "F++O", "OOOO");
        
        mb.uncoverCell(0, 1);
        
        assertBoard(mb, "    ",
                "1OOO",
                "F++O",
                "OOOO");
        
        mb.uncoverCell(0, 2);
        assertBoard(mb, "    ",
                "1OOO",
                "F++O",
                "OOOO");
        
        
        
        mb.uncoverCell(1, 2);
        
        assertBoard(mb, "    ",
                "1OOO",
                "F*+O",
                "OOOO");
        
    }
    
    /**
     * this tests flagged cell
     */
    public void testFlagCell() {
        
        mb.loadBoardState("OOOO",
                "O+OO",
                "+OOO",
                "OOOO");
        assertEquals(MineSweeperCell.INVALID_CELL, 
                mb.getCell(-1, 0));
        mb.flagCell(0, 1);
        assertBoard(mb, "OOOO", "F+OO", "+OOO", "OOOO");
        mb.flagCell(0, 2);
        assertBoard(mb, "OOOO", "F+OO", "MOOO", "OOOO");
        mb.flagCell(0, 1);
        assertBoard(mb, "OOOO", "O+OO", "MOOO", "OOOO");
        mb.flagCell(0, 2);
        assertBoard(mb, "OOOO", "O+OO", "+OOO", "OOOO");
        mb.flagCell(-1, 0);
    }
    
    
    /**
     * this tests if they won or lost
     */
    public void testIsGameWon() {
        
        mb.loadBoardState("    ",
                "2M1 ",
                "M21 ",
                "11  ");
        
        assertEquals(true, mb.isGameWon());
        
        mb.loadBoardState("    ",
                "2*1 ",
                "M21 ",
                "11  ");
        assertEquals(false, mb.isGameWon());
        
        mb.loadBoardState("    ",
                "2M1O",
                "M21O",
                "11  ");
        
        assertEquals(false, mb.isGameWon());
        
        mb.loadBoardState("    ",
                "2M1 ",
                "M21F",
                "11  ");
        
        assertEquals(false, mb.isGameWon());
        
    }
    
    /**
     * tests if game is lost 
     */
    public void testIsGameLost() {
        mb.loadBoardState("    ",
                "2*1 ",
                "*21 ",
                "11OO");
        assertEquals(true, mb.isGameLost());
        
        mb.loadBoardState("    ",
                "2M1 ",
                "M21 ",
                "11  ");
        
        assertEquals(false, mb.isGameLost());
        
        
    }
    
    /**
     * tests reveal board
     */
    public void testRevealBoard() {
        mb.loadBoardState("OOOO",
                "O*OO",
                "*OOO",
                "OOOO");
        mb.revealBoard();
        
        assertBoard(mb, "111 ", "2*1 ", "*21 ", "11  ");
    }
    
    /**
     * tests the equals method
     */
    public void testEquals() {
        mb.loadBoardState("    ",
                "OOOO",
                "O++O",
                "OOOO");
        board.loadBoardState("    ",
                "OOOO",
                "O++O",
                "OOOO");
        assertEquals(mb, board);
        assertEquals(mb, mb);
        
    }
    
    
    /**
     * tests adj to
     */
    public void testAdjacentTo()
    {
        MineSweeperCell c = MineSweeperCell.ADJACENT_TO_0;
        assertNotNull(c);
      // testing for exception
        Exception thrown = null;
        try
        {
            c = MineSweeperCell.adjacentTo(10);
        }
        catch (Exception e)
        {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);

        thrown = null;
        try
        {
            MineSweeperCell.adjacentTo(-1);
        }
        catch (Exception e)
        {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertNotNull(MineSweeperCell.values());
        assertNotNull(
            MineSweeperCell.valueOf(MineSweeperCell.ADJACENT_TO_0.toString()));

    }
    
    /**
     * this tests the loadBoardState method
     */
    public void testloadBoardState()
    { 
        MineSweeperBoard a = new MineSweeperBoard(2, 2, 1);
        Exception thrown = null;
      //loadBoardState testing 
      //wrong number of rows
        try
        {
            a.loadBoardState("00");
        } 
        catch (Exception e)
        {
            thrown = e;
        } 
        assertTrue(thrown instanceof IllegalArgumentException);
        thrown = null;
      // wrong number of columns
        try
        {
            a.loadBoardState("0000 ", " ");
        } 
        catch (Exception e)
        {
            thrown = e;
        } 
        assertTrue(thrown instanceof IllegalArgumentException);
      // Wrong symbol in cell
        try
        {
            a.loadBoardState("00", "$+");
        } 
        catch (Exception e)
        {
            thrown = e;
        } 
        assertTrue(thrown instanceof IllegalArgumentException);
    }
     
    /** 
     * * This method test Equals.
     */ 
    public void testEqual()
    {
        MineSweeperBoard mBoard1 = new MineSweeperBoard(4, 4, 6);
        MineSweeperBoard mBoard2 = new MineSweeperBoard(4, 4, 6);
        mBoard1.loadBoardState("    ", "OOOO", "O++O", "OOOO");
        mBoard2.loadBoardState("    ", "OOOO", "O++O", "OOOO");
          // test the same board same dimensions
        assertTrue(mBoard1.equals(mBoard2));
          // same board testing same board
        assertTrue(mBoard1.equals(mBoard1));
          // testing same dimensions board with different cell
        MineSweeperBoard mBoard3 = new MineSweeperBoard(4, 4, 6);
        mBoard3.loadBoardState("    ", "O+OO", "O++O", "OOOO");
        assertFalse(mBoard1.equals(mBoard3));
        MineSweeperBoard mBoard4 = new MineSweeperBoard(15, 1, 0);
        mBoard4.loadBoardState("OFM+* 123456788");
        assertFalse(mBoard1.toString().equals(mBoard3.toString()));
            // testing two string against a board
        assertFalse(mBoard4.toString().equals(mBoard2.toString()));
          // testing against a string
        assertFalse(mBoard1.equals("abc"));
        assertFalse(mBoard1 == (null));
          // same width but different height
        MineSweeperBoard mBoard6 = new MineSweeperBoard(4, 5, 6);
        mBoard6.loadBoardState("    ", "O+OO", "O++O", "OOOO", "OOOO");
        assertFalse(mBoard6.equals(mBoard1));
        // different width same height
        MineSweeperBoard mBoard5 = new MineSweeperBoard(5, 4, 6);
        mBoard5.loadBoardState("     ", "O+OOO", "O++OO", "OOOOO");
        assertFalse(mBoard5.equals(mBoard1));
    }
    
    
    
    
    
    
    
    
    
    
    
    
}

