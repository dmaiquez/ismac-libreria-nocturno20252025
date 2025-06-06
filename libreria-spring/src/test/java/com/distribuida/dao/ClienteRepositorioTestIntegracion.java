package com.distribuida.dao;

import com.distribuida.model.Cliente;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Rollback(value = false)
public class ClienteRepositorioTestIntegracion {

    @Autowired   // inyeccion de dependencias
    private ClienteRepositorio clienteRepositorio;

    @Test
    public void findAll(){
        List<Cliente> clientes = clienteRepositorio.findAll();
        assertNotNull(clientes);
        assertTrue(clientes.size() > 0);
        for (Cliente item : clientes){
            System.out.println(item.toString());
        }

    }

    @Test
    public void findOne(){
        Optional<Cliente> cliente = clienteRepositorio.findById(1);
        assertTrue(cliente.isPresent(), "El cliente con id=39 deberia existir");
        System.out.println(cliente.toString());
    }

    @Test
    public void save(){
        Cliente cliente = new Cliente(0,"1701234567","Juan","Taipe","Av. por ahi.", "0987564321","jtaipe@correo.com");
        Cliente clienteGuardado = clienteRepositorio.save(cliente);
        assertNotNull(clienteGuardado.getIdCliente(),"el cliente guardado debe tener un id");
        assertEquals("1701234567",clienteGuardado.getCedula());
        assertEquals("Juan",clienteGuardado.getNombre());

    }

    @Test
    public void update(){
        Optional<Cliente> clienteExistente = clienteRepositorio.findById(40);
        assertTrue(clienteExistente.isPresent());

        clienteExistente.orElse(null).setCedula("17012345622");
        clienteExistente.orElse(null).setNombre("Juan2");
        clienteExistente.orElse(null).setApellido("Taipe2");
        clienteExistente.orElse(null).setDireccion("Av. 2222");
        clienteExistente.orElse(null).setTelefono("09876543222");
        clienteExistente.orElse(null).setCorreo("jtaipe222@correo.com");

        Cliente clienteActualizado = clienteRepositorio.save(clienteExistente.orElse(null));

        assertEquals("Juan2", clienteActualizado.getNombre());
        assertEquals("Taipe2", clienteActualizado.getApellido());

    }

    @Test
    public void delete(){
        if(clienteRepositorio.existsById(40)){
            clienteRepositorio.deleteById(40);
        }
        assertFalse(clienteRepositorio.existsById(40),"EL id=40 debería haberse eliminado");
    }

}
