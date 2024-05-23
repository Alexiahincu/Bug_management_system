package org.example.Domain;

public class Bug extends Entity<Integer>{
    private String name;
    private String description;
    private BugStatus status;

    public Bug(Integer id, String name, String description, BugStatus status) {
        super(id);
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BugStatus getStatus() {
        return status;
    }

    public void setStatus(BugStatus status) {
        this.status = status;
    }

    public String toString(){
        return "Bug { id: " + id
                + ", name:" + name
                + ", description: " + description
                + ", status: " + status.toString()
                + " }";
    }
}
