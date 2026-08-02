package com.mathverse.core.service;

import com.mathverse.core.entity.RefreshToken;
import com.mathverse.core.entity.User;
import com.mathverse.core.exception.RefreshTokenNotFoundException;
import com.mathverse.core.exception.TokenExpiredException;
import com.mathverse.core.repository.RefreshTokenRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RefreshTokenServiceTest {

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @InjectMocks
    private RefreshTokenService refreshTokenService;

    @Test
    void createRefreshToken_shouldDeleteOldTokenAndSaveNewOne() {
        User user = new User();
        user.setId(1L);

        when(refreshTokenRepository.save(any(RefreshToken.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        RefreshToken result = refreshTokenService.createRefreshToken(user);

        verify(refreshTokenRepository).deleteByUser(user);
        ArgumentCaptor<RefreshToken> captor = ArgumentCaptor.forClass(RefreshToken.class);
        verify(refreshTokenRepository).save(captor.capture());

        assertThat(captor.getValue().getUser()).isEqualTo(user);
        assertThat(captor.getValue().getToken()).isNotBlank();
        assertThat(captor.getValue().getExpiryDate()).isAfter(LocalDateTime.now().plusDays(6));
        assertThat(result.getUser()).isEqualTo(user);
    }

    @Test
    void verifyExpiration_shouldReturnToken_whenNotExpired() {
        RefreshToken token = new RefreshToken();
        token.setToken("valid-token");
        token.setExpiryDate(LocalDateTime.now().plusDays(1));

        when(refreshTokenRepository.findByToken("valid-token")).thenReturn(Optional.of(token));

        RefreshToken result = refreshTokenService.verifyExpiration("valid-token");

        assertThat(result).isEqualTo(token);
        verify(refreshTokenRepository, never()).delete(any());
    }

    @Test
    void verifyExpiration_shouldThrowAndDeleteToken_whenExpired() {
        RefreshToken token = new RefreshToken();
        token.setToken("expired-token");
        token.setExpiryDate(LocalDateTime.now().minusDays(1));

        when(refreshTokenRepository.findByToken("expired-token")).thenReturn(Optional.of(token));

        assertThatThrownBy(() -> refreshTokenService.verifyExpiration("expired-token"))
                .isInstanceOf(TokenExpiredException.class)
                .hasMessage("Refresh-токен истёк, войдите заново");

        verify(refreshTokenRepository).delete(token);
    }

    @Test
    void verifyExpiration_shouldThrow_whenTokenNotFound() {
        when(refreshTokenRepository.findByToken("unknown")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> refreshTokenService.verifyExpiration("unknown"))
                .isInstanceOf(RefreshTokenNotFoundException.class)
                .hasMessage("Refresh-токен не найден");
    }
}