package com.pay.restapp.resources;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

// Springboot will create a repository object based on this interface.
// We will use the repository object to access the H2 database.
@Repository
public class TodoRepositoryCustomImpl implements TodoRepositoryCustom{
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<Todo> getTodoDues(String dueDate) {
		Query query = entityManager.createNativeQuery(
				"SELECT * from Todos WHERE dueDate < ?",
				Todo.class);
		query.setParameter(1, dueDate);
		return query.getResultList();
	}

}
