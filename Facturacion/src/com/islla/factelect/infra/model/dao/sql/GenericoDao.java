package com.islla.factelect.infra.model.dao.sql;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;
import java.util.List;
import java.util.Vector;
import org.hibernate.criterion.Criterion;


import com.islla.factelect.infra.model.dao.GenericoDaoI;

public class GenericoDao<T, ID extends Serializable> implements GenericoDaoI<T, ID>{
	
	private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_CONNECTION = "jdbc:mysql://localhost/factura_DB";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "isllaroot";
	
	 private Connection connect = null;
	  private Statement statement = null;
	  private PreparedStatement preparedStatement = null;
	  private ResultSet resultSet = null;
	  
	  
	  
	  public   Connection getDBConnection() {
		  
			Connection dbConnection = null;
	 
			try {
	
				Class.forName(DB_DRIVER);
	 
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				
				System.out.println(e.getMessage());
	 
			}
	 
			try {
	 
				dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER,DB_PASSWORD);
				return dbConnection;
	 
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
			return dbConnection;
	 
		}
	  
	
	protected ResultSet getResultados(String consulta) {
		try{
		
		statement = getDBConnection().createStatement();
		
		return statement.executeQuery(consulta);
		
		  
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		finally{
			close();
		}
	}
	
	protected ResultSet getResultados(PreparedStatement consulta) {
		try{
			
		  //ResultSet resultado = consulta.executeQuery();
		  
		 
		  return consulta.executeQuery();
		  
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		finally{
			close();
		}
	}
	
	protected ResultSet ejectuarInsercion(PreparedStatement consulta) {
		try{
			
		  //ResultSet resultado = consulta.executeQuery();
		  
		 consulta.executeUpdate();
		  return consulta.getGeneratedKeys();
		  
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		finally{
			close();
		}
	}
	
	protected ResultSet ejectuarActualizacion(PreparedStatement consulta) {
		try{
			
		  //ResultSet resultado = consulta.executeQuery();
		  
		 consulta.executeUpdate();
		  return consulta.getGeneratedKeys();
		  
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		finally{
			close();
		}
	}
	
	
	
	
	private void close() {
	    try {
	      if (resultSet != null) {
	        resultSet.close();
	      }

	      if (statement != null) {
	        statement.close();
	      }
	      
	      if(preparedStatement!=null){
	    	   preparedStatement.close();
	       }
	      
	      if (connect != null) {
	        connect.close();
	      }
	       
	      
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	  }



	


}
