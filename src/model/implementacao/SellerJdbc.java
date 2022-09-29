package model.implementacao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.BDadosException;
import db.BDadosIntegrityException;
import db.BancoDados;
import model.dao.SellerDataAcessObject;
import model.entidades.Department;
import model.entidades.Seller;

public class SellerJdbc implements SellerDataAcessObject {

	private Connection connection = null;

	public SellerJdbc(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void insert(Seller seller) {
		PreparedStatement statement = null;
        
		try {
			statement = connection.prepareStatement(
					"INSERT INTO seller (name, email, birthdate, basesalary, departmentid) VALUES (?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			statement.setString(1, seller.getName());
			statement.setString(2, seller.getEmail());
			statement.setDate(3, conversorTime(seller.getBirthDate()));
			statement.setDouble(4, seller.getBaseSalary());
			statement.setLong(5, seller.getDepartment().getId());
            statement.executeUpdate();
		} catch (SQLException error) {
			throw new BDadosException(error.getMessage());
		} finally {
			BancoDados.closeStatement(statement);
		}

	}

	@Override
	public void update(Seller seller) {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(
					"UPDATE seller SET name = ?, email = ?, birthdate = ?, departmentid = ?, basesalary = ? WHERE id = ? ");
			statement.setString(1, seller.getName());
			statement.setString(2, seller.getEmail());
			statement.setDate(3, conversorTime(seller.getBirthDate()));
			statement.setLong(4, seller.getDepartment().getId());
			statement.setDouble(5, seller.getBaseSalary());
			statement.setLong(6, seller.getId());
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
			statement = connection.prepareStatement("DELETE FROM seller WHERE id = ?");
			statement.setLong(1, id);
			statement.executeUpdate();
		} catch (SQLException error) {
			throw new BDadosIntegrityException(error.getMessage());
		} finally {
			BancoDados.closeStatement(statement);
		}
	}

	@Override
	public Seller findById(Long id) {
		Seller seller = new Seller();
		Department department = new Department();
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			statement = connection.prepareStatement(
					"SELECT * FROM seller s INNER JOIN department d ON s.departmentid = d.id WHERE s.id = ?");
			statement.setLong(1, id);
			result = statement.executeQuery();
			while (result.next()) {
				seller.setId(result.getLong("s.id"));
				seller.setName(result.getString("s.name"));
				seller.setEmail(result.getString("s.email"));
				seller.setBirthDate(
						LocalDate.parse(result.getString("birthdate"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
				seller.setBaseSalary(result.getDouble("s.basesalary"));
				department.setId(result.getLong("d.id"));
				department.setName(result.getString("d.name"));
				seller.setDepartment(department);

			}
			return seller;
		} catch (SQLException error) {
			throw new BDadosException(error.getMessage());
		} finally {
			BancoDados.closeResultSet(result);
			BancoDados.closeStatement(statement);
		}
	}

	@Override
	public List<Seller> findAll() {
		PreparedStatement statement = null;
		ResultSet result = null;

		List<Seller> listaSeller = new ArrayList<>();

		try {
			statement = connection.prepareStatement(
					"SELECT * FROM seller INNER JOIN department ON seller.DepartmentId = department.Id");
			result = statement.executeQuery();

			while (result.next()) {
				Seller seller = new Seller();
				Department department = new Department();
				seller.setId(result.getLong("id"));
				seller.setName(result.getString("name"));
				seller.setEmail(result.getString("email"));
				seller.setBirthDate(
						LocalDate.parse(result.getString("birthdate"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
				seller.setBaseSalary(result.getDouble("basesalary"));
				department.setId(result.getLong("department.id"));
				department.setName(result.getString("department.name"));
				seller.setDepartment(department);
				listaSeller.add(seller);
			}
			return listaSeller;
		} catch (SQLException error) {
			throw new BDadosException(error.getMessage());
		} finally {
			BancoDados.closeResultSet(result);
			BancoDados.closeStatement(statement);
		}
	}

	public List<Seller> findByDepartment(Department department) {
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			statement = connection.prepareStatement(
					"SELECT seller.*,department.Name as DepName " + "FROM seller INNER JOIN department "
							+ "ON seller.DepartmentId = department.Id " + "WHERE DepartmentId = ? " + "ORDER BY Name");

			statement.setLong(1, department.getId());

			result = statement.executeQuery();

			List<Seller> listaSeller = new ArrayList<>();
			Map<Long, Department> map = new HashMap<>();
			while (result.next()) {
				Department dep = map.get(result.getLong("departmentid"));
				if (dep == null) {
					dep = new Department(result.getLong("departmentid"), result.getString("depname"));
					map.put(result.getLong("departmentid"), dep);
				}
				Seller seller = new Seller();
				seller.setId(result.getLong("seller.id"));
				seller.setName(result.getString("seller.name"));
				seller.setEmail(result.getString("seller.email"));
				seller.setBirthDate(LocalDate.parse(result.getString("seller.birthdate"),
						DateTimeFormatter.ofPattern("yyyy-MM-dd")));
				seller.setBaseSalary(result.getDouble("seller.basesalary"));
				seller.setDepartment(dep);
				listaSeller.add(seller);
			}
			return listaSeller;

		} catch (SQLException error) {
			throw new BDadosException(error.getMessage());
		} finally {
			BancoDados.closeStatement(statement);
			BancoDados.closeResultSet(result);
		}
	}

	/** STACK-OVER-FLOW SAVE THE DAY */
	private static java.sql.Date conversorTime(LocalDate localDate) {
		Date date = Date.valueOf(localDate);
		return new java.sql.Date(date.getTime());
	}

}
