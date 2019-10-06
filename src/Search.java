import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Stack;

public class Search {

	public static void main(String args[]) throws Exception {
		
		int minState = 99999999;
		int maxState = 0;
		int avgState=0;
		int minSteps = 99999999;
		int maxSteps = 0;
		int avgStep=0;
		int minQueueLength = 99999999; 
		int maxQueueLength = 0;
		int avgQLength=0;
		int i=0;
		while(i<20 )
		{
			if(i!=10)
			{
		
		BufferedReader br = new BufferedReader(new FileReader(".\\Puzzle2-"+i+".mb"));
		br.readLine();
		String st;
		String stb;
		Tile t;
		Scanner sc;
		ArrayList<Integer> a = new ArrayList<Integer>();
		ArrayList<Tile> allTiles = new ArrayList<Tile>();
		while ((st = br.readLine()) != null && !st.equalsIgnoreCase("</Marble>")) {
			stb = st.substring(5);
			sc = new Scanner(stb).useDelimiter("[^0-9]+");
			while (sc.hasNext()) {
				a.add(sc.nextInt());
			}
			t = new Tile(a.get(a.size() - 4), a.get(a.size() - 3), a.get(a.size() - 2), a.get(a.size() - 1));
			allTiles.add(t);

		}
		GlobeCube g = new GlobeCube(allTiles);
		SolutionObject solution = null;
		
			/*GlobeSolverBFS bfs = new GlobeSolverBFS();
			solution = bfs.BFS_Solve(g);*/
		
			AStarSolver astar = new AStarSolver();
			solution = astar.aStarSolve(g);
		/*} else if (args[0].equalsIgnoreCase("Rbfs")) {
			RBFSSolver rbfsSolver = new RBFSSolver();
			solution =rbfsSolver.rbfsSolved(g);
		}*/
		if(solution!=null)
		{
			if(minState > solution.expandedStates)
			{
				minState = solution.expandedStates;
			}
			if(maxState < solution.expandedStates)
			{
				maxState = solution.expandedStates;
			}
			if(minQueueLength > solution.maxQueueLength)
			{
				minQueueLength = solution.maxQueueLength;
			}
			
			if(maxQueueLength < solution.maxQueueLength)
			{
				maxQueueLength = solution.maxQueueLength;
			}
			if(minSteps > solution.solutionString.size())
			{
				minSteps = solution.solutionString.size();
			}
			if(maxSteps < solution.solutionString.size())
			{
				maxSteps = solution.solutionString.size();
			}
			
			
			avgQLength = avgQLength + solution.maxQueueLength;
			avgState = avgState + solution.expandedStates;
			avgStep= avgStep + solution.solutionString.size();
			System.out.println("Puzzle "+ i);
			System.out.println(solution.expandedStates +" states were expanded in total");
			System.out.println("The maximum size of the queue was "+ solution.maxQueueLength);
			ArrayList<String> solvedList = new ArrayList<String>(solution.solutionString);
			System.out.println("The total path length to solution is : " + solvedList.size());
			System.out.println("The steps to solution : ");
			while (!solvedList.isEmpty()) {
				String action = solvedList.remove(0);
				if (action.equalsIgnoreCase("inc0180")) {
					System.out.println("Increment 0-180 longitude by 30");
				} else if (action.equalsIgnoreCase("dec0180")) {
					System.out.println("Decrement 0-180 longitude by 30");
				} else if (action.equalsIgnoreCase("inc90270")) {
					System.out.println("Increment 90-270 longitude by 30");
				} else if (action.equalsIgnoreCase("dec90270")) {
					System.out.println("Deccrement 90-270 longitude by 30");
				} else if (action.equalsIgnoreCase("incEquator")) {
					System.out.println("Increment Equator by 30");
				} else if (action.equalsIgnoreCase("decEquator")) {
					System.out.println("Decrement Equator by 30");
				}
		}
		}

		br.close();
		
	}
			i++;}
		System.out.println("*******************************************");
		System.out.println("Min Steps : " + minSteps);
		System.out.println("Max Steps : " + maxSteps);
		System.out.println("Min States : " + minState);
		System.out.println("Max Steps : " + maxState);
		System.out.println("MaxQueue : " + maxQueueLength);
		System.out.println("MinQueue : " + minQueueLength);
		System.out.println("Average Steps : " + (float)avgStep/19);
		System.out.println("Average States : " + (float)avgStep/19);
		System.out.println("Average Queue : " + (float)avgQLength/19);
		
	}
	
}

