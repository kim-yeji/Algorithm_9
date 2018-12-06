package dfs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class DFS {
	ArrayList<Edge> edgeList = new ArrayList<Edge>(); // 원소8개를 담을 배열
	final Integer WHITE = 0; // Not yet discovered
	final Integer GRAY = 1; // Discovered by not all adjacent vertices are discovered.
	final Integer BLACK = 2; // Discovered and all adjacent vertices are discovered.
	int time = 0;

	public void DepthFirstSearch(int[][] array) throws IOException {
		//초기화
		for (int i = 0; i < 8; i++) {
			Edge edge = new Edge(i, WHITE, null);
			edge.distance = -1;
			edgeList.add(edge);
		}
		
		for (int i = 0; i < edgeList.size(); i++) {
			Edge u = edgeList.get(i);
			if (u.color==WHITE) { //edgeList에서 꺼내왔는데 한 번도 들린적 없었다면
				DFS_visit(array,u); //인접한 정점을 시작 정점으로 DFS를 시작
			}
		}

		File file = new File("C:/Users/Administrator/Desktop/DFS_result.txt");
		BufferedWriter bw = null;
		PrintWriter pw = null;

		if (file.exists()) {
			file.delete();
		}
		bw = new BufferedWriter(new FileWriter(file, true));
		pw = new PrintWriter(bw, true);
		if (file.isFile() && file.canWrite()) {
			for (int i = 0; i < edgeList.size(); i++) {
				System.out.println((char) (edgeList.get(i).uniq + 114) + "\t" + edgeList.get(i).distance+ "\t"
						+ edgeList.get(i).f);
				pw.println((char) (edgeList.get(i).uniq + 114) + "\t" + edgeList.get(i).distance + "\t"
						+ edgeList.get(i).f);
			}
		}
		bw.close();
		pw.close();

	}

	public void DFS_visit(int[][] array, Edge u) {
		time = time + 1; //호출 될 때 마다 시간 추가
		u.distance = time;
		u.color = GRAY; //들렀으니 표시하기
		ArrayList<Edge> adjList = getAdj(array, u); //인접한 노드를 담을 리스트
		for (int i = 0; i < adjList.size(); i++) {
			Edge v = adjList.get(i); //인접리스트에서 하나씩 빼와서
			if (v.color == WHITE) { //방문하지 않았다면
				v.predecessor = u; //방금 뺀애의 전임자는 파라미터로 받아온 직전애로 설정하고
				DFS_visit(array, v);//방문하지 않았다면 해당 노드를 시작 노드로 다시호출
			}
		}
		u.color = BLACK;  //방문 끝
		time = time + 1; 
		u.f = time; //정점 다 찍고 순회끝난 time을 저장

	}

	// 인접한 원소들을 찾아서 리스트로 정리한 후 리턴하는 함수
	public ArrayList<Edge> getAdj(int[][] array, Edge u) {
		ArrayList<Edge> adj = new ArrayList();
		for (int i = 0; i < array[u.uniq].length; i++) {
			if (array[u.uniq][i] == 1) { // u와 인접했으면
				adj.add(edgeList.get(i));// 인접리스트에 추가
			}
		}
		return adj;
	}

	
	public static void main(String[] args) throws IOException {
		DFS dfs = new DFS();
		int[][] array = new int[8][8];
		BufferedReader br = new BufferedReader(new FileReader("C:/Users/Administrator/Desktop/graph.txt"));

		String num = br.readLine();
		for (int i = 0; i < Integer.parseInt(num); i++) {
			String line = br.readLine();
			if (line == null) {
				break;
			} else {
				String[] list = line.split(" ");
				for (int j = 0; j < list.length; j++) {
					array[i][j] = Integer.parseInt(list[j]);
				}
			}
		}
		dfs.DepthFirstSearch(array);
		br.close();
	}
}
