package ex0.algo;

import ex0.Building;
import ex0.CallForElevator;
import ex0.Elevator;

import java.util.*;
import java.util.stream.Collectors;

//Hagai Hen 313414872
//Nir Geron 315874925

public class MyElevatorAlgo implements ElevatorAlgo {

    private Building building;
    private ArrayList<Integer>[] queue;
    private ArrayList<Elevator> elevList = new ArrayList<>();


    public MyElevatorAlgo(Building b) {
        this.building = b;
        this.queue = new ArrayList[building.numberOfElevetors()];

        for (int i = 0; i < building.numberOfElevetors(); i++) {
            this.queue[i] = new ArrayList<>();
            elevList.add(this.building.getElevetor(i));
        }
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public ArrayList<Integer>[] getQueue() {
        return queue;
    }

    public void setQueue(ArrayList<Integer>[] queue) {
        this.queue = queue;
    }

    public ArrayList<Elevator> getElevList() {
        return elevList;
    }

    public void setElevList(ArrayList<Elevator> elevList) {
        this.elevList = elevList;
    }

    @Override
    public Building getBuilding() {
        return building;
    }

    @Override
    public String algoName() {
        return "Nir & Hagai Algorithm";
    }

    @Override
    public int allocateAnElevator(CallForElevator c) {

        int chosenElev = 0;
        double time = Integer.MAX_VALUE;
        for (int i = 0; i < this.building.numberOfElevetors(); i++) {
            if (time >= CalculateTime(c, elevList.get(i).getID())) {
                time = CalculateTime(c, elevList.get(i).getID());
                chosenElev = elevList.get(i).getID();
            }
        }
        if (c.getType() == 1) {
            if (this.building.getElevetor(chosenElev).getPos() <= c.getSrc()) {
                this.queue[chosenElev].add(c.getSrc());
                this.queue[chosenElev].add(c.getDest());
                Collections.sort(this.queue[chosenElev]);
            } else {
                this.queue[chosenElev].add(c.getSrc());
                this.queue[chosenElev].add(c.getDest());
            }
        } else {
            if (c.getType() == -1) {
                this.queue[chosenElev].add(c.getSrc());
                this.queue[chosenElev].add(c.getDest());
            }
        }
        return chosenElev;


    }
    // this function calculate the time for each elevator and choose the fastest

    private double CalculateTime(CallForElevator c, int elev) {
        double ans = 0;
        double openDoor = this.building.getElevetor(elev).getTimeForOpen();
        double closeDoor = this.building.getElevetor(elev).getTimeForClose();
        double startTime = this.building.getElevetor(elev).getStartTime();
        double stopTime = this.building.getElevetor(elev).getStopTime();
        Elevator thisElev = this.building.getElevetor(elev);
        int posElev = thisElev.getPos();
        double speed = thisElev.getSpeed();
        int src = c.getSrc();
        int dest = c.getDest();
        double floorTime = openDoor + closeDoor + startTime + stopTime;
        List<Integer> currQueue = this.queue[elev].stream().collect(Collectors.toList()); //remove duplicates
        if (currQueue.isEmpty()) {
            ans = ans + Math.abs((src - posElev) / speed) + Math.abs((dest - src) / speed) + floorTime; //calculate the time form src to pos, and src to dest
        } else {
            if (c.getType() == 1) {
                currQueue.add(c.getSrc());
                currQueue.add(c.getDest());
                Collections.sort(currQueue);
             } else {
                currQueue.add(c.getSrc());
                currQueue.add(c.getDest());
            }
        }
        for (int i = 0; i < currQueue.size() - 1; i++) {
            if (this.queue[elev].get(0) != null) {
                ans = ans + (Math.abs(currQueue.get(i) - currQueue.get(i + 1)) / speed) + floorTime + 1 / speed; //calculate the time between floors
            }
            if (i == 0) {
                ans = ans + (Math.abs(currQueue.get(i) - posElev) / speed) + floorTime; //calculate the time from pos to first mission
            }
        }

        return ans;
    }

    @Override
    public void cmdElevator(int elev) {
        if (!(this.queue[elev].isEmpty())) {
            Elevator curr = this.getBuilding().getElevetor(elev);
            if (this.building.getElevetor(elev).getState() == 0) {
                curr.goTo(this.queue[elev].get(0));
            }
        }
        if (!(this.queue[elev].isEmpty())) {
            if (this.building.getElevetor(elev).getPos() == this.queue[elev].get(0)) {
                removeAllAppears(this.queue[elev], this.queue[elev].get(0));
            }
        }
    }

    private void removeAllAppears(ArrayList<Integer> AL, int val) {
        for (int i = 0; i < AL.size(); i++) {
            if (AL.get(i) == val)
                AL.remove(i);
        }

    }
}