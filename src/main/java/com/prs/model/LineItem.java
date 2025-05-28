package com.prs.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity	
public class LineItem implements Comparable<LineItem> {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	@Column(name = "Id")
	private int id;
	@ManyToOne
	@JoinColumn(name = "RequestId")
	private Request request;
	@ManyToOne
	@JoinColumn(name = "ProductId")
	private Product product;
	
	private int quantity;

	// Constructors
	public LineItem() {
		super();
	}

	public LineItem(int id, Request request, Product product, int quantity) {
		super();
		this.id = id;
		this.request = request;
		this.product = product;
		this.quantity = quantity;
	}

	// Getters and Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	
	@Override
	public String toString() {
		return "LineItem [id=" + id + ", requestId=" + request + ", productId=" + product + ", quantity=" + quantity
				+ "]";
	}

	@Override
	public int compareTo(LineItem o) {
		if (o instanceof LineItem) {
            LineItem other = (LineItem) o;
            return Integer.compare(this.id, other.id);
        }
		return 0;
	}
			
}

