public class Estudiante {
    private int pbm;
    private String nombre;
    private String correo;

    public Estudiante(String nombre, int pbm, String correo) {
        this.pbm = pbm;
        this.nombre = nombre;
        this.correo = correo;
    }

    // GETTERS
    public int getPbm() {
        return pbm;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    // SETTERS
    public void setPbm(int pbm) {
        this.pbm = pbm;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
