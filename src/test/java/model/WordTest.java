package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class WordTest {
	Crossword crossWord = new Crossword();

	String palabra1 = "CAMELLO";
	String palabra2 = "CAMELLO";
	String palabra3 = "GATO";
	String palabra4 = "PEZ";
	String palabra5 = "TORTUGA";
	String palabra6 = "LINCE";
	String palabraLarga = "RINOCERONTE";
	String palabraDosLetras = "ÑU";

	List<String> palabras = new ArrayList<>();

	@Test
	void addWordTest() {
		crossWord.addWord("PALABRA");
		System.out.println(crossWord.getWords().size());
		assertEquals(1, crossWord.getWords().size());
	}


	@Test
	void checkSizeTest() {
		agregarPalabras();
		for (String palabra : palabras) {
			assertTrue(crossWord.checkSize(palabra));
		}
	}

	@Test
	void checkLowSizeTest() {
		assertTrue(!crossWord.checkSize(palabraDosLetras));
	}

	@Test
	void checkHigSizeTest() {
		assertTrue(!crossWord.checkSize(palabraLarga));
	}

	@Test
	void checkCharacterTest() {
		assertTrue(crossWord.checkCharacters("ABCDEFGHIJKLMNÑOPQRSTVWXYZ"));
	}

	@Test
	void checkCharacterNumberTest() {
		assertTrue(!crossWord.checkCharacters("1234567890"));
	}

	@Test
	void checkCharacterSpecialsTest() {
		assertTrue(!crossWord.checkCharacters("!#$%&/()[]¨*-+"));
	}

	@Test
	void checkDuplicateTes() {
		crossWord.addWord(palabra1);
		assertTrue(crossWord.checkDuplicates(palabra2));
	}

	// verifica el numero de palabras en la lista
	@Test
	void verificarNumeroPalabrasTest() {
		agregarPalabras();
		assertTrue(palabras.size() >= 4);
	}

	// verifica el numero de palabras en la lista
	@Test
	void verificarMenorNumeroPalabrasTest() {
		for (int i = 1; i < 4; i++) {
			palabras.add("PALABRA " + i);
			assertTrue(palabras.size() < 4);
		}

	}

	// verifica los caracteres en la lista

	private void agregarPalabras() {
		palabras.add(palabra1);
		palabras.add(palabra2);
		palabras.add(palabra3);
		palabras.add(palabra4);
		palabras.add(palabra5);
		palabras.add(palabra6);
	}

}
