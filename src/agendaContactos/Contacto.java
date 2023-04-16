package agendaContactos;
import java.io.*;

public class Contacto implements Comparable<Contacto>, 
		Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	private String nombre;
	private String telefono;
	private String correo;

	public Contacto(String nombre, String telefono, 
			String correo) {
		this.nombre = nombre;
		this.telefono = telefono;
		this.correo = correo;
	}

	public Contacto(Contacto otroContacto) {
		nombre = otroContacto.nombre;
		telefono = otroContacto.telefono;
		correo = otroContacto.correo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public String getCorreo() {
		return correo;
	}

	@Override
	public String toString() {
		return nombre + " (" + telefono + "), email: "
				+ correo;
	}

	public boolean contiene(Object otro) {
		Contacto otroContacto = (Contacto) otro;
		return this.nombre.toLowerCase().contains
				(otroContacto.nombre.toLowerCase())
				&& this.telefono.toLowerCase().contains
				(otroContacto.telefono.toLowerCase()) 
				&& this.correo.toLowerCase().contains
				(otroContacto.correo.toLowerCase());
	}
	
	@Override
	public boolean equals(Object otro) {
		Contacto otroContacto = (Contacto) otro;
		return this.nombre.equals(otroContacto.nombre)
				&& this.telefono.equals(otroContacto.telefono) 
				&& this.correo.equals(otroContacto.correo);
	}

	@Override
	public int compareTo(Contacto otro) {
		return this.nombre.toLowerCase().compareTo
				(otro.nombre.toLowerCase());
	}
}
