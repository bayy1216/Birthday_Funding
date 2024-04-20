package team.haedal.gifticionfunding.repository.auth;

import org.springframework.stereotype.Repository;
import team.haedal.gifticionfunding.domain.Vendor;
import team.haedal.gifticionfunding.domain.VendorUserInfo;

@Repository
public class KakaoClient implements OAuthClient{

    @Override
    public boolean canHandle(Vendor vendor) {
        return Vendor.KAKAO.equals(vendor);
    }

    @Override
    public String getAccessToken(String code) {
        return null;
    }

    @Override
    public VendorUserInfo getVendorUserInfo(String accessToken) {
        return null;
    }
}
