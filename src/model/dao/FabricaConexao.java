package model.dao;

import db.BancoDados;
import model.implementacao.DepartmentJdbc;
import model.implementacao.SellerJdbc;

public class FabricaConexao {
     
	public static SellerDataAcessObject getSellerJdbc() {
		return new SellerJdbc(BancoDados.getConnection());
	}
	
	public static DepartmentDataAcessObject getDepartmentJdbc() {
		return new DepartmentJdbc(BancoDados.getConnection());
	}
}
