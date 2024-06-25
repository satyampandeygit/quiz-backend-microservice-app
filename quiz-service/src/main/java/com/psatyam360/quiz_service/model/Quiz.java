package com.psatyam360.quiz_service.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Entity
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer quiz_id;
    private String title;

    @ElementCollection
    private List<Integer> questionIds;

}
