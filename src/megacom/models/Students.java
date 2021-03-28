package megacom.models;

public class Students {

    private String firstname;
    private String secondname;
    private int id_subgroup;

    public Students(String firstname, String secondname, int id_subgroup) {
        this.firstname = firstname;
        this.secondname = secondname;
        this.id_subgroup = id_subgroup;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSecondname() {
        return secondname;
    }

    public void setSecondname(String secondname) {
        this.secondname = secondname;
    }

    public int getId_subgroup() {
        return id_subgroup;
    }

    public void setId_subgroup(int id_subgroup) {
        this.id_subgroup = id_subgroup;
    }
}
