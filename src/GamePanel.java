

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

public class GamePanel extends JPanel implements ActionListener{ //grid is drawn in a dedicated Panel
    // Margin for the grid on the frame
    private int MARGIN =30;
    private int DIMENSION =550;
    public int dimension = 3;
    final int GRID_SIZE =(DIMENSION- 2 * MARGIN);
    final int BALL_DISPLACEMENT = 160;
    static int zeroX = -1;
    static int zeroY = -1;
    static int zeroXOnGUI;
    static int zeroYOnGUI;
    Random random = new Random();
    private int[][] option1 = { {7, 3, 4},
            {6, 1, 0},
            {5, 2, 8} };
    private int[][] option2 = { {4, 6, 2},
            {5, 0, 8},
            {1, 7, 3} };
    private int[][]option3 = { {1, 8, 2},
            {0, 4, 3},
            {7, 6, 5} };
    Timer timer = new Timer(500, this);
    boolean isGameOver = false;

    // Foreground Color
    private static final Color FOREGROUND_COLOR = new Color(239, 83, 80);
    // Random object to shuffle tiles
    private static final Random RANDOM = new Random();
    // Storing the tiles in a 2D Array of integers
    private int[][] tiles = { {1, 0, 3},
            {2, 8, 4},
            {5, 7, 6} };
    // Size of tile on UI
    private int tileSize;
    // Grid UI Size
    private int gridSize;
    BufferedImage ball[];
    Solver solver = new Solver();
    int decide =1;


    /**********************************************
     * Calls solver to generate Graph of initial state,
     * calls makeImageArray, sets up game essentially
     * ****************/
    public GamePanel( ) throws UnsolveablePuzzleException {
        solver.breadthFirstSearch(new Vertex(tiles), new Graph(true));
        solver.list.pollFirst(); //to remove matching state from list
        findZero();
        makeImageArray();
        setPreferredSize(new Dimension(DIMENSION, DIMENSION + MARGIN));
        setBackground(Color.GRAY);
        setForeground(FOREGROUND_COLOR);
        setFont(new Font("SansSerif", Font.BOLD, 60));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
            drawGrid(g);
        if(isSolved()){
            isGameOver = true;
        }

    }
/*******************************************************
 * @param x  current x
 * @param y  current y value
 * @param newX x value of desired move
 * @param newY y value of desired move
 * *******************************/
    private void swapValues(int x, int y, int newX, int newY){
        this.tiles[x][y]       = this.tiles[x][y] + this.tiles[newX][newY];
        this.tiles[newX][newY] = this.tiles[x][y] - this.tiles[newX][newY];
        this.tiles[x][y]       = this.tiles[x][y] - this.tiles[newX][newY];
        findZero();
        repaint();
    }

    /*******************************************************
     * Swaps tile values w/o creating a new Vertex.
     * Directly alteres tile values that belong to GamePanel class
     * by calling swapValues
     * */
    protected void moveLeft(){
        if(canMoveLeft()&& !isGameOver) {
            swapValues(zeroX, zeroY, zeroX, zeroY -1);
        }
    }
    protected void moveUp(){
        if(canMoveUp() && !isGameOver) {
            swapValues(zeroX - 1, zeroY, zeroX, zeroY );
        }
    }
    protected void moveDown(){
        if(canMoveDown()&& !isGameOver) {
            swapValues(zeroX + 1, zeroY, zeroX, zeroY );
        }
    }
    protected void moveRight(){
        if(canMoveRight()&& !isGameOver) {
            swapValues(zeroX, zeroY, zeroX, zeroY +1 );
        }
    }
    public boolean isSolved(){
        return (new Vertex(tiles).calculateDistanceFromDefaultGoal() == 0);
    }

/*****************************************************************
 * Positions ball images to be painted to reflect 3 x 3 puzzle
 * ***********/
    protected void drawGrid(Graphics g){
        int n = this.tiles.length;
        int x = 0;
        int y = 0;
        for (int row = 0; row < n; row++) {
            x = row * BALL_DISPLACEMENT;
            for (int column = 0; column < n; column++) {
                y = column * BALL_DISPLACEMENT;
                int value = tiles[column][row];
                System.out.println(value);
                g.drawImage(ball[value], x, y, this);
                if( value == 0){
                    zeroYOnGUI = x;
                    zeroXOnGUI = y;
                }

            }
        }
    }
/***********************************************************************
 * @param imgName local path for image
 * @return BufferedImage of file
 *******************/
    private BufferedImage generateImage(String imgName) {
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(getClass().getResource(imgName));
        }//end of try
        catch (IOException e) {
            System.out.println("No file found!!");
            e.printStackTrace();
        }//end of catch
        return bi;

    }
