package ru.max0l0gy.eshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@Entity
@Accessors(chain = true)
@Table(name = "portfolio_image")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PortfolioImage implements Comparable<PortfolioImage> {
    @Id
    @GeneratedValue(generator = Constants.ID_GENERATOR_PORTFOLIO_IMAGE)
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
