package br.teste.modelo;
//https://thorben-janssen.com/hibernate-tip-many-to-many-association-with-additional-attributes/
import java.io.Serializable;
import java.time.LocalDate;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
	
	//problemas com data!!! Modo inicial: import era do java.sql.Date
	@Column
	private Date data;
	/*
	@Temporal(TemporalType.DATE)
	private Calendar data;
	*/
	
	@Column
	private String fornecedor;
	
	@OneToMany(mappedBy = "compra", cascade = CascadeType.ALL) //atributo que vincula essa tabela à outra
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
	
}

