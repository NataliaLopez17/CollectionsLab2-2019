import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;


public class JavaRestaurant {


	/**
	 * Exercise 1
	 * In this exercise you want to simulate a restaurant waiting line.
	 * Time is denoted by a variable "t" that starts at 0
	 * Follow the instructions in the comments to complete this exercise
	 * @param incomingCustomers
	 * @return - returns the total amount of money that the customers 
	 * paid for the service. 
	 */
	public static int[] fastFood(Queue<Customer> incomingCustomers) {
		int[] solution= new int[2];
		int timer = 0;
		int t = 0; //time
		int totalMoney = 0; //money earned
		Customer customerAtCashRegister = null; //where the customer will be attended
		Queue<Customer> waitingLine = new LinkedList<>(); //waiting Line

		//Looks for first customer
		while (t != incomingCustomers.peek().getTimeEntered()) {
			t++;
			timer++;
		}

		//Assign first customer to cash register
		customerAtCashRegister = (incomingCustomers.poll());

		//If initial list, the waiting line and the cash register aren't empty.
		while (!incomingCustomers.isEmpty() || !waitingLine.isEmpty() || customerAtCashRegister!= null) {

			/** If cash register is available and someone is waiting in line, 
			 * then move the next customer from waiting line to cash register.
			 * 
			 * Looks if some incoming customer can enter the restaurant.
			 * If that is the case, move that customer to the waiting line.
			 *	
			 * Takes 1 unit of time from customer in cash register for their respective order.
			 *	
			 * If the cash register is done, accumulate the money from the order
			 * and free the cash register.
			 */
			if (customerAtCashRegister == null){
				customerAtCashRegister = waitingLine.poll();
			}
			while(!incomingCustomers.isEmpty() && incomingCustomers.peek().getTimeEntered() == t){
				if(customerAtCashRegister == null){
					customerAtCashRegister = incomingCustomers.poll();
				}
				else{
					waitingLine.add(incomingCustomers.poll());
				}
			}

			if(customerAtCashRegister != null){
				customerAtCashRegister.setTimeToComplete(customerAtCashRegister.getTimeToComplete() - 1);
				if (customerAtCashRegister.getTimeToComplete() == 0){
					totalMoney += customerAtCashRegister.getBill();
					customerAtCashRegister = null;
				}
			}


			t++;
			timer++;
		}
		solution[0] = totalMoney;
		solution[1]= timer;
		return solution;
	}

	/**
	 * Exercise 2.A
	 * In this exercise you want to simulate a restaurant waiting line.
	 * Time is denoted by a variable "t" that starts at 0
	 * Follow the instructions in the comments to complete this exercise
	 * The customer's patience runs out in the waiting line as time passes by.
	 * @param incomingCustomers
	 * @return - returns the total amount of money that the customers 
	 * paid for the service. 
	 * Remember: you must to complete the method call patienceReducer, needed for this problem. 
	 * See Exercice 2.B
	 */

	public static int[] fastFoodWithPatience(Queue<Customer> incomingCustomers) {
		int[] solution= new int[2];
		int timer = 0;
		int t = 0; //time
		int totalMoney = 0; //money earned
		Customer customerAtCashRegister = null; //where the customer will be attended
		Queue<Customer> waitingLine = new LinkedList<>(); //waiting Line

		//Looks for first customer
		while (t != incomingCustomers.peek().getTimeEntered()) {
			t++;
			timer++;
		}

		//Assign first customer to cash register
		customerAtCashRegister = (incomingCustomers.poll());

		//If initial list, the waiting line and the cash register aren't empty.
		while (!incomingCustomers.isEmpty() || !waitingLine.isEmpty() || customerAtCashRegister!= null) {

			/** If cash register is available and someone is waiting in line, 
			 * then move the next customer from waiting line to cash register.
			 * 
			 * Looks if some incoming customer can enter the restaurant.
			 * If that is the case, move that customer to the waiting line.
			 *	
			 * Takes 1 unit of time from customer in cash register for their respective order.
			 *	
			 * The customer's patience decreases in the waiting line.	
			 *
			 * If the cash register is done, accumulate the money from the order
			 * and free the cash register.
			 */
			if (customerAtCashRegister == null && !waitingLine.isEmpty()){
				customerAtCashRegister = waitingLine.poll();
			}
			while(!incomingCustomers.isEmpty() && incomingCustomers.peek().getTimeEntered() == t){
				waitingLine.add(incomingCustomers.poll());

			}
			if(customerAtCashRegister != null){
				customerAtCashRegister.setTimeToComplete(customerAtCashRegister.getTimeToComplete() - 1); 
			}
			waitingLine = patienceReducer(waitingLine);




			if (customerAtCashRegister.getTimeToComplete() == 0){
				totalMoney += customerAtCashRegister.getBill();
				customerAtCashRegister = null;
			}

			t++;
			timer++;
		}
		solution[0]= totalMoney;
		solution[1]= timer;

		return solution;
	}

	/**
	 * Exercise 2.B
	 * Implement a method to reduce 1 unit of time to every
	 * customer in the waiting line to his/hers patience.
	 * If patience is equals to zero, the customer will get out of the line and leave.
	 * @param waitingLine
	 * @return the waitingLine with the patience depleted and those with equal to 0 removed.
	 */
	private static Queue<Customer> patienceReducer(Queue<Customer> waitingLine) {
		Queue<Customer> neue = new LinkedList<>();

		for(Customer c : waitingLine) {

			c.setPatience(c.getPatience() - 1);
		}

		for(Customer c : waitingLine) {
			if(c.getPatience() > 0) {
				neue.add(c);
			}
		}

		return neue;
	}



