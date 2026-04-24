package com.ochoa.yeisson.movies_api.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "directors")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Director extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "director")
    private List<Movie> movies;
}