class Tile {
	int lat, lon;
	int elat, elon;

	Tile(int lat, int lon, int elat, int elon) {
		this.elat = elat;
		this.elon = elon;
		this.lat = lat;
		this.lon = lon;
	}

	public void printTile() {
		System.out.println("Latitude : " + lat + " Longitude : " + lon + "\nTarget Latitude : " + elat
				+ " Target Longitude : " + elon);
	}
}

class GlobeCube {
	ArrayList<Tile> allTiles;

	public GlobeCube(ArrayList<Tile> l) {
		this.allTiles = l;
	}

	public void printGloble() {
		Tile cur;
		for (int i = 0; i < allTiles.size(); i++) {
			cur = allTiles.get(i);
			System.out.println(cur.lat + " " + cur.lon);
		}

	}

	public void inc0180() {
		Tile cur;
		for (int i = 0; i < allTiles.size(); i++) {
			cur = allTiles.get(i);
			if (cur.lon == 0) {
				if (cur.lat == 150) {
					cur.lat = 180;
					cur.lon = 180;
				} else {
					cur.lat = cur.lat + 30;
				}
			} else if (cur.lon == 180) {
				if (cur.lat == 30) {
					cur.lat = 0;
					cur.lon = 0;
				} else {
					cur.lat = cur.lat - 30;
				}
			}
		}
	}

	public void dec0180() {
		Tile cur;
		for (int i = 0; i < allTiles.size(); i++) {
			cur = allTiles.get(i);
			if (cur.lon == 0) {
				if (cur.lat == 0) {
					cur.lat = 30;
					cur.lon = 180;
				} else {
					cur.lat = cur.lat - 30;
				}
			} else if (cur.lon == 180) {
				if (cur.lat == 180) {
					cur.lat = 150;
					cur.lon = 0;
				} else {
					cur.lat = cur.lat + 30;
				}
			}
		}
	}

	public void incEquator() {
		Tile cur;
		for (int i = 0; i < allTiles.size(); i++) {
			cur = allTiles.get(i);
			if (cur.lat == 90) {
				cur.lon = (cur.lon + 30) % 360;
			}
		}
	}

	public void decEquator() {
		Tile cur;
		for (int i = 0; i < allTiles.size(); i++) {
			cur = allTiles.get(i);
			if (cur.lat == 90) {
				if (cur.lon == 0) {
					cur.lon = 330;
				} else {
					cur.lon = cur.lon - 30;
				}
			}
		}
	}

	public void inc90270() {
		Tile cur;
		for (int i = 0; i < allTiles.size(); i++) {
			cur = allTiles.get(i);
			if (cur.lat == 0 && cur.lon == 0) {
				cur.lat = 30;
				cur.lon = 90;
			} else if (cur.lat == 180 && cur.lon == 180) {
				cur.lat = 150;
				cur.lon = 270;
			} else if (cur.lat == 30 && cur.lon == 270) {
				cur.lat = 0;
				cur.lon = 0;
			} else if (cur.lat == 150 && cur.lon == 90) {
				cur.lat = 180;
				cur.lon = 180;

			} else {
				if (cur.lon == 270) {
					cur.lat = cur.lat - 30;
				} else if (cur.lon == 90) {
					cur.lat = cur.lat + 30;
				}
			}
		}
	}

	public void dec90270() {
		Tile cur;
		for (int i = 0; i < allTiles.size(); i++) {

			cur = allTiles.get(i);
			if (cur.lat == 0 && cur.lon == 0) {
				cur.lat = 30;
				cur.lon = 270;
			} else if (cur.lat == 180 && cur.lon == 180) {
				cur.lat = 150;
				cur.lon = 90;
			} else if (cur.lat == 150 && cur.lon == 270) {
				cur.lat = 180;
				cur.lon = 180;
			} else if (cur.lat == 30 && cur.lon == 90) {
				cur.lat = 0;
				cur.lon = 0;

			} else {
				if (cur.lon == 270) {
					cur.lat = cur.lat + 30;
				} else if (cur.lon == 90) {
					cur.lat = cur.lat - 30;
				}
			}
		}
	}

