public class Student {

    // atributes
    private int id;
    private String name;
    private String last_name;
    private String gender;
    private String career;
    private Double score;

    // constructor
    public Student(int id, String name, String last_name,String gender, String career, Double score) {
        this.id = id;
        this.name = name;
        this.last_name = last_name;
        this.gender = gender;
        this.career = career;
        this.score = score;
    }

    // getters & setters }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", gender='" + gender + '\'' +
                ", career='" + career + '\'' +
                ", score=" + score +
                '}';
    }
}
