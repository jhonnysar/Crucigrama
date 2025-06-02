package business;

import java.util.*;

public class CrosswordGenerator {
	private char[][] board;

	private List<String> horizontalWords;
	private List<String> verticalWords;

	public CrosswordGenerator(int size) {
		board = new char[size][size];
		for (char[] row : board) {
			Arrays.fill(row, ' ');
		}
		horizontalWords = new ArrayList<String>();
		verticalWords = new ArrayList<String>();
	}

	// genera la palabra central de la que el crucigrama va partir
	public List<String> generateFlexibleCrossword(List<String> words) {
		List<String> notPlaced = new ArrayList<>();
		words.sort((a, b) -> b.length() - a.length()); // más larga primero

		String first = words.get(0);

		int mid = board.length / 2;

		if (!placeWordHorizontally(first, mid, (board.length - first.length()) / 2)) {
			notPlaced.add(first);
		}

		for (String word : words) {
			if (!word.equals(first))
				if (!placeWordWithIntersection(word)) {
					notPlaced.add(word);
				}
		}

		return notPlaced;
	}
	
	// busca una interseccion de palabras

	private boolean placeWordWithIntersection(String word) {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				for (int k = 0; k < word.length(); k++) {
					if (board[i][j] == word.charAt(k)) {
						if (tryVertical(word, i - k, j, true))
							return true;
						if (tryHorizontal(word, i, j - k, true))
							return true;
					}
				}
			}
		}
		return false;
	}

	// Busca una posicion Horizontal
	private boolean tryHorizontal(String word, int row, int col, boolean mustIntersect) {
		if (row < 0 || col < 0 || col + word.length() > board[0].length)
			return false;

		boolean hasIntersection = false;

		for (int i = 0; i < word.length(); i++) {
			int r = row, c = col + i;
			char current = board[r][c];

			if (current != ' ' && current != word.charAt(i))
				return false;
			if (current == word.charAt(i))
				hasIntersection = true;

			if (current == ' ') {
				if ((r > 0 && board[r - 1][c] != ' ') || (r < board.length - 1 && board[r + 1][c] != ' '))
					return false;
			}
		}

		if ((col > 0 && board[row][col - 1] != ' ')
				|| (col + word.length() < board[0].length && board[row][col + word.length()] != ' '))
			return false;

		if (mustIntersect && !hasIntersection)
			return false;

		for (int i = 0; i < word.length(); i++) {
			board[row][col + i] = word.charAt(i);
		}

		horizontalWords.add(word);
		return true;
	}

	// busca una posicion en vertical
	private boolean tryVertical(String word, int row, int col, boolean mustIntersect) {
		if (row < 0 || col < 0 || row + word.length() > board.length)
			return false;

		boolean hasIntersection = false;

		for (int i = 0; i < word.length(); i++) {
			int r = row + i, c = col;
			char current = board[r][c];

			if (current != ' ' && current != word.charAt(i))
				return false;
			if (current == word.charAt(i))
				hasIntersection = true;

			if (current == ' ') {
				if ((c > 0 && board[r][c - 1] != ' ') || (c < board[0].length - 1 && board[r][c + 1] != ' '))
					return false;
			}
		}

		if ((row > 0 && board[row - 1][col] != ' ')
				|| (row + word.length() < board[0].length && board[row + word.length()][col] != ' '))
			return false;

		if (mustIntersect && !hasIntersection)
			return false;

		verticalWords.add(word);

		for (int i = 0; i < word.length(); i++) {
			board[row + i][col] = word.charAt(i);
		}

		return true;
	}

	// verifica que la palabra a posicionar sea de un tamaño menor a la matriz
	// ademas de verificar que esta palabra pueda entrar en el espacio del
	// crucigrama
	private boolean placeWordHorizontally(String word, int row, int col) {

		for (int i = 0; i < word.length(); i++) {
			if (board[row][col + i] != ' ')
				return false;
		}

		for (int i = 0; i < word.length(); i++) {
			board[row][col + i] = word.charAt(i);
		}
		horizontalWords.add(word);
		return true;
	}



	public void adjustBoard() {
		int rowTop = findFirstChar(board, "top");
		int rowBot = findFirstChar(board, "bot");
		int colLeft = findFirstChar(board, "left");
		int colRigth = findFirstChar(board, "rigth");
		int rowsize=10;
		int colsize=10;
		char newBoard[][];
		
		System.out.println(rowBot - rowTop);
		if(rowBot - rowTop >= 10){
			rowsize++;
			rowsize = 1+rowBot - rowTop;
		}	
		System.out.println(colRigth - colLeft );
		if(colRigth - colLeft >= 10) {
			colRigth++;
			colsize=colRigth - colLeft;
		}
		newBoard=new char[rowsize][colsize];
		for (char[] row : newBoard) {
			Arrays.fill(row, ' ');
		}
		System.out.println(rowTop);
		for (int i = rowTop; i < rowBot; i++) {
			for (int j = colLeft; j < colRigth+1;j++) {
				System.out.println((i - rowTop)+"     "+(j-colLeft));
				newBoard[i - rowTop][j-colLeft] = board[i][j];
			}
		}
		board=newBoard;
	}

	private int findFirstChar(char[][] matriz, String direccion) {
		int filas = matriz.length;
		int columnas = matriz[0].length;

		int startRow = 0, endRow = filas, stepRow = 1;
		int startCol = 0, endCol = columnas, stepCol = 1;

		switch (direccion.toLowerCase()) {
		case "top":
			break;
		case "bot":
			startRow = filas - 1;
			endRow = -1;
			stepRow = -1;
			break;
		case "left":
			break;
		case "rigth":
			startCol = columnas - 1;
			endCol = -1;
			stepCol = -1;
			break;
		}

		if (direccion.equals("top") || direccion.equals("bot")) {
			for (int i = startRow; i != endRow; i += stepRow) {
				for (int j = 0; j < columnas; j++) {
					if (matriz[i][j] != ' ') {
						return i;
					}
				}
			}
		} else {
			for (int j = startCol; j != endCol; j += stepCol) {
				for (int i = 0; i < filas; i++) {
					if (matriz[i][j] != ' ') {
						return j;
					}
				}
			}
		}
		return 0;
	}

	// devuelve las palabras ubicadase en su sitio
	public char[][] getBoard() {
		return board;
	}

	public List<String> getHorizontalWords() {
		return horizontalWords;
	}

	public List<String> getVerticalWords() {
		return verticalWords;
	}

}
