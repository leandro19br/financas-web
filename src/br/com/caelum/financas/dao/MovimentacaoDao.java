package br.com.caelum.financas.dao;


import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.caelum.financas.exception.ValorInvalidoException;
import br.com.caelum.financas.modelo.Conta;
import br.com.caelum.financas.modelo.Movimentacao;
import br.com.caelum.financas.modelo.TipoMovimentacao;
import br.com.caelum.financas.modelo.ValorPorMesEAno;

@Stateless
public class MovimentacaoDao {
	
	//@PersistenceContext
	@Inject
	EntityManager manager;

	public List<Movimentacao>listaTodasMovimentacoes(Conta conta){
		
		manager.joinTransaction();
		
		String jpql = "select m from Movimentacao m " + 
						"where m.conta = :conta order by m.valor desc";
		Query query = manager.createQuery(jpql);
		query.setParameter("conta", conta);
		return query.getResultList();
		
		
	}
	
	public BigDecimal calculaTotalMovimento (Conta conta, TipoMovimentacao tipo){
		
		manager.joinTransaction();
		
		TypedQuery<BigDecimal> q = manager.createQuery("select sum(m.valor) from Movimentacao m where m.conta =:conta and m.tipoMovimentacao =:tipo", BigDecimal.class);
		
		q.setParameter("conta", conta);
		q.setParameter("tipo", tipo);
		
			
		return q.getSingleResult();
	}
	
public List<Movimentacao>listaPorValorETipo(BigDecimal valor, TipoMovimentacao tipo){
		
		manager.joinTransaction();
		
		TypedQuery<Movimentacao> q = manager.createQuery("select m from Movimentacao m where m.valor =:valor and m.tipoMovimentacao =:tipo", Movimentacao.class);
		
		q.setParameter("valor", valor);
		q.setParameter("tipo", tipo);
		
		q.setHint("org.hibernate.cacheable", "true");
			
		return q.getResultList();
	}
	
public List<Movimentacao> buscaTodasAsMovimentacoesDaConta(String titular){
	
		manager.joinTransaction();
		
		TypedQuery<Movimentacao> q = manager.createQuery("select m from Movimentacao m where m.conta.titular like :titular", Movimentacao.class);
		
		q.setParameter("titular", "%"+titular+"%");
		
		
			
		return q.getResultList();
	}



public List<ValorPorMesEAno> listaMesesComMovimentacoes(Conta conta, TipoMovimentacao tipo){
	
	manager.joinTransaction();
	
	TypedQuery<ValorPorMesEAno> q = manager.createQuery("select new br.com.caelum.financas.modelo.valorPorMesEAno(month(m.data),yaer(m.dat),sum(m.valor)) from movimentscao m "
			+ "where m.conta =: conta and m.tipoMovimentacao = :tipo group by year(m.data),month(m.data) order by sum(m.valor) desc", ValorPorMesEAno.class);
	
	q.setParameter("conta", conta);
	q.setParameter("tipo", tipo);
	
	return q.getResultList();
}


	
	public void adiciona(Movimentacao movimentacao) {
		
		manager.joinTransaction();
		this.manager.persist(movimentacao);
		
		if (movimentacao.getValor().compareTo(BigDecimal.ZERO) < 0) {
			throw new ValorInvalidoException("movimentação Negativa");
		}
	}
	

	public Movimentacao busca(Integer id) {
		
		manager.joinTransaction();
		return this.manager.find(Movimentacao.class, id);
	}
	

	public List<Movimentacao> listaComCategoria() {
		
		manager.joinTransaction();
		return this.manager.createQuery("select distinct m from Movimentacao m left join fetch m.categorias", Movimentacao.class).getResultList();
	}

	public List<Movimentacao> lista() {
		
		manager.joinTransaction();
		return this.manager.createQuery("select m from Movimentacao m", Movimentacao.class).getResultList();
	}

	public void remove(Movimentacao movimentacao) {
		
		manager.joinTransaction();
		Movimentacao movimentacaoParaRemover = this.manager.find(Movimentacao.class, movimentacao.getId());
		this.manager.remove(movimentacaoParaRemover);
	}
	
	public void altera(Movimentacao movimentacao) {
		
		manager.joinTransaction();
		this.manager.merge(movimentacao);
	}
	


}
