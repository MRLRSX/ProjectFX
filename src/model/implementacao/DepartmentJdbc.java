package model.implementacao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.BDadosException;
import db.BancoDados;
import model.dao.DepartmentDataAcessObject;
import model.entidades.Department;

public class DepartmentJdbc implements DepartmentDataAcessObject {

	private Connection connection = null;

	public DepartmentJdbc(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void insert(Department department) {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement("INSERT INTO department(name) values(?)");
			statement.setString(1, department.getName());
			statement.executeUpdate();
		} catch (SQLException error) {
			throw new BDadosException(error.getMessage());
		} finally {
			BancoDados.closeStatement(statement);
		}

	}

	@Override
	public void update(Department department) {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement("UPDATE department SET name = ? WHERE id = ?");
			statement.setString(1, department.getName());
			statement.setLong(2, department.getId());
			statement.executeUpdate();
		} catch (SQLException error) {
			throw new BDadosException(error.getMessage());
		} finally {
			BancoDados.closeStatement(statement);
		}

	}

	@Override
	public void deleteById(Long id) {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement("DELETE FROM department where id = ?");
			statement.setLong(1, id);
			statement.executeUpdate();
		} catch (SQLException error) {
			throw new BDadosException(error.getMessage());
		} finally {
			BancoDados.closeStatement(statement);
		}

	}

	@Override
	public Department findById(Long id) {
		PreparedStatement statement = null;
		ResultSet result = null;
		Department department = new Department();
		try {
			statement = connection.prepareStatement("SELECT * FROM department Where id = ?");
			statement.setLong(1, id);
			result = statement.executeQuery();
			while (result.next()) {
				department.setId(result.getLong("id"));
				department.setName(result.getString("name"));
			}
			return department;
		} catch (SQLException error) {
			throw new BDadosException(error.getMessage());
		} finally {
			BancoDados.closeResultSet(result);
			BancoDados.closeStatement(statement);
		}
	}

	@Override
	public List<Department> findAll() {
		PreparedStatement statement = null;
		ResultSet result = null;
		List<Department> listaDepartment = new ArrayList<>();
		try {
			statement = connection.prepareStatement("SELECT * FROM department");
			result = statement.executeQuery();
			while (result.next()) {
				Department department = new Department();
				department.setId(result.getLong("id"));
				department.setName(result.getString("name"));
				listaDepartment.add(department);
			}
			return listaDepartment;
		} catch (SQLException error) {
			throw new BDadosException(error.getMessage());
		} finally {
			BancoDados.closeResultSet(result);
			BancoDados.closeStatement(statement);
		}
	}

}
