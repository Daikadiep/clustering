/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AlgorithmClustering;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Diep
 */
public class Cluster {

    private String name;

    private Cluster parent;

    private List<Cluster> children;

    private List<String> leafNames;

    private Distance distance = new Distance();

    public Cluster(String name) {
        this.name = name;
        leafNames = new ArrayList<String>();
    }

    public Distance getDistance() {
        return distance;
    }

    public Double getWeightValue() {
        return distance.getWeight();
    }

    public Double getDistanceValue() {
        return distance.getDistance();
    }

    public void setDistance(Distance distance) {
        this.distance = distance;
    }

    public List<Cluster> getChildren() {
        if (children == null) {
            children = new ArrayList<Cluster>();
        }

        return children;
    }

    public void addLeafName(String lname) {
        leafNames.add(lname);
    }

    public void appendLeafNames(List<String> lnames) {
        leafNames.addAll(lnames);
    }

    public List<String> getLeafNames() {
        return leafNames;
    }

    public void setChildren(List<Cluster> children) {
        this.children = children;
    }

    public Cluster getParent() {
        return parent;
    }

    public void setParent(Cluster parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addChild(Cluster cluster) {
        getChildren().add(cluster);

    }

    public boolean contains(Cluster cluster) {
        return getChildren().contains(cluster);
    }

    @Override
    public String toString() {
        return "Cluster " + name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Cluster other = (Cluster) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return (name == null) ? 0 : name.hashCode();
    }

    public boolean isLeaf() {
        return getChildren().size() == 0;
    }

    public int countLeafs() {
        return countLeafs(this, 0);
    }

    public int countLeafs(Cluster node, int count) {
        if (node.isLeaf()) {
            count++;
        }
        for (Cluster child : node.getChildren()) {
            count += child.countLeafs();
        }
        return count;
    }

    public void getList(HashMap m) {

        if (isLeaf()) {
            m.put(m.size() + 1, getName());
        } else {
            for (Cluster child : getChildren()) {
                child.getList(m);
            }
        }

    }

    public void toConsole(int indent, int t, ArrayList<HashMap> hash) {

        if (t == 0) {
            HashMap m = new HashMap();
            for (Cluster child : getChildren()) {
                child.getList(m);
            }
            hash.add(m);
        } else if (indent == t) {
            HashMap h = new HashMap();
            if (isLeaf()) {
                h.put(h.size() + 1, getName());
            } else {
                for (Cluster child : getChildren()) {
                    child.getList(h);
                }
            }
            hash.add(h);
        } else if (isLeaf()) {
            HashMap h = new HashMap();
            h.put(h.size() + 1, getName());
            hash.add(h);
        } else {
            indent++;
            for (Cluster child : getChildren()) {
                child.toConsole(indent, t, hash);
            }
        }

        /*for (int i = 0; i < indent; i++)
        {
            System.out.print("  ");

        }
        String name = getName() + (isLeaf() ? " (leaf)" : "") + (distance != null ? "  distance: " + distance : "");
        System.out.println(name);
        for (Cluster child : getChildren())
        {
            child.toConsole(indent + 1);
        }*/
    }

    public double getTotalDistance() {
        Double dist = getDistance() == null ? 0 : getDistance().getDistance();
        if (getChildren().size() > 0) {
            dist += children.get(0).getTotalDistance();
        }
        return dist;

    }

}