	public boolean isGlobeSolved() {
		Tile cur;
		boolean solved = true;
		for (int i = 0; i < allTiles.size(); i++) {
			cur = allTiles.get(i);
			if (cur.lat != cur.elat || cur.elon != cur.lon) {
				solved = false;
				break;
			}
		}
		return solved;
	}

	public int getHashState() {
		StringBuffer allT = new StringBuffer("");
		for (int i = 0; i < allTiles.size(); i++) {
			allT.append(allTiles.get(i).lat).append(allTiles.get(i).lon);
		}

		return allT.toString().hashCode();
	}

	public float heuristic_function() {

		int lon, lat, elat, elon;
		int a = 0, a1 = 0;
		for (int i = 0; i < allTiles.size(); i++) {
			lon = allTiles.get(i).lon;
			lat = allTiles.get(i).lat;
			elat = allTiles.get(i).elat;
			elon = allTiles.get(i).elon;

			if (lat == elat && lon == elon) {

			}

			else if (lat == elat && lat == 90) {
				a1 = Math.abs(lon - elon) <= (360 - Math.abs(elon - lon)) ? Math.abs(lon - elon)
						: (360 - Math.abs(elon - lon));
				a = a1 + a;
			}

			else if (lon == elon) {
				a = Math.abs(elat - lat) + a;
			} else if ((lon == 90 && elon == 0 && elat == 0) || (lon == 270 && elon == 0 && elat == 0)
					|| (lat == 0 && lon == 0 && elon == 90) || (lon == 180 && elat == 180 && elon == 0)) {
				a = Math.abs(elat - lat) + a;
			} else if ((lon == 180 && elon == 0) || (lon == 0 && elon == 180) || (lon == 90 && elon == 270)
					|| (lon == 270 && elon == 90)) {
				a1 = (lat + elat) <= (360 - lat - lon) ? (lat + elat) : (360 - lat - elat);
				a = a + a1;
			}

			else if ((lon == 0 && elon == 90) || (lon == 90 && elon == 0) || (lon == 180 && elon == 90)
					|| (lon == 90 && elon == 180) || (lon == 0 && elon == 270) || (lon == 270 && elon == 0)
					|| (lon == 180 && elon == 270) || (lon == 270 && elon == 180)) {
				a1 = (360 - (lat + elat)) <= (lat + elat) ? (360 - (lat + elat)) : (lat + elat);
				a = a + a1;
			} else {
				a = Math.abs(90 - elat) + Math.abs(90 - lat) + a;
				a1 = Math.abs(lon - elon) <= (360 - Math.abs(elon - lon)) ? Math.abs(lon - elon)
						: (360 - Math.abs(elon - lon));
				a = a1 + a;

			}

		}

		return (float) a / 360;
	}

}
class SolutionObject
{
	int expandedStates;
	Stack<String> solutionString;
	int maxQueueLength;
	public SolutionObject(int expandedStates, Stack<String> solutionString, int maxQueueLength)
	{
		this.solutionString = solutionString;
		this.expandedStates = expandedStates;
		this.maxQueueLength = maxQueueLength;
	}
}
class BFSGraphNode {
	BFSGraphNode parent;
	String action;

	public BFSGraphNode(BFSGraphNode parent, String action) {
		this.parent = parent;
		this.action = action;
	}
}

class GlobeSolverBFS {
	ArrayList<Integer> exploredStates = new ArrayList<Integer>();
	String moves[] = { "inc0180", "dec0180", "incEquator", "decEquator", "inc90270", "dec90270" };
	int maxQueueLength=0;

