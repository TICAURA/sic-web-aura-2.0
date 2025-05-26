package mx.com.email.dto;



import java.util.Date;
import java.util.List;

/**
 *
 * @author dgais01
 */
public class CorreoDTO {
    
    private String ruta;
    private String nombre;
    private String titulo;
    private Integer estatus;
    private List<String> emails;
    private byte[] plantilla;
    private String mensaje;
    private String mensajeAux;
    private Date fecha;
    
    public CorreoDTO() {
        //DO NOTHING
    }
    public CorreoDTO(String ruta, String nombre, String titulo, Integer idProceso, String idPerfil, Integer estatus, List<String> emails) {
        this.ruta = ruta;
        this.nombre = nombre;
        this.titulo = titulo;
        this.estatus = estatus;
        this.emails = emails;
    }

    public CorreoDTO(String ruta, String nombre, String titulo, Integer idProceso, Integer estatus) {
        this.ruta = ruta;
        this.nombre = nombre;
        this.titulo = titulo;
        this.estatus = estatus;
    } 

    @Override
    public String toString() {
        return "SoaCorreoDTO{" + "ruta=" + ruta + ", nombre=" + nombre + '}';
    }
    
    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public byte[] getPlantilla() {
        return plantilla;
    }

    public void setPlantilla(byte[] plantilla) {
        this.plantilla = plantilla;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getMensajeAux() {
        return mensajeAux;
    }

    public void setMensajeAux(String mensajeAux) {
        this.mensajeAux = mensajeAux;
    }
    
}
