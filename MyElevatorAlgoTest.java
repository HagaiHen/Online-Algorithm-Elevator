package ex0.algo;

import ex0.Building;
import ex0.CallForElevator;
import ex0.simulator.Simulator_A;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//Hagai Hen 313414872
//Nir Geron 315874925

class MyElevatorAlgoTest {


    Building b1 = Simulator_A.getBuilding();
    MyElevatorAlgo myAlgo1;
    CallForElevator call = new CallForElevator() {
        @Override
        public int getState() {
            return 1;
        }

        @Override
        public double getTime(int state) {
            return 3.0;
        }

        @Override
        public int getSrc() {
            return -4;
        }

        @Override
        public int getDest() {
            return 16;
        }

        @Override
        public int getType() {
            return 1;
        }

        @Override
        public int allocatedTo() {
            return 0;
        }
    };
    Building b2;
    MyElevatorAlgo myAlgo2;
    @Test
    void getBuilding() {
        //assertEquals(Simulator_A.getBuilding(), b1);
    }

    @Test
    void algoName() {

    }

    @Test
    void allocateAnElevator() { //you have to run it separate

        Simulator_A.initData(9, null); //choose case 9
        b1 = Simulator_A.getBuilding();
        myAlgo1 = new MyElevatorAlgo(b1);

        int allocate = myAlgo1.allocateAnElevator(call);
        boolean bool = false;
        for (int i = 0; i < myAlgo1.getBuilding().numberOfElevetors(); i++) {
            if (i == allocate)
                bool = true;
        }
        assertTrue(bool);

        try { //should be an error, because the floor it doesnt exists
            allocate = myAlgo1.allocateAnElevator(call);
            bool = false;
            for (int i = 0; i < myAlgo1.getBuilding().numberOfElevetors(); i++) {
                if (i == allocate)
                    bool = true;
            }
            assertTrue(bool);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR: the floor doesn't exists");
        }

    }

    @Test
    void cmdElevator() {
        Simulator_A.initData(9, null);//choose case 9
        b2 = Simulator_A.getBuilding();
        myAlgo2 = new MyElevatorAlgo(b2);
        ElevatorAlgo ex0_alg = new MyElevatorAlgo(Simulator_A.getBuilding());
        Simulator_A.initAlgo(ex0_alg);
        int ind=myAlgo2.allocateAnElevator(call);

        Simulator_A.runSim();

        assertEquals(call.getSrc(),myAlgo2.getBuilding().getElevetor(ind).getPos());
    }
}