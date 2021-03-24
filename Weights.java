package hao.graph.practice;

import java.util.ArrayList;

public final class Weights {
    public ArrayList<String> vehicle;
    public ArrayList<Double> travelTime;
    public ArrayList<Integer> travelCost;
    public int numberOfPath;

    public Weights() {
        this.vehicle = new ArrayList<>();
        this.travelTime = new ArrayList<>();
        this.travelCost = new ArrayList<>();
        this.numberOfPath = 0;
    }

    public void addWeights(String vehicle, double time, int cost){
        this.vehicle.add(vehicle);
        this.travelTime.add(time);
        this.travelCost.add(cost);
        this.numberOfPath++;
    }

    public int leastCostWeight(){
        int min = Integer.MAX_VALUE;
        for(int i=0;i<this.numberOfPath;i++){
            if (this.travelCost.get(i)<min){
                min = this.travelCost.get(i);
            }
        }
        return min;
    }
    public double leastTimeWeight(){
        double min = Integer.MAX_VALUE;
        for(int i=0;i<this.numberOfPath;i++){
            if (this.travelTime.get(i)<min){
                min = this.travelTime.get(i);
            }
        }
        return min;
    }
    public String leastCostVehicle(){
        String output = null;
        int min = Integer.MAX_VALUE;
        for(int i=0;i<this.numberOfPath;i++){
            if (this.travelCost.get(i)<min){
                min = this.travelCost.get(i);
                output = this.vehicle.get(i);
            }
        }
        return output;
    }
    public String leastTimeVehicle(){
        String output = null;
        double min = Integer.MAX_VALUE;
        for(int i=0;i<this.numberOfPath;i++){
            if (this.travelTime.get(i)<min){
                min = this.travelTime.get(i);
                output = this.vehicle.get(i);
            }
        }
        return output;
    }

}