	public SolutionObject BFS_Solve(GlobeCube g) {
		if (g.isGlobeSolved())
			return null;
		BFSGraphNode parent = new BFSGraphNode(null, null);

		ArrayList<BFSGraphNode> frontier = new ArrayList<BFSGraphNode>();
		frontier.add(parent);
		maxQueueLength  = frontier.size();
		BFSGraphNode curNode;
		BFSGraphNode b;
		String nowMove;
		Stack<String> movesToCurent = new Stack<String>();
		Runtime.getRuntime().addShutdownHook(new Thread() 
		{
		    @Override
		    public void run() 
		    {
		        System.out.println("Execution Interrupted Midway");
		        System.out.println("Till this point, the total number of states expanded : " + exploredStates.size());
		        System.out.println("The largest queue size is : " + maxQueueLength);
		    }
		});	
		while (true) {
			if(maxQueueLength < frontier.size())
			{
				maxQueueLength = frontier.size();
			}
			curNode = frontier.remove(0);

			b = curNode;

			while (b.parent != null && b.action != null) {
				movesToCurent.push(b.action);
				b = b.parent;
			}
			Stack<String> movesToSolution = new Stack<String>();
			while (!movesToCurent.isEmpty()) {
				nowMove = movesToCurent.pop();
				movesToSolution.push(nowMove);
				if (nowMove.equalsIgnoreCase("inc0180")) {
					g.inc0180();
				} else if (nowMove.equalsIgnoreCase("dec0180")) {
					g.dec0180();

				} else if (nowMove.equalsIgnoreCase("incEquator")) {
					g.incEquator();
				} else if (nowMove.equalsIgnoreCase("decEquator")) {

					g.decEquator();
				} else if (nowMove.equalsIgnoreCase("inc90270")) {

					g.inc90270();

				} else if (nowMove.equalsIgnoreCase("dec90270")) {

					g.dec90270();
				}
			}
			exploredStates.add(g.getHashState());
			String negateStep = "";
			if (curNode.action == null) {
				negateStep = "";
			} else if (curNode.action.equalsIgnoreCase("inc0180")) {
				negateStep = "dec0180";
			} else if (curNode.action.equalsIgnoreCase("dec0180")) {
				negateStep = "inc0180";
			} else if (curNode.action.equalsIgnoreCase("inc90270")) {
				negateStep = "dec90270";
			} else if (curNode.action.equalsIgnoreCase("dec90270")) {
				negateStep = "inc90270";
			} else if (curNode.action.equalsIgnoreCase("incEquator")) {
				negateStep = "decEquator";
			} else if (curNode.action.equalsIgnoreCase("decEquator")) {
				negateStep = "incEquator";
			}
			for (int i = 0; i < 6; i++) {
				if (!moves[i].equalsIgnoreCase(negateStep)) {
					if (moves[i].equalsIgnoreCase("inc0180")) {
						g.inc0180();
						if (!exploredStates.contains(g.getHashState())) {
							if (g.isGlobeSolved()) {
								movesToSolution.push(moves[i]);
								return new SolutionObject(exploredStates.size(), movesToSolution, maxQueueLength);
							}
							frontier.add(new BFSGraphNode(curNode, moves[i]));
						}
						g.dec0180();
					} else if (moves[i].equalsIgnoreCase("dec0180")) {
						g.dec0180();
						if (!exploredStates.contains(g.getHashState())) {
							if (g.isGlobeSolved()) {
								movesToSolution.push(moves[i]);
								return new SolutionObject(exploredStates.size(), movesToSolution, maxQueueLength);
							}
							frontier.add(new BFSGraphNode(curNode, moves[i]));
						}
						g.inc0180();
					} else if (moves[i].equalsIgnoreCase("inc90270")) {
						g.inc90270();
						if (!exploredStates.contains(g.getHashState())) {
							if (g.isGlobeSolved()) {
								movesToSolution.push(moves[i]);
								return new SolutionObject(exploredStates.size(), movesToSolution, maxQueueLength);
							}
							frontier.add(new BFSGraphNode(curNode, moves[i]));
						}
						g.dec90270();
					} else if (moves[i].equalsIgnoreCase("dec90270")) {
						g.dec90270();
						if (!exploredStates.contains(g.getHashState())) {
							if (g.isGlobeSolved()) {
								movesToSolution.push(moves[i]);
								return new SolutionObject(exploredStates.size(), movesToSolution, maxQueueLength);
							}
							frontier.add(new BFSGraphNode(curNode, moves[i]));
						}
						g.inc90270();
					} else if (moves[i].equalsIgnoreCase("incEquator")) {
						g.incEquator();
						if (!exploredStates.contains(g.getHashState())) {
							if (g.isGlobeSolved()) {
								movesToSolution.push(moves[i]);
								return new SolutionObject(exploredStates.size(), movesToSolution, maxQueueLength);
							}
							frontier.add(new BFSGraphNode(curNode, moves[i]));
						}
						g.decEquator();
					} else if (moves[i].equalsIgnoreCase("decEquator")) {
						g.decEquator();
						if (!exploredStates.contains(g.getHashState())) {
							if (g.isGlobeSolved()) {
								movesToSolution.push(moves[i]);
								return new SolutionObject(exploredStates.size(), movesToSolution, maxQueueLength);
							}
							frontier.add(new BFSGraphNode(curNode, moves[i]));
						}
						g.incEquator();
					}

				}
			}
			String revMove;
			while (!movesToSolution.isEmpty()) {
				revMove = movesToSolution.pop();
				if (revMove.equalsIgnoreCase("inc0180")) {
					g.dec0180();
				} else if (revMove.equalsIgnoreCase("dec0180")) {
					g.inc0180();
				} else if (revMove.equalsIgnoreCase("inc90270")) {
					g.dec90270();
				} else if (revMove.equalsIgnoreCase("dec90270")) {
					g.inc90270();
				} else if (revMove.equalsIgnoreCase("incEquator")) {
					g.decEquator();
				} else if (revMove.equalsIgnoreCase("decEquator")) {
					g.incEquator();
				}
			}
			movesToSolution.clear();
		}
	}
}

