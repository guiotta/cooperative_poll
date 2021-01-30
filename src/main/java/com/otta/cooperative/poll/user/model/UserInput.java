package com.otta.cooperative.poll.user.model;

import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserInput {
    @Size(min = 11, max = 11, message = "Document must be a CPF with 11 characters.")
    private String document;
    @NotBlank
    private String name;
    @NotBlank
    private String password;

    public UserInput() { }

    public UserInput(String document, String name, String password) {
        this.document = document;
        this.name = name;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int hashCode() {
        return Objects.hash(document, name, password);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof UserInput)) {
            return false;
        }
        UserInput other = (UserInput) obj;
        return Objects.equals(document, other.document) && Objects.equals(name, other.name)
                && Objects.equals(password, other.password);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("UserInput [document=");
        builder.append(document);
        builder.append(", name=");
        builder.append(name);
        builder.append("]");
        return builder.toString();
    }

}
