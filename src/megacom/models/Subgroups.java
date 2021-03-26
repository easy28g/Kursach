package megacom.models;

public class Subgroups {
    private String subgroupName;
    private int id_subgroups;

    public Subgroups(String subgroupName, int id_subgroups) {
        this.subgroupName = subgroupName;
        this.id_subgroups = id_subgroups;
    }

    public String getSubgroupName() {
        return subgroupName;
    }

    public void setSubgroupName(String subgroupName) {
        this.subgroupName = subgroupName;
    }

    public int getId_subgroups() {
        return id_subgroups;
    }

    public void setId_subgroups(int id_subgroups) {
        this.id_subgroups = id_subgroups;
    }
}
