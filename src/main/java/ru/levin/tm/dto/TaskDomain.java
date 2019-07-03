package ru.levin.tm.dto;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import ru.levin.tm.entity.Task;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "tasks")
public class TaskDomain implements Serializable {

    private static final long serialVersionUID = 1534405118018965471L;

    @NotNull
    @Getter
    @XmlElement(name = "task")
    private final List<Task> tasks = new ArrayList<>();

}
