package apifestivos.apifestivos.presentacion;


import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import apifestivos.apifestivos.core.interfaces.servicios.IFestivoServicio;


/**
 * Controlador REST para manejar las solicitudes relacionadas con los festivos.
 */

@RestController
@RequestMapping("/festivos")
public class FestivoControlador {

    private final IFestivoServicio festivoServicio;

    /**
     * Inyección de dependencias a través del constructor.
     * 
     * @param festivoServicio Servicio de festivos para manejar la lógica de negocio.
     */
    public FestivoControlador(IFestivoServicio festivoServicio) {
        this.festivoServicio = festivoServicio;
    }

    /**
     * Endpoint para verificar si una fecha es festiva.
     * 
     * @param anio Año de la fecha a verificar.
     * @param mes Mes de la fecha a verificar.
     * @param dia Día de la fecha a verificar.
     * @return Respuesta HTTP con el resultado de la verificación.
     */
    @GetMapping("/verificar/{anio}/{mes}/{dia}")
    public ResponseEntity<String> verificarFestivo(@PathVariable int anio, @PathVariable int mes,
        @PathVariable int dia) {
        try {
            // Intentar crear una instancia de LocalDate con los valores proporcionados
            LocalDate fechaLocalDate = LocalDate.of(anio, mes, dia);

            // Convertir LocalDate a Date para usar con el servicio
            Date fecha = Date.from(fechaLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

            // Verificar si es festivo usando el servicio
            String resultado = festivoServicio.verificarSiEsFestivo(fecha);
            return ResponseEntity.ok(resultado);
        } catch (DateTimeException e) {
            // Capturar excepciones de fechas inválidas
            return ResponseEntity.ok("Fecha no válida");
        }
    }
}
