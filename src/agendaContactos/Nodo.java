package agendaContactos;
import java.io.*;

public class Nodo implements Serializable{
	@Serial
	private static final long serialVersionUID = 1L;
	Contacto contacto;
	Nodo enlace;

	public Nodo(Contacto contacto, Nodo enlace) {
		this.contacto = contacto;
		this.enlace = enlace;
	}
}
