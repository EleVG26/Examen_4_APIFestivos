package apifestivos.apifestivos.dominio.entidades;

import java.util.Set;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tipo")
public class Tipo {

    @Id
    private Integer id; // ID conocido, no se genera automáticamente

    @Column(name = "tipo", nullable = false, length = 100)
    private String descripcion;

    @OneToMany(mappedBy = "tipo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Festivo> festivos;

    // Constructor vacío
    public Tipo() {
    }

    // Constructor con parámetros
    public Tipo(Integer id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    // Getters y Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<Festivo> getFestivos() {
        return festivos;
    }

    public void setFestivos(Set<Festivo> festivos) {
        this.festivos = festivos;
    }
}

