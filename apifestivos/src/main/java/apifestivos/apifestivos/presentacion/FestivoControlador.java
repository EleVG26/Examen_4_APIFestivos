package apifestivos.apifestivos.presentacion;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import apifestivos.apifestivos.core.interfaces.servicios.IFestivoServicio;
import apifestivos.apifestivos.dominio.DTOs.FestivoDTO;

@RestController
@RequestMapping("/festivos")
public class FestivoControlador {

    private final IFestivoServicio festivoServicio;

    public FestivoControlador(IFestivoServicio festivoServicio) {
        this.festivoServicio = festivoServicio;
    }

    @GetMapping("/verificar/{año}/{mes}/{dia}")
    public ResponseEntity<String> verificarFestivo(@PathVariable int año, @PathVariable int mes,
        @PathVariable int dia) {
    try {
        LocalDate fecha = LocalDate.of(año, mes, dia);
        String resultado = festivoServicio.verificarSiEsFestivo(fecha);
        return ResponseEntity.ok(resultado);
    } catch (DateTimeException e) {
        return ResponseEntity.ok("Fecha no válida");
    }
}

    @GetMapping("/{año}")
    public ResponseEntity<List<FestivoDTO>> obtenerFestivosPorAño(@PathVariable int año) {
        List<FestivoDTO> festivos = festivoServicio.obtenerFestivosPorAño(año);
        return ResponseEntity.ok(festivos);
    }
}
