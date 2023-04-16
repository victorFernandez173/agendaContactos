package agendaContactos;
import java.io.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Agenda implements Serializable{
	@Serial
	private static final long serialVersionUID = 1L;
	Nodo inicio;
	Nodo fin;
	int numeroContactos;

	public Agenda() {
		inicio = null;
		fin = null;
		numeroContactos = 0;
	}

	public boolean vacia() {
		return inicio == null;
	}

	public void meterOrdenado(Contacto cntctAInsertar) {
		Nodo nuevo = new Nodo(cntctAInsertar, null);
		if (vacia()) {
			inicio = nuevo;
			fin = nuevo;
		} else if (cntctAInsertar.compareTo(inicio.contacto) <= 0) {
			nuevo.enlace = inicio;
			inicio = nuevo;
		} else {
			Nodo previo = inicio;
			Nodo posterior = inicio.enlace;
			while (posterior != null && 
					cntctAInsertar.compareTo(posterior.contacto) > 0) {
				previo = previo.enlace;
				posterior = posterior.enlace;
			}
			if (previo == fin && 
					cntctAInsertar.compareTo(previo.contacto) > 0) {
				fin.enlace = nuevo;
				fin = nuevo;
			} else {
				previo.enlace = nuevo;
				nuevo.enlace = posterior;
			}
		}
		numeroContactos++;
	}

	public void borrarContacto(Contacto cnt) {
		Nodo previo = inicio;
		Nodo actual = inicio.enlace;
		if(cnt.equals(inicio.contacto)) {
			inicio = inicio.enlace;
		} else {
			while(actual != null && !cnt.equals(actual.contacto)) {
				previo = actual;
				actual = actual.enlace;
			}
			previo.enlace = actual.enlace;
		}
		numeroContactos--;
	}
	
	public void buscarContactos(Contacto cnt, DefaultTableModel dtm) {
		Nodo actual = inicio;
		if (vacia()) {
			JOptionPane.showMessageDialog(null, 
					"<html><font face='Arial'size ='15'>Sin contactos");
		} else {
			while (actual != null) {
				if(actual.contacto.contiene(cnt)){
					dtm.addRow(new Object[]{actual.contacto.getNombre(),
							actual.contacto.getTelefono(),
							actual.contacto.getCorreo()});
				}
				actual = actual.enlace;
			}
		}
	}
	
	public void generadorTabla(DefaultTableModel dtm) {
		Nodo actual = inicio;
		if (!vacia()) {
			while (actual != null) {
				dtm.addRow(new Object[]{actual.contacto.getNombre(),
						actual.contacto.getTelefono(),
						actual.contacto.getCorreo()});
				actual = actual.enlace;
			}
		}
	}

	public int confirmar() {
		return JOptionPane.showConfirmDialog(null,
				"<html>" + "<font face='Arial'" + "size='15'color='black'>"
						+ "¿Estás seguro?", "Salir",
				JOptionPane.YES_NO_CANCEL_OPTION);
	}

	public void mensajeSinResultados(){
		JOptionPane.showMessageDialog(null,
				"<html><font face='Arial'size ='15'>Sin coincidencias");
	}

	public void mensajeVacia(){
		JOptionPane.showMessageDialog(null,
				"<html><font face='Arial'size ='15'>Sin contactos");
	}

}
