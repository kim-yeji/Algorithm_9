package bfs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class BFS {

	ArrayList<Edge> edgeList = new ArrayList<Edge>(); //원소8개를 담을 배열
	ArrayList<Edge> queue = new ArrayList<>();
	final Integer WHITE = 0; // Not yet discovered
	final Integer GRAY = 1; // Discovered by not all adjacent vertices are discovered.
	final Integer BLACK = 2; // Discovered and all adjacent vertices are discovered.

	public void BreadthFirstSearch(int[][] array) throws IOException {
		for (int i = 0; i < 8; i++) {
			Edge edge = new Edge(i, WHITE, null);
			edge.distance = -1;
			edgeList.add(edge);
		}
		Edge s = edgeList.get(0); // 0부터 시작
		s.color = GRAY;
		s.distance = 0;
		s.predecessor = null;
		Enqueue(s);

		while (queue.size() != 0) {
			Edge u = Dequeue(queue); //큐에서 빼온다
			ArrayList<Edge> adjList = getAdj(array, u); //getAdj에서 넘겨진 리스트를 adjList에 담기
			for (int i = 0; i < adjList.size(); i++) {
				Edge v = adjList.get(i);
				if (v.color==WHITE) { //아직 들르지 않은 원소라면
					v.color = GRAY; //들렀다는 표시 해주고
					v.distance = u.distance + 1;//거리 계산
					v.predecessor = u; //전임자 u로 설정
					Enqueue(v); //큐에 추가해준다
				}
			}
			u.color = BLACK; //큐에서 빼온 애들은 검정으로 표시
		}

		File file = new File("C:/Users/Administrator/Desktop/BFS_result.txt");
		BufferedWriter bw = null;
		PrintWriter pw = null;
		
		if (file.exists()) {
			file.delete();
		}
		bw = new BufferedWriter(new FileWriter(file, true));
		pw = new PrintWriter(bw, true);
		if (file.isFile() && file.canWrite()) {
			for(int i = 0; i<edgeList.size();i++){
				System.out.println((char)(edgeList.get(i).uniq+114) + "\t" + edgeList.get(i).distance);
				pw.println((char)(edgeList.get(i).uniq+114) + "\t" + edgeList.get(i).distance);
			}
		}
		bw.close();
		pw.close();
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

	public void Enqueue(Edge edge) {
		queue.add(edge);
	}

	public Edge Dequeue(ArrayList<Edge> queue) {
		if (queue.size() != 0) {
			Edge getEdge = queue.get(0);
			queue.remove(0);
			return getEdge;
		}
		return null;
	}

	public static void main(String[] args) throws IOException {
		BFS bfs = new BFS();
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
		bfs.BreadthFirstSearch(array);
		br.close();
	}

}
