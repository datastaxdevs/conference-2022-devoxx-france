package com.datastaxdev.todo;

import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.quarkus.runtime.api.session.QuarkusCqlSession;
import com.datastaxdev.todo.api.Todo;

import io.quarkus.runtime.StartupEvent;
import io.smallrye.common.annotation.Blocking;

@ApplicationScoped
@Path("/api")
public class AstraTODO {
    @Inject
    QuarkusCqlSession cqlSession;

    private String keyspaceName = "quarkus";
    private String tableName = "todolist";

    public boolean onStart(@Observes StartupEvent ev){
        ResultSet rs = this.cqlSession.execute("CREATE TABLE IF NOT EXISTS " + this.keyspaceName+"." + this.tableName + "(list_id text, id text, title text, completed boolean, PRIMARY KEY(list_id, id));");
        System.out.println("**** Table created " + rs.wasApplied() + "****");
        return rs.wasApplied();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/todo/{list_id}")
    @Blocking
    public List<Todo> getTodos(@PathParam("list_id") String list_id) {
        ResultSet rs = this.cqlSession
                .execute("SELECT * FROM " + this.keyspaceName + "." + this.tableName + " where list_id =?", list_id);

        List<Row> rows = rs.all();
        return rows.stream()
          .map(x -> new Todo(x.getString("id"), x.getString("title"), x.getBoolean("completed")))
          .collect(Collectors.toList());
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/todo/{list_id}")
    @Blocking
    public Response setTodo(@PathParam("list_id") String list_id, Todo todo) {
        ResultSet rs = this.cqlSession
                .execute("INSERT INTO " + this.keyspaceName + "." + this.tableName +
                        "(list_id, id, title, completed) VALUES (?,?,?,?)",
                        list_id, todo.getId(), todo.getTitle(), todo.isCompleted());

        boolean successful = rs.wasApplied();
        if (successful) {
            return Response.ok().build();
        }else{
            return Response.serverError().build();
        }

    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/todo/{list_id}/{id}")
    @Blocking
    public boolean getTodos(@PathParam("list_id") String list_id, @PathParam("id") String id){
        ResultSet rs = this.cqlSession.execute("DELETE FROM " + this.keyspaceName + "." +
                this.tableName + " WHERE list_id = ? AND id = ?",list_id, id);

        return rs.wasApplied();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/todo/{list_id}/{id}")
    @Blocking
    public boolean completeTodo(@PathParam("list_id") String list_id, @PathParam("id") String id) {

        ResultSet rs = this.cqlSession.execute("INSERT INTO " + this.keyspaceName +
                "." + this.tableName + " (id, list_id, completed) VALUES (?, ?, ?)", id, list_id, true);

        return rs.wasApplied();

    }
}
