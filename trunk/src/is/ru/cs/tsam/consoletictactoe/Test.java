package is.ru.cs.tsam.consoletictactoe;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		TicTacToe t = new TicTacToe();
		System.out.println(t);
		t.markSquare(0, 0);
		System.out.println(t);
		t.markSquare(2, 1);
		System.out.println(t);
		t.markSquare(6, 0);
		System.out.println(t);
		t.markSquare(3, 1);
		System.out.println(t);
		t.markSquare(8, 0);
		System.out.println(t);
		t.markSquare(4, 1);
		System.out.println(t);
		if(t.getWinner() == -1)
		t.markSquare(1, 0);
		System.out.println(t);
		t.markSquare(5, 1);
		if(t.getWinner() != -1) {
			System.out.println(t);
			System.out.println(t.getWinner());
		}
		
		

	}

}
