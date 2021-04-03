package ru.max0l0gy.eshop.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.max0l0gy.eshop.entity.Portfolio;
import ru.max0l0gy.eshop.entity.PortfolioImage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Accessors(chain = true)
public class PortfolioDto {
    protected Long id;

    private String name;

    private String description;

    private String shortDescription;

    protected List<String> images = new ArrayList<>();

    public static PortfolioDto of(Portfolio portfolio) {
        return new PortfolioDto()
                .setId(portfolio.getId())
                .setDescription(portfolio.getDescription())
                .setShortDescription(portfolio.getShortDescription())
                .setName(portfolio.getName())
                .setImages(
                        portfolio
                        .getImages()
                        .stream()
                        .sorted()
                        .map(PortfolioImage::getUri)
                        .collect(Collectors.toList())
                );

    }
}
