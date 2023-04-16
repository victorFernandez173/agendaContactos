package agendaContactos;

public class TestAgenda {
	public static void main(String[] args) {
		VistaAgenda vistaAgenda = new VistaAgenda();
		ControladorAgenda controladorAgenda = 
				new ControladorAgenda(vistaAgenda);
		vistaAgenda.conectaControlador(controladorAgenda);
	}
}
