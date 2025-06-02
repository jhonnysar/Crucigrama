package vista;

import java.util.List;
import java.util.Scanner;

import business.CrosswordGenerator;
import model.Crossword;

public class TerminalCrossword {

	private Crossword crossword;
	private Scanner scanner;
	private CrosswordGenerator crosswordGenerator;

	public TerminalCrossword() {
		crossword = new Crossword();
		scanner = new Scanner(System.in);
		crosswordGenerator = new CrosswordGenerator(20); // tamaño tablero 20x20	
	}

	public void iniciar() {
		System.out.println("=== SISTEMA DE CRUCIGRAMA ===");

		while (true) {
			System.out.println("\nOpciones:");
			System.out.println("1. Añadir palabras");
			System.out.println("2. Mostrar palabras");
			System.out.println("3. Eliminar palabra");
			System.out.println("4. Añadir descripciones a palabras");
			System.out.println("5. Mostrar palabras con descripciones");
			System.out.println("6. Generar y mostrar crucigrama");
			System.out.println("7. Salir");
			System.out.print("Seleccione una opción: ");

			String opcion = scanner.nextLine();

			switch (opcion) {
			case "1":
				introducirPalabras();
				break;
			case "2":
				mostrarPalabras();
				break;
			case "3":
				eliminarPalabra();
				break;
			case "4":
				pedirDescripciones(crossword.getWords());
				break;
			case "5":
				mostrarPalabrasConDescripcion(crossword.getWords());
				break;
			case "6":
				generarYMostrarCrucigrama();
				break;
			case "7":
				System.out.println("Saliendo del programa...");
				return;
			default:
				System.out.println("Opción no válida. Intente nuevamente.");
			}
		}
	}

	void introducirPalabras() {
		System.out.println("\n--- INTRODUCIR PALABRAS ---");
		System.out.println("(Escribe 'fin' para terminar)");

		while (true) {
			System.out.print("Ingresa una palabra: ");
			String input = scanner.nextLine().trim();

			if (input.equalsIgnoreCase("fin")) {
				break;
			}

			if (validarPalabra(input)) {
				String palabra = input.toUpperCase();
				crossword.addWord(palabra);
				System.out.println("✅ '" + palabra + "' añadida correctamente");
			}
		}
	}

	boolean validarPalabra(String palabra) {
		if (palabra.isEmpty()) {
			System.out.println("❌ Error: No puedes ingresar texto vacío");
			return false;
		}

		if (!crossword.checkSize(palabra)) {
			System.out.println("❌ Error: La palabra debe tener entre 3 y 10 letras");
			return false;
		}
		if (!crossword.checkCharacters(palabra)) {
			System.out.println("❌ Error: Solo se permiten letras (A-Z, Ñ)");
			return false;
		}

		if (crossword.checkDuplicates(palabra)) {
			System.out.println("❌ Error: La palabra ya existe en el crucigrama");
			return false;
		}
		return true;
	}

	void mostrarPalabras() {
		System.out.println("\n--- PALABRAS ACTUALES ---");
		if (crossword.getWords().isEmpty()) {
			System.out.println("No hay palabras en el crucigrama.");
		} else {
			for (int i = 0; i < crossword.getWords().size(); i++) {
				System.out.println((i + 1) + ". " + crossword.getWords().get(i));
			}
		}
	}

	void eliminarPalabra() {
		mostrarPalabras();

		if (crossword.getWords().isEmpty()) {
			return;
		}

		System.out.print("\nIngresa el número de la palabra a eliminar: ");
		try {
			int numero = Integer.parseInt(scanner.nextLine());

			if (numero >= 1 && numero <= crossword.getWords().size()) {
				crossword.deleteWord(crossword.getWords().get(numero - 1));
				System.out.println("✅ '" + crossword.getWords().get(numero - 1) + "' eliminada correctamente");
			} else {
				System.out.println("❌ Número fuera de rango");
			}
		} catch (NumberFormatException e) {
			System.out.println("❌ Debes ingresar un número válido");
		}
	}

	void generarYMostrarCrucigrama() {
		if (crossword.getWords().size() < 4) {
			System.out.println("No hay palabras suficientes para generar el crucigrama.");
			return;
		}

		List<String> noColocadas = crosswordGenerator.generateFlexibleCrossword(crossword.getWords());

		mostrarCrucigrama();

		if (!noColocadas.isEmpty()) {
			System.out.println("\nPalabras que no pudieron colocarse en el crucigrama:");
			for (String palabra : noColocadas) {
				System.out.println("- " + palabra);
			}
		}
	}

	private void mostrarPalabrasConDescripcion(List<String> palabras) {
		System.out.println("\n--- PALABRAS Y DESCRIPCIONES ---");
		for (String palabra : palabras) {
			String desc = "(Sin descripción)";
			if (crossword.getDescription(palabra) != null)
				desc = crossword.getDescription(palabra);
			System.out.println(palabra + " - " + desc);
		}
	}

	private void mostrarCrucigrama() {
		System.out.println("\n--- CRUCIGRAMA GENERADO ---");
		for (char[] fila : crosswordGenerator.getBoard()) {
			for (char c : fila) {
				System.out.print(c == ' ' ? "· " : c + " ");
			}
			System.out.println();
		}
	}

	// Método modificado para mostrar palabras con descripciones
	private void mostrarCrucigramaConDescripciones() {
		System.out.println("\n--- CRUCIGRAMA GENERADO ---");
		for (char[] fila : crosswordGenerator.getBoard()) {
			for (char c : fila) {
				System.out.print(c == ' ' ? "· " : c + " ");
			}
			System.out.println();
		}

		System.out.println("\nPalabras horizontales y sus descripciones:");
		for (String palabra : crosswordGenerator.getHorizontalWords()) {
			String descripcion = crossword.getDescription(palabra);
			System.out.println(palabra + " - " + (descripcion != null ? descripcion : "(Sin descripción)"));
		}

		System.out.println("\nPalabras verticales y sus descripciones:");
		for (String palabra : crosswordGenerator.getVerticalWords()) {
			String descripcion = crossword.getDescription(palabra);
			System.out.println(palabra + " - " + (descripcion != null ? descripcion : "(Sin descripción)"));
		}
	}

	private void pedirDescripciones(List<String> palabras) {
		System.out.println("\n=== INGRESAR DESCRIPCIONES ===");
		for (String palabra : palabras) {
			System.out.print("Descripción para '" + palabra + "': ");
			String descripcion = scanner.nextLine().trim();

			while (descripcion.isEmpty()) {
				System.out.print("La descripción no puede estar vacía. Intente nuevamente: ");
				descripcion = scanner.nextLine().trim();
			}

			crossword.addDescription(palabra, descripcion);
			System.out.println("Descripción guardada.");
		}
	}

	public static void main(String[] args) {
		TerminalCrossword crucigrama = new TerminalCrossword();
		crucigrama.iniciar();
	}
}
