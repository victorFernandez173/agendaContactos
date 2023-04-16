package agendaContactos;
import javax.swing.*;
import java.awt.*;
import java.io.Serial;

public class VentanaEdicion extends JFrame{
	@Serial
	private static final long serialVersionUID = 1L;
	
	JLabel modificacion;
	JLabel nombreEtiqueta;
	JLabel telfEtiqueta;
	JLabel correoEtiqueta;
	JTextField nombre;
	JTextField telefono;
	JTextField correo;
	JButton aceptar;
	JButton cancelar;
	JPanel panel;
	Container cp;
	
	public VentanaEdicion() {
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
		modificacion = new JLabel("++++++Modificacion++++++");
		nombreEtiqueta = new JLabel("Nombre ");
		telfEtiqueta = new JLabel("Telefono ");
		correoEtiqueta = new JLabel("Email ");
		nombre = new JTextField(19);
		telefono = new JTextField(19);
		correo = new JTextField(19);
		aceptar = new JButton("Aceptar");
		cancelar = new JButton("Cancelar");
		panel = new JPanel(new GridLayout(4, 2));
	}
	
	public void colocarComponentes() {
		cp = this.getContentPane();
		this.add(modificacion);
		panel.add(nombreEtiqueta);
		panel.add(nombre);
		panel.add(telfEtiqueta);
		panel.add(telefono);
		panel.add(correoEtiqueta);
		panel.add(correo);
		panel.add(aceptar);
		panel.add(cancelar);
		this.add(panel);
		editarComponentes(cp.getComponents());
		aceptar.setBackground(Color.DARK_GRAY);
		cancelar.setBackground(Color.DARK_GRAY);
		aceptar.setForeground(Color.white);
		cancelar.setForeground(Color.white);
		cp.setBackground(Color.lightGray);
		nombre.setBackground(Color.white);
		telefono.setBackground(Color.white);
		correo.setBackground(Color.white);
		this.setLayout(new FlowLayout());
		this.setSize(1000, 300);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(false);
	}
}
