package org;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Person {

    @Id
    @GeneratedValue
    @Column(name = "id_person")
    private Long idPerson;

    private String personName;

    @ManyToOne
    @JoinColumn(name = "FK_Department")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Department department;

    public Person(String personName) {
        this.personName = personName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Person person = (Person) o;
        return idPerson != null && Objects.equals(idPerson, person.idPerson);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}


