package model.dao;

import java.util.List;

import model.entidades.Department;

public interface DepartmentDataAcessObject {

	public void insert(Department department);

	public void update(Department department);

	public void deleteById(Long id);

	public Department findById(Long id);

	public List<Department> findAll();
}
