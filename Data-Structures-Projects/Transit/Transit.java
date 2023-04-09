import java.util.ArrayList;
import java.util.LinkedList;

/**
 * This class contains methods which perform various operations on a layered linked
 * list to simulate transit
 * 
 * @author Ishaan Ivaturi
 * @author Prince Rawal
 */

public class Transit {

	//helper methods
	//find length of linked list
	private static int findLength(TNode trainZero){
		TNode curr = trainZero;
		int count = 0; 
		while (curr != null){
			count++;
			curr=curr.next;
		}
		return count;
	}

	//convert linked list to array
	private static int[] convertArr(TNode trainZero){
		int len = findLength(trainZero);
		int []arr = new int[len];
		int index = 0;
		TNode curr = trainZero;
		while(curr!=null){
			arr[index++]=curr.location;
			curr=curr.next;
		}
		return arr;
	}

	//remove duplicate locations from a linked list
	private static void removeDuplicates(TNode trainZero)
    {
        // do nothing if the list is empty
        if (trainZero == null){
            return;
        }
        TNode current = trainZero;

        // compare the current node with the next node
        while (current.next != null){
            if (current.location == current.next.location){
                TNode nextNext = current.next.next;
                current.next = nextNext;
            }
            else {
                current = current.next;    // only advance if no deletion
            }
        }
    }

	//get last node in a linked list
	private static TNode getLast(TNode trainZero){
		TNode temp = trainZero;
		while(temp.next!=null){
			temp = temp.next;
		}
		return temp;
	}

	/**
	 * Makes a layered linked list representing the given arrays of train stations, bus
	 * stops, and walking locations. Each layer begins with a location of 0, even though
	 * the arrays don't contain the value 0.
	 * 
	 * @param trainStations Int array listing all the train stations
	 * @param busStops Int array listing all the bus stops
	 * @param locations Int array listing all the walking locations (always increments by 1)
	 * @return The zero node in the train layer of the final layered linked list
	 */


	public static TNode makeList(int[] trainStations, int[] busStops, int[] locations) {
		// WRITE YOUR CODE HERE	
		//create 3 singly linked lists, populate w array items
		TNode trainHead = new TNode(0);
		TNode trainLast = trainHead;
		for(int count = 0; count < trainStations.length; count++){
			TNode newNode = new TNode(trainStations[count]);
			TNode ptr = trainLast;
			trainLast = newNode;
			trainLast.next = null;
			ptr.next = trainLast;
			ptr = trainLast;
		}

		TNode busHead = new TNode(0);
		TNode busLast = busHead;
		for(int count = 0; count < busStops.length; count++){
			TNode newNode = new TNode(busStops[count]);
			TNode ptr = busLast;
			busLast = newNode;
			busLast.next = null;
			ptr.next = busLast;
			ptr = busLast;
		}

		TNode locHead = new TNode(0);
		TNode locLast = locHead;
		for(int count = 0; count < locations.length; count++){
			TNode newNode = new TNode(locations[count]);
			TNode ptr = locLast;
			locLast = newNode;
			locLast.next = null;
			ptr.next = locLast;
			ptr = locLast;
		}

		//temporary pointer nodes to traverse
		TNode trainPtr = trainHead;
		TNode busPtr = busHead;
		TNode busPtr2 = busHead;
		TNode locPtr = locHead;
		//links top and middle layer
		while (trainPtr!=null){
			if (trainPtr.location==busPtr.location){
				trainPtr.down = busPtr;
				busPtr = busPtr.next;
				trainPtr = trainPtr.next;
			}
			else if(busPtr.location < trainPtr.location){
				busPtr = busPtr.next;
			}
		}
		//links middle and bottom layer
		while (busPtr2!=null){
			if (busPtr2.location==locPtr.location){
				busPtr2.down = locPtr;
				locPtr = locPtr.next;
				busPtr2 = busPtr2.next;
			}
			else if(locPtr.location < busPtr2.location){
				locPtr = locPtr.next;
			}
		}
		//delete duplicate 0 column when duplicate() is called
		removeDuplicates(trainHead);
		removeDuplicates(busHead);
		removeDuplicates(locHead);

		return trainHead;
	}

	/**
	 * Modifies the given layered list to remove the given train station but NOT its associated
	 * bus stop or walking location. Do nothing if the train station doesn't exist
	 * 
	 * @param trainZero The zero node in the train layer of the given layered list
	 * @param station The location of the train station to remove
	 */
	public static void removeTrainStation(TNode trainZero, int station) {
		// WRITE YOUR CODE HERE
		TNode ptrPrevious = trainZero;
		TNode ptr = trainZero.next;
		while(ptr!=null){
			if(ptr.location == station){
				ptrPrevious.next = ptr.next;
				break;
			}
			ptr = ptr.next;
			ptrPrevious = ptrPrevious.next;
		}
	}

