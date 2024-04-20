package team.haedal.gifticionfunding.repository.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import team.haedal.gifticionfunding.domain.Vendor;
import team.haedal.gifticionfunding.domain.VendorUserInfo;
import team.haedal.gifticionfunding.entity.user.User;
import team.haedal.gifticionfunding.repository.user.UserJpaRepository;

@Repository
@RequiredArgsConstructor
public class CredentialsClient implements OAuthClient {
    private final UserJpaRepository userJpaRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public boolean canHandle(Vendor vendor) {
        return Vendor.CREDENTIALS.equals(vendor);
    }

    /**
     * code는 username, password를 base64로 인코딩한 값이다.
     * code를 디코딩하여 username, password를 추출하고
     * email로 사용자를 조회하여 password가 일치하는지 확인한다.
     * 이후 사용자를 식별하기 위해 vendorEmail을 반환한다. <br>
     * 다른 oAuthClient에서는 accessToken으로 리소스서버와 통신하지만 <br>
     * credentials는 서비스의 자체 로그인이므로 [vendorEmail]를 반환하여
     * 함수 [getVendorUserInfo]에서는 입력값을 그대로 반환한다.
     */
    @Transactional(readOnly = true)
    @Override
    public String getAccessToken(String code) {
        String decoded = new String(java.util.Base64.getDecoder().decode(code));
        String[] split = decoded.split(":");
        String email = split[0];
        String password = split[1];
        return userJpaRepository.findByEmail(email)
                .filter(user -> bCryptPasswordEncoder.matches(password, user.getPassword()))
                .map(User::getVendorEmail)
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
    }

    /**
     * credentials는 서비스의 자체 로그인이므로 [vendorEmail]를 입력받아
     * 그대로 VendorUserInfo를 반환한다.
     * @see team.haedal.gifticionfunding.repository.auth.CredentialsClient#getAccessToken(String)
     */
    @Transactional(readOnly = true)
    @Override
    public VendorUserInfo getVendorUserInfo(String accessToken) {
        return VendorUserInfo.builder()
                .vendor(Vendor.CREDENTIALS)
                .vendorEmail(accessToken)
                .build();
    }
}
