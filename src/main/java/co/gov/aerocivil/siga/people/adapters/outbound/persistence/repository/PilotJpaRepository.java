package co.gov.aerocivil.siga.people.adapters.outbound.persistence.repository;

import co.gov.aerocivil.siga.core.data.entity.person.PersonEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface PilotJpaRepository extends JpaRepositoryImplementation<PersonEntity, Long> {

    interface PilotRow {
        Long getIdPersona();
        String getNumeroLicencia();

        LocalDate getFechaExpedicion();
        LocalDate getFechaCaducidad();

        Integer getEstadoLicencia();

        String getLimitaciones();
        String getCadenaHabilitaciones();

        String getPrimerNombre();
        String getSegundoNombre();
        String getPrimerApellido();
        String getSegundoApellido();

        String getNumeroDocumento();
        LocalDate getFechaNacimiento();

        String getNacionalidad();
        String getTelefono();
        String getCelular();
        String getCorreo();
        String getDomicilio();

        String getTipoLicenciaCodigo();
        String getTipoLicenciaDescripcion(); // opcional
    }

    @Query(value = """
        SELECT
          l.ID_PERSONA                 AS "idPersona",
          l.NUMERO_LICENCIA            AS "numeroLicencia",
          l.FECHA_EXPEDICION           AS "fechaExpedicion",
          l.FECHA_CADUCIDAD            AS "fechaCaducidad",
          l.ESTADO                     AS "estadoLicencia",
          l.LIMITACIONES               AS "limitaciones",
          l.CADENA_HABILITACIONES      AS "cadenaHabilitaciones",

          p.PRIMER_NOMBRE              AS "primerNombre",
          p.SEGUNDO_NOMBRE             AS "segundoNombre",
          p.PRIMER_APELLIDO            AS "primerApellido",
          p.SEGUNDO_APELLIDO           AS "segundoApellido",

          p.NUMERO_DOCUMENTO           AS "numeroDocumento",
          p.FECHA_NACIMIENTO           AS "fechaNacimiento",
          p.NACIONALIDAD               AS "nacionalidad",
          p.TELEFONO                   AS "telefono",
          p.CELULAR                    AS "celular",
          p.CORREO_ELECTRONICO         AS "correo",
          p.DOMICILIO                  AS "domicilio",

          tl.CODIGO                    AS "tipoLicenciaCodigo",
          tl.DESCRIPCION               AS "tipoLicenciaDescripcion"

        FROM SIGA_NEGOCIO.PER_LICENCIA l
        LEFT JOIN SIGA_NEGOCIO.ADM_PERSONA p
          ON p.ID_PERSONA = l.ID_PERSONA
        LEFT JOIN SIGA_NEGOCIO.ADM_TIPO_LICENCIA_PERSONAL_AER tl
          ON tl.ID_TIPO_LICENCIA_PERSONAL_AER = l.ID_TIPO_LICENCIA
        WHERE UPPER(TRIM(TO_CHAR(l.NUMERO_LICENCIA))) = UPPER(TRIM(:licenseNumber))
        """, nativeQuery = true)
    Optional<PilotRow> findPilotRowByLicense(@Param("licenseNumber") String licenseNumber);
}