class AStarNode {
	AStarNode parent;
	String action;
	float f_n;

	public AStarNode(AStarNode parent, String action, float f_n) {
		// TODO Auto-generated constructor stub
		this.parent = parent;
		this.action = action;
		this.f_n = f_n;
	}
}

class AStarNodeComparator implements Comparator<AStarNode> {
	@Override
	public int compare(AStarNode a1, AStarNode a2) {
		if (a1.f_n < a2.f_n)
			return -1;
		else if (a1.f_n > a2.f_n)
			return 1;
		return 0;
	}
}

class AStarSolver {
	ArrayList<Integer> exploredStates = new ArrayList<Integer>();
	String moves[] = { "inc0180", "dec0180", "incEquator", "decEquator", "inc90270", "dec90270" };
	int maxLengthQueue=0;
	
	public SolutionObject aStarSolve(GlobeCube g) {
		if (g.isGlobeSolved())
			return null;
		float h_n = 0;
		int turnMoves = 0;
		AStarNode parent = new AStarNode(null, null, 0);
		PriorityQueue<AStarNode> frontier = new PriorityQueue<AStarNode>(new AStarNodeComparator());
		maxLengthQueue=1;
		frontier.add(new AStarNode(null, null, g.heuristic_function()));
		AStarNode curNode;

		AStarNode b;
		String nowMove;
		Stack<String> movesToCurent = new Stack<String>();
		Runtime.getRuntime().addShutdownHook(new Thread() 
		{
		    @Override
		    public void run() 
		    {
		        System.out.println("Execution Interrupted Midway");
		        System.out.println("Till this point, the total number of states expanded : " + exploredStates.size());
		        System.out.println("The largest queue size is : " + maxLengthQueue);
		    }
		});	
		while (true) {

			if(maxLengthQueue < frontier.size())
			{
				maxLengthQueue = frontier.size();
			}
			curNode = frontier.poll();
			b = curNode;
			turnMoves = 0;
			while (b.parent != null && b.action != null) {
				movesToCurent.push(b.action);
				b = b.parent;
				turnMoves = turnMoves + 1;
			}

			Stack<String> movesToSolution = new Stack<String>();
			while (!movesToCurent.isEmpty()) {
				nowMove = movesToCurent.pop();
				movesToSolution.push(nowMove);
				if (nowMove.equalsIgnoreCase("inc0180")) {
					g.inc0180();
				} else if (nowMove.equalsIgnoreCase("dec0180")) {
					g.dec0180();

				} else if (nowMove.equalsIgnoreCase("incEquator")) {
					g.incEquator();
				} else if (nowMove.equalsIgnoreCase("decEquator")) {

					g.decEquator();
				} else if (nowMove.equalsIgnoreCase("inc90270")) {

					g.inc90270();

				} else if (nowMove.equalsIgnoreCase("dec90270")) {

					g.dec90270();
				}
			}
			exploredStates.add(g.getHashState());

			String negateStep = "";
			if (curNode.action == null) {
				negateStep = "";
			} else if (curNode.action.equalsIgnoreCase("inc0180")) {
				negateStep = "dec0180";
			} else if (curNode.action.equalsIgnoreCase("dec0180")) {
				negateStep = "inc0180";
			} else if (curNode.action.equalsIgnoreCase("inc90270")) {
				negateStep = "dec90270";
			} else if (curNode.action.equalsIgnoreCase("dec90270")) {
				negateStep = "inc90270";
			} else if (curNode.action.equalsIgnoreCase("incEquator")) {
				negateStep = "decEquator";
			} else if (curNode.action.equalsIgnoreCase("decEquator")) {
				negateStep = "incEquator";
			}
			for (int i = 0; i < 6; i++) {
				if (!moves[i].equalsIgnoreCase(negateStep)) {
					if (moves[i].equalsIgnoreCase("inc0180")) {
						g.inc0180();
						if (!exploredStates.contains(g.getHashState())) {
							if (g.isGlobeSolved()) {
								movesToSolution.push(moves[i]);
								return new SolutionObject(exploredStates.size(), movesToSolution, maxLengthQueue);
							}

							h_n = g.heuristic_function();
							frontier.add(new AStarNode(curNode, moves[i], h_n + turnMoves + 1));
						}
						g.dec0180();
					} else if (moves[i].equalsIgnoreCase("dec0180")) {
						g.dec0180();
						if (!exploredStates.contains(g.getHashState())) {
							if (g.isGlobeSolved()) {
								movesToSolution.push(moves[i]);
								return new SolutionObject(exploredStates.size(), movesToSolution, maxLengthQueue);
							}

							h_n = g.heuristic_function();
							frontier.add(new AStarNode(curNode, moves[i], h_n + turnMoves + 1));
						}
						g.inc0180();
					} else if (moves[i].equalsIgnoreCase("inc90270")) {
						g.inc90270();
						if (!exploredStates.contains(g.getHashState())) {
							if (g.isGlobeSolved()) {
								movesToSolution.push(moves[i]);
								return new SolutionObject(exploredStates.size(), movesToSolution, maxLengthQueue);
							}

							h_n = g.heuristic_function();
							frontier.add(new AStarNode(curNode, moves[i], h_n + turnMoves + 1));
						}
						g.dec90270();
					} else if (moves[i].equalsIgnoreCase("dec90270")) {
						g.dec90270();
						if (!exploredStates.contains(g.getHashState())) {
							if (g.isGlobeSolved()) {
								movesToSolution.push(moves[i]);
								return new SolutionObject(exploredStates.size(), movesToSolution, maxLengthQueue);
							}

							h_n = g.heuristic_function();
							frontier.add(new AStarNode(curNode, moves[i], h_n + turnMoves + 1));
						}
						g.inc90270();
					} else if (moves[i].equalsIgnoreCase("incEquator")) {
						g.incEquator();
						if (!exploredStates.contains(g.getHashState())) {
							if (g.isGlobeSolved()) {
								movesToSolution.push(moves[i]);
								return new SolutionObject(exploredStates.size(), movesToSolution, maxLengthQueue);
							}

							h_n = g.heuristic_function();
							frontier.add(new AStarNode(curNode, moves[i], h_n + turnMoves + 1));
						}
						g.decEquator();
					} else if (moves[i].equalsIgnoreCase("decEquator")) {
						g.decEquator();
						if (!exploredStates.contains(g.getHashState())) {
							if (g.isGlobeSolved()) {
								movesToSolution.push(moves[i]);
								return new SolutionObject(exploredStates.size(), movesToSolution, maxLengthQueue);
							}

							h_n = g.heuristic_function();
							frontier.add(new AStarNode(curNode, moves[i], h_n + turnMoves + 1));
						}
						g.incEquator();
					}

				}

			}
			String revMove;
			while (!movesToSolution.isEmpty()) {
				revMove = movesToSolution.pop();
				if (revMove.equalsIgnoreCase("inc0180")) {
					g.dec0180();
				} else if (revMove.equalsIgnoreCase("dec0180")) {
					g.inc0180();
				} else if (revMove.equalsIgnoreCase("inc90270")) {
					g.dec90270();
				} else if (revMove.equalsIgnoreCase("dec90270")) {
					g.inc90270();
				} else if (revMove.equalsIgnoreCase("incEquator")) {
					g.decEquator();
				} else if (revMove.equalsIgnoreCase("decEquator")) {
					g.incEquator();
				}
			}
			movesToSolution.clear();

		}
	}
}