/**********************************************************************
 * Initializes  array of pool ball images
 *************************/
    private void makeImageArray(){
        ball = new BufferedImage[9];
        String prefix = "/lazyballs";
        for(int i = 0 ; i < 9; i++)
            ball[i] = generateImage(prefix + i + ".png");
    }

    /*********************************************************************************
     * All four "Move" methods
     * Verifies zeroY or ZeroX are within bounds
     * @return whether or not the move is legal
     * Preferrably, there wouldnt be two sets of these, but it would be more work
     * to locate Vertex to call these methods And the Vertex may not exit within
     * the graph if the user moved in a direction that has not been generated. Likely
     * if the move is not the closest to the shortest solution.
     * */
    public boolean canMoveLeft(){
        return(zeroY > 0);
    }
    public boolean canMoveRight(){
        return (zeroY < 2);
    }
    public boolean canMoveUp(){
        return (zeroX  > 0);
    }
    public boolean canMoveDown(){
        return (zeroX  < 2);
    }

    /***********************************************************
     * Locates where the value of zero lies within the 2D array
     * */
    private void findZero(){
        int n = this.tiles.length;
        for (int row = 0; row < n; row++) {
            for (int column = 0; column < n; column++) {
                if(tiles[row][column] == 0){
                    this.zeroX = row;
                    this.zeroY = column;
                    return;
                }
            }
        }
    }

    /***********************************************************
     * Rearranges tiles to match given 2D int array.
     * @param inputTiles tiles for GUI to match/represent visually
     * This is useful for displaying each Vertex/state in the
     * puzzle.
     * */
    public void reflectTiles(int[][] inputTiles){
            int n = tiles.length;
            for (int row = 0; row < n; row++) {
                for (int column = 0; column < n; column++) {
                    this.tiles[row][column] = inputTiles[row][column];
                }
            }
            findZero();
           repaint();
        }


    /****************************************************************
     * Pops Vertex from solver's LinkedList of Verticies. Verticies
     * are added to LinkedList after BFS method is called and the
     * path is retraced, so the path reflects each step ( or state)
     * for solving the puzzle.
     */
    public void getHint() throws UnsolveablePuzzleException {
        if(!solver.list.isEmpty()) {
            reflectTiles(solver.list.pollFirst().tiles);
        }
    }
    /****************************************************************
     * Starts timer to call actionPerformed, actionPerformed
     * pops Verticies from solver's list until empty
     * */
    public void solve() throws InterruptedException {
        timer.start();

    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        try {
            getHint();
        } catch (UnsolveablePuzzleException e) {
            e.printStackTrace();
        }
        if(solver.list.isEmpty()){
            timer.stop();
        }

    }

    void shuffleTiles() throws UnsolveablePuzzleException {
        if (decide == 3) {
            for (int row = 0; row < option3.length; row++) {
                for (int column = 0; column < option3.length; column++) {
                    this.tiles[row][column] = option3[row][column];
                }
                decide =1;
            }
        } else if (decide == 2) {
            for (int row = 0; row < option2.length; row++) {
                for (int column = 0; column < option2.length; column++) {
                    this.tiles[row][column] = option2[row][column];
                }
                decide =3;
            }

        } else if (decide == 1) {
            for (int row = 0; row < option1.length; row++) {
                for (int column = 0; column < option1.length; column++) {
                    this.tiles[row][column] = option1[row][column];
                }
                decide =2;
            }

            while (!solver.list.isEmpty()) {
                solver.list.pop();
            }
            solver.breadthFirstSearch(new Vertex(tiles), new Graph(true));
            solver.list.pollFirst();
            isGameOver = false;
            repaint();
            findZero();


        }
    }
}
