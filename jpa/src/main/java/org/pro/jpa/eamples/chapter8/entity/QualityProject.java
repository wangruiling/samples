package org.pro.jpa.eamples.chapter8.entity;

import javax.persistence.Entity;
import javax.persistence.DiscriminatorValue;

@Entity
@DiscriminatorValue("QP")
public class QualityProject extends Project {
}