class RBFSNode implements Comparable<RBFSNode> {
	RBFSNode parent;
	String action;
	String result;
	float f_n;
	float flimit;

	public RBFSNode(RBFSNode parent, String action, float f_n) {
		// TODO Auto-generated constructor stub
		this.parent = parent;
		this.action = action;
		this.f_n = f_n;
	}

	public static Comparator<RBFSNode> RBFSNodeComparator = new Comparator<RBFSNode>() {

		public int compare(RBFSNode n1, RBFSNode n2) {

			if (n1.f_n > n2.f_n) {
				return 1;
			} else if (n1.f_n < n2.f_n) {
				return -1;
			} else
				return 0;

		}

	};

	@Override
	public int compareTo(RBFSNode o) {
		// TODO Auto-generated method stub
		return 0;
	}
}

class RBFSSolver {
	int maxQueueLength=0;
	ArrayList<RBFSNode> successors = new ArrayList<RBFSNode>();
	int exploredStatesNumber;
	ArrayList<Integer> exploredStates = new ArrayList<Integer>();
	String moves[] = { "inc0180", "dec0180", "incEquator", "decEquator", "inc90270", "dec90270" };

	public SolutionObject rbfsSolved(GlobeCube g) {
		Runtime.getRuntime().addShutdownHook(new Thread() 
		{
		    @Override
		    public void run() 
		    {
		        System.out.println("Execution Interrupted Midway");
		        System.out.println("Till this point, the total number of states expanded : " + exploredStates.size());
		        System.out.println("The largest queue size is : " + maxQueueLength);
		    }
		});	
		
		RBFSNode parent = new RBFSNode(null, null, g.heuristic_function());
		RBFSNode sol = rbfs(g, parent, 999999);
		Stack<String> movesToCurent = new Stack<String>();
		RBFSNode b = sol;
		int turnMoves = 0;
		while (b.parent != null && b.action != null) {
			movesToCurent.add(b.action);
			b = b.parent;
			turnMoves = turnMoves + 1;
		}
		Stack<String> movesToSolution = new Stack<String>();
		while(!movesToCurent.isEmpty())
		{
			movesToSolution.add(movesToCurent.pop());
		}
		return new SolutionObject(exploredStates.size(),movesToSolution, maxQueueLength);
	}

