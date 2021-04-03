package ru.max0l0gy.eshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Getter
@Setter
@Entity
@Accessors(chain = true)
@Table(name = "commodity_image")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PortfolioImage  implements Comparable<PortfolioImage> {
    @Id
    @Column(name = "uri", nullable = false, unique = true)
    private String uri;

    @ManyToOne(optional = false)
    @JoinColumn(name = "portfolio_id", referencedColumnName = "id")
    @JsonIgnore
    private Portfolio portfolio;

    private Integer width;
    private Integer height;

    @Column(nullable = false, name = "image_order")
    private Short imageOrder;

    @Override
    public int hashCode() {
        return Objects.hash(uri, portfolio);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof PortfolioImage)) return false;
        PortfolioImage that = (PortfolioImage) object;
        return getUri().equals(that.getUri());
    }

    @Override
    public int compareTo(PortfolioImage commodityImage) {
        return Short.compare(getImageOrder(), commodityImage.getImageOrder());
    }
}
