package ru.max0l0gy.eshop.service;

import ru.max0l0gy.eshop.dto.PortfolioDto;
import ru.max0l0gy.eshop.entity.Portfolio;
import ru.max0l0gy.eshop.entity.PortfolioImage;
import ru.max0l0gy.eshop.repository.PortfolioImageRepository;
import ru.max0l0gy.eshop.repository.PortfolioRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class PortfolioService {
    @Inject
    PortfolioRepository portfolioRepository;
    @Inject
    PortfolioImageRepository portfolioImageRepository;

    public List<PortfolioDto> list() {
        return portfolioRepository.listAll()
                .stream()
                .map(PortfolioDto::of)
                .collect(Collectors.toList());
    }

    @Transactional
    public PortfolioDto persist(PortfolioDto portfolioToSave) {
        Portfolio portfolio = new Portfolio()
                .setDescription(portfolioToSave.getDescription())
                .setName(portfolioToSave.getName())
                .setShortDescription(portfolioToSave.getShortDescription());

        portfolioRepository.persist(
                portfolio.setImages(createImageList(portfolio, portfolioToSave.getImages()))
        );
        return PortfolioDto.of(portfolio);
    }


    private List<PortfolioImage> createImageList(Portfolio portfolio, List<String> imagesUrls) {
        short order = 0;
        List<PortfolioImage> images = new ArrayList<>();
        for (String imagesUrl : imagesUrls) {
            images.add(
                    new PortfolioImage()
                            .setPortfolio(portfolio)
                            .setImageOrder(order++)
                            .setUri(imagesUrl)
            );
        }
        return images;
    }

    @Transactional
    public PortfolioDto update(PortfolioDto portfolioDto) {
        return portfolioRepository.findByIdOptional(portfolioDto.getId())
                .map(portfolio -> updateAttributes(portfolio, portfolioDto))
                .map(portfolio -> {
                    portfolioRepository.persist(portfolio);
                    return portfolio;
                })
                .map(PortfolioDto::of)
                .orElse(portfolioDto);
    }

    private Portfolio updateAttributes(Portfolio portfolioToUpdate, PortfolioDto updateSrc) {
        portfolioToUpdate.getImages().forEach(portfolioImage -> {
            portfolioImage.setPortfolio(null);
        });
        portfolioToUpdate.getImages().clear();
        portfolioToUpdate.getImages().addAll(createImageList(portfolioToUpdate, updateSrc.getImages()));
        return portfolioToUpdate
                .setDescription(updateSrc.getDescription())
                .setName(updateSrc.getName())
                .setShortDescription(updateSrc.getShortDescription())
                ;
    }

    public Optional<PortfolioDto> find(Long id) {
        return portfolioRepository.findByIdOptional(id)
                .map(PortfolioDto::of);
    }

    @Transactional
    public boolean delete(Long id) {
        return portfolioRepository.deleteById(id);
    }
}

