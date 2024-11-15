package apifestivos.apifestivos.aplicacion;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import org.springframework.stereotype.Service;

@Service
public class FechaServicio {
    
    public LocalDate getFechaPascua(int año) {
        int a = año % 19;
        int b = año / 100;
        int c = año % 100;
        int d = b / 4;
        int e = b % 4;
        int f = (b + 8) / 25;
        int g = (b - f + 1) / 3;
        int h = (19 * a + b - d - g + 15) % 30;
        int i = c / 4;
        int k = c % 4;
        int l = (32 + 2 * e + 2 * i - h - k) % 7;
        int m = (a + 11 * h + 22 * l) / 451;
        int mes = (h + l - 7 * m + 114) / 31;
        int dia = ((h + l - 7 * m + 114) % 31) + 1;

        return LocalDate.of(año, mes, dia);
    }

    
    public LocalDate incrementarDias(LocalDate fecha, int dias) {
        return fecha.plusDays(dias);
    }

    
    public LocalDate siguienteLunes(LocalDate fecha) {
        return fecha.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));
    }

    
    public boolean esFechaValida(LocalDate fecha) {
        return fecha != null && fecha.getYear() > 0;
    }

    
    public LocalDate calcularFechaFestivo(int tipoFestivo, Integer dia, Integer mes, Integer diasPascua, int año) {
        LocalDate fechaFestivo;

        switch (tipoFestivo) {
            case 1:
                // Festivo fijo que no se traslada
                fechaFestivo = LocalDate.of(año, mes, dia);
                break;

            case 2:
                // Festivo fijo que se traslada al siguiente lunes (Ley de Puente Festivo)
                fechaFestivo = LocalDate.of(año, mes, dia);
                fechaFestivo = siguienteLunes(fechaFestivo);
                break;

            case 3:
                // Festivo basado en el domingo de Pascua
                LocalDate fechaPascua = getFechaPascua(año);
                fechaFestivo = incrementarDias(fechaPascua, diasPascua);
                break;

            case 4:
                // Festivo basado en Pascua y que se traslada al siguiente lunes
                fechaPascua = getFechaPascua(año);
                fechaFestivo = incrementarDias(fechaPascua, diasPascua);
                fechaFestivo = siguienteLunes(fechaFestivo);
                break;

            default:
                throw new IllegalArgumentException("Tipo de festivo desconocido: " + tipoFestivo);
        }

        return fechaFestivo;
    }

}

