package com.islla.factelect.infra.model.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Vector;

import org.hibernate.criterion.Criterion;

/**
 * 
 * Interfaz GenericoDaoI, recibe dos objetos de cualquier clase que implemente la interfaz.
 * Permite encapsular las operaciones de acceso a datos.
 * Se crean los metodos que indican que hacer(pero no como hacerlo).
 *
 */
public interface GenericoDaoI<T, ID extends Serializable> {

	public Connection getDBConnection();


	

}