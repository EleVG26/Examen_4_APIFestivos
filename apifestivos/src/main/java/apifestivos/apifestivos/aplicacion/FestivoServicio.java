package apifestivos.apifestivos.aplicacion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import apifestivos.apifestivos.core.interfaces.repositorios.IFestivoRepositorio;
import apifestivos.apifestivos.core.interfaces.servicios.IFestivoServicio;
import apifestivos.apifestivos.dominio.DTOs.FestivoDTO;
import apifestivos.apifestivos.dominio.entidades.Festivo;

@Service
public class FestivoServicio implements IFestivoServicio {

    private final IFestivoRepositorio festivoRepositorio;
    private final FechaServicio fechaServicio;

    // Inyección de dependencias a través del constructor
    public FestivoServicio(IFestivoRepositorio festivoRepositorio, FechaServicio fechaServicio) {
        this.festivoRepositorio = festivoRepositorio;
        this.fechaServicio = fechaServicio;
    }

    @Override
    public String verificarSiEsFestivo(LocalDate fecha) {
        try {
            if (fecha == null || !fechaServicio.esFechaValida(fecha)) {
                return "Fecha no válida";
            }

            int año = fecha.getYear();

            // Obtener todos los festivos
            List<Festivo> festivos = festivoRepositorio.findAll();

            for (Festivo festivo : festivos) {
                LocalDate fechaFestivo = fechaServicio.calcularFechaFestivo(
                        festivo.getTipo().getId(),
                        festivo.getDia(),
                        festivo.getMes(),
                        festivo.getDiasPascua(),
                        año
                );

                if (fecha.equals(fechaFestivo)) {
                    return "Es Festivo: " + festivo.getNombre();
                }
            }

            return "No es Festivo";

        } catch (Exception e) {
            e.printStackTrace();
            return "Error interno del servidor: " + e.getMessage();
        }
    }

    @Override
    public List<FestivoDTO> obtenerFestivosPorAño(int año) {
        List<Festivo> festivos = festivoRepositorio.findAll();
        List<FestivoDTO> festivosDTO = new ArrayList<>();

        for (Festivo festivo : festivos) {
            try {
                LocalDate fechaFestivo = fechaServicio.calcularFechaFestivo(
                        festivo.getTipo().getId(),
                        festivo.getDia(),
                        festivo.getMes(),
                        festivo.getDiasPascua(),
                        año
                );

                FestivoDTO dto = new FestivoDTO(
                        festivo.getNombre(),
                        fechaFestivo
                );
                festivosDTO.add(dto);
            } catch (Exception e) {
                // Manejar excepciones si es necesario
                continue;
            }
        }

        return festivosDTO;
    }
}

