package com.islla.factelect.test;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.testng.annotations.Test;

import com.islla.factelect.repository.ClienteDao;
import com.islla.factelect.domain.Cliente;
import com.islla.factelect.domain.Emisor;
import com.islla.factelect.domain.Municipios;
import com.islla.factelect.domain.MunicipiosId;

public class TestClienteDao {

	ClienteDao clienteDao;

	@Test(enabled = true)
	public void setConfiguration() {
		ApplicationContext ctx = new FileSystemXmlApplicationContext(
				"/WebContent/WEB-INF/applicationContext.xml");
		clienteDao = (ClienteDao) ctx.getBean("clienteDaoSQL");
	}

	@Test(enabled = false, dependsOnMethods = { "setConfiguration" })
	public void obtenerCliente() {

		Cliente cliente = new Cliente();
		cliente.setEmisor(new Emisor());
		cliente.getEmisor().setRfcEmisor("MOSF840216");
		cliente.setRfcCliente("RFC");
		cliente.setNombreRazonSocial("Juan");

		List<Cliente> clientes = clienteDao.buscarCliente(cliente);

		System.out.println("size " + clientes.size());

		for (Cliente c : clientes) {

			System.out.println("nombre " + c.getNombreRazonSocial());
			System.out.println("correo " + c.getCorreo());
			System.out.println("telefono " + c.getTelefono());
			System.out.println("");
		}

	}

	@Test(enabled = false, dependsOnMethods = { "setConfiguration" })
	public void insertarCliente() {

		Cliente cliente = new Cliente();
		cliente.setEmisor(new Emisor());
		cliente.setMunicipios(new Municipios());
		cliente.setCorreo("");
		cliente.setCp("7627");
		cliente.setDireccion("Direccion del Cliente");
		cliente.getEmisor().setRfcEmisor("MOSF840216");
		cliente.setRfcCliente("RFC");
		cliente.setNombreRazonSocial("Juan");
		cliente.getMunicipios().setId(new MunicipiosId(1, 1));
		cliente.setEstatus('A');
		cliente.setFechaModificacion(new Date());
		cliente.setTelefono("1232112");

		cliente = clienteDao.insertarCliente(cliente);
		System.out.println("id cliente " + cliente.getIdCliente());

	}
	
	
	@Test(enabled = true, dependsOnMethods = { "setConfiguration" })
	public void actualizarCliente() throws SQLException {
		
		
		Cliente cliente = new Cliente();
		cliente.setEmisor(new Emisor());
		cliente.getEmisor().setRfcEmisor("MOSF840216");
		cliente.setRfcCliente("RFC");

		List<Cliente> clientes = clienteDao.buscarCliente(cliente);
		cliente = clientes.get(0);
		
		System.out.println("Cliente encontrado " + cliente.getNombreRazonSocial());
		System.out.println("Cliente ID " + cliente.getIdCliente());
		
		cliente.setEmisor(new Emisor());
		cliente.setMunicipios(new Municipios());
		cliente.setCorreo("correoElectronico");
		cliente.setCp("7627");
		cliente.setDireccion("Direccion del Cliente Modificada");
		cliente.getEmisor().setRfcEmisor("MOSF840216");
		cliente.setRfcCliente("RFC");
		cliente.setNombreRazonSocial("Juan");
		cliente.getMunicipios().setId(new MunicipiosId(1, 1));
		cliente.setFechaModificacion(new Date());
		cliente.setTelefono("654645");

		cliente = clienteDao.actualizarCliente(cliente);
		System.out.println("id cliente " + cliente.getIdCliente());

	}

}
