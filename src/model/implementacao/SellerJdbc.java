package model.implementacao;

import java.sql.Connection;
import java.util.List;

import model.dao.SellerDataAcessObject;
import model.entidades.Seller;

public class SellerJdbc implements SellerDataAcessObject{

	private Connection connection = null;
	
	public SellerJdbc(Connection connection) {
		this.connection = connection;
	}
	
	@Override
	public void insert(Seller seller) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Seller seller) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Seller findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Seller> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
