package com.disney.demo.model;


import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "question")
public class Question extends Auditable {
}
