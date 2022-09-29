package model.dao;

import java.util.List;

import model.entidades.Seller;

public interface SellerDataAcessObject {

	public void insert(Seller seller);

	public void update(Seller seller);

	public void deleteById(Long id);

	public Seller findById(Long id);

	public List<Seller> findAll();
}
