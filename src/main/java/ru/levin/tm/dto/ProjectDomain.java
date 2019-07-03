package ru.levin.tm.dto;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import ru.levin.tm.entity.Project;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "projects")
public class ProjectDomain implements Serializable {

    private static final long serialVersionUID = 1609688004232673037L;

    @NotNull
    @Getter
    @XmlElement(name = "project")
    private final List<Project> projects = new ArrayList<>();

}
