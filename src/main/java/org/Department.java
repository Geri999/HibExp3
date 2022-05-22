package org;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Department {

    @Id
    @GeneratedValue
    @Column(name = "id_department", nullable = false)
    private Long idDepartment;

    private String departmentName;

    @OneToMany(mappedBy = "department", cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @ToString.Exclude
    private Set<Person> personSet = new HashSet<>();

    public Department(String departmentName, Set<Person> personSet) {
        this.departmentName = departmentName;
        this.personSet = personSet;
    }


    public void addPerson(Person person){
        this.personSet.add(person);
        person.setDepartment(this);
    }
    public Department(String departmentName) {
        this.departmentName = departmentName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Department that = (Department) o;
        return idDepartment != null && Objects.equals(idDepartment, that.idDepartment);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