	/**
	 * Modifies the given layered list to add a new bus stop at the specified location. Do nothing
	 * if there is no corresponding walking location.
	 * 
	 * @param trainZero The zero node in the train layer of the given layered list
	 * @param busStop The location of the bus stop to add
	 */
	public static void addBusStop(TNode trainZero, int busStop) {
		// WRITE YOUR CODE HERE
		TNode ptrPrevious = trainZero.down;
		TNode ptr = trainZero.down.next;
		TNode newStop = new TNode();
		newStop.location = busStop;

		//if bus stop has no walking location, do nothing
		int locSize = findLength(trainZero.down.down)-1;
		if(locSize < busStop){
			return;
		}	
		
		//insert busStop into bus layer
		else{
			while(ptr!=null){
				if( ptr.location > busStop && ptrPrevious.location < busStop){
					ptrPrevious.next = newStop;
					newStop.next = ptr;
				}
				ptr = ptr.next;
				ptrPrevious = ptrPrevious.next;
			}

			//if bus stop is greater than length of current bus stop layer
			if(busStop > findLength(trainZero.down)){
				TNode last = getLast(trainZero.down);
				last.next = newStop;
			}

			//connect busStop to walking layer
			TNode ptrLocation = trainZero.down.down;
			while(ptrLocation!=null){
				if(ptrLocation.location == newStop.location){
					newStop.down = ptrLocation;
					break;
				}
				ptrLocation = ptrLocation.next;
			}
		}

	}
	
	/**
	 * Determines the optimal path to get to a given destination in the walking layer, and 
	 * collects all the nodes which are visited in this path into an arraylist. 
	 * 
	 * @param trainZero The zero node in the train layer of the given layered list
	 * @param destination An int representing the destination
	 * @return
	 */
	public static ArrayList<TNode> bestPath(TNode trainZero, int destination) {
		// WRITE YOUR CODE HERE
		ArrayList<TNode> bestPath = new ArrayList<>();
		TNode path = trainZero.next;
		TNode pathPrevious = trainZero;
		TNode trainTail = getLast(trainZero);
		while(pathPrevious!= null){
			if(path.location < destination){
				bestPath.add(pathPrevious);
				if(path.next==null){
					bestPath.add(path);
					bestPath.add(path.down);
					path=path.down.next;
					pathPrevious = path.down;
				}
				else{
				path=path.next;
				pathPrevious = pathPrevious.next;	
				}
			}
			if(path.location == destination){
				bestPath.add(pathPrevious);
				bestPath.add(path);
				break;
			}
			if(path.location > destination){
				bestPath.add(pathPrevious);
				pathPrevious = pathPrevious.down;
				path = pathPrevious.next;
			}
		}
			


		if(path.down!=null){
			bestPath.add(path.down);
			if(path.down.down!=null){
				bestPath.add(path.down.down);
			}
		}
		return bestPath;
	}

	/**
	 * Returns a deep copy of the given layered list, which contains exactly the same
	 * locations and connections, but every node is a NEW node.
	 * 
	 * @param trainZero The zero node in the train layer of the given layered list
	 * @return
	 */
	public static TNode duplicate(TNode trainZero) {
		// WRITE YOUR CODE HERE

		TNode trainHead = trainZero;
		TNode busHead = trainZero.down;
		TNode locHead = trainZero.down.down;
		//convert linked list to array
		int[] trainCopy = convertArr(trainHead);
		int[] busCopy = convertArr(busHead);
		int[] locCopy = convertArr(locHead);

		//call makeList 
		TNode deepCopy = makeList(trainCopy, busCopy, locCopy);
		return deepCopy;
	}

	/**
	 * Modifies the given layered list to add a scooter layer in between the bus and
	 * walking layer.
	 * 
	 * @param trainZero The zero node in the train layer of the given layered list
	 * @param scooterStops An int array representing where the scooter stops are located
	 */
	public static void addScooter(TNode trainZero, int[] scooterStops) {
		// WRITE YOUR CODE HERE
		TNode scooterZero = new TNode(0);
		TNode scooterLast = scooterZero;
		for(int count = 0; count < scooterStops.length; count++){
			TNode newNode = new TNode(scooterStops[count]);
			TNode ptr = scooterLast;
			scooterLast = newNode;
			scooterLast.next = null;
			ptr.next = scooterLast;
			ptr = scooterLast;
		}

		//temporary pointer nodes to traverse
		TNode busPtr = trainZero.down;
		TNode scootPtr = scooterZero;
		TNode scootPtr2= scooterZero;
		TNode locPtr = trainZero.down.down;
		//links bus and scooter layer
		while (busPtr!=null){
			if (busPtr.location==scootPtr.location){
				busPtr.down = scootPtr;
				scootPtr = scootPtr.next;
				busPtr = busPtr.next;
			}
			else if(scootPtr.location < busPtr.location){
				scootPtr = scootPtr.next;
			}
		}

		//links scooter and walking layer
		while (scootPtr2!=null){
			if (scootPtr2.location==locPtr.location){
				scootPtr2.down = locPtr;
				locPtr = locPtr.next;
				scootPtr2 = scootPtr2.next;
			}
			else if(locPtr.location < scootPtr2.location){
				locPtr = locPtr.next;
			}
		}
	}
}