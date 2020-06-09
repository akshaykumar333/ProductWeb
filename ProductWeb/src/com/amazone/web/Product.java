package com.amazone.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Driver;

public class Product extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Connection CON = null;
		Statement stmt = null;
		ResultSet rs = null;
		PrintWriter out = resp.getWriter();

		try {
			Driver dref = new Driver();
			DriverManager.registerDriver(dref);

			String dburl = "jdbc:mysql://localhost:3306/market?user=root&password=root";
			CON = DriverManager.getConnection(dburl);

			String query = "select * from mobile";
			stmt = CON.createStatement();

			File file = new File("iphonex.jpeg");

			FileOutputStream fileout = new FileOutputStream(file);

			rs = stmt.executeQuery(query);

			while (rs.next()) {
				String name = rs.getString("name");
				int price = rs.getInt("price");
				String desc = rs.getString("description");
				double rating = rs.getDouble("rating");

				out.println("model name =" + " " + name);
				out.println("model price =" + " " + price);
				out.println("model Description =" + " " + desc);
				out.println("model rating =" + " " + rating);

				InputStream ip = rs.getBinaryStream("image");

				byte[] ref = new byte[1024];

				while (ip.read(ref) > 0)

				{

					fileout.write(ref);
				}

				out.println();
				

			}

		} catch (SQLException e) {

			e.printStackTrace();
		}

		finally {
			try {
				if (CON != null)
					CON.close();

				if (stmt != null)
					stmt.close();

				if (rs != null)
					rs.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}
