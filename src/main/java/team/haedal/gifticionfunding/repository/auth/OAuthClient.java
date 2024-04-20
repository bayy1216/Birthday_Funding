package team.haedal.gifticionfunding.repository.auth;

import team.haedal.gifticionfunding.domain.Vendor;
import team.haedal.gifticionfunding.domain.VendorUserInfo;

public interface OAuthClient {
    boolean canHandle(Vendor vendor);
    String getAccessToken(String code);
    VendorUserInfo getVendorUserInfo(String accessToken);
}
