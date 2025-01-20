import java.util.*;

public class TowerOfHanoi {
	public static void main(String[] args) {
		Stack<Integer> start = new Stack<>();
		Stack<Integer> middle = new Stack<>();
		Stack<Integer> end = new Stack<>();
		
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter number of discs: ");
		int numOfDisc = scanner.nextInt();
		scanner.close();
		
		loadDisc(start, numOfDisc);
		
		System.out.println("\nInitial State:"); 
		System.out.println(start);
		System.out.println(middle);
		System.out.println(end);
		
		solve(numOfDisc, start, middle, end);
		
		System.out.println("\nSolved State:");
		System.out.println(start);
		System.out.println(middle);
		System.out.println(end);
		
		System.out.println("The puzzle is done!");
		
	}
	
	private static void loadDisc(Stack start, int size) {
		for (int i = 0; i < size; i++) {
			start.push(size - i);
		}
	}
	
	private static void solve(int n, Stack start, Stack middle, Stack end) {
		// base case
		if (n == 1) {
			end.push(start.pop());
			return;
		}
		
		// recursive case
		
		// put n - 1 discs at the middle to make way for the nth disc
		solve(n - 1, start, end, middle);
		
		// put nth disc at the end
		end.push(start.pop());
		
		// then recursively call solve on the remaining n - 1 discs,
        // since we put in the middle previously, we use middle as the start
        // and put it in the end
		solve(n - 1, middle, start, end);
	}
}