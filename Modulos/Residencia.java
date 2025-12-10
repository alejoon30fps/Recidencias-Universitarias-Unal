package Modulos;
import Estructuras.AVLEstudiante;
import Estructuras.Estudiante;
import Estructuras.MinHeap;
import Estructuras.UniversalHashTable;

public class Residencia {

    private AVLEstudiante ordenPrioridad;
    private MinHeap colaPrioridad;
    UniversalHashTable estudiantesPorID;
    private UniversalHashTable aceptados;
    private int cupos;

    public Residencia(){
        cupos=16; // cargo predeterminado de cupos
        ordenPrioridad= new AVLEstudiante();
        colaPrioridad= new MinHeap<Estudiante>(1000000);//capacidad grande inicial
        estudiantesPorID= new UniversalHashTable<Estudiante>();
        aceptados=new UniversalHashTable<Estudiante>();
        
    }

    // Ingresar número de cupos disponibles
    public void setCupos(int nuevoCupo){
    if (nuevoCupo <= 0){
        System.out.println("❌ El número de cupos debe ser mayor que 0.");
        return;
    }

    this.cupos = nuevoCupo;

    //// Reiniciar heap con nueva capacidad pero conservando los estudiantes actuales
    //MinHeap<Estudiante> nuevoHeap = new MinHeap<>(nuevoCupo);
//
    //// Recargar todos los estudiantes existentes en el heap
    //for (int i = 0; i < ordenPrioridad.totalNodos(); i++) {
    //    // Este método lo agregamos más abajo (obtener lista del AVL)
    //    Estudiante e = ordenPrioridad.getByIndexInOrder(i);
    //    nuevoHeap.insert(e);
    //}
//
    //this.colaPrioridad = nuevoHeap;

    System.out.println("✔ Cupos actualizados a: " + nuevoCupo);
}


    public void registrarEstudiante(String nombre, int pbm, String correo){
        Estudiante nuevoEstudiante=new Estudiante(nombre, pbm, correo);
        ordenPrioridad.insertar(nuevoEstudiante);
        colaPrioridad.insert(nuevoEstudiante);
        estudiantesPorID.insert(nuevoEstudiante.getId(), nuevoEstudiante);
    }

    public String queryEstudianteId(long id){
        Estudiante e = (Estudiante) estudiantesPorID.get(id);

        if (e == null) return "No existe estudiante con ID " + id;
        return e.toString();
    }

    public void changeValue(int nuevoPbm,long idEstudiante){
        Estudiante cambioEstudiante= (Estudiante) estudiantesPorID.get(idEstudiante);
        if(cambioEstudiante==null) return;

        ordenPrioridad.changeKey(cambioEstudiante, nuevoPbm); //dentro de este metodo cambiamos el valor del pbm
        colaPrioridad.changeKey(cambioEstudiante, cambioEstudiante);//aqui simplemente actualizamos el mismo objeto pero con atributos modificados

        cambioEstudiante.setPbm(nuevoPbm);
    }

    public void asigCupos(){
        // Reiniciar la tabla de aceptados ya que se puede llamar varias veces y desde una sola puede cambiar todo
        // o verse afectado por cambios previos
        aceptados= new UniversalHashTable<Estudiante>();

        //Reconstruir heap desde el AVL para asegurar consistencia
        MinHeap<Estudiante> nuevoHeap = new MinHeap<>(ordenPrioridad.totalNodos());

        for (int i = 0; i < ordenPrioridad.totalNodos(); i++) {
            // Este método lo agregamos más abajo (obtener lista del AVL)
            Estudiante e = ordenPrioridad.getByIndexInOrder(i);
            nuevoHeap.insert(e);
        }
        colaPrioridad = nuevoHeap;

        //Asignar cupos
        int cuposRestantes = cupos;
        while (cuposRestantes > 0 && !colaPrioridad.isEmpty()) {

            Estudiante cupo = (Estudiante) colaPrioridad.extractMin();

            // Si este estudiante ya fue aceptado (por si acaso), saltarlo
            if (aceptados.get(cupo.getId()) != null) continue;

            aceptados.insert(cupo.getId(), cupo);
            cuposRestantes--;
        }
    }

    public String listaEntera(){
        String[]listaCompleta=ordenPrioridad.listaInOrder(aceptados);
        return "***Estudiantes Admitidos***:\n"+listaCompleta[0]+
        "\n***Estudiantes no admitidos:\n***"+listaCompleta[1];
    }

    public boolean estadoFinal(long idEstudiante){
        return aceptados.get(idEstudiante) != null;
    }

    public boolean eliminarEstudiante(long id) {

        // 1. Buscar estudiante en la HashTable (acceso O(1))
        Estudiante estudiante = (Estudiante) estudiantesPorID.get(id);


        if (estudiante == null) {
            System.out.println(" No existe un estudiante con ID " + id);
            return false;
        }

        // 2. Verificar si estaba en el HEAP y eliminarlo
        boolean estaEnHeap = estudiante.getIndexHeap() != -1;
        boolean heapOK = true;;
        if (estaEnHeap) {
            heapOK = colaPrioridad.remove(estudiante);
        }


        // 3. Eliminar del AVL
        boolean avlOK = ordenPrioridad.remove(estudiante);

        // 4. Eliminar de la tabla hash
        aceptados.remove(id); // En caso de que esté, lo elimina sin problema
        boolean hashOK = estudiantesPorID.remove(id);

        // 5. Verificar si fue aceptado antes de eliminar
        aceptados.remove(id);
        //// 6. Si fue aceptado, reasignar cupo
         
        //
        //if (fueAceptado) {
        //    aceptados.remove(id);
//
        //    System.out.println("Reasignando cupo tras eliminación de estudiante aceptado...");
        //    cupos++; // Incrementar cupo temporalmente
        //    asigCupos();
        //    cupos--; // Devolver al valor original
        //}


        System.out.println("\n--- RESULTADOS ELIMINACIÓN ---");
        System.out.println("AVL:  " + avlOK);
        System.out.println("HEAP: " + heapOK);
        System.out.println("HASH: " + hashOK);

        return heapOK && avlOK && hashOK;
    }


    public String listarEstudiantesPorPrioridad(){
    return ordenPrioridad.listarPrioridad();
}



}
