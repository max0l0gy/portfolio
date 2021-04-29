package ru.max0l0gy.eshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Objects;

import static ru.max0l0gy.eshop.entity.Constants.ID_GENERATOR_PORTFOLIO_IMAGE;
import static ru.max0l0gy.eshop.entity.Constants.ID_GENERATOR_PORTFOLIO_IMAGE_SEQUENCE_NAME;

@Getter
@Setter
@Entity
@Accessors(chain = true)
@Table(name = "portfolio_image")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PortfolioImage implements Comparable<PortfolioImage> {
    @Id
    @NotNull
    @SequenceGenerator(
            name=ID_GENERATOR_PORTFOLIO_IMAGE,
            sequenceName=ID_GENERATOR_PORTFOLIO_IMAGE_SEQUENCE_NAME
    )
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator=ID_GENERATOR_PORTFOLIO_IMAGE)
    private Long id;

    @Column(nullable = false)
    private String uri;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "portfolio_id", referencedColumnName = "id")
    private Portfolio portfolio;

    @Column(nullable = false, name = "image_order")
    private Short imageOrder;

    @Override
    public int compareTo(PortfolioImage commodityImage) {
        return Short.compare(getImageOrder(), commodityImage.getImageOrder());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PortfolioImage that = (PortfolioImage) o;
        return Objects.equals(getId(), that.getId()) && getUri().equals(that.getUri()) && getPortfolio().equals(that.getPortfolio()) && getImageOrder().equals(that.getImageOrder());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUri(), getPortfolio(), getImageOrder());
    }
}
