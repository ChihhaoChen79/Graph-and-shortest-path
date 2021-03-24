/*
 * Graph Class
 * 1.Constructor: Graph(ArrayList<String> strings)
 *      the input ArrayList is the vertices' names
 * 2.Methods:
 *      1.1 setEdge(String vertex1, String vertex2, String vehicle, double time, int cost):
 *          add an edge and set the weights between vertex1 and vertex2
 *          time: double, in hours
 *          cost: int, in NTD
 *          you can set multiple edges between 2 vertices
 *      @1.2 getResultPath()
 *          get the least cost path or least time path
 *          must be called after calling leastCost() or leastTime()
 *      @1.3 getResultVehicle()
 *          get the vehicles used during least cost path or least time travel
 *          must be called after calling leastCost() or leastTime()
 *      @1.4 showAllWeights(String vertex1, String vertex2):
 *          check all edges and their weights between 2 input vertices
 *      @1.5 leastCost(String startVertex, String endVertex):
 *          update least cost path and the vehicle used
 *          get the result by calling getResultPath() and getResultVehicle()
 *      @1.6 leastTime(String startVertex, String endVertex):
 *          update least time path and the vehicle used
 *          get the result by calling getResultPath() and getResultVehicle()
 */

package hao.graph.practice;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public final class Graph {


    private final ArrayList<String> vertices;
    private final ArrayList<ArrayList<Weights>> adjacencyMatrix;

    private final ArrayList<String> leastCostOrTimePath;
    private final ArrayList<String> leastCostOrTimeVehicle;

    public Graph(ArrayList<String> strings){
        vertices = new ArrayList<>();
        this.adjacencyMatrix = new ArrayList<>();
        for (int i=0;i<strings.size();i++){
            vertices.add(strings.get(i));
            // initializing the adjacency matrix with weight = 0
            ArrayList<Weights> rows = new ArrayList<>();
            for (int j=0;j<strings.size();j++){
                rows.add(new Weights());
            }
            adjacencyMatrix.add(rows);
        }

        this.leastCostOrTimePath = new ArrayList<>();
        this.leastCostOrTimeVehicle = new ArrayList<>();
    }

    public void setEdge(String vertex1, String vertex2, String vehicle, double time, int cost){
        int v1 = this.vertices.indexOf(vertex1);
        int v2 = this.vertices.indexOf(vertex2);
        this.adjacencyMatrix.get(v1).get(v2).addWeights(vehicle,time,cost);
        this.adjacencyMatrix.get(v2).get(v1).addWeights(vehicle,time,cost);
    }
    public ArrayList<String> getResultPath(){
        return this.leastCostOrTimePath;
    }
    public ArrayList<String> getResultVehicle(){
        return this.leastCostOrTimeVehicle;
    }
    public void showAllWeights(String vertex1, String vertex2){
        int v1 = this.vertices.indexOf(vertex1);
        int v2 = this.vertices.indexOf(vertex2);
        if(v1==-1 || v2==-1){
            System.out.println("Check the input vertices' name.");
        }
        else{
            System.out.println("These are the ways from " + vertex1 + " to " + vertex2);
            Weights temp = this.adjacencyMatrix.get(v1).get(v2);
            for (int i=0;i<temp.numberOfPath;i++){
                System.out.println(i+1);
                System.out.println("Vehicle: "+temp.vehicle.get(i));
                System.out.println("Time: "+temp.travelTime.get(i));
                System.out.println("Cost: "+temp.travelCost.get(i));
            }
        }
    }

    private boolean allVertexVisited(boolean[] isVisited){
        boolean output = true;
        for (boolean b : isVisited){
            output &= b;
        }
        return output;
    }
    private void Dijkstra(int startIndex, double[] minTime, String[] lastVertex, boolean[] isVisited,String[] vehicleUsed){
        for(int i=0;i<this.vertices.size();i++){
            if(this.adjacencyMatrix.get(startIndex).get(i).numberOfPath!=0 && !isVisited[i]){
                double newMinTime = this.adjacencyMatrix.get(startIndex).get(i).leastTimeWeight() + minTime[startIndex];
                String newVehicle = this.adjacencyMatrix.get(startIndex).get(i).leastTimeVehicle();
                if (newMinTime < minTime[i]){
                    minTime[i] = newMinTime;
                    lastVertex[i] = this.vertices.get(startIndex);
                    vehicleUsed[i] = newVehicle;
                }
            }
        }
        if(!allVertexVisited(isVisited)){
            double min = Double.MAX_VALUE;
            int nextVertexIndex = startIndex;
            for(int i=0;i<this.vertices.size();i++){
                if (!isVisited[i] && minTime[i] < min){
                    min = minTime[i];
                    nextVertexIndex = i;
                }
            }
            isVisited[nextVertexIndex] = true;
            this.Dijkstra(nextVertexIndex,minTime,lastVertex,isVisited,vehicleUsed);
        }
    }
    private void Dijkstra(int startIndex, int[] minCost, String[] lastVertex, boolean[] isVisited,String[] vehicleUsed){
        for(int i=0;i<this.vertices.size();i++){
            if(this.adjacencyMatrix.get(startIndex).get(i).numberOfPath!=0 && !isVisited[i]){
                int newMinCost = this.adjacencyMatrix.get(startIndex).get(i).leastCostWeight() + minCost[startIndex];
                String newVehicle = this.adjacencyMatrix.get(startIndex).get(i).leastCostVehicle();
                if (newMinCost < minCost[i]){
                    minCost[i] = newMinCost;
                    lastVertex[i] = this.vertices.get(startIndex);
                    vehicleUsed[i] = newVehicle;
                }
            }
        }
        if(!allVertexVisited(isVisited)){
            int min = Integer.MAX_VALUE;
            int nextVertexIndex = startIndex;
            for(int i=0;i<this.vertices.size();i++){
                if (!isVisited[i] && minCost[i] < min){
                    min = minCost[i];
                    nextVertexIndex = i;
                }
            }
            isVisited[nextVertexIndex] = true;
            this.Dijkstra(nextVertexIndex,minCost,lastVertex,isVisited,vehicleUsed);
        }
    }
    private void trackPath(String startVertex, String endVertex,String[] lastVertex,String[] vehicleUsed){
        this.leastCostOrTimePath.clear();
        this.leastCostOrTimeVehicle.clear();

        String tempVertex = endVertex; // this is an iterator tracking from end vertex to start vertex
        while(!tempVertex.equals(startVertex)){
            this.leastCostOrTimePath.add(tempVertex);
            int index = this.vertices.indexOf(tempVertex);
            this.leastCostOrTimeVehicle.add(vehicleUsed[index]);
            tempVertex = lastVertex[index];
        }
        this.leastCostOrTimePath.add(startVertex);
        Collections.reverse(this.leastCostOrTimePath);
        Collections.reverse(this.leastCostOrTimeVehicle);
    }
    public int leastCost(String startVertex, String endVertex){
        if(!this.vertices.contains(startVertex) || !this.vertices.contains(endVertex)){
            System.out.println("Input vertex is incorrect. (in leastCost() method)");
            return -1;
        }

        int startIndex = this.vertices.indexOf(startVertex);
        int size = this.vertices.size();

        int[] minCost = new int[size];
        double[] minTime = new double[size];
        Arrays.fill(minCost,Integer.MAX_VALUE);
        minCost[startIndex] = 0;
        Arrays.fill(minTime,Double.MAX_VALUE);
        minTime[startIndex] = 0;
        String[] lastVertex = new String[size];
        boolean[] isVisited = new boolean[size];
        Arrays.fill(isVisited,false);
        isVisited[startIndex] = true;
        String[] vehicleUsed = new String[size];

        this.findLeastCostOrTimePathToAll(startVertex,2,minCost,minTime,lastVertex,isVisited,vehicleUsed);

        this.trackPath(startVertex,endVertex,lastVertex,vehicleUsed);

        return minCost[this.vertices.indexOf(endVertex)];
    }

    public double leastTime(String startVertex, String endVertex){
        if(!this.vertices.contains(startVertex) || !this.vertices.contains(endVertex)){
            System.out.println("Input vertex is incorrect. (in leastTime() method)");
            return -1;
        }

        int startIndex = this.vertices.indexOf(startVertex);
        int size = this.vertices.size();

        int[] minCost = new int[size];
        double[] minTime = new double[size];
        Arrays.fill(minCost,Integer.MAX_VALUE);
        minCost[startIndex] = 0;
        Arrays.fill(minTime,Double.MAX_VALUE);
        minTime[startIndex] = 0;
        String[] lastVertex = new String[size];
        boolean[] isVisited = new boolean[size];
        Arrays.fill(isVisited,false);
        isVisited[startIndex] = true;
        String[] vehicleUsed = new String[size];

        this.findLeastCostOrTimePathToAll(startVertex,1,minCost,minTime,lastVertex,isVisited,vehicleUsed);

        this.trackPath(startVertex,endVertex,lastVertex,vehicleUsed);

        return minTime[this.vertices.indexOf(endVertex)];
    }

    private void findLeastCostOrTimePathToAll(String startVertex,int timeOrMoney, int[] minCost,double[] minTime,
             String[] lastVertex, boolean[] isVisited,String[] vehicleUsed){

        if(timeOrMoney==1){
            this.Dijkstra(this.vertices.indexOf(startVertex),minTime,lastVertex,isVisited,vehicleUsed);
        }
        else{
            this.Dijkstra(this.vertices.indexOf(startVertex),minCost,lastVertex,isVisited,vehicleUsed);
        }
    }
}
