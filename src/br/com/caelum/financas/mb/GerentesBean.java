package br.com.caelum.financas.mb;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.financas.dao.GerenteDao;
import br.com.caelum.financas.modelo.Conta;
import br.com.caelum.financas.modelo.Gerente;
import br.com.caelum.financas.modelo.GerenteConta;

@Named
@ViewScoped
public class GerentesBean implements Serializable{

	private static long serialVersionUID = 1L;
	
	private Gerente gerente = new Gerente();
	
	@Inject
	private GerenteDao gerenteDAO;
	
	private List<Gerente> gerentes;
	
	
	

	public static long getSerialversionuid() {
		return serialVersionUID;
	}




	public static void setSerialversionuid(long serialversionuid) {
		serialVersionUID = serialversionuid;
	}




	public Gerente getGerente() {
		return gerente;
	}




	public void setGerente(Gerente gerente) {
		this.gerente = gerente;
	}




	public GerenteDao getGerenteDAO() {
		return gerenteDAO;
	}




	public void setGerenteDAO(GerenteDao gerenteDAO) {
		this.gerenteDAO = gerenteDAO;
	}




	public List<Gerente> getGerentes() {
		if (this.gerentes == null) {
			this.gerentes = gerenteDAO.lista();
		}
		return gerentes;
	}




	public void setGerentes(List<Gerente> gerentes) {
		this.gerentes = gerentes;
	}




	private void limpaFormularioDoJSF() {
		
		this.gerente = new GerenteConta();
	}

}
