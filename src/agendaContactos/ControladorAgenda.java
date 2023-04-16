package agendaContactos;
import java.io.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class ControladorAgenda implements ActionListener, KeyListener, 
	ListSelectionListener {

	VistaAgenda vistaAgenda;
	File ficheroAgenda;
	Agenda agendaNodos;
	Contacto cntBorrar = null;

	public ControladorAgenda(VistaAgenda vistaAgenda) {
		this.vistaAgenda = vistaAgenda;
		ficheroAgenda = new File("agendaGUI3.dat");
		agendaNodos = new Agenda();
		cargarAgenda();
		vistaAgenda.desactivarBotones();
		agendaNodos.generadorTabla(vistaAgenda.dtm);
	}

	public void cargarAgenda() {
		ObjectInputStream ois;
		try {
			if (ficheroAgenda.exists()) {
				ois = new ObjectInputStream(new FileInputStream(ficheroAgenda));
				agendaNodos = (Agenda) ois.readObject();
			}
			if(agendaNodos.vacia())
				agendaNodos.mensajeVacia();
		} catch (IOException e) {
			System.out.println("IOError cargarAgenda()");
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFound cargarAgenda()");
		}
	}

	public void guardarAgenda() {
		ObjectOutputStream ous = null;
		try {
			ous = new ObjectOutputStream(new FileOutputStream(ficheroAgenda));
			ous.writeObject(agendaNodos);
		} catch (IOException e) {
			System.err.println("IOError guardarAgenda()");
		} finally {
			try {
				if (ous != null)
					ous.close();
			} catch (IOException e) {
				System.err.println("Error en el cierre guardarAgenda()");
			}
		}
	}

	public void vaciarTabla(){
		while(vistaAgenda.dtm.getRowCount() > 0){
			vistaAgenda.dtm.removeRow(0);
		}
	}

	@Override
	public void actionPerformed(ActionEvent ae) {

		if (ae.getSource() == vistaAgenda.salir) {

			int opcion = agendaNodos.confirmar();
			if (opcion == JOptionPane.OK_OPTION) {
				guardarAgenda();
				System.exit(0);
			}

		} else if (ae.getSource() == vistaAgenda.guardar) {

			agendaNodos.meterOrdenado(vistaAgenda.obtenerCamposDatos());
			vaciarTabla();
			agendaNodos.generadorTabla(vistaAgenda.dtm);
			vistaAgenda.borrarCamposDatos();
			vistaAgenda.desactivarBotones();

		} else if (ae.getSource() == vistaAgenda.buscarNombre) {

			vaciarTabla();
			agendaNodos.buscarContactos(vistaAgenda.obtenerCamposDatos(), vistaAgenda.dtm);
			if(vistaAgenda.dtm.getRowCount() == 0) {
				agendaNodos.mensajeSinResultados();
				agendaNodos.generadorTabla(vistaAgenda.dtm);
				vistaAgenda.borrarCamposDatos();
			}

		} else if (ae.getSource() == vistaAgenda.buscarTelf) {

			vaciarTabla();
			agendaNodos.buscarContactos(vistaAgenda.obtenerCamposDatos(), vistaAgenda.dtm);
			if(vistaAgenda.dtm.getRowCount() == 0){
				agendaNodos.mensajeSinResultados();
				agendaNodos.generadorTabla(vistaAgenda.dtm);
				vistaAgenda.borrarCamposDatos();
			}

		} else if (ae.getSource() == vistaAgenda.buscarCorreo) {

			vaciarTabla();
			agendaNodos.buscarContactos(vistaAgenda.obtenerCamposDatos(), vistaAgenda.dtm);
			if(vistaAgenda.dtm.getRowCount() == 0) {
				agendaNodos.mensajeSinResultados();
				agendaNodos.generadorTabla(vistaAgenda.dtm);
				vistaAgenda.borrarCamposDatos();
			}

		} else if (ae.getSource() == vistaAgenda.cancelar) {

			vistaAgenda.borrarCamposDatos();
			vistaAgenda.desactivarBotones();
			vaciarTabla();
			agendaNodos.generadorTabla(vistaAgenda.dtm);

		} else if (ae.getSource() == vistaAgenda.borrar) {

			if(agendaNodos.confirmar() == 0) {
				agendaNodos.borrarContacto(cntBorrar);
				vistaAgenda.borrar.setEnabled(false);
				vistaAgenda.editarContacto.setEnabled(false);
				vaciarTabla();
				agendaNodos.generadorTabla(vistaAgenda.dtm);
				if (agendaNodos.vacia())
					agendaNodos.mensajeVacia();
			}
	        
		} else if (ae.getSource() == vistaAgenda.editarContacto) {
			
			vistaAgenda.ventanaEd.setVisible(true);
			vistaAgenda.ventanaEd.nombre.setText(cntBorrar.getNombre());
			vistaAgenda.ventanaEd.telefono.setText(cntBorrar.getTelefono());
			vistaAgenda.ventanaEd.correo.setText(cntBorrar.getCorreo());

		} else if(ae.getSource() == vistaAgenda.ventanaEd.aceptar) {

			if(agendaNodos.confirmar() == 0) {
				agendaNodos.borrarContacto(cntBorrar);
				agendaNodos.meterOrdenado(
						new Contacto(vistaAgenda.ventanaEd.nombre.getText(),
								vistaAgenda.ventanaEd.telefono.getText(),
								vistaAgenda.ventanaEd.correo.getText()));
				cntBorrar = null;
				vistaAgenda.editarContacto.setEnabled(false);
				vistaAgenda.borrar.setEnabled(false);
				vaciarTabla();
				agendaNodos.generadorTabla(vistaAgenda.dtm);
				vistaAgenda.ventanaEd.setVisible(false);
			}

		} else if(ae.getSource() == vistaAgenda.ventanaEd.cancelar) {
			
			vistaAgenda.ventanaEd.setVisible(false);
			
		}
	}
		
	@Override
	public void valueChanged(ListSelectionEvent e) {
			
		int fila = vistaAgenda.tabla.getSelectedRow();
		vistaAgenda.borrar.setEnabled(true);
		vistaAgenda.editarContacto.setEnabled(true);
		if (!e.getValueIsAdjusting() && fila != -1) {
			int convertida = vistaAgenda.tabla.convertRowIndexToModel(fila);
			String nombre = (String) vistaAgenda.tabla.getModel().
					getValueAt(convertida, 0);
			String telf = (String) vistaAgenda.tabla.getModel().
					getValueAt(convertida, 1);
			String correo = (String) vistaAgenda.tabla.getModel().
					getValueAt(convertida, 2);
			cntBorrar = new Contacto(nombre, telf, correo);
		}		
	}

	@Override
	public void keyTyped(KeyEvent e) {

		if (e.getSource() == vistaAgenda.telefono) {

			String contenido = vistaAgenda.telefono.getText();
			if (!contenido.equals(""))
				vistaAgenda.activarBotones();
			else{
				vistaAgenda.desactivarBotones();
				vaciarTabla();
				agendaNodos.generadorTabla(vistaAgenda.dtm);
			}


		} else if (e.getSource() == vistaAgenda.nombre) {

			String contenido = vistaAgenda.nombre.getText();
			if (!contenido.equals(""))
				vistaAgenda.activarBotones();
			else{
				vistaAgenda.desactivarBotones();
				vaciarTabla();
				agendaNodos.generadorTabla(vistaAgenda.dtm);
			}

		} else if (e.getSource() == vistaAgenda.correo) {

			String contenido = vistaAgenda.correo.getText();
			if (!contenido.equals(""))
				vistaAgenda.activarBotones();
			else{
				vistaAgenda.desactivarBotones();
				vaciarTabla();
				agendaNodos.generadorTabla(vistaAgenda.dtm);
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getSource() == vistaAgenda.telefono) {

			String contenido = vistaAgenda.telefono.getText();
			if (!contenido.equals(""))
				vistaAgenda.activarBotones();
			else
				vistaAgenda.desactivarBotones();

		} else if (e.getSource() == vistaAgenda.nombre) {

			String contenido = vistaAgenda.nombre.getText();
			if (!contenido.equals(""))
				vistaAgenda.activarBotones();
			else
				vistaAgenda.desactivarBotones();

		} else if (e.getSource() == vistaAgenda.correo) {

			String contenido = vistaAgenda.correo.getText();
			if (!contenido.equals(""))
				vistaAgenda.activarBotones();
			else
				vistaAgenda.desactivarBotones();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getSource() == vistaAgenda.telefono) {

			String contenido = vistaAgenda.telefono.getText();
			if (!contenido.equals(""))
				vistaAgenda.activarBotones();
			else
				vistaAgenda.desactivarBotones();

		} else if (e.getSource() == vistaAgenda.nombre) {

			String contenido = vistaAgenda.nombre.getText();
			if (!contenido.equals(""))
				vistaAgenda.activarBotones();
			else
				vistaAgenda.desactivarBotones();

		} else if (e.getSource() == vistaAgenda.correo) {

			String contenido = vistaAgenda.correo.getText();
			if (!contenido.equals(""))
				vistaAgenda.activarBotones();
			else
				vistaAgenda.desactivarBotones();
		}	
	}
}
