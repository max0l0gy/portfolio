package ru.max0l0gy.eshop.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static ru.max0l0gy.eshop.entity.Constants.ID_GENERATOR_PORTFOLIO;
import static ru.max0l0gy.eshop.entity.Constants.ID_GENERATOR_PORTFOLIO_IMAGE;
import static ru.max0l0gy.eshop.entity.Constants.ID_GENERATOR_PORTFOLIO_IMAGE_SEQUENCE_NAME;
import static ru.max0l0gy.eshop.entity.Constants.ID_GENERATOR_PORTFOLIO_SEQUENCE_NAME;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "portfolio")
@Accessors(chain = true)
public class Portfolio {

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_of_creation", nullable = false, updatable = false)
    @org.hibernate.annotations.CreationTimestamp
    protected Date dateOfCreation;

    @Id
    @NotNull
    @SequenceGenerator(
            name=ID_GENERATOR_PORTFOLIO,
            sequenceName=ID_GENERATOR_PORTFOLIO_SEQUENCE_NAME
    )
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator=ID_GENERATOR_PORTFOLIO)
    private Long id;

    @NotBlank(message = "{validation.portfolio.name}")
    @Column(nullable = false, length = 256)
    private String name;

    @NotBlank(message = "{validation.portfolio.description}")
    @Column(nullable = false, length = 2048)
    private String description;

    @NotBlank(message = "{validation.portfolio.shortDescription")
    @Column(name="short_description",nullable = false, length = 1024)
    private String shortDescription;

    @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "portfolio", targetEntity = PortfolioImage.class, fetch = FetchType.EAGER)
    @org.hibernate.annotations.OrderBy(clause = "image_order asc")
    @org.hibernate.annotations.BatchSize(size = 10)
    protected List<PortfolioImage> images = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Portfolio portfolio = (Portfolio) o;
        return getDateOfCreation().equals(portfolio.getDateOfCreation()) && getId().equals(portfolio.getId()) && getName().equals(portfolio.getName()) && getDescription().equals(portfolio.getDescription()) && getShortDescription().equals(portfolio.getShortDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDateOfCreation(), getId(), getName(), getDescription(), getShortDescription());
    }
}
