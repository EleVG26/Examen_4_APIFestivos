package apifestivos.apifestivos.core.interfaces.servicios;



import java.util.Date;


public interface IFestivoServicio {

    //  Verifica si una fecha específica es un festivo.
    String verificarSiEsFestivo(Date fecha);
    
}

