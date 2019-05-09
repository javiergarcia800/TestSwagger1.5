package com.autentia.tutorial.rest;

import com.autentia.tutorial.rest.data.Message;
import com.autentia.tutorial.rest.data.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/users/")
@Api(value = "/users", description = "Operaciones con usuarios")
@Produces(MediaType.APPLICATION_JSON)
public class UserService /*extends JavaHelp*/ {

    private static final Map<Integer, User> USERS = new HashMap<Integer, User>();
    static {
        USERS.put(1, new User(1, "Juan"));
        USERS.put(2, new User(2, "Pepe"));
        USERS.put(3, new User(3, "Antonio"));
    }

    private static final Response NOT_FOUND = Response.status(Response.Status.NOT_FOUND).
            entity(new Message("El usuario no existe")).
            build();

    private static final Response USER_ALREADY_EXISTS = Response.status(Response.Status.BAD_REQUEST).
            entity(new Message("El usuario ya existe")).
            build();

    private static final Response OK = Response.ok().
            entity(new Message("Operacion realizada corretamente")).
                    build();

    @GET
    @Path("/find/all")
    @ApiOperation(
            value = "Devuelve todos los usuarios",
            notes = "Devuelve todos los usuarios del sistema"
    )
    public Response findAll() {
    	List<User> users = new ArrayList<User>(USERS.values());
    	GenericEntity< List<User> > entity  = new GenericEntity< List< User > >( users ) { };
    	
    	return Response.ok(entity).build();
    }

    @GET
    @Path("/find/{id}")
    @ApiOperation(
            value = "Busca un usuario por ID",
            notes = "Devuelve los datos relativos a un usuario"
    )
    public Response findById(@PathParam("id") int id) {
    	User user = USERS.get(id);
    	if (user == null){
    		return NOT_FOUND;
    	}
    	
        return Response.ok().entity(user).build();
    }

    @POST
    @Path("/add")
    @Consumes({MediaType.APPLICATION_JSON})
    @ApiOperation(
            value = "Da de alta un nuevo usuario",
            notes = "Crea un nuevo usuario a partir de un ID y un nombre. El usuario no debe existir"
    )
    public Response addUser(User newUser) {
    	if (USERS.get(newUser.getId()) != null) {
    		return USER_ALREADY_EXISTS;
    	}
        
    	USERS.put(newUser.getId(), newUser);
        return OK;
    }

    @PUT
    @Path("/update/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @ApiOperation(
            value = "Actualiza los datos de un usuario",
            notes = "Actualiza los datos del usuario que se corresponda con el id. El usuario debe existir"
    )
    public Response updateUser(@PathParam("id") int id, User userToUpdate) {
    	if (USERS.get(userToUpdate.getId()) == null) {
    		return NOT_FOUND;
    	}
    	
    	USERS.put(userToUpdate.getId(), userToUpdate);
        return OK;
    }
    

    @DELETE
    @Path("/delete/{id}")
    @ApiOperation(
            value = "Elimina un usuario",
            notes = "Elimina los datos del usuario que se corresponda con el id. El usuario debe existir"
    )
    public Response removeUser(@PathParam("id") int id) {
    	if (USERS.get(id) == null) {
    		return NOT_FOUND;
    	}
    	
    	USERS.remove(id);
        return OK;
    }

}