package apifestivos.apifestivos.aplicacion;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
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

    // Inyección de dependencias a través del constructor
    public FestivoServicio(IFestivoRepositorio festivoRepositorio) {
        this.festivoRepositorio = festivoRepositorio;
    }

    public String verificarSiEsFestivo(LocalDate fecha) {
        try {
            if (fecha == null || !esFechaValida(fecha)) {
                return "Fecha no válida";
            }

            int anio = fecha.getYear();

            // Obtener todos los festivos
            List<Festivo> festivos = festivoRepositorio.findAll();

            for (Festivo festivo : festivos) {
                LocalDate fechaFestivo = calcularFechaFestivo(
                        festivo.getTipo().getId(),
                        festivo.getDia(),
                        festivo.getMes(),
                        festivo.getDiasPascua(),
                        anio
                );

                if (fecha.equals(fechaFestivo)) {
                    return "Es festivo: ";
                }
            }

            return "No es festivo";

        } catch (Exception e) {
            e.printStackTrace(); // Para registrar la excepción
            return "Error interno del servidor: " + e.getMessage();
        }
    }

    public List<FestivoDTO> obtenerFestivosPorAnio(int anio) {
        List<Festivo> festivos = festivoRepositorio.findAll();
        List<FestivoDTO> festivosDTO = new ArrayList<>();

        for (Festivo festivo : festivos) {
            LocalDate fechaFestivo = calcularFechaFestivo(
                festivo.getTipo().getId(),
                festivo.getDia(),
                festivo.getMes(),
                festivo.getDiasPascua(),
                anio
            );

            FestivoDTO dto = new FestivoDTO(festivo.getNombre(), fechaFestivo);
            festivosDTO.add(dto);
        }

        return festivosDTO;
    }

    private boolean esFechaValida(LocalDate fecha) {
        return fecha != null && fecha.getYear() > 0; // Verifica que la fecha no sea nula y el año sea válido.
    }

    private LocalDate calcularFechaFestivo(int tipoFestivo, Integer dia, Integer mes, Integer diasPascua, int anio) {
        LocalDate fechaFestivo;

        switch (tipoFestivo) {
            case 1:
                // Festivo fijo que no se traslada
                fechaFestivo = LocalDate.of(anio, mes, dia);
                break;

            case 2:
                // Festivo fijo que se traslada al siguiente lunes (Ley de Puente Festivo)
                fechaFestivo = LocalDate.of(anio, mes, dia);
                fechaFestivo = siguienteLunes(fechaFestivo);
                break;

            case 3:
                // Festivo basado en el domingo de Pascua
                LocalDate fechaPascua = getSemanaSanta(anio);
                fechaFestivo = incrementarDias(fechaPascua, diasPascua);
                break;

            case 4:
                // Festivo basado en Pascua y que se traslada al siguiente lunes
                fechaPascua = getSemanaSanta(anio);
                fechaFestivo = incrementarDias(fechaPascua, diasPascua);
                fechaFestivo = siguienteLunes(fechaFestivo);
                break;

            default:
                throw new IllegalArgumentException("Tipo de festivo desconocido: " + tipoFestivo);
        }

        return fechaFestivo;
    }

    public LocalDate getSemanaSanta(int anio) {
        int a = anio % 19;
        int b = anio % 4;
        int c = anio % 7;
        int d = (19 * a + 24) % 30;
        int e = (2 * b + 4 * c + 6 * d + 5) % 7;
        int dia = 15 + d + e;
        return LocalDate.of(anio, 3, 1).plusDays(dia - 1);
    }

    public LocalDate incrementarDias(LocalDate fecha, int dias) {
        return fecha.plusDays(dias);
    }

    public LocalDate siguienteLunes(LocalDate fecha) {
        return fecha.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));
    }
}

