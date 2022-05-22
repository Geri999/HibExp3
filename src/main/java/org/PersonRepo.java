package org;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class PersonRepo {

    public static Person findPersonByName(String personName) {
        EntityManager entityManager = Main.EMF.createEntityManager();
        entityManager.getTransaction().begin();
        TypedQuery<Person> query = entityManager.createQuery("SELECT p from Person as p where p.personName=:personName", Person.class);
        query.setParameter("personName", personName);

        Person person = query.getSingleResult();

        entityManager.getTransaction().commit();
        entityManager.close();
        return person;

    }
}