package apifestivos.apifestivos.core.interfaces.servicios;

import java.time.LocalDate;
import java.util.List;

import apifestivos.apifestivos.dominio.DTOs.FestivoDTO;

public interface IFestivoServicio {

    String verificarSiEsFestivo(LocalDate fecha);
    List<FestivoDTO> obtenerFestivosPorAño(int año);

}

