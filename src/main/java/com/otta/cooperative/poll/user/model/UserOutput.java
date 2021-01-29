package com.otta.cooperative.poll.user.model;

import java.util.Objects;

public class UserOutput {
    private Long id;
    private String document;
    private String name;

    public UserOutput() {
    }

    public UserOutput(Long id, String document, String name) {
        this.id = id;
        this.document = document;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(document, id, name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof UserOutput)) {
            return false;
        }
        UserOutput other = (UserOutput) obj;
        return Objects.equals(document, other.document) && Objects.equals(id, other.id)
                && Objects.equals(name, other.name);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("UserOutput [id=");
        builder.append(id);
        builder.append(", document=");
        builder.append(document);
        builder.append(", name=");
        builder.append(name);
        builder.append("]");
        return builder.toString();
    }

}
