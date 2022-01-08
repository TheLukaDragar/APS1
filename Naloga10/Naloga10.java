import java.io.BufferedReader;
import java.io.FileReader;

import java.io.*;
import java.util.*;

class Naloga10 {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(args[0]));
        String line;
        int n;

        n = Integer.parseInt(br.readLine());
        ArrayList<Point> points = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            line = br.readLine();
            points.add(
                    new Point(i + 1, Double.parseDouble(line.split(",")[0]), Double.parseDouble(line.split(",")[1])));


        }

        int Ngroups = Integer.parseInt(br.readLine());

        br.close();

        ArrayList<Distance> distances = new ArrayList<>();

        // calculate distance beetwen every point and every other point
        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {

                distances.add(new Distance(points.get(i), points.get(j),
                        Math.sqrt(Math.pow(points.get(i).x - points.get(j).x, 2)
                                + Math.pow(points.get(i).y - points.get(j).y, 2))));

            }
        }

        Collections.sort(distances);

        ArrayList<Group> groupsList = new ArrayList<>();

        for (Point p : points) {
            groupsList.add(new Group(p));
        }

        for (Distance distance : distances) {
            if (groupsList.size() <= Ngroups)
                break;

            Group g1 = null;
            Group g2 = null;

            for (Group group : groupsList) {


                if (group.areInGroup(distance.p1, distance.p2)) {
                    if (g1 == null){
                        g1 = group;

                    } 
                    else{
                        g2 = group;

                    }
                        
                }
            }
            if (g1 != null && g2 != null) {
                g1.merge(g2);
                groupsList.remove(g2);
            }
        }

        Writer out = new FileWriter(args[1]);
        for (Group gg : groupsList) {
           
            ArrayList<String> ids = new ArrayList<>();
            for (Point p : gg.points) {
                ids.add(String.valueOf(p.id)); 
            }

            out.write(String.join(",", ids));
            out.write("\n");
        }

        out.close();

    }

    static class Distance implements Comparable<Distance> {
        Point p1;
        Point p2;
        double distance;

        public Distance(Point p1, Point p2, double d) {
            this.p1 = p1;
            this.p2 = p2;
            this.distance = d;
        }

        @Override
        public int compareTo(Distance o) {
            return Double.compare(this.distance, o.distance);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Distance) {
                Distance dbp = (Distance) obj;
                if (dbp.p1 == this.p1 && dbp.p2 == this.p2
                        || dbp.p2 == this.p1 && dbp.p1 == this.p2)
                    return true;
            }
            return false;
        }

    }

    static class Point implements Comparable<Point> {

        int id;
        double x;
        double y;

        public Point(int id, double d, double e) {
            this.x = d;
            this.y = e;
            this.id = id;

        }

        @Override
        public int compareTo(Point p) {
            return Integer.compare(this.id, p.id);
        }

    }

    static class Group {
        int id;
        ArrayList<Point> points;

        Group(Point p) {
            points = new ArrayList<>();
            addPoint(p);
        }
       
        public void merge(Group a) {
            for (Point p : a.points) {
                addPoint(p);
            }
        }
        public void addPoint(Point p) {
            points.add(p);
            Collections.sort(points);
            id = points.get(0).id;
        }
        public boolean areInGroup(Point a, Point b) {
            for (Point p : this.points) {
                if (p.equals(a) || p.equals(b))
                    return true;
            }
            return false;
        }
    }

}