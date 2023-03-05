import java.util.*;
public class Solver {
    // Bottom, left, top, right
    int[] checkRow = { 1, 0, -1, 0 };
    int[] checkCol = { 0, -1, 0, 1 };
    public int dimension = 3;
    int count = 0;
    LinkedList<Vertex> list = new LinkedList<Vertex>();

    /*******************************************************************************
     * @param initialState starting Vertex
     * @param graph to be created
     *  Utilizes breadth first search algorithm to create Graph with needed Verticies
     *  to solve puzzle . Creates all possible verticies for each Vertex in priority queue,
     *   but Vertex is only added to priority queue if it is the smallest distance
     *   from the goal. Stops when priority queue is empty.
     **********/
    public void breadthFirstSearch(Vertex initialState, Graph graph) throws UnsolveablePuzzleException {
        if(!isSolvable(initialState.tiles)){
            throw new UnsolveablePuzzleException();
        }
        PriorityQueue<Vertex> pq = new PriorityQueue<>(1000, Comparator.comparingInt(a -> (int) (a.getCost() + a.getLevel())));
        initialState.setCost(initialState.calculateDistanceFromDefaultGoal());
        pq.add(initialState);
        while(!pq.isEmpty()) {
            Vertex min = pq.poll();
            if (min.getCost() == 0) {
                System.out.println("Solution found!" + min);
                count = 0;
                printPath(min);
                return;
            }

            for (int i = 0; i < 4; i++) {
                if (isSafe(min.zeroX + checkRow[i], min.zeroY + checkCol[i])) {
                    Vertex child = new Vertex(min.tiles, min.zeroX, min.zeroY, min.zeroX+ checkRow[i], min.zeroY + checkCol[i], min.level + 1, min);
                    child.cost = child.calculateDistanceFromDefaultGoal();
                    pq.add(child);
                }
            }
        }
    }

    /**********************************************************
     * check if a matrix is solveable (can be ordered )
     * @param matrix
     * ***************************************/
    public boolean isSolvable(int[][] matrix) {
        int count = 0;
        List<Integer> array = new ArrayList<Integer>();

        for (int row = 0; row < matrix.length; row++) {
            for (int column = 0; column < matrix.length; column++) {
                array.add(matrix[row][column]);
            }
        }

        Integer[] anotherArray = new Integer[array.size()];
        array.toArray(anotherArray);

        for (int i = 0; i < anotherArray.length - 1; i++) {
            for (int j = i + 1; j < anotherArray.length; j++) {
                if (anotherArray[i] != 0 && anotherArray[j] != 0 && anotherArray[i] > anotherArray[j]) {
                    count++;
                }
            }
        }

        return count % 2 == 0;
    }

    public boolean isSafe(int x, int y) {
        return (x >= 0 && x < dimension && y >= 0 && y < dimension);
    }

    /*******************************************************************
     * prints path to solve puzzle to console, but also adds
     * each Vertex to LinkedList, that can then be popped out to solve
     * puzzle in order, using popFirst.
     * @param root to start at, to recursively print 2D int data
     *             and travel to each parent Vertex
     *
     * */
    public void printPath(Vertex root) {
        if (root == null) {
            return;
        }
        printPath(root.parent);
        list.add(root);
        System.out.println(root.toString());
        count++;
        //System.out.println();
    }


}//end of Solver class




