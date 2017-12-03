package br.com.caelum.financas.mb;

import java.io.Serializable;
import java.util.List;

import br.com.caelum.financas.dao.ContaDao;
import br.com.caelum.financas.dao.GerenteDao;
import br.com.caelum.financas.modelo.Conta;
import br.com.caelum.financas.modelo.Gerente;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class ContasBean implements Serializable {
    
    private static final long serialVersionUID = 1L;

	private Conta conta = new Conta();
	private List<Conta> contas;
	@Inject
	private ContaDao contaDAO;
	
	@Inject
	private GerenteDao gerenteDAO;
	
	private Integer gerenteId;
	
	

	public Integer getGerenteId() {
		return gerenteId;
	}

	public void setGerenteId(Integer gerenteId) {
		this.gerenteId = gerenteId;
	}

	public ContaDao getContaDAO() {
		return contaDAO;
	}

	public void setContaDAO(ContaDao contaDAO) {
		this.contaDAO = contaDAO;
	}

	public GerenteDao getGerenteDAO() {
		return gerenteDAO;
	}

	public void setGerenteDAO(GerenteDao gerenteDAO) {
		this.gerenteDAO = gerenteDAO;
	}

	public void setContas(List<Conta> contas) {
		this.contas = contas;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public void grava() {
		
		if (gerenteId != null) {
			Gerente gerenteRelacionado = gerenteDAO.busca(gerenteId);
			this.conta.setGerente(gerenteRelacionado);
		}
		
		if (conta.getId() == null) {
			contaDAO.adiciona(conta);
		}else{
			
			contaDAO.altera(conta);
			
		}
				
		contas = contaDAO.lista();
		limpaFormularioDoJSF();
		
		System.out.println("Gravando a conta");

		limpaFormularioDoJSF();
	}

	public List<Conta> getContas() {
		
		if (this.contas == null) {
			this.contas =contaDAO.lista();
		}
		
		System.out.println("Listando as contas");

		return contas;
	}

	public void remove() {
		
		System.out.println("Removendo a conta");
		contaDAO.remove(conta);
		contas = contaDAO.lista();
		
		limpaFormularioDoJSF();
	}

	/**
	 * Esse metodo apenas limpa o formulario da forma com que o JSF espera.
	 * Invoque-o no momento em que precisar do formulario vazio.
	 */
	private void limpaFormularioDoJSF() {
		this.conta = new Conta();
	}
}
