import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.stream.*;

public class Main {
    static List<Student> estudiantes;

    public static void main(String[] args) throws IOException {
        cargarArchivo();
        mostrarEstudiantesPorCarreraYTotal();
        cantMujeresPorCarrera();
        cantHombresPorCarrera();
        puntajeMasAltoPorCarrera();
        estudianesConMayorPuntaje();
        puntajePromedioPorCarrera();
    }

    static void cargarArchivo() throws IOException{
        Pattern pattern =Pattern.compile(",");
        String filename= "student-scores.csv";

        try(Stream<String> lines = Files.lines(Path.of(filename))){
            estudiantes=lines.skip(1).map(line->{
                String[]arr=pattern.split(line);
                return new Student(Integer.parseInt(arr[0]) ,Integer.parseInt(arr[10]), arr[1], arr[2], arr[4], arr[9]);
            }).collect(Collectors.toList());
            // estudiantes.forEach(System.out::println);
        }
    }

    static void mostrarEstudiantesPorCarreraYTotal(){
        System.out.printf("%nEstudiantes por carrera:%n");
        Map<String, List<Student>> agrupadoPorCarrera =
                estudiantes.stream()
                        .collect(Collectors.groupingBy(Student::getCareer));


        Map<String, Long> conteoEstudiantesPorCarrera =
                estudiantes.stream()
                        .collect(Collectors.groupingBy(Student::getCareer,
                                TreeMap::new, Collectors.counting()));

        agrupadoPorCarrera.forEach(
                (carrera, estudianteEnCarrera) ->
                {
                    // System.out.println("- " + carrera);
                    estudianteEnCarrera.forEach(
                            estudiante -> System.out.printf(" %s%n", estudiante));

                    conteoEstudiantesPorCarrera.forEach(
                            (car, conteo) -> {
                                if (carrera.equals(car)) { System.out.printf("- %s tiene: %d estudiantes(s)%n", car, conteo); }
                            });
                }
        );

        /*
        conteoEstudiantesPorCarrera.forEach(
                (carrera, conteo) -> System.out.printf(
                        "%s tiene %d empleado(s)%n", carrera, conteo));

         */
    }

    static void cantMujeresPorCarrera() {
        System.out.printf("%nConteo de mujeres por carrera:%n");

        Map<String, Long> conteoMujeresPorCarrera =
                estudiantes.stream().filter(e -> Objects.equals(e.getGender(), "female"))
                        .collect(Collectors.groupingBy(Student::getCareer,
                                TreeMap::new, Collectors.counting()));

        conteoMujeresPorCarrera.forEach(
                (carrera, conteo) -> {
                    System.out.printf("- %s tiene %d mujeres%n", carrera, conteo);
                }
        );
    }

    static void cantHombresPorCarrera() {
        System.out.printf("%nConteo de hombres por carrera:%n");

        Map<String, Long> conteoHombresPorCarrera =
                estudiantes.stream().filter(e -> Objects.equals(e.getGender(), "male"))
                        .collect(Collectors.groupingBy(Student::getCareer,
                                TreeMap::new, Collectors.counting()));

        conteoHombresPorCarrera.forEach(
                (carrera, conteo) -> {
                    System.out.printf("- %s tiene %d hombres%n", carrera, conteo);
                }
        );
    }

    static void puntajeMasAltoPorCarrera(){
        Function<Student, Integer> porSalario = Student::getMathScore;
        Comparator<Student> SalarioDescendete =
                Comparator.comparing(porSalario);
        System.out.printf("%nEstudiantes con puntaje más alto por carrera: %n");
        Map<String, List<Student>> agrupadoPorDepartamento =
                estudiantes.stream()
                        .collect(Collectors.groupingBy(Student::getCareer));
        agrupadoPorDepartamento.forEach(
                (departamento, empleadosEnDepartamento) ->
                {
                    System.out.print("- " + departamento+": ");
                    Student empleadoMas=empleadosEnDepartamento.stream().sorted(SalarioDescendete.reversed())
                            .findFirst()
                            .get();
                    System.out.println(empleadoMas.getFirstName()+" "+empleadoMas.getLastName()+
                            " sacó: "+empleadoMas.getMathScore()+" puntos");
                }
        );
    }

    static void estudianesConMayorPuntaje(){
        System.out.println("\nEstudiantes con puntaje más alto: ");

        Function<Student, Integer> porSalario = Student::getMathScore;
        Comparator<Student> SalarioDescendete =
                Comparator.comparing(porSalario);

        Map<String, List<Student>> agrupadoPorCarrera =
                estudiantes.stream().filter(e -> e.getMathScore() == 100)
                        .collect(Collectors.groupingBy(Student::getCareer));

        agrupadoPorCarrera.forEach(
                (carrera, estudiantesEnCarrera) ->
                {
                    Student estudianteMas=estudiantesEnCarrera.stream().sorted(SalarioDescendete.reversed())
                            .findFirst()
                            .get();
                    System.out.println("- "+estudianteMas.getFirstName()+" "+estudianteMas.getLastName()+
                            " sacó: "+estudianteMas.getMathScore()+" puntos");
                }
        );
    }

    static void puntajePromedioPorCarrera(){
        Map<String, List<Student>> agrupadoPorCarrera =
                estudiantes.stream()
                        .collect(Collectors.groupingBy(Student::getCareer));

        System.out.println("\nPuntaje promedio por carrera:");
        agrupadoPorCarrera.forEach((carrera, estudiante)-> {
            System.out.print("- "+carrera+": ");
            System.out.printf("%.2f%n", estudiante
                    .stream()
                    .mapToDouble(Student::getMathScore)
                    .average()
                    .getAsDouble());
        });
    }


}