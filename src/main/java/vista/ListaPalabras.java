package vista;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import model.Crossword;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class ListaPalabras extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private JTable table;
	private DefaultTableModel datosTabla;
	private JScrollPane tablaScroll;
	private JScrollPane descripcionScroll;
	private JTextField palabra;
	private JTextArea descripcion;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ListaPalabras frame = new ListaPalabras(new Crossword());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ListaPalabras(Crossword crossword) {
		setTitle("Lista de palabras");
		datosTabla = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tablaScroll = new JScrollPane();
		descripcionScroll = new JScrollPane();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 510);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel title = new JLabel("LISTA DE PALABRAS");
		title.setBounds(157, 11, 119, 14);

		String titulos[] = { "PALABRAS", "DESCRIPCION" };
		datosTabla.setColumnIdentifiers(titulos);
		table = new JTable(datosTabla);
		table.setBounds(20, 40, 400, 50);
		tablaScroll.setBounds(20, 52, 400, 225);
		tablaScroll.setViewportView(table);

		contentPane.add(title);
		contentPane.add(tablaScroll);

		JButton btnAñadir = new JButton("AÑADIR");
		btnAñadir.addActionListener(e -> aniadirPalabra(crossword));

		contentPane.add(btnAñadir);

		btnAñadir.setBounds(20, 406, 89, 23);
		contentPane.add(btnAñadir);

		JButton btnEditar = new JButton("EDITAR DESCRIPCION");
		btnEditar.addActionListener(e -> editarDescripcion(crossword));
		btnEditar.setBounds(228, 406, 160, 23);
		contentPane.add(btnEditar);

		JButton btnEliminar = new JButton("ELIMINAR");
		btnEliminar.addActionListener(e -> eliminarPalabra(crossword));
		btnEliminar.setBounds(119, 406, 89, 23);
		contentPane.add(btnEliminar);

		palabra = new JTextField();
		palabra.setBounds(30, 342, 121, 20);

		JLabel lblPalabra = new JLabel("Ingresa la palabra:");
		lblPalabra.setBounds(30, 299, 121, 14);
		contentPane.add(lblPalabra);

		contentPane.add(palabra);
		palabra.setColumns(10);

		JLabel lblDescripcion = new JLabel("Ingresa su descripcion:");
		lblDescripcion.setBounds(221, 299, 167, 14);
		contentPane.add(lblDescripcion);

		descripcion = new JTextArea();
		descripcion.setBounds(220, 330, 170, 60);
		descripcion.setWrapStyleWord(true);
		descripcion.setLineWrap(true);
		descripcionScroll.setBounds(220, 330, 170, 60);
		descripcionScroll.add(descripcion);
		descripcionScroll.setViewportView(descripcion);
		contentPane.add(descripcionScroll);

		JButton btnCrear = new JButton("CREAR CRUCIGRAMA");
		btnCrear.setBounds(140, 448, 160, 23);
		btnCrear.addActionListener(e -> generarCrucigrama(crossword));
		contentPane.add(btnCrear);

	}

	// metodos del boton añadir Palabra
	private void aniadirPalabra(Crossword crossword) {
		String nuevaPalabra;
		nuevaPalabra = palabra.getText().toUpperCase();

		if (verificarPalabra(nuevaPalabra, crossword) && verificarDescripcion()) {
			crossword.addWord(nuevaPalabra);
			crossword.addDescription(descripcion.getText());
			datosTabla.addRow(new Object[] { nuevaPalabra, descripcion.getText() });

			palabra.setText("");
			descripcion.setText("");
		}
	}

	private boolean verificarPalabra(String nuevaPalabra, Crossword crossword) {
		if (crossword.checkSize(nuevaPalabra)) {
			if (crossword.checkCharacters(nuevaPalabra)) {
				if (!crossword.checkDuplicates(nuevaPalabra)) {
				} else {
					JOptionPane.showMessageDialog(this, "La palabra '" + nuevaPalabra + "' ya esta registrada");
					return false;
				}
			} else {
				JOptionPane.showMessageDialog(this, "La palabra '" + nuevaPalabra + "' no cumple con ser solo letra");
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(this, "La palabra '" + nuevaPalabra + "' no cumple con la longitud (3-10).");
			return false;
		}
		return true;

	}

	private boolean verificarDescripcion() {
		if (descripcion.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "La descripcion no tiene que estar vacia.");
			return false;
		}
		return true;
	}

	// metodos del boton borrar Palabra
	private void eliminarPalabra(Crossword crossword) {
		crossword.deleteWord(datosTabla.getValueAt(table.getSelectedRow(), 0).toString());
		datosTabla.removeRow(table.getSelectedRow());
	}

	private void editarDescripcion(Crossword crossword) {
		if(table.isRowSelected(table.getSelectedRow())) {
		crossword.setDescription(table.getSelectedRow(), descripcion.getText());
		datosTabla.setValueAt(descripcion.getText(), table.getSelectedRow(), 1);
		palabra.setText("");
		descripcion.setText("");
		}else {
			JOptionPane.showMessageDialog(this, "Selecciona una fila, para editar.");
		}
	}

	//metodos del boton crear crucigrama
	private void generarCrucigrama(Crossword crossword) {
		if (crossword.getWords().size() < 4) {
            JOptionPane.showMessageDialog(this, "Debe ingresar al menos 4 palabras.");
            return;
        }
		//por ahora el crucigrama esta siendo creado con tamaño de 25 hay que crear un metodo en crosswordGenerator para que este sea autoAjustable
		JFrame vista = new CrosswordGUI(crossword, 25);
		vista.setVisible(true);
		this.setVisible(false);
		this.dispose();
	}
}
