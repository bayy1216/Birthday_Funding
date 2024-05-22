package team.haedal.gifticionfunding.domain.giftucon;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GifticonSearch {
    private final Integer priceMax;
    private final Integer priceMin;
    private final String name;
    private final String category;
    private final String brand;
    private final GifticonSort gifticonSort;
}
