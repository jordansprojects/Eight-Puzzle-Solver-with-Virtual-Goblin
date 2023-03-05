public class Vertex {
    protected Vertex parent;
    protected int[][] tiles; //vertex's data

    protected int zeroX;
    protected int zeroY;
    protected final int[][] defaultGoal    = { {1, 2, 3}, {4, 5, 6}, {7, 8, 0} };
    protected double cost;
    protected int level;

    /*************************************************************
     * @param tiles 2D array for Vertex to copy/contain
     * */
    public Vertex(int[][] tiles){
        this.tiles = new int[tiles.length][tiles.length];
        int n = tiles.length;
        for (int row = 0; row < n; row++) {
            for (int column = 0; column < n; column++){
                this.tiles[row][column] = tiles[row][column];
                //find and assigns zero coordinates
                if(tiles[row][column] ==0){
                    zeroX = row;
                    zeroY = column;
                }
            }
        }
    }
    /*************************************************************
     * BFS constructor
     * @param tiles 2D array for Vertex to copy/contain
     * @param level the Vertex's relationship in a graph in terms
     *             of when its added to said Graph, used for BFS
     * @param x  the parent's previous x value
     * @param y  the parent's previous y value
     * @param parent the preceding Vertex that this Vertex is a slightly
     *               altered version of, used for BFS
     *
     * */
    public Vertex(int[][] tiles, int x, int y, int newX, int newY, int level, Vertex parent) {
        this.parent = parent;
        this.tiles = new int[tiles.length][];
        for (int i = 0; i < tiles.length; i++) {
            this.tiles[i] = tiles[i].clone();
        }
        // Swap value
        this.tiles[x][y]       = this.tiles[x][y] + this.tiles[newX][newY];
        this.tiles[newX][newY] = this.tiles[x][y] - this.tiles[newX][newY];
        this.tiles[x][y]       = this.tiles[x][y] - this.tiles[newX][newY];

        this.cost = Integer.MAX_VALUE;
        this.level = level;
        this.zeroX = newX;
        this.zeroY = newY;
    }


    public int[][] getTiles(){
        return tiles;
    }
    /********************************************************************************
     * This is a dated method mainly kept around for the tests. It stores
     * the values of zeroY and zeroX and then returns them as a small array. This
     * can be nice when dealing with the indexes of the tiles (int[][]).
     * @return arr
     * **********************************************/
    public int[] getZeroCoordArray(){
        if(zeroY == -1|| zeroX == -1){ findZero(); }
       int arr[] = new int[2]; arr[0] = zeroX; arr[1] = zeroY;
        return arr;
    }
    /********************************************************************************
     * Formats the string as a prettier matrix
     * @return string
     * * **********************************************/
    @Override
    public String toString(){
        return  "\n"+ tiles[0][0] +  " "+ tiles [0][1] + " "+ tiles [0][2] + "\n"
                + tiles[1][0] + " "+ tiles [1][1] + " "+ tiles [1][2] + "\n"
                + tiles [2][0] + " "+ tiles [2][1] + " "  + tiles[2][2] ;
    }
    /************************************************************
     * Compares tiles instance variable to a goal matrix
     * @return the distance from default goal
     *********************************************/
    public int calculateDistanceFromDefaultGoal() {
        int count = 0;
        int n = this.tiles.length;
        for (int row = 0; row < n; row++) {
            for (int column = 0; column < n; column++) {
                if (tiles[row][column] != defaultGoal[row][column]) {
                    //  System.out.println(" DEBUG : Index[" +row +"][" +column + "] mismatch w/goal.");
                    count++;
                }
            }
        }
        return count;
    }

    /************************************************************
     * Copy tiles to new 2D array, change index of zero
     * @param x  zero's current x coordinate
     * @param y zero's current y coordinate
     * @param newX the desired X coordinate
     * @param newY the desired Y coordinate
     * @return new 2D array with changed zero index
     *********************************/
    private int[][] moveTileZero(int x, int y, int newX, int newY){
        if(tiles[x][y] != 0){
            try {
                throw new Exception("Interger at [x][y] must be zero!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(tiles[x][y] == tiles[newX][newY]){
            System.out.println("Nothing has changed since last generation of tiles.");
            return tiles;
        }
        int n = tiles.length;
        int newTiles[][] = new int[n][n];
        for (int row = 0; row < n; row++) {
            for (int column = 0; column < n; column++){
                newTiles[row][column] = tiles[row][column];
            }
        }
        newTiles[x][y]       = newTiles[x][y] + newTiles[newX][newY];
        newTiles[newX][newY] = newTiles[x][y] - newTiles[newX][newY];
        newTiles[x][y]       = newTiles[x][y] - newTiles[newX][newY];

        return newTiles;
    }

    /*********************************************************************************
     * All four "canMove" methods
     * Verifies zeroY or ZeroX are within bounds
     * @return whether or not the move is legal
     * */
    public boolean canMoveLeft(){
        if (zeroY > 0){
            return true;
        }
        return false;
    }
    public boolean canMoveRight(){
        if (zeroY < 2){
            return true;
        }
        return false;

    }
    public boolean canMoveUp(){
        if(zeroX  > 0){
           return true;
        }
        return false;
    }
    public boolean canMoveDown(){
        if(zeroX  < 2){
            return true;
        }
        return false;
    }
    /*********************************************************************************
     * All four "move" methods :
     *  Displaces zeroY or ZeroX to make move, calls moveTileZero
     * @return new 2D array with moved tiles
     ** ** ** ** ** ** ** ** ** ** ** * */
    public int[][] moveLeft (){
        int displacementX = 0;
        int displacementY = 0;
        if(zeroY  > 0){
             displacementY--;
        }
        return moveTileZero(zeroX, zeroY, zeroX + displacementX, zeroY +displacementY);
    }
    public int[][] moveRight (){
        int displacementX = 0;
        int displacementY = 0;
        if(zeroY  < 2){
            displacementY++;
        }
        return moveTileZero(zeroX, zeroY, zeroX + displacementX, zeroY +displacementY);
    }
    public int[][] moveUp (){
        int displacementX = 0;
        int displacementY = 0;
        if(zeroX  > 0){
            displacementX--;
        }
        return moveTileZero(zeroX, zeroY, zeroX + displacementX, zeroY +displacementY);
    }
    public int[][] moveDown(){
        int displacementX = 0;
        int displacementY = 0;
        if(zeroX  < 2){
            displacementX++;
        }
        return moveTileZero(zeroX, zeroY, zeroX + displacementX, zeroY +displacementY);
    }

    /****************************************************
     * finds coordinates of zero value in tiles array. Generally
     * limit the usage of this by using variables zeroX and zeroY
     * consistently.
     */
    private void findZero(){
        int zeroCoorinates[] = new int[2];
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
    @Override
    public boolean equals(Object o) {
        if (o instanceof Vertex) {
            Vertex v = (Vertex) o;
            int n = v.tiles.length;
            for (int row = 0; row < n; row++) {
                for (int column = 0; column < n; column++) {
                    if (v.tiles[row][column] != this.tiles[row][column]) {
                        return false;
                    }
                }
            }
        }
            return true;

    }
/***************************************************************
 * Checks of Vertex contains given matrix
 * @param tiles  matrix to compare Vertex's tiles to
 * @return whether or not tile arrays  are the same
 */
    public boolean containsMatrix(int[][]tiles) {
        int n = tiles.length;
        for (int row = 0; row < n; row++) {
            for (int column = 0; column < n; column++) {
                if (tiles[row][column] != this.tiles[row][column]) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        StringBuilder hash = new StringBuilder();
        for (int row = 0; row < tiles.length; row++) {
            for (int column = 0; column < tiles.length; column++) {
                hash.append(tiles[row][column] + "");
            }
        }
        return Integer.parseInt(hash.toString());
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }


    public int getLevel() {
        return level;
    }


} //end of Vertex class

