package agendaContactos;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class VistaAgenda {

	JFrame ventana;
	JLabel registroContactosEtiqueta;
	JLabel nombreEtiqueta;
	JLabel telfEtiqueta;
	JLabel correoEtiqueta;
	JLabel tablaContactosEtiqueta;
	JLabel separador2;
	JLabel espacio3;
	JLabel separador;
	JTextField nombre;
	JTextField telefono;
	JTextField correo;
	JButton guardar;
	JButton cancelar;
	JButton salir;
	JButton borrar;
	JButton buscarNombre;
	JButton buscarTelf;
	JButton buscarCorreo;
	JButton editarContacto;
	DefaultTableModel dtm;
	JTable tabla;
	JScrollPane deslizador;
	JScrollBar barraDeslizadora;
	Container cp;
	JPanel panelTabla;
	JPanel panelSuperior;
	VentanaEdicion ventanaEd;
	String[] nombreColumnas = { "Nombre", "Telf", "Correo" };
	Object[][]datosContactos;

	public VistaAgenda() {
		crearComponentes();
		colocarComponentes();
	}
	
	public void editarComponentes(Component[] comps) {
		for (Component comp : comps) {
			if (comp instanceof Container)
				editarComponentes(((Container) comp).getComponents());
			try {
				comp.setFont(new Font("Arial", Font.BOLD, 25));
				comp.setBackground(Color.ORANGE);
			} catch (Exception e) {
				System.out.println("VentanaEdicion," +
						"editarComponentes():");
				e.printStackTrace();
			}
		}
	}
	
	public void crearComponentes() {
		ventana = new JFrame("++++++Agenda de contactos+++++++");
		registroContactosEtiqueta = 
				new JLabel("+++++++++++Registro de contactos+++++++++++");
		nombreEtiqueta = new JLabel("               Nombre  ");
		telfEtiqueta = new JLabel("               Telefono ");
		correoEtiqueta = new JLabel("                Email      ");
		tablaContactosEtiqueta = new JLabel("++++++Contactos++++++");
		separador = new JLabel
				("+---------------------------------------------"
						+ "--------------------------------------+");
		nombre = new JTextField(19);
		telefono = new JTextField(19);
		correo = new JTextField(19);
		guardar = new JButton("Guardar contacto");
		cancelar = new JButton(" Deshacer ");
		salir = new JButton("Salir");
		separador2 = new JLabel("+-----------------------------------"
				+ "------------------------------------->");
		espacio3 = new JLabel("                                                "
				+ "                                  ");
		borrar = new JButton("Borrar contacto");
		buscarNombre = new JButton("Busca nombre");
		buscarTelf = new JButton("Busca telf");
		buscarCorreo = new JButton("Busca email");
		editarContacto = new JButton("Editar contacto");
		panelSuperior = new JPanel(new GridLayout(3, 3));

		panelTabla = new JPanel();
		dtm = new DefaultTableModel(datosContactos, nombreColumnas);
		tabla = new JTable(dtm);
		deslizador = new JScrollPane(tabla,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		barraDeslizadora = deslizador.getVerticalScrollBar();
		barraDeslizadora.setPreferredSize(new Dimension(30, 0));

		ventanaEd = new VentanaEdicion();
	}
	
	public void colocarComponentes() {
		cp = ventana.getContentPane();
		cp.setBackground(Color.lightGray);
		ventana.setLayout(new FlowLayout());
		ventana.setSize(915, 1010);
		ventana.setResizable(false);
		ventana.setLocationRelativeTo(null);
		ventana.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		ventana.add(registroContactosEtiqueta);
		
		panelSuperior.add(nombreEtiqueta);
		panelSuperior.add(nombre);
		panelSuperior.add(telfEtiqueta);
		panelSuperior.add(telefono);
		panelSuperior.add(correoEtiqueta);
		panelSuperior.add(correo);
		ventana.add(panelSuperior);
		
		ventana.add(espacio3);
		ventana.add(buscarNombre);
		ventana.add(buscarTelf);
		ventana.add(buscarCorreo);
		ventana.add(guardar);
		ventana.add(cancelar);
		ventana.add(separador);
		ventana.add(tablaContactosEtiqueta);
		ventana.add(panelTabla);

		ventana.add(editarContacto);
		ventana.add(borrar);
		ventana.add(separador2);
		ventana.add(salir);
		ventana.setVisible(true);
		editarComponentes(cp.getComponents());
		guardar.setEnabled(false);
		borrar.setEnabled(false);
		editarContacto.setEnabled(false);
		panelSuperior.setBackground(Color.lightGray);

		panelTabla.add(deslizador);
		panelTabla.setBackground(Color.DARK_GRAY);
		tabla.setPreferredScrollableViewportSize(new Dimension(860, 440));
		tabla.setDefaultEditor(Object.class, null);
		deslizador.setViewportView(tabla);
		tabla.setFont(new Font("Arial", Font.PLAIN, 25));
		tabla.setRowHeight(40);
		tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 25));
		tabla.getTableHeader().setBackground(Color.black);
		tabla.getTableHeader().setForeground(Color.white);
		tabla.setBackground(Color.white);
		deslizador.setVisible(true);
	}
	
	public void conectaControlador(ControladorAgenda controladorAgenda) {
		guardar.addActionListener(controladorAgenda);
		cancelar.addActionListener(controladorAgenda);
		salir.addActionListener(controladorAgenda);
		buscarNombre.addActionListener(controladorAgenda);
		borrar.addActionListener(controladorAgenda);
		buscarTelf.addActionListener(controladorAgenda);
		buscarCorreo.addActionListener(controladorAgenda);
		editarContacto.addActionListener(controladorAgenda);
		
		nombre.addKeyListener(controladorAgenda);
		telefono.addKeyListener(controladorAgenda);
		correo.addKeyListener(controladorAgenda);
		
		tabla.getSelectionModel().addListSelectionListener(controladorAgenda);
		
		ventanaEd.nombre.addActionListener(controladorAgenda);
		ventanaEd.telefono.addActionListener(controladorAgenda);
		ventanaEd.correo.addActionListener(controladorAgenda);
		ventanaEd.aceptar.addActionListener(controladorAgenda);
		ventanaEd.cancelar.addActionListener(controladorAgenda);
	}

	public void desactivarBotones() {
		guardar.setEnabled(false);
		buscarTelf.setEnabled(false);
		buscarNombre.setEnabled(false);
		cancelar.setEnabled(false);
		buscarCorreo.setEnabled(false);
	}

	public void activarBotones() {
		guardar.setEnabled(true);
		buscarTelf.setEnabled(true);
		buscarNombre.setEnabled(true);
		cancelar.setEnabled(true);
		buscarCorreo.setEnabled(true);
	}

	public Contacto obtenerCamposDatos() {
		return new Contacto(nombre.getText(),
				telefono.getText(), correo.getText());
	}

	public void borrarCamposDatos() {
		nombre.setText("");
		telefono.setText("");
		correo.setText("");
	}
}
