package apifestivos.apifestivos.presentacion;


import java.time.LocalDate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import apifestivos.apifestivos.core.interfaces.servicios.IFestivoServicio;


@RestController
@RequestMapping("/festivos")
public class FestivoControlador {

    private final IFestivoServicio festivoServicio;

    // Inyección de dependencias a través del constructor
    public FestivoControlador(IFestivoServicio festivoServicio) {
        this.festivoServicio = festivoServicio;
    }

    @GetMapping("/verificar/{anio}/{mes}/{dia}")
    public ResponseEntity<String> verificarFestivo(@PathVariable int anio, @PathVariable int mes, @PathVariable int dia) {
        try {
            // Crear una instancia de LocalDate con los valores de año, mes y día
            LocalDate fecha = LocalDate.of(anio, mes, dia);
            
            // Verificar si es festivo usando el servicio
            String resultado = festivoServicio.verificarSiEsFestivo(fecha);
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            // Capturar cualquier excepción que ocurra (por ejemplo, fechas inválidas)
            return ResponseEntity.ok("Fecha no válida");
        }
    }
}
