package br.teste.modelo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class Produto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	private String categoria;
	
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "marca_id")
	private Marca marca;
	
	private String modelo;
	
	private int capacidade;
	
	private BigDecimal valor;
	/*
	 * Desnecessário relacionar com as compras em que esse produto esteve, por regra de negócio, 
	 *mas esse seria o código caso fosse necessário saber todas as compras de um determinado produto
	@OneToMany(mappedBy = "produto")
	private List<ProdutoCompra> produtosComprados = new ArrayList<ProdutoCompra>();
	 */
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public Marca getMarca() {
		return marca;
	}
	public void setMarcaId(Marca marca) {
		this.marca = marca;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public int getCapacidade() {
		return capacidade;
	}
	public void setCapacidade(int capacidade) {
		this.capacidade = capacidade;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	//Método para converter o número da categoria para texto
	public void categoriaParaTexto() {
		//se for 1, Geladeira. Se for 2, Freezer. Se tiver outra coisa, mantém o q tiver (que deve ser Geladeira ou Freezer)
		this.setCategoria(this.getCategoria().equals("1") ? "Geladeira" : this.getCategoria().equals("2") ? "Freezer": this.getCategoria());
	}
	
}

