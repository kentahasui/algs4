import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import com.google.common.base.Stopwatch;

public class KdTreeClient {

    public static void main(String[] args) {
        // initialize the two data structures with point from file
        String filename = args[0];
        In in = new In(filename);
        PointSET brute = new PointSET();
        KdTree kdtree = new KdTree();

        Stopwatch construction = Stopwatch.createStarted();
        System.out.println("Inserting points");
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            brute.insert(p);
        }
        construction.stop();
        System.out.printf("%s seconds to insert points \n\n", construction.elapsed().getSeconds());

        System.out.println("Calculating nearest for KdTree");
        int count = 0;
        int duration = 10;
        Point2D query = new Point2D(0.3, 0.6);
        Stopwatch stopwatch = Stopwatch.createStarted();
        while (stopwatch.elapsed().getSeconds() < duration) {
            kdtree.nearest(query);
            count++;
        }
        System.out.printf("%s nearest operations in %s seconds\n", count, duration);
        System.out.printf("%s nearest operations per second\n", count / duration);
        System.out.printf("tree.nearest((0.3, 0.6)) => %s\n\n", kdtree.nearest(query));


//        System.out.println("Calculating nearest for Brute");
//        count = 0;
//        stopwatch = Stopwatch.createStarted();
//        while(stopwatch.elapsed().getSeconds() < duration){
//            brute.nearest(query);
//            count++;
//        }
//        System.out.printf("%s nearest operations in %s seconds\n", count, duration);
//        System.out.printf("%s nearest operations per second\n", count/duration);
//        System.out.printf("tree.nearest((0.3, 0.6)) => %s\n", brute.nearest(query));
    }
}
