package br.teste.modelo;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class id implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Column(name = "produtos_id")
	 private int produtosId;

	 @Column(name = "compras_id")
	 private int comprasId;
	 
	 public id(){
		
	}
	
	public id(int produto, int compra){
		super();
		this.comprasId = compra;
		this.produtosId = produto;
	}
	
	 public int getProdutosId() {
			return produtosId;
		}
		public void setProdutosId(int produtosId) {
			this.produtosId = produtosId;
		}
		public int getComprasId() {
			return comprasId;
		}
		public void setComprasId(int comprasId) {
			this.comprasId = comprasId;
		}

		//Gerar automaticamente pelo Eclipse com: Source - generate hashCode and equals...
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + comprasId;
			result = prime * result + produtosId;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			id other = (id) obj;
			if (comprasId != other.comprasId)
				return false;
			if (produtosId != other.produtosId)
				return false;
			return true;
		}
		
		
}
