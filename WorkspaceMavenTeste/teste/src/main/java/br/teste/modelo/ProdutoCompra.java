package br.teste.modelo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name="produtocomprado")
public class ProdutoCompra implements Serializable{
	
	private static final long serialVersionUID = 1L;

	//EM JPA, relacionamentos n:n que tenham mais campos do que as chaves estrangeiras das 2 tabelas 
	//devem ser criados como uma tabela q tenha seu próprio ID e as chaves estrangeiras das duas entidades
	@Id
	@Column(name = "produtocomprado_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name="compra_id")
	private Compra compra;
	
	@ManyToOne
	@JoinColumn(name="produto_id")
	private Produto produto;
	
	private int quantidade;
	private BigDecimal valor;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Compra getCompra() {
		return compra;
	}
	public void setCompra(Compra compra) {
		this.compra = compra;
	}
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
}


