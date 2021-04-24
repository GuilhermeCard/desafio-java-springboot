package com.produtos.api.repository;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.produtos.api.model.Product;

@Repository
public class ProductCustomRepository {

	private final EntityManager em;

	public ProductCustomRepository(EntityManager em) {
		this.em = em;
	}

	public List<Product> findSearch(String q, BigDecimal min_price, BigDecimal max_price) {

		String query = "select P from Product as P ";
		String condicao = " where ";

		if (q != null && q != "") {
			query += condicao + " ((LOWER(P.name) like LOWER(:q)) or (LOWER(P.description) like LOWER(:q))) ";
			condicao = " and ";
		}

		if (min_price != null) {
			query += condicao + " P.price >= :min_price ";
			condicao = " and ";
		}

		if (max_price != null) {
			query += condicao + " P.price <= :max_price ";
			condicao = " and ";
		}

		if (min_price != null && max_price != null) {
			query += condicao + " (P.price >= :min_price and P.price <= :max_price)";

		}

		TypedQuery<Product> qJpa = em.createQuery(query, Product.class);

		if (q != null) {
			qJpa.setParameter("q", "%" + q + "%");
		}

		if (min_price != null) {
			qJpa.setParameter("min_price", min_price);
		}

		if (max_price != null) {
			qJpa.setParameter("max_price", max_price);
		}

		return qJpa.getResultList();
	}

}
