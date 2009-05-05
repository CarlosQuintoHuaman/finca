package icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes;

public class InfoPanelEspecifico {
	private String identificador;
	private String contenido;
	
	public InfoPanelEspecifico(String id,String cont){
		this.identificador = id;
		this.contenido = cont;
	}
	
	public void setId (String id){this.identificador = id;}
	public void setContenido (String c){this.contenido = c;}
	
	public String getIdentificador(){return this.identificador;}
	public String getContenido(){return this.contenido;}

}
