package model.service;

import java.util.List;

import model.dao.FabricaConexao;
import model.dao.SellerDataAcessObject;
import model.entidades.Seller;

public class SellerService {


	private SellerDataAcessObject sellerDao = FabricaConexao.getSellerJdbc();
	
	public List<Seller> findAll() {
		return sellerDao.findAll();
	}
	
	public void saveOrUpdate(Seller seller) {
		if (seller.getId() == null) {
			sellerDao.insert(seller);
		}
		else {
			sellerDao.update(seller);
		}
	}
	
	public void remove(Seller seller) {
		sellerDao.deleteById(seller.getId());
	}
}
