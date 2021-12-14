package com.op.boot.demo.mall;

import com.op.boot.mall.constants.MallType;
import com.op.boot.mall.token.granter.MallTokenGranterChain;
import com.op.boot.mall.token.request.TokenAcquireRequest;
import com.op.boot.mall.token.request.TokenRefreshRequest;
import com.op.boot.mall.token.response.MallTokenResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class MallTokenGranterTest {
    @Autowired
    private MallTokenGranterChain mallTokenGranterChain;

    @Test
    public void refreshJdToken() {
        TokenRefreshRequest request = new TokenRefreshRequest();
        request.setAccountName("保利总部VOP1");
        request.setRefreshToken("cf01d0e7bf9e4cadbc2a1e9ee808aa63ztdi");
        request.setAppKey("A7BEA9155DA7355D6B4B811742349630");
        request.setAppSecret("f7ed5ed813124c72a91fef3d25167afc");
        MallTokenResponse mallTokenResponse = mallTokenGranterChain.refreshToken(MallType.JD, request);

        log.info("mallTokenResponse【{}】", mallTokenResponse);
    }

    @Test
    public void acquireJdBillToken() {
        TokenAcquireRequest request = new TokenAcquireRequest();
        request.setAccountName("保利vop007");
        request.setPassword("jd123456");
        request.setAppKey("TU5YHNnLF9Fin8qnzZs9");
        request.setAppSecret("8RkkLzFSQlVDx9V0F3sZ");
        Optional<MallTokenResponse> mallTokenResponse = mallTokenGranterChain.acquireToken(MallType.JD_BILL, request);
        log.info("mallTokenResponse【{}】", mallTokenResponse.orElse(null));
    }

    @Test
    public void refreshJdBillToken() {
        TokenRefreshRequest request = new TokenRefreshRequest();
        request.setAccountName("保利vop007");
        request.setRefreshToken("sse7XWEc1YN6EqiUnk601ZbOP5DxNq3A1GWtOGjfgv");
        request.setAppKey("TU5YHNnLF9Fin8qnzZs9");
        request.setAppSecret("8RkkLzFSQlVDx9V0F3sZ");
        MallTokenResponse mallTokenResponse = mallTokenGranterChain.refreshToken(MallType.JD_BILL, request);
        log.info("mallTokenResponse【{}】", mallTokenResponse);
    }
}
