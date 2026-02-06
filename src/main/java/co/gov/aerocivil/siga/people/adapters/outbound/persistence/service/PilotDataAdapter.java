package co.gov.aerocivil.siga.people.adapters.outbound.persistence.service;

import co.gov.aerocivil.siga.people.adapters.outbound.persistence.repository.PilotJpaRepository;
import co.gov.aerocivil.siga.people.application.port.out.data.PilotDataPort;
import co.gov.aerocivil.siga.people.domain.model.person.Pilot;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PilotDataAdapter implements PilotDataPort {

    private final PilotJpaRepository pilotJpaRepository;

    @Override
    public Optional<Pilot> findByLicense(String licenseNumber) {
        return pilotJpaRepository.findPilotRowByLicense(licenseNumber)
            .map(row -> {
                String nombre = StringUtils.trimToEmpty(row.getPrimerNombre()) +
                    (StringUtils.isBlank(row.getSegundoNombre()) ? "" : " " + StringUtils.trimToEmpty(row.getSegundoNombre()));

                String apellidos = StringUtils.trimToEmpty(row.getPrimerApellido()) +
                    (StringUtils.isBlank(row.getSegundoApellido()) ? "" : " " + StringUtils.trimToEmpty(row.getSegundoApellido()));

                // Tipo documento: no se consulta PersonEntity para evitar ORA-00942 (tablas ADM_* pueden no existir o no ser accesibles).
                // Si en el futuro la consulta nativa incluye código de tipo documento, mapearlo aquí con mapTipoDocumento().
                String tipoDocumento = null;

                List<String> habilitaciones = parseHabilitaciones(row.getCadenaHabilitaciones());

                return Pilot.builder()
                    .licencia(row.getNumeroLicencia())
                    .nombre(nombre.trim())
                    .apellidos(apellidos.trim())
                    .tipoDocumento(tipoDocumento)
                    .numeroDocumento(row.getNumeroDocumento())
                    .fechaNacimiento(row.getFechaNacimiento())
                    .nacionalidad(row.getNacionalidad())
                    .telefono(StringUtils.defaultIfBlank(row.getCelular(), row.getTelefono()))
                    .email(row.getCorreo())
                    .direccion(row.getDomicilio())
                    .tipoLicencia(row.getTipoLicenciaCodigo())
                    .fechaExpedicionLicencia(row.getFechaExpedicion())
                    .fechaVencimientoLicencia(row.getFechaCaducidad())
                    .restricciones(StringUtils.defaultIfBlank(row.getLimitaciones(), null))

                    // No vi estas columnas en las tablas del backup:
                    .horasVueloTotal(null)
                    .horasVueloUltimos12Meses(null)
                    .certificadoMedico(null)
                    .fechaVencimientoCertificadoMedico(null)

                    .habilitaciones(habilitaciones)
                    .estado(mapEstado(row.getEstadoLicencia()))
                    .build();
            });
    }

    private List<String> parseHabilitaciones(String cadena) {
        if (StringUtils.isBlank(cadena)) return List.of();
        return Arrays.stream(cadena.split("[,;|]+"))
            .map(String::trim)
            .filter(StringUtils::isNotBlank)
            .toList();
    }

    private String mapEstado(Integer estado) {
        if (estado == null) return null;
        return estado == 1 ? "activo" : "inactivo";
    }

    private String mapTipoDocumento(String code) {
        if (code == null) return null;
        String c = code.trim().toUpperCase();
        return switch (c) {
            case "CC", "C.C", "C.C." -> "cedula";
            case "CE" -> "cedula_extranjeria";
            case "PA", "PAS" -> "pasaporte";
            default -> c.toLowerCase();
        };
    }
}
