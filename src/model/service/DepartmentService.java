package model.service;

import java.util.List;

import model.dao.DepartmentDataAcessObject;
import model.dao.FabricaConexao;
import model.entidades.Department;

public class DepartmentService {
  
private DepartmentDataAcessObject departmentDao = FabricaConexao.getDepartmentJdbc();
	
	public List<Department> findAll() {
		return departmentDao.findAll();
	}
	
	public void saveOrUpdate(Department department) {
		if (department.getId() == null) {
			departmentDao.insert(department);
		}
		else {
			departmentDao.update(department);
		}
	}
	
	public void remove(Department department) {
		departmentDao.deleteById(department.getId());
	}
}
