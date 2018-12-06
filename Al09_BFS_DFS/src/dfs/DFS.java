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
	ArrayList<Edge> edgeList = new ArrayList<Edge>(); // ����8���� ���� �迭
	final Integer WHITE = 0; // Not yet discovered
	final Integer GRAY = 1; // Discovered by not all adjacent vertices are discovered.
	final Integer BLACK = 2; // Discovered and all adjacent vertices are discovered.
	int time = 0;

	public void DepthFirstSearch(int[][] array) throws IOException {
		//�ʱ�ȭ
		for (int i = 0; i < 8; i++) {
			Edge edge = new Edge(i, WHITE, null);
			edge.distance = -1;
			edgeList.add(edge);
		}
		
		for (int i = 0; i < edgeList.size(); i++) {
			Edge u = edgeList.get(i);
			if (u.color==WHITE) { //edgeList���� �����Դµ� �� ���� �鸰�� �����ٸ�
				DFS_visit(array,u); //������ ������ ���� �������� DFS�� ����
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
		time = time + 1; //ȣ�� �� �� ���� �ð� �߰�
		u.distance = time;
		u.color = GRAY; //�鷶���� ǥ���ϱ�
		ArrayList<Edge> adjList = getAdj(array, u); //������ ��带 ���� ����Ʈ
		for (int i = 0; i < adjList.size(); i++) {
			Edge v = adjList.get(i); //��������Ʈ���� �ϳ��� ���ͼ�
			if (v.color == WHITE) { //�湮���� �ʾҴٸ�
				v.predecessor = u; //��� ������ �����ڴ� �Ķ���ͷ� �޾ƿ� �����ַ� �����ϰ�
				DFS_visit(array, v);//�湮���� �ʾҴٸ� �ش� ��带 ���� ���� �ٽ�ȣ��
			}
		}
		u.color = BLACK;  //�湮 ��
		time = time + 1; 
		u.f = time; //���� �� ��� ��ȸ���� time�� ����

	}

	// ������ ���ҵ��� ã�Ƽ� ����Ʈ�� ������ �� �����ϴ� �Լ�
	public ArrayList<Edge> getAdj(int[][] array, Edge u) {
		ArrayList<Edge> adj = new ArrayList();
		for (int i = 0; i < array[u.uniq].length; i++) {
			if (array[u.uniq][i] == 1) { // u�� ����������
				adj.add(edgeList.get(i));// ��������Ʈ�� �߰�
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
