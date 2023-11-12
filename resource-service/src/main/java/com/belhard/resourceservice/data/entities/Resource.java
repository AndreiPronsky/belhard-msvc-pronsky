package com.belhard.resourceservice.data.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "resources")
public class Resource {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "audio")
    private byte[] audio;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resource resource = (Resource) o;
        return Objects.equals(id, resource.id) && Arrays.equals(audio, resource.audio);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id);
        result = 31 * result + Arrays.hashCode(audio);
        return result;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "id=" + id +
                '}';
    }
}
