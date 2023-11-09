package com.vladhacksmile.orgmanagement.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "Coordinates")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Coordinates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "x", nullable = false)
    private Float x; //Максимальное значение поля: 662, Поле не может быть null
    @Column(name = "y", nullable = false)
    private Float y; //Поле не может быть null

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return Objects.equals(id, that.id) && Objects.equals(x, that.x) && Objects.equals(y, that.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, x, y);
    }
}
