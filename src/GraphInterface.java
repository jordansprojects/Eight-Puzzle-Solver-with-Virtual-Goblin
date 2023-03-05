
import java.util.Iterator;

public interface GraphInterface {

    // Accessor Methods

    /** Determine whether this is a directed graph.
     * @return true if this is a directed graph
     */
    boolean isDirected();
    /** Insert a new edge into the graph.
     * @param edge The new edge
     */
    void insert(Edge edge);
    /** Determine whether an edge exists.
     @param source The source vertex
     @param dest The destination vertex
     @return true if there is an edge from source to dest
     */
    boolean isEdge(Vertex source, Vertex dest);
    /** Get the edge between two vertices.
     @param source The source vertex
     @param dest The destination vertex
     @return The Edge between these two vertices
     or null if there is no edge
     */
     Edge getEdge(Vertex source, Vertex dest);

}
