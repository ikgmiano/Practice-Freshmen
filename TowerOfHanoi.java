import java.util.*;

public class TowerOfHanoi {
	static int numOfMoves = 0;
	
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
		
		solve(numOfDisc, start, middle, end, start, middle, end);
		
		System.out.println("The puzzle is done!");
		System.out.println("Number of moves: " + numOfMoves);
	}
	
	private static void loadDisc(Stack start, int size) {
		for (int i = 0; i < size; i++) {
			start.push(size - i);
		}
	}
	
	private static void solve(int n, Stack start, Stack middle, Stack end, Stack startRef, Stack midRef, Stack endRef) {
		// BASE CASE
		if (n == 1) {
			try {
				Thread.sleep(400);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			end.push(start.pop());
			numOfMoves++;
			
			System.out.println("\n" + startRef);
			System.out.println(midRef);
			System.out.println(endRef);

			return;
		}
		
		// RECURSIVE CASE
		
		// put the n - 1 discs at the middle to make way for the nth disc
		solve(n - 1, start, end, middle, startRef, midRef, endRef);
		
		try {
			Thread.sleep(400);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// put nth disc at the end
		end.push(start.pop());
		numOfMoves++;

		System.out.println("\n" + startRef);
		System.out.println(midRef);
		System.out.println(endRef);
		
		// recursively call solve() on the remaining n - 1 discs.
		solve(n - 1, middle, start, end, startRef, midRef, endRef);
	}
}