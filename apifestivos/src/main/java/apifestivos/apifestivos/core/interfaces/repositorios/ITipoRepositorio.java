package apifestivos.apifestivos.core.interfaces.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import apifestivos.apifestivos.dominio.entidades.Tipo;

public interface ITipoRepositorio extends JpaRepository<Tipo, Integer>{

}