	public RBFSNode rbfs(GlobeCube g, RBFSNode curNode, float flimit) {
		
		Stack<String> movesToCurent = new Stack<String>();
		exploredStatesNumber = exploredStatesNumber + 1;
		RBFSNode b = curNode;
		int turnMoves = 0;
		while (b.parent != null && b.action != null) {
			movesToCurent.push(b.action);
			b = b.parent;
			turnMoves = turnMoves + 1;
		}
		String nowMove;
		Stack<String> movesToSolution = new Stack<String>();
		while (!movesToCurent.isEmpty()) {
			nowMove = movesToCurent.pop();
			movesToSolution.push(nowMove);
			if (nowMove.equalsIgnoreCase("inc0180")) {
				g.inc0180();
			} else if (nowMove.equalsIgnoreCase("dec0180")) {
				g.dec0180();

			} else if (nowMove.equalsIgnoreCase("incEquator")) {
				g.incEquator();
			} else if (nowMove.equalsIgnoreCase("decEquator")) {
				g.decEquator();
			} else if (nowMove.equalsIgnoreCase("inc90270")) {
				g.inc90270();

			} else if (nowMove.equalsIgnoreCase("dec90270")) {
				g.dec90270();
			}
		}
		if (g.isGlobeSolved()) {
			
			curNode.result = "success";
			return curNode;
		}
		exploredStates.add(g.getHashState());
		successors.clear();
		String negateStep = "";
		if (curNode.action == null) {
			negateStep = "";
		} else if (curNode.action.equalsIgnoreCase("inc0180")) {
			negateStep = "dec0180";
		} else if (curNode.action.equalsIgnoreCase("dec0180")) {
			negateStep = "inc0180";
		} else if (curNode.action.equalsIgnoreCase("inc90270")) {
			negateStep = "dec90270";
		} else if (curNode.action.equalsIgnoreCase("dec90270")) {
			negateStep = "inc90270";
		} else if (curNode.action.equalsIgnoreCase("incEquator")) {
			negateStep = "decEquator";
		} else if (curNode.action.equalsIgnoreCase("decEquator")) {
			negateStep = "incEquator";
		}
		float h_n = 0;
		for (int i = 0; i < 6; i++) {
			if (!moves[i].equalsIgnoreCase(negateStep)) {
				if (moves[i].equalsIgnoreCase("inc0180")) {
					g.inc0180();
					if (!exploredStates.contains(g.getHashState())) {
						h_n = g.heuristic_function();
						successors.add(new RBFSNode(curNode, moves[i], h_n + turnMoves + 1));
					}
					g.dec0180();
				} else if (moves[i].equalsIgnoreCase("dec0180")) {
					g.dec0180();
					if (!exploredStates.contains(g.getHashState())) {
						h_n = g.heuristic_function();
						successors.add(new RBFSNode(curNode, moves[i], h_n + turnMoves + 1));
					}
					g.inc0180();
				} else if (moves[i].equalsIgnoreCase("inc90270")) {
					g.inc90270();
					if (!exploredStates.contains(g.getHashState())) {
						h_n = g.heuristic_function();
						successors.add(new RBFSNode(curNode, moves[i], h_n + turnMoves + 1));
					}
					g.dec90270();
				} else if (moves[i].equalsIgnoreCase("dec90270")) {
					g.dec90270();
					if (!exploredStates.contains(g.getHashState())) {
						h_n = g.heuristic_function();
						successors.add(new RBFSNode(curNode, moves[i], h_n + turnMoves + 1));
					}
					g.inc90270();
				} else if (moves[i].equalsIgnoreCase("incEquator")) {
					g.incEquator();
					if (!exploredStates.contains(g.getHashState())) {
						h_n = g.heuristic_function();
						successors.add(new RBFSNode(curNode, moves[i], h_n + turnMoves + 1));
					}
					g.decEquator();
				} else if (moves[i].equalsIgnoreCase("decEquator")) {
					g.decEquator();
					if (!exploredStates.contains(g.getHashState())) {
						h_n = g.heuristic_function();
						successors.add(new RBFSNode(curNode, moves[i], h_n + turnMoves + 1));
					}
					g.incEquator();
				}
			}
		}
		maxQueueLength = successors.size() + maxQueueLength;
		String revMove;
		while (!movesToSolution.isEmpty()) {
			revMove = movesToSolution.pop();
			if (revMove.equalsIgnoreCase("inc0180")) {
				g.dec0180();
			} else if (revMove.equalsIgnoreCase("dec0180")) {
				g.inc0180();
			} else if (revMove.equalsIgnoreCase("inc90270")) {
				g.dec90270();
			} else if (revMove.equalsIgnoreCase("dec90270")) {
				g.inc90270();
			} else if (revMove.equalsIgnoreCase("incEquator")) {
				g.decEquator();
			} else if (revMove.equalsIgnoreCase("decEquator")) {
				g.incEquator();
			}
		}
		movesToSolution.clear();
		if (successors.isEmpty()) {
			curNode.result = "failure";
			curNode.flimit = 999999;
			return curNode;

		}

		for (int j = 0; j < successors.size(); j++) {
			RBFSNode s = successors.get(j);
			s.f_n = s.f_n < curNode.f_n ? curNode.f_n : s.f_n;
		}

		RBFSNode min1Node, min2Node;
		
		while (true) {
			successors.sort(RBFSNode.RBFSNodeComparator);
			min1Node = successors.get(0);
			min2Node = successors.get(1);
			if (min1Node.f_n > flimit) {
				curNode.f_n = min1Node.f_n;
				curNode.result = "failure";
				return curNode;
			}
			if (min2Node.f_n > flimit) {
				min1Node = rbfs(g, min1Node, flimit);

			} else {
				min1Node = rbfs(g, min1Node, min2Node.f_n);

			}
			if (!min1Node.result.equalsIgnoreCase("failure")) {
				return min1Node;
			} else
				continue;

		}

	}
}



