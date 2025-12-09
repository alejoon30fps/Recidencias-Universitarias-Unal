package Estructuras;
public class Estudiante implements Comparable<Estudiante>,getAndStIdHeap<Estudiante>{
    
    private static long contadorIds = 0; // Contador estático para asignar IDs únicos
    
    
    private int pbm;
    private String nombre;
    private String correo;
    private long id;
    private int indexHeap;

    public Estudiante(String nombre, int pbm, String correo) {
        this.id = ++contadorIds; // Asignar ID único autoincremental
        this.pbm = pbm;
        this.nombre = nombre;
        this.correo = correo;
        this.indexHeap = -1; // Inicialmente no está en el heap
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
        int cmp = Integer.compare(this.pbm, otro.pbm);
        if (cmp != 0) return cmp;
        // Si los PBM son iguales, comparar por ID y el menor ID tiene mayor prioridad porque se inserto antes
        return Long.compare(this.id, otro.id);
    }

    @Override
    public String toString() {
        return "Estudiante{id=" + id + ", nombre='" + nombre + "', pbm=" + pbm + ", correo='" + correo + "'}";
    }

    public static void resetIDCounter() {
        contadorIds = 0;
    }

}
