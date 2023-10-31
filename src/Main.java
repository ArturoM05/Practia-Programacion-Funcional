import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException {
        cargarArchivo();
        mostrarEstudianteEnCarrera();
        mostrarPorGenero("female");
        cantEstudiantesPorCarrera("female");
        mostrarPorGenero("male");
        cantEstudiantesPorCarrera("male");
        MejorNotaPorCarrera();
        EstudianteConMejorNota();
        CantEstudiantesMejorNota();
        promNotaporCarrera();
    }

    static List<Student> students;
    // Carga el archivo
    static void cargarArchivo() throws IOException{
        Pattern pattern =Pattern.compile(",");
        String filename= "student-scores.csv";

        try(Stream<String> lines = Files.lines(Path.of(filename))){
            students =lines.skip(1).map(line->{
                String[]arr=pattern.split(line);
                return new Student(Integer.parseInt(arr[0]),arr[1],arr[2],arr[4],arr[9],Double.parseDouble(arr[10]));
            }).collect(Collectors.toList());
            //students.forEach(System.out::println); //Imprime todos los estudiantes
        }
    }

    // muestra los estudiantes por carrera
    static void mostrarEstudianteEnCarrera(){
        System.out.printf("%nEstudiantes por carrera:%n");
        Map<String, List<Student>> agrupadoPorCarrera =
                students.stream()
                        .collect(Collectors.groupingBy(Student::getCareer));
        agrupadoPorCarrera.forEach(
                (career, estudiantesEnCarrera) ->
                {
                    System.out.println("-------------------------"+career+"-------------------------");
                    estudiantesEnCarrera.forEach(
                            student -> System.out.printf(" %s%n", student));
                }
        );
    }

    // muestra los estudiantes por genero
    static void mostrarPorGenero(String genero){
        System.out.println("%nEstudiantes por carrera:%n");
        Map<String, List<Student>> agrupadoPorCarrera =
                students.stream().filter(student -> student.getGender().equals(genero))
                        .collect(Collectors.groupingBy(Student::getCareer));
        agrupadoPorCarrera.forEach(
                (career, estudiantesEnCarrera) ->
                {
                    System.out.println("-------------------------"+career+"-"+genero+"-------------------------");
                    estudiantesEnCarrera.forEach(student -> System.out.printf(" %s%n", student));
                }
        );

    }

    // muestra la cantidad de estudiantes por genero
    static void cantEstudiantesPorCarrera(String genero){
        Map<String, Long> agrupadoPorCarrera =
                students.stream()
                        .filter(student -> student.getGender().equals(genero)).collect(Collectors.groupingBy(Student::getCareer,Collectors.counting()));
        agrupadoPorCarrera.forEach(
                (career, estudiantesEnCarrera) ->
                {
                    System.out.println("-------------------------"+career+"-------------------------");
                    System.out.println("Total "+genero + " students: " + estudiantesEnCarrera);
                }
        );

    }

    // muestra la mejor nota por carrera
    static void MejorNotaPorCarrera(){
        Function<Student, Double> porScore = Student::getScore;
        Comparator<Student> mejoresNotas =
                Comparator.comparing(porScore);
        System.out.printf("%nMejor nota por Carrera: %n");
        Map<String, List<Student>> agrupadoPorCarrera =
                students.stream()
                        .collect(Collectors.groupingBy(Student::getCareer));
        agrupadoPorCarrera.forEach(
                (carrera, estudiantesPorCarrera ) ->
                {
                    System.out.print(carrera+": ");
                    Student MejorNota=estudiantesPorCarrera.stream().sorted(mejoresNotas.reversed())
                            .findFirst()
                            .get();
                    System.out.println(MejorNota.getName()+" "+MejorNota.getLast_name()+
                            " Nota: "+MejorNota.getScore());
                }
        );
    }

    // muestra el estudiante con la mejor nota
    static void EstudianteConMejorNota(){
        Map<Double, List<Student>> estudiantesPorNota = students.stream()
                .collect(Collectors.groupingBy(Student::getScore));

        estudiantesPorNota.keySet().stream()
                .max(Double::compare)
                .ifPresent(mejorNota -> {
                    List<Student> estudiantesConMejorNota = estudiantesPorNota.get(mejorNota);
                    estudiantesConMejorNota.forEach(estudiante -> {
                        System.out.println("Estudiante con la mejor puntuación:");
                        System.out.println(" - " + estudiante.getName()+ " " + estudiante.getLast_name() + " Nota: " + estudiante.getScore());
                    });
                });
    }

    // muestra la cantidad de estudiantes con la mejor nota
    static void CantEstudiantesMejorNota(){
        Map<Double, List<Student>> estudiantesPorNota = students.stream()
                .collect(Collectors.groupingBy(Student::getScore));

        long cantidadEstudiantesMaxNota = estudiantesPorNota.keySet().stream()
                .max(Double::compare)
                .map(maxNota -> estudiantesPorNota.get(maxNota).size())
                .orElse(Math.toIntExact(0L));

        System.out.println("Cantidad de estudiantes con la mejor nota: " + cantidadEstudiantesMaxNota);
    }

    // muestra el promedio de la nota de los estudiantes por carrera
    static void promNotaporCarrera(){
        Map<String, List<Student>> agrupadoPorCarrera =
                students.stream()
                        .collect(Collectors.groupingBy(Student::getCareer));
        System.out.println("\nPromedio de la nota de los estudiantes por Carrera:");
        agrupadoPorCarrera.forEach((carrera, estudiantesPorCarrera)-> {
            System.out.print(carrera+": ");
            System.out.printf("%f%n",estudiantesPorCarrera
                    .stream()
                    .mapToDouble(Student::getScore)
                    .average()
                    .getAsDouble());
        });


    }


}
