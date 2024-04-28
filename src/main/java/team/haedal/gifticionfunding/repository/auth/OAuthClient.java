package team.haedal.gifticionfunding.repository.auth;

import team.haedal.gifticionfunding.domain.user.Vendor;
import team.haedal.gifticionfunding.domain.user.VendorUserInfo;

public interface OAuthClient {
    boolean canHandle(Vendor vendor);
    String getAccessToken(String code);
    VendorUserInfo getVendorUserInfo(String accessToken);
}
