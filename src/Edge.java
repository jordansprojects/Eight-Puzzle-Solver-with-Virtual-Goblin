
//import java.io.InvalidObjectException;
import java.security.InvalidParameterException;

class Edge implements Comparable{
    private Vertex source; //from
    private Vertex dest; //to
    private double weight;

     /*****************************************
      * Creates edge object.
      * Weight is calculated based on the
      * destination vertex's distance from
      * the goal.
      * @param source  "to" vertex
      * @param dest   "from" vertex
      ****/
    public Edge(Vertex source, Vertex dest){
        this.source = source;
        this.dest = dest;
        this.weight = dest.calculateDistanceFromDefaultGoal();
    }
    public Vertex getSource() {
        return source;
    }
    public Vertex getDest() {
        return dest;
    }

    @Override
    public String toString(){
        return "SOURCE NODE" + source + "\nDESTINATION NODE " + dest;
     }

     public double getWeight() {
        return weight;
     }


     @Override
     public int compareTo(Object o) {
        if(!(o instanceof Edge)) {
            try {
                throw new InvalidParameterException("compareTo is overridden to only accept Edge objects as parameters. ");
            } catch (InvalidParameterException e) {
                e.printStackTrace();
            }
        }
            Edge other = (Edge)o;
            if(this.weight < other.weight){
                return -1;
            }else if (this.weight > other.weight){
                return 1;
            }else{ return 0; }

     }//end of compareTo method


    @Override
    public boolean equals(Object o){
        if(o instanceof Edge){
            Edge other = (Edge)o;
            if(other.source.containsMatrix(this.source.getTiles())){
                if(other.dest.containsMatrix(this.dest.getTiles())){
                    return true;
                }
            }

        }

        return false;
    }

}//end of Edge class

