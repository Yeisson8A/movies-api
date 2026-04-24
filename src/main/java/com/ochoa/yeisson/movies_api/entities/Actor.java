package com.ochoa.yeisson.movies_api.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "actors")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Actor extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "actors")
    private List<Movie> movies;
}
