package br.com.caelum.financas.mb;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.financas.dao.CategoriaDAO;
import br.com.caelum.financas.dao.ContaDao;
import br.com.caelum.financas.dao.MovimentacaoDao;
import br.com.caelum.financas.modelo.Categoria;
import br.com.caelum.financas.modelo.Conta;
import br.com.caelum.financas.modelo.Movimentacao;
import br.com.caelum.financas.modelo.TipoMovimentacao;

@SuppressWarnings("serial")
@Named
@ViewScoped
public class MovimentacoesBean implements Serializable {

	private static long serialVersionUID = 1L;
	
	private List<Movimentacao> movimentacoes;
	private Movimentacao movimentacao = new Movimentacao();
	
	private Conta conta = new Conta();
	
	private Integer contaId;
	private Integer categoriaId;
	
	@Inject
	private MovimentacaoDao dao;
	
	@Inject
	private ContaDao contaDAO;
	
	@Inject
	private CategoriaDAO categoriaDAO;
	
	private List<Categoria> categorias;
	

	
	public void adicionaCategoria(){
		if (this.categoriaId != null && this.categoriaId > 0) {
			Categoria categoria = categoriaDAO.busca(categoriaId);
			this.movimentacao.getCategorias().add(categoria);
		}
	}
	
	
	public void grava() {
				
		
		if (movimentacao.getId() == null) {
			
			conta = contaDAO.busca(contaId);
			movimentacao.setConta(conta);
						
			dao.adiciona(movimentacao);
			
		}else{
			
			dao.altera(movimentacao);
			
		}
				
		movimentacoes = dao.lista();
		
		limpaFormularioDoJSF();
		
		System.out.println("Gravando a Movimentação");

		limpaFormularioDoJSF();
		System.out.println("Fazendo a gravacao da movimentacao");
		
		
		limpaFormularioDoJSF();
	}
	

	public void remove() {
		
		System.out.println("Removendo a movimentacao");
		
		dao.remove(movimentacao);
		movimentacoes = dao.lista();
		
		limpaFormularioDoJSF();
		
		
	}

	public List<Movimentacao> getMovimentacoes() {
		if (this.movimentacao == null) {
			this.movimentacoes = dao.lista();
		}
		
		System.out.println("Listando as contas");

		return movimentacoes;
	
	}
	
	public Movimentacao getMovimentacao() {
		if(movimentacao.getData()==null) {
			movimentacao.setData(LocalDateTime.now());
		}
		return movimentacao;
	}

	public void setMovimentacao(Movimentacao movimentacao) {
		this.movimentacao = movimentacao;
	}

	public Integer getContaId() {
		return contaId;
	}

	public void setContaId(Integer contaId) {
		this.contaId = contaId;
	}
	
	

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public static void setSerialversionuid(long serialversionuid) {
		serialVersionUID = serialversionuid;
	}


	
	public MovimentacaoDao getDao() {
		return dao;
	}


	public void setDao(MovimentacaoDao dao) {
		this.dao = dao;
	}


	public void setMovimentacoes(List<Movimentacao> movimentacoes) {
		this.movimentacoes = movimentacoes;
	}


	public Integer getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(Integer categoriaId) {
		this.categoriaId = categoriaId;
	}

	/**
	 * Esse metodo apenas limpa o formulario da forma com que o JSF espera.
	 * Invoque-o no momento manager que precisar do formulario vazio.
	 */
	private void limpaFormularioDoJSF() {
		this.movimentacao = new Movimentacao();
	}

	public TipoMovimentacao[] getTiposDeMovimentacao() {
		return TipoMovimentacao.values();
	}

	public List<Categoria> getCategorias() {
		if (this.categorias == null) {
			System.out.println("Listando as Categorias");
			this.categorias = this.categoriaDAO.lista();
		}
		
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}
	
	
	
}
