//package com.example.clicker.user;
//
//import com.example.clicker.messaging.confirmation.ConfirmationService;
//import com.example.clicker.security.core.models.token.models.Token;
//import com.example.clicker.security.core.models.token.models.TokenUserDetails;
//import com.example.clicker.user.models.dto.PublicUserResponseDto;
//import com.example.clicker.user.models.dto.UserRegistrationDto;
//import com.example.clicker.user.models.dto.UserResponseDto;
//import com.example.clicker.user.models.dto.UserUpdateDto;
//import com.example.clicker.wallets.models.dto.WalletResponseDto;
//import com.example.clicker.wallets.models.dto.WalletUpdateDto;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.time.Instant;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Set;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.*;
//
//
//@ExtendWith(MockitoExtension.class)
//class UserControllerUnitTest {
//
//    @Mock
//    private UserFacade userFacade;
//
//    @Mock
//    private ConfirmationService confirmationService;
//
//    @InjectMocks
//    private UserController userController;
//
//    private final TokenUserDetails tokenUserDetails = new TokenUserDetails(1L, null, List.of(), new Token());
//
//    @Test
//    void createUser_whenValidRequest_shouldReturn201() {
//        UserRegistrationDto userRegistrationDto = new UserRegistrationDto("test@example.com", "password12");
//        doNothing().when(userFacade).save(userRegistrationDto);
//        doNothing().when(confirmationService).sendConfirmationMessage(userRegistrationDto.getEmail());
//
//        ResponseEntity<?> result = userController.createUser(userRegistrationDto);
//
//        assertEquals(HttpStatus.CREATED, result.getStatusCode());
//        verify(userFacade, times(1)).save(any(UserRegistrationDto.class));
//        verifyNoMoreInteractions(userFacade);
//        verify(confirmationService, times(1)).sendConfirmationMessage(eq("test@example.com"));
//        verifyNoMoreInteractions(confirmationService);
//    }
//
//    @Test
//    void getUser_whenExist_shouldReturn200AndUserResponseDto() {
//        UserResponseDto userResponseDto = new UserResponseDto(
//                1L,
//                "test@example.com",
//                Set.of(new WalletResponseDto(1L, "BTC", "test_wallet_address")),
//                "test_img_url",
//                Instant.now().toString()
//        );
//        doReturn(userResponseDto).when(userFacade).findById(1L);
//
//        ResponseEntity<?> result = userController.getUser(tokenUserDetails);
//
//        assertEquals(HttpStatus.OK, result.getStatusCode());
//        assertEquals(userResponseDto, result.getBody());
//        verify(userFacade, times(1)).findById(any());
//        verifyNoMoreInteractions(userFacade);
//    }
//
//    @Test
//    void getUserById_whenExist_shouldReturn200AndUserResponseDto() {
//        UserResponseDto userResponseDto = new UserResponseDto(
//                1L,
//                "test@example.com",
//                Set.of(new WalletResponseDto(1L, "BTC", "test_wallet_address")),
//                "test_img_url",
//                Instant.now().toString()
//        );
//        doReturn(userResponseDto).when(userFacade).findById(1L);
//
//        ResponseEntity<?> result = userController.getUserById(1L);
//
//        assertEquals(HttpStatus.OK, result.getStatusCode());
//        assertEquals(userResponseDto, result.getBody());
//        verify(userFacade, times(1)).findById(anyLong());
//        verifyNoMoreInteractions(userFacade);
//    }
//
//    @Test
//    void updateUser_whenExist_shouldReturn200() {
//        UserUpdateDto userUpdateDto = new UserUpdateDto(
//                "test@example.com",
//                "test@example.com",
//                List.of(new WalletUpdateDto(1L, 1L, "test_wallet_address")),
//                "test_img_url"
//        );
//        doNothing().when(userFacade).update(1L, userUpdateDto);
//
//        ResponseEntity<?> result = userController.updatedUser(tokenUserDetails, userUpdateDto);
//
//        assertEquals(HttpStatus.OK, result.getStatusCode());
//        verify(userFacade, times(1)).update(anyLong(), any(UserUpdateDto.class));
//        verifyNoMoreInteractions(userFacade);
//    }
//
//    @Test
//    void updateUserById_whenExist_shouldReturn200() {
//        UserUpdateDto userUpdateDto = new UserUpdateDto(
//                "test@example.com",
//                "test@example.com",
//                List.of(new WalletUpdateDto(1L, 1L, "test_wallet_address")),
//                "test_img_url"
//        );
//        doNothing().when(userFacade).update(1L, userUpdateDto);
//
//        ResponseEntity<?> result = userController.updatedUserById(1L, userUpdateDto);
//
//        assertEquals(HttpStatus.OK, result.getStatusCode());
//        verify(userFacade, times(1)).update(anyLong(), any(UserUpdateDto.class));
//        verifyNoMoreInteractions(userFacade);
//    }
//
//    @Test
//    void getAllUsers_whenExist_shouldReturn200() {
//        List<PublicUserResponseDto> users = new ArrayList<>(
//                List.of(
//                        new PublicUserResponseDto(1L, "test_img_url", Instant.now().toString()),
//                        new PublicUserResponseDto(2L, "test_img2_url", Instant.now().toString())
//                )
//        );
//        doReturn(users).when(userFacade).findAll();
//
//        ResponseEntity<?> result = userController.getAllUsers();
//
//        assertEquals(HttpStatus.OK, result.getStatusCode());
//        assertEquals(users, result.getBody());
//        verify(userFacade, times(1)).findAll();
//        verifyNoMoreInteractions(userFacade);
//    }
//
//    @Test
//    void deleteUser_whenExist_shouldReturn204() {
//        doNothing().when(userFacade).deleteByPassword(1L, "password");
//
//        ResponseEntity<?> result = userController.deleteUser(tokenUserDetails, "password");
//
//        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
//        verify(userFacade, times(1)).deleteByPassword(anyLong(), anyString());
//        verifyNoMoreInteractions(userFacade);
//    }
//
//    @Test
//    void deleteUserById_whenExist_shouldReturn204() {
//        doNothing().when(userFacade).deleteById(1L);
//
//        ResponseEntity<?> result = userController.deleteUserById(1L, "password");
//
//        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
//        verify(userFacade, times(1)).deleteById(anyLong());
//        verifyNoMoreInteractions(userFacade);
//    }
//}
