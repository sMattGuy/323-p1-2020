import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.*;
import java.io.PrintStream;
/**
* Matthew Flammia, 23661371
* CSCI 355 Project 1
* To execute, have the source data file in working directory.
* args[0] is the source data file
* args[1] is the stack file name
* args[2] is the queue file name
* args[3] is the linked list file name
* Example run commmand:
* java -cp bin FlammiaM_Project1_Java StackQueueList_Data.txt outFile1.txt outFile2.txt outFile3.txt
**/
public class FlammiaM_Project1_Java{
	public static void main(String[] args) throws Exception{
		//file io setup
		File data = new File(args[0]);
		//stack portion
		LLStack stackPart = new LLStack();
		stackPart.buildStack(data);
		stackPart.dumpStack(args[1]);
		//queue
		LLQueue queuePart = new LLQueue();
		queuePart.buildQueue(data);
		queuePart.dumpQueue(args[2]);
		//LList
		LList listPart = new LList();
		listPart.buildList(data);
		listPart.printList(args[3]);
	}
}

class listNode{
	//variables
	int data;
	listNode next;
	//constructors
	listNode(int data){
		this.data = data;
	}
}

class LLStack{
	//variables
	listNode top;
	//constructors
	LLStack(){
		this.top = new listNode(99999);
		this.top.next = null;
	}
	//methods
	void push(listNode node){
		node.next = this.top;
		this.top = node;
	}
	listNode pop(){
		//checks if empty; then returns null if is
		if(isEmpty()){
			return null;
		}
		//makes a temp node, stores the value of top, then sets new top to top.next
		listNode temp;
		temp = this.top;
		this.top = this.top.next;
		return temp;
	}
	boolean isEmpty(){
		//checks if stack is empty
		if(this.top.next==null){
			return true;
		}
		return false;
	}
	void printTop(){
		//prints to console, or whatever outstream is set to
		System.out.println(this.top.data);
	}
	void buildStack(File data) throws Exception{
		//begins scanning input file for int
		Scanner input = new Scanner(data);
		while(input.hasNextInt()){
			//goes through all integers and adds them to stack
			listNode node = new listNode(input.nextInt());
			this.push(node);
		}
	}
	void dumpStack(String outFile1) throws IOException{
		//sets output stream to the name specified in args
		PrintStream fileOut = new PrintStream(outFile1);
		System.setOut(fileOut);
		//loops the stack, and empties it and prints it
		while(!this.isEmpty()){
			this.printTop();
			this.pop();
		}
		fileOut.close();
	}
}

class LLQueue{
	//variables
	listNode head;
	listNode tail;
	listNode dummy;
	//methods
	LLQueue(){
		//constructor
		dummy = new listNode(99999);
		head = new listNode(0);
		tail = new listNode(0);
		head.next = dummy;
		tail.next = dummy;
	}
	void insertQ(listNode node){
		//special case for first node insert
		if(this.isEmpty()){
			dummy.next = node;
			tail.next = node;
		}
		//case for all other nodes after first
		else{
			tail.next.next = node;
			tail.next = node;
		}
	}
	listNode deleteQ(){
		//prevents deleting empty queue
		if(isEmpty()){
			return null;
		}
		//special case for single node in queue
		if(dummy.next == tail.next){
			listNode temp = dummy.next;
			tail.next = dummy;
			dummy.next = null;
			return temp;
		}
		//generic node removal 
		else{
			listNode temp = dummy.next;
			dummy.next = dummy.next.next;
			return temp;
		}
	}
	boolean isEmpty(){
		if(tail.next == dummy){
			return true;
		}
		return false;
	}
	void buildQueue(File data) throws Exception{
		//begins scanning input file for int
		Scanner input = new Scanner(data);
		while(input.hasNextInt()){
			//goes through all integers and adds them to queue
			listNode node = new listNode(input.nextInt());
			this.insertQ(node);
		}
	}
	void dumpQueue(String outFile2) throws IOException{
		//creates file output stream
		PrintStream fileOut = new PrintStream(outFile2);
		System.setOut(fileOut);
		//loops until empty, printing and removing nodes
		while(!this.isEmpty()){
			listNode temp = this.deleteQ();
			System.out.println(temp.data);
		}
		fileOut.close();
	}
}

class LList{
	//variables
	listNode head;
	listNode dummy;
	//methods
	LList(){
		//constructor
		head = new listNode(0);
		dummy = new listNode(99999);
		head.next = dummy;
	}
	listNode findSpot(listNode node){
		listNode spot = dummy;
		//loops until spot reaches the final node
		while(spot.next != null){
			if(spot.next.data >= node.data){
				//if spots data is greater, get ready for insert
				return spot;
			}
			spot = spot.next;
		}
		//if spot reaches the end, return the final position
		return spot;
	}
	void insertOneNode(listNode spot, listNode node){
		//simple insert code
		node.next = spot.next;
		spot.next = node;
	}
	void buildList(File data) throws Exception{
		//begins scanning input file for int
		Scanner input = new Scanner(data);
		while(input.hasNextInt()){
			//goes through all integers and adds them to list
			listNode node = new listNode(input.nextInt());
			this.insertOneNode(this.findSpot(node), node);
		}
	}
	void printList(String outFile3) throws IOException{
		//creates file stream 
		PrintStream fileOut = new PrintStream(outFile3);
		System.setOut(fileOut);
		//creates read head to traverse 
		listNode readHead = this.head.next;
		//initial printout
		System.out.print("listHead-->");
		while(readHead.next != null){
			//read head loops, printing the data value of itself and its next
			System.out.print("("+readHead.data+", "+readHead.next.data+")-->");
			readHead = readHead.next;
		}
		//prints final data message
		System.out.println("("+readHead.data+", NULL)-->NULL");
		fileOut.close();
	}
}


































