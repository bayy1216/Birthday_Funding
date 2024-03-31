package team.haedal.gifticionfunding.dto.gifticon.request;

import org.springframework.data.domain.PageRequest;
import team.haedal.gifticionfunding.domain.GifticonSearch;
import team.haedal.gifticionfunding.domain.GifticonSort;

public record GifticonsQuery(
        int page,
        int size,

        Integer priceMax,
        Integer priceMin,
        String name,
        String category,
        String brand,
        GifticonSort gifticonSort
) {
    public PageRequest toPageRequest() {
        return PageRequest.of(page, size);
    }
    public GifticonSearch toSearch() {
        return GifticonSearch.builder()
                .priceMax(priceMax)
                .priceMin(priceMin)
                .name(name)
                .category(category)
                .brand(brand)
                .gifticonSort(gifticonSort)
                .build();
    }
}
