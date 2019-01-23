import java.lang. *;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Java code for thread creation by extending
// the Thread class

enum SockType {white, black, green, blue}

class Sock
{
    public SockType value;
    public int taken;

    Sock(SockType val)
    {
        value = val;
        taken = 0;
    }

    public void takeIt()
    {
        taken = 1;
    }
}

class RoboticArms extends Thread
{
    public Sock having;
    public List<Sock> input;

    RoboticArms(List<Sock> sockArray) {
        having = null;
        input = sockArray;
    }
    public void run() {
        try
        {
            System.out.println(Thread.currentThread().getId() + " running");
            pickUpASock();
        }
        catch (Exception e)
        {
            // Throwing an exception
            System.out.println ("Exception is caught");
        }
    }

    public void pickUpASock() {
        System.out.println(Thread.currentThread().getId() + "picking up started");
        while (having == null) {
            if(input.size() != 0) {
                int rand = new Random().nextInt(input.size());
                System.out.println(Thread.currentThread().getId() + " " + input.get(rand).value);
                synchronized (input.get(rand)) {
                    if (input.get(rand).taken == 0) {
                        System.out.println(Thread.currentThread().getId() + "inside if");
                        having = input.get(rand);
                        having.taken = 1;
                        input.remove(rand);
                        System.out.println(Thread.currentThread().getId() + " picked up " + having.value);
                    }
                }
            }
        }
    }

    public void dropASock() {
        if(having != null) {
            System.out.println(Thread.currentThread().getId() + " dropping " + having.value);
            input.add(having);
            having.taken = 0;
            having = null;
        }
    }

}

class MatchingMachine
{
    public Sock first, second;
    public int firstRoboticArm, secondRoboticArm;
    public boolean isFirstPresent;

    public MatchingMachine()
    {
        isFirstPresent = false;
    }
}

// Main Class
public class pru
{
    public static int result[] = new int[4];
    public static List<Sock> socks;
    public static int numberOfSockPairs;
    public static int numberOfSockPairsMatched;
    public static int numberOfRoboticArms;
    public static RoboticArms[] roboticArmsArray;
    public static MatchingMachine matchingMachineObject;

    public static void main(String[] args)
    {
        numberOfSockPairs = 5;
        numberOfRoboticArms = 6;
        loadSocks();
        createRoboticArms();
        matchingMachineObject = new MatchingMachine();
        pickUpSameColorSocks();
        for(int i = 0; i < 4; i++)
        {
            System.out.println(result[i]);
        }
    }

    public static void loadSocks()
    {
        socks = new ArrayList<>();
//        socks = new Sock[2 * numberOfSockPairs];
        for(int i = 0; i < numberOfSockPairs; i ++)
        {
            SockType rand = randomColour();
            Sock temp = new Sock(rand);
            socks.add(temp);
            temp = new Sock(rand);
            socks.add(temp);
            System.out.println(rand);
        }
    }

    public static SockType randomColour() {
        int pick;
        pick = new Random().nextInt(SockType.values().length);
//        System.out.println(pick);
        return SockType.values()[pick];
    }

    public static void createRoboticArms()
    {
        roboticArmsArray = new RoboticArms[numberOfRoboticArms];
        for(int i = 0; i < numberOfRoboticArms; i++)
        {
            roboticArmsArray[i] = new RoboticArms(socks);
            roboticArmsArray[i].start();
        }
    }

    public static void pickUpSameColorSocks()
    {
        int found = 0;
        int i = 0;
        while(found == 0 && numberOfSockPairsMatched < numberOfSockPairs)
        {
            for (i = 0; i < numberOfRoboticArms; i++)
            {
                if(roboticArmsArray[i].having != null)
                {
                    matchingMachineObject.first = roboticArmsArray[i].having;
                    matchingMachineObject.firstRoboticArm = i;
                }
                for (int j = i + 1; j < numberOfRoboticArms; j++)
                {
                    if(roboticArmsArray[j].having != null)
                    {
                        if (matchingMachineObject.first.value == roboticArmsArray[j].having.value)
                        {
                            matchingMachineObject.second = roboticArmsArray[j].having;
                            matchingMachineObject.secondRoboticArm = j;
                            found = 1;
                            break;
                        }
                    }
                }
                if(found == 1)
                {
                    break;
                }
            }
            if (found == 0)
            {
                for (i = 0; i < numberOfRoboticArms; i++)
                {
                    roboticArmsArray[i].dropASock();
                }
                for (i = 0; i < numberOfRoboticArms; i++)
                {
                    roboticArmsArray[i].pickUpASock();
                }
            }
        }
        if(found == 1)
        {
            roboticArmsArray[matchingMachineObject.firstRoboticArm].dropASock();
            roboticArmsArray[matchingMachineObject.firstRoboticArm].pickUpASock();
            roboticArmsArray[matchingMachineObject.secondRoboticArm].dropASock();
            roboticArmsArray[matchingMachineObject.secondRoboticArm].pickUpASock();
            shelfManager(matchingMachineObject.first.value);
            numberOfSockPairsMatched++;
            if(numberOfSockPairsMatched < numberOfSockPairs)
                pickUpSameColorSocks();
        }
    }

    public static void shelfManager(SockType val)
    {
        System.out.println(val + " added in result");
        result[val.ordinal()]++;
    }

}
