package edu.au.cpsc.checkpoint4;

public class Query {

    private String category;
    private String description;
    private String statement;

    public Query(String category, String description, String statement) {
        this.category = category;
        this.description = description;
        this.statement = statement;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }
}
