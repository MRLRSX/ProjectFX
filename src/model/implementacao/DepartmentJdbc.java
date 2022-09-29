package model.implementacao;

import java.sql.Connection;
import java.util.List;

import model.dao.DepartmentDataAcessObject;
import model.entidades.Department;

public class DepartmentJdbc implements DepartmentDataAcessObject{

	private Connection connection = null;
	
	public DepartmentJdbc(Connection connection) {
		this.connection = connection;
	}
	@Override
	public void insert(Department department) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Department department) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Department findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Department> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
