package org;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;
import java.util.function.Consumer;

public class Main {
    public static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("defaultH2");

    public static void main(String[] args) {
        Person person1 = new Person("Jan Kowalski");
        Person person2 = new Person("Tomasz Nowak");
        Department dep1 = new Department("Accounting");
        dep1.addPerson(person1);
        dep1.addPerson(person2);

        process(e -> e.persist(dep1));

//        try (Scanner sc = new Scanner(System.in)) {
//            sc.nextLine();
//        }

        // to co poniżej nie działa tak jak bym chciał:
        // 1) "detached entity passed to persist"
        // 2) chce by Kowalski i Nowak zostali przepięci do nowego departamentu tj. Marketing
        process(e -> {
            Department dep2 = new Department("Marketing");
            Person p1 = PersonRepo.findPersonByName("Jan Kowalski");
            Person p2 = PersonRepo.findPersonByName("Tomasz Nowak");

            System.out.println(p1);
            System.out.println(p2);

            e.merge(p1);
            e.merge(p2);

            dep2.addPerson(p1);
            dep2.addPerson(p2);

            e.persist(dep2);
        });
    }


    public static void process(Consumer<EntityManager> consumer) {
        EntityManager entityManager = EMF.createEntityManager();
        entityManager.getTransaction().begin();

        consumer.accept(entityManager);

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}