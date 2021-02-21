package mountain;

public class Side {
    Point p1, p2;
    
    /** Constructs and initializes a side with the point p1 and p2 */
    public Side(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    @Override
    public boolean equals(Object a) {
        Side b = (Side) a;
        return (p1 == b.p1 && p2 == b.p2 || p1 == b.p2 && p2 == b.p1);
    }   

	@Override
    public int hashCode() {
	    return p1.hashCode() + p2.hashCode();
    }
}
