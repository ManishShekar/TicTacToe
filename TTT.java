import java.util.Random;
import java.util.Scanner;

class tictactoe {
	static char[][] board;

	public tictactoe() {
		board = new char[3][3];
	}

	void initBoard() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				board[i][j] = ' ';
			}
		}
	}

	static void Mark(int row, int col, char c) {
		try {
			board[row][col] = c;
		} catch (Exception e) {
			System.out.println("Enter valid Position");
		}
	}

	static void DisplayBoard() {
		System.out.println("-------------");
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				System.out.print("| " + board[i][j] + " ");
			}
			System.out.println("|");
			System.out.println("-------------");
		}
	}

	static boolean colwin(char c) {
		for (int j = 0; j < 3; j++) {
			if (board[0][j] == c && (board[0][j] == board[1][j] && board[1][j] == board[2][j])) {
				return true;
			}
		}
		return false;
	}

	static boolean rowwin(char c) {
		for (int j = 0; j < 3; j++) {
			if (board[j][0] == c && (board[j][0] == board[j][1] && board[j][1] == board[j][2])) {
				return true;
			}
		}
		return false;
	}

	static boolean Diagnolwin() {
		if ((board[0][0] != ' ' && (board[0][0] == board[1][1] && board[1][1] == board[2][2]))
				|| (board[0][2] != ' ' && (board[0][2] == board[1][1] && board[1][1] == board[2][0]))) {
			return true;
		} else {
			return false;
		}
	}

	static boolean isDraw() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (tictactoe.board[i][j] == ' ') {
					return false;
				}
			}
		}
		return true;
	}

}

class Human extends Player {

	public Human(String name, char Mark) {
		this.name = name;
		this.Mark = Mark;
	}

	void Move() {
		Scanner sc = new Scanner(System.in);

		int row;
		int col;
		do {
			System.out.println("Enter Row and Column");
			row = sc.nextInt();
			col = sc.nextInt();
		} while (!isValidMove(row, col));
		tictactoe.Mark(row, col, Mark);

	}

	boolean isValidMove(int row, int col) {
		if (row <= 2 && row >= 0 && col <= 2 && col >= 0 && tictactoe.board[row][col] == ' ') {
			return true;
		} else {
			System.out.println("Invalid move");
			return false;
		}
	}

}

abstract class Player {
	String name;
	char Mark;

	abstract void Move();

}

class ComPlayer extends Player {

	public ComPlayer(String name, char Mark) {
		this.name = name;
		this.Mark = Mark;
	}

	void Move() {

		int row;
		int col;
		do {
			Random r = new Random();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			row = r.nextInt(3);
			col = r.nextInt(3);
		} while (!isValidMove(row, col));
		tictactoe.Mark(row, col, Mark);

	}

	boolean isValidMove(int row, int col) {
		if (row <= 2 && row >= 0 && col <= 2 && col >= 0 && tictactoe.board[row][col] == ' ') {
			return true;
		} else {
			System.out.println("Invalid move");
			return false;
		}
	}

}

public class TTT {

	public static void main(String[] args) {
		tictactoe t = new tictactoe();
		t.initBoard();

		Scanner sc = new Scanner(System.in);
		char c;
		System.out.println("Press 'P' to Play Against player ******  Press 'C' to Play Against Computer");
		while (true) {
			c = sc.next().charAt(0);
			if (c == 'P' || c == 'C') {
				break;
			} else {
				System.out.println("Please Enter Valid input");
			}
		}

		Human p1;
		Human p2 = null;
		String n1;
		String n2;
		char m1;
		char m2;
		ComPlayer com = null;

		if (c == 'P') {
			System.out.println("Enter Player 1's Name : ");
			n1 = sc.next();
			System.out.println("Enter Player 1's Mark : ");
			m1 = sc.next().charAt(0);
			System.out.println("Enter Player 2's Name : ");
			n2 = sc.next();
			System.out.println("Enter Player 2's Mark : ");
			m2 = sc.next().charAt(0);
			System.out.println("Player 1 : " + n1 + " Mark : " + m1);
			System.out.println("Player 2 : " + n2 + " Mark : " + m2);
			p1 = new Human(n1, m1);
			p2 = new Human(n2, m2);
		} else {
			System.out.println("Enter Player's Name : ");
			n1 = sc.next();
			System.out.println("Enter Player's Mark(Except 'C') : ");
			while (true) {
				m1 = sc.next().charAt(0);
				if (m1 != 'C') {
					break;
				} else {
					System.out.println("Invalid Mark");
				}
			}
			n2 = "Computer";
			m2 = 'C';
			System.out.println("Player 1 : " + n1 + " Mark : " + m1);
			System.out.println("Player 2 : " + n2 + " Mark : " + m2);
			p1 = new Human(n1, m1);
			com = new ComPlayer(n2, m2);
		}

		Player cp = p1;

		while (true) {
			System.out.println(cp.name + "'s turn");
			cp.Move();
			tictactoe.DisplayBoard();
			if (tictactoe.rowwin(cp.Mark) || tictactoe.colwin(cp.Mark) || tictactoe.Diagnolwin()) {
				System.out.println(cp.name + " won");
				break;
			} else if (tictactoe.isDraw()) {
				System.out.println("Draw Match");
				break;
			} else {
				if (cp == p1) {
					if (p2 != null) {
						cp = p2;
					} else {
						cp = com;
					}
				} else {
					cp = p1;
				}
			}

		}

	}

}
