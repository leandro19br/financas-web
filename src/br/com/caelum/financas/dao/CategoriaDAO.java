package br.com.caelum.financas.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.caelum.financas.modelo.Categoria;
import br.com.caelum.financas.modelo.Conta;

@Stateless
public class CategoriaDAO {

	@PersistenceContext
	EntityManager manager;
	
	
	public void adiciona(Categoria categoria) {
		this.manager.persist(categoria);
	}

	public Categoria busca(Integer id) {
		return this.manager.find(Categoria.class, id);
	}

	public List<Categoria> lista() {
		return this.manager.createQuery("select c from Categoria c", Categoria.class)
				.getResultList();
	}

	public void remove(Categoria categoria) {
		Conta contaParaRemover = this.manager.find(Conta.class, categoria.getId());
		this.manager.remove(contaParaRemover);
	}
	
	public void altera(Categoria categoria) {
		this.manager.merge(categoria);
	}

}




