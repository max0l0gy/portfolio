package ru.max0l0gy.eshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "portfolio_image")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PortfolioImage implements Comparable<PortfolioImage> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String uri;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "portfolio_id", referencedColumnName = "id")
    private Portfolio portfolio;

    private Integer width;
    private Integer height;

    @Column(nullable = false, name = "image_order")
    private Short imageOrder;

    @Override
    public int compareTo(PortfolioImage commodityImage) {
        return Short.compare(getImageOrder(), commodityImage.getImageOrder());
    }
}
