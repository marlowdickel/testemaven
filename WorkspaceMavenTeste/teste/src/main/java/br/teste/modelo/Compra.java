package br.teste.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table
public class Compra implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	//Exemplo sobre como trabalhar com datas. Se atualizarmos o Java, provavelmente teria que ser diferente.
	@Column
	@Temporal(TemporalType.DATE)
	private Date data;
	
	@Column
	private String fornecedor;
	
	@OneToMany(mappedBy = "compra") //mapeado pelo nome do atributo da outra classe do relacionamento
	private List<ProdutoCompra> produtos = new ArrayList<ProdutoCompra>();

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getFornecedor() {
		return fornecedor;
	}
	public void setFornecedor(String fornecedor) {
		this.fornecedor = fornecedor;
	}
	public List<ProdutoCompra> getProdutos() {
		return produtos;
	}
	public void setProdutos(List<ProdutoCompra> produtos) {
		this.produtos = produtos;
	}
	
	//Método que prepara uma compra pra exibição
	public void formatarParaExibir() {
		//Para cada produto relacionado com aquela compra na tabela N:N
		for (ProdutoCompra produtocompra: this.getProdutos()) {
			//Apaga a compra dele, já que temos todos os dados na própria conta, evitando assim problemas de recursividade no Json e tráfego desnecessário
			produtocompra.setCompra(null);
			//System.out.println("cat 1:"+produtocompra.getProduto().getCategoria());
			//Chama o método que altera o número da categoria que está no BD para o texto do nome dela
			produtocompra.getProduto().categoriaParaTexto();
		}
	}
}

