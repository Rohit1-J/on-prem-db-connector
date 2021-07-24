package com.db.connector.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.db.connector.config.DBSocketFactory;

@RestController
@RequestMapping("/db")
public class DBController {

	private Connection getConnection() throws SQLException {
		String url = "jdbc:postgresql://localhost:5912/DB";
		Properties props = new Properties();
		props.setProperty("user", "username");
		props.setProperty("password", "password");
		props.setProperty("socketFactory", DBSocketFactory.class.getName());
		Connection conn = DriverManager.getConnection(url, props);
		return conn;
	}

	private String printCount(Connection conn) throws SQLException {
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(
				"select count(*) from DUMMY");
		rs.next();
		return "Table contains " + rs.getInt("c") + " rows";
	}

	@GetMapping("/connect")
	public String connect() throws SQLException {
		Connection conn = getConnection();
		return printCount(conn);
	}

}
