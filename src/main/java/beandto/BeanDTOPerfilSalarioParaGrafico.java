package beandto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BeanDTOPerfilSalarioParaGrafico implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private List<Double> listaMediaSalarial = new ArrayList<Double>();
	private List<String> listaPerfil = new ArrayList<String>();
	
	public List<Double> getListaMediaSalarial() {
		return listaMediaSalarial;
	}
	public void setListaMediaSalarial(List<Double> listaMediaSalarial) {
		this.listaMediaSalarial = listaMediaSalarial;
	}
	public List<String> getListaPerfil() {
		return listaPerfil;
	}
	public void setListaPerfil(List<String> listaPerfil) {
		this.listaPerfil = listaPerfil;
	}
		

}
