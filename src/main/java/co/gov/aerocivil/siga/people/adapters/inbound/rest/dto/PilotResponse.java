package co.gov.aerocivil.siga.people.adapters.inbound.rest.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PilotResponse {
    private String licencia;
    private String nombre;
    private String apellidos;
    private String tipoDocumento;
    private String numeroDocumento;
    private LocalDate fechaNacimiento;
    private String nacionalidad;
    private String telefono;
    private String email;
    private String direccion;
    private String tipoLicencia;
    private LocalDate fechaExpedicionLicencia;
    private LocalDate fechaVencimientoLicencia;
    private String restricciones;

    private Integer horasVueloTotal;
    private Integer horasVueloUltimos12Meses;
    private String certificadoMedico;
    private LocalDate fechaVencimientoCertificadoMedico;

    private List<String> habilitaciones;
    private String estado;
}
