package com.islla.factelect.repository;
/*Fernando Morales Serrano
 * 23/11/2012
 * 
 * */


import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import com.islla.factelect.infra.model.dao.GenericoDaoI;
import com.islla.factelect.domain.Cliente;

/**
 * Interface Cliente, que contiene metodos que manipulan la entidad de Cliente 
 * 
 * 
 * */
public interface ClienteDao{

	
	
	public Cliente insertarCliente(Cliente cliente);
	
	public List buscarCliente(Cliente cliente);
	
	public Cliente actualizarCliente(Cliente cliente)throws SQLException;
	
	
}
