package business;

import java.util.*;

public class CrosswordGenerator {
	private char[][] board;
	private int size;

	private List<String> horizontalWords;
	private List<String> verticalWords;

	public CrosswordGenerator(int size) {
		this.size = size;
		board = new char[size][size];
		for (char[] row : board) {
			Arrays.fill(row, ' ');
		}
		horizontalWords = new ArrayList<String>();
		verticalWords = new ArrayList<String>();
	}

	// busca una interseccion de palabras

	private boolean placeWordWithIntersection(String word) {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
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
		if (row < 0 || col < 0 || col + word.length() > size)
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
				if ((r > 0 && board[r - 1][c] != ' ') || (r < size - 1 && board[r + 1][c] != ' '))
					return false;
			}
		}

		if ((col > 0 && board[row][col - 1] != ' ')
				|| (col + word.length() < size && board[row][col + word.length()] != ' '))
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
		if (row < 0 || col < 0 || row + word.length() > size)
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
				if ((c > 0 && board[r][c - 1] != ' ') || (c < size - 1 && board[r][c + 1] != ' '))
					return false;
			}
		}

		if ((row > 0 && board[row - 1][col] != ' ')
				|| (row + word.length() < size && board[row + word.length()][col] != ' '))
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
		if (col < 0 || col + word.length() > size)
			return false;

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

	// genera la palabra central de la que el crucigrama va partir
	public List<String> generateFlexibleCrossword(List<String> words) {
		List<String> notPlaced = new ArrayList<>();
		words.sort((a, b) -> b.length() - a.length()); // más larga primero
		
		String first = words.get(0);

		int mid = size / 2;

		if (!placeWordHorizontally(first, mid, (size - first.length()) / 2)) {
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
