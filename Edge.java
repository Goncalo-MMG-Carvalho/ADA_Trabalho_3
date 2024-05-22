public class Edge {

    private final int begin;
    private final int end;
    private int weight;

    public Edge(int begin, int end, int weight) {
        this.begin = begin;
        this.end = end;
        this.weight = weight;
    }

    public int getBegin() {
        return this.begin;
    }

    public int getEnd() {
        return this.end;
    }

    public int getWeight() {
        return this.weight;
    }
    
    public void setWeight(int weight) {
    	this.weight = weight;
    }
}