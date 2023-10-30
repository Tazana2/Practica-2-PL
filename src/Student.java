public class Student {
    private int id, mathScore;
    private String firstName, lastName, gender, career;

    public Student(int id, int mathScore, String firstName, String lastName, String gender, String career) {
        this.id = id;
        this.mathScore = mathScore;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.career = career;
    }

    public int getId() { return this.id; }
    public int getMathScore() { return this.mathScore; }
    public String getFirstName() { return this.firstName; }
    public String getLastName() { return this.lastName; }
    public String getGender() { return this.gender; }
    public String getCareer() { return this.career; }
    public void setId(int id) { this.id = id; }
    public void setMathScore(int mathScore) { this.mathScore = mathScore; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setGender(String gender) { this.gender = gender; }
    public void setCareer(String career) { this.career = career; }

    @Override
    public String toString() {
        return "Estudiante{" +
                "id=" + this.id +
                ", primerNombre='" + this.firstName + '\'' +
                ", apellidoPaterno='" + this.lastName + '\'' +
                ", genero=" + this.gender +
                ", carrera='" + this.career +
                ", Puntaje matemáticas=" + this.mathScore + '\'' +
                '}';
    }
}
