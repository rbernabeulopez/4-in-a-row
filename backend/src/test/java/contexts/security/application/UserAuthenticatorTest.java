package contexts.security.application;

import contexts.UnitTestsBase;
import contexts.security.infrastructure.configuration.JwtConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserAuthenticatorTest extends UnitTestsBase {

    @InjectMocks
    private UserAuthenticator userAuthenticator;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtConfig jwtConfig;

    private String jwtSecretKey;
    private int jwtExpirationTime;
    private String username;
    private String password;

    @BeforeEach
    void setUp() {
        jwtSecretKey = "eoisfiouewjklfaw3893qaojsdf98a9p23hd";
        jwtExpirationTime = 38400000;
        username = "foo";
        password = "bar";
    }

    @Test
    void willReturnJwtToken_WhenUserPasswordIsValid() {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);

        when(jwtConfig.getExpiration()).thenReturn(jwtExpirationTime);
        when(jwtConfig.getKey()).thenReturn(jwtSecretKey);
        when(authenticationManager.authenticate(any())).thenReturn(usernamePasswordAuthenticationToken);

        String authenticate = this.userAuthenticator.authenticate(usernamePasswordAuthenticationToken);

        assertThat(authenticate).isNotNull();
        verify(jwtConfig, times(1)).getExpiration();
        verify(jwtConfig, times(1)).getKey();
        verify(authenticationManager, times(1)).authenticate(any());
    }


    @Test
    void willThrowException_WhenUserPasswordIsInvalid() {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);

        when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException(""));

        assertThatThrownBy(() -> this.userAuthenticator.authenticate(usernamePasswordAuthenticationToken))
                .isInstanceOf(BadCredentialsException.class);
        verify(authenticationManager, times(1)).authenticate(any());
        verify(jwtConfig, never()).getExpiration();
        verify(jwtConfig, never()).getKey();
    }
}