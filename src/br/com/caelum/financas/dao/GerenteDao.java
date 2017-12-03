package br.com.caelum.financas.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.caelum.financas.modelo.Conta;
import br.com.caelum.financas.modelo.Gerente;
import br.com.caelum.financas.modelo.GerenteConta;

@Stateless
public class GerenteDao {

	//@PersistenceContext
	@Inject
	EntityManager manager;
	

	
	public void adiciona(Gerente gerente) {
		
		manager.joinTransaction();
		this.manager.persist(gerente );
	}

	public GerenteConta busca(Integer id) {
		
		manager.joinTransaction();
		return this.manager.find(GerenteConta.class, id);
	}

	public List<Gerente> lista() {
		
		manager.joinTransaction();
		return this.manager.createQuery("select g from Gerente c", Gerente.class)
				.getResultList();
	}

	public void remove(Gerente gerente) {
		
		manager.joinTransaction();
		Gerente gerenteParaRemover = this.manager.find(Gerente.class, gerente.getId());
		this.manager.remove(gerenteParaRemover);
	}
	
	public void altera(Gerente gerente) {
		
		manager.joinTransaction();
		this.manager.merge(gerente);
	}

}




