package business;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;

class PalabraTest {

	String palabra1 = "camello";
	String palabra2 = "leopardo";
	String palabra3 = "gato";
	String palabra4 = "pez";
	String palabra5 = "tortuga";
	String palabra6 = "lince";
	String palabraLarga = "rinoceronte";
	String palabraDosLetras = "ñu";
	String palabraTilde = "león";

	List<String> palabras = new ArrayList<String>();

	// se verifica que el tamaño sea entre 3-10 caracteres
	@Test
	void verificarTamañoTest() {
		agregarPalabras();
		for (String palabra : palabras) {
			assertTrue(palabra.length() >= 3 && palabra.length() <= 10);
		}

	}

	// Verifica la entrada de una palabra menor a 3 letras
	@Test
	void verificarTamañoMenorTest() {
		agregarPalabras();
		palabras.add(palabraDosLetras);
		boolean comprobacion = false;
		for (String palabra : palabras) {
			if ((palabra.length() >= 3 && palabra.length() <= 10) != true) {
				comprobacion = true;
			}
		}
		assertTrue(comprobacion);
	}

	// Verifica la entrada de una palabra mayor a 10 letras
	@Test
	void verificarTamañoMayorTest() {
		agregarPalabras();
		palabras.add(palabraLarga);
		boolean comprobacion = false;
		for (String palabra : palabras) {
			if ((palabra.length() >= 3 && palabra.length() <= 10) != true) {
				comprobacion = true;
			}
		}
		assertTrue(comprobacion);
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
			palabras.add("palabra " + i);
			assertTrue(palabras.size() < 4);
		}

	}

	//verifica los caracteres en la lista
	@Test
	void TipoDeCaracterTest() {
		agregarPalabras();
		palabras.add("niño");
		for (String palabra : palabras) {
			palabra=palabra.toUpperCase();
			for (char letra : palabra.toCharArray()) {
				assertTrue(65 <= (int) letra && 90 >= (int) letra || (int) letra == 209);
			}
		}

	}

	private void agregarPalabras() {
		palabras.add(palabra1);
		palabras.add(palabra2);
		palabras.add(palabra3);
		palabras.add(palabra4);
		palabras.add(palabra5);
		palabras.add(palabra6);
	}

}
