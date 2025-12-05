public class Estudiante implements Comparable<Estudiante>,getAndStIdHeap<Estudiante>{
    private int pbm;
    private String nombre;
    private String correo;
    private long id;
    private int indexHeap;

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

    public long getId() {
        return id;
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

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public void setIndexHeap(int indexHeap){
        this.indexHeap=indexHeap;
    }
    @Override
    public int getIndexHeap(){
        return indexHeap;
    }
    @Override
    public int compareTo(Estudiante otro) {
        return Integer.compare(this.pbm, otro.pbm);
    }
}
