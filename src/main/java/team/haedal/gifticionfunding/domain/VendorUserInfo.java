package team.haedal.gifticionfunding.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VendorUserInfo {
    private final String vendorEmail;
    private final Vendor vendor;
}