	/**
	 * Exercise 3
	 * this is a famous restaurant, usually some celebrity used to visit this restaurant.
	 * This celebrity don't know anyone in the restaurant. Complete the method celebrityCustumer to 
	 * find is there is a celebrity in the restaurant. 
	 * @param KNOWSTATUS; adjacent matrix, contain the relation between customers. Knows or unknows
	 * and the number of people that are in the restaurant, each represented by index.
	 * @return the index of celebrity (related to the  adjacent matrix) is there exits, or -1 is 
	 * none customer is a celebrity.
	 * Hint: Use the method knows to find if the customer A knows customer B. 
	 * This relation is not bidirectional.
	 * REMEMBER: You must to solve the problem using stack, otherwise you will get no points.
	 */

	public static int celebrityCustomer(int[][] KNOWSTATUS, int n){
		// Step 1 :Push everybody onto stack, remember that the people are 
		// represented by index.

		// Step 2 :Pop off top 
		// two persons from the  
		// stack, discard one  
		// person based on return 
		// status of knows(A, B). 

		// Step 3 : Push the  
		// remained person onto stack. 

		// Step 5 : Check if the last  
		// person is celebrity or not,
		// depend the result, return the index of the celebrity
		// otherwise return -1.

		Stack<Integer> st = new Stack<>();

		for(int i = 0; i <= n; i++) {
			st.push(n);
		}
		int p1 = st.pop();
		int p2 = st.pop();
		boolean r = knows(KNOWSTATUS, p1, p2);
		if(r) {
			st.push(p2);
		}
		st.push(p1);

		for(int i = 0; i < n; i++) {
			if(KNOWSTATUS[i][i++]==1){
				return-1;
			}
		}
		return st.peek();
	}


	/*
	 * This method check if the person 'a' knows the person 'b'.
	 * if 'a' know 'b' then return true, otherwise false.
	 * */
	static boolean knows(int[][] KNOWSTATUS ,int a, int b)
	{
		if(KNOWSTATUS[a][b]  == 1)
		{
			return true;
		}
		else
			return false;
	}

	public static class CustomerComparator implements Comparator<Customer> {


		@Override
		public int compare(Customer arg0, Customer arg1) {

			if(arg0.getPatience() == arg1.getPatience())
			{ 
				if(arg0.getBill() < arg1.getBill()) return 1;
				else
					return -1;
			}
			else if (arg0.getPatience()<arg1.getPatience()) {return -1;}

			else 
				return 1;
		}


	}

	/**
	 * Recursive Selection Sort for an array
	 * @param shelve - Array to be sorted
	 */
	public static Queue<Customer> selectionSort(Queue<Customer> line, Comparator<Customer> comp) {
		Customer[] lineArr = new Customer[line.size()];
		int idx = 0;
		for(Customer cu: line) {
			lineArr[idx] = cu;
			idx++;
		}
		selHelper(lineArr, 0, comp);
		Queue<Customer> newLine = new LinkedList<>();
		for(idx = 0; idx < lineArr.length; idx++) {
			newLine.add(lineArr[idx]);
		}
		return newLine;

	}

	private	static void	selHelper(Customer[] lineArr, int tailIndex, Comparator<Customer> comp) {
		if(tailIndex >= lineArr.length) { return; }
		int	minIndex = tailIndex;
		for(int	i = tailIndex; i < lineArr.length; i++) {
			if(comp.compare(lineArr[minIndex], lineArr[i]) > 0) {
				minIndex = i;
			}	
		}
		Customer temp = lineArr[minIndex];
		lineArr[minIndex] = lineArr[tailIndex];
		lineArr[tailIndex] = temp;
		selHelper(lineArr, tailIndex+1, comp);
	}

	public static class Customer {
		public int timeEntered;
		public int bill;
		public int timeToComplete;
		public int patience;
		public String Name;

		public Customer(String Name,int timeEntered, int bill, int timeToComplete, int patience) {
			this.timeEntered = timeEntered;
			this.bill = bill;
			this.timeToComplete = timeToComplete;
			this.patience = patience;
			this.Name = Name;
		}

		public Customer(CustomerBuilder builder) {
			this(builder.Name,
					builder.timeEntered, 
					builder.bill, 
					builder.timeToCompleted, 
					builder.patience);
		}

		public String getName() {
			return Name;
		}

		public void setName(String name) {
			Name = name;
		}

		public int getTimeEntered() {
			return timeEntered;
		}

		public void setTimeEntered(int timeEntered) {
			this.timeEntered = timeEntered;
		}

		public int getBill() {
			return bill;
		}

		public void setBill(int bill) {
			this.bill = bill;
		}

		public int getTimeToComplete() {
			return timeToComplete;
		}

		public void setTimeToComplete(int timeToComplete) {
			this.timeToComplete = timeToComplete;
		}

		public int getPatience() {
			return patience;
		}

		public void setPatience(int pacience) {
			patience = pacience;
		}

		public String toString() {
			return "("+Name + ", Patience: " + patience + " \nOrders Time: " + timeToComplete+")";
		}

	}
	public static class CustomerBuilder{
		private int timeEntered = 0;
		private int bill = 0;
		private int timeToCompleted = 0;
		private int patience = 0;
		private String Name = "";

		public CustomerBuilder timeEntered(int val) {
			timeEntered = val;
			return this;
		}

		public CustomerBuilder bill(int val) {
			bill = val;
			return this;
		}

		public CustomerBuilder timeToCompleted(int val) {
			timeToCompleted = val;
			return this;
		}

		public CustomerBuilder patience(int val) {
			patience = val;
			return this;
		}

		public CustomerBuilder Name(String val) {
			Name = val;
			return this;
		}

		public Customer build() {
			return new Customer(this);
		}
	}
}
