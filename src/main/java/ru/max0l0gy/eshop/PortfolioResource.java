package ru.max0l0gy.eshop;

import ru.max0l0gy.eshop.dto.PortfolioDto;
import ru.max0l0gy.eshop.dto.PortfolioResponse;
import ru.max0l0gy.eshop.service.PortfolioService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/api/v1/portfolios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PortfolioResource {
    @Inject
    PortfolioService portfolioService;

    @GET
    public PortfolioResponse<List<PortfolioDto>> list() {
        return new PortfolioResponse<>("success", portfolioService.list());
    }

    @POST
    @Transactional
    public PortfolioResponse<PortfolioDto> add(PortfolioDto portfolioToSave) {
        return new PortfolioResponse<>("success", portfolioService.persist(portfolioToSave));
    }

    @PUT
    @Transactional
    public PortfolioResponse<PortfolioDto> update(PortfolioDto portfolioDto) {
        return new PortfolioResponse<>("success", portfolioService.update(portfolioDto));
    }

    @GET
    @Path("{id}")
    public PortfolioResponse<PortfolioDto> find(@PathParam("id") Long id) {
        return portfolioService.find(id)
                .map(portfolioDto -> new PortfolioResponse<>("success", portfolioDto))
                .orElse(fail());
    }

    private PortfolioResponse<PortfolioDto> fail() {
        return new PortfolioResponse<>("fail", null);
    }
}
