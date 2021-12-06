package com.enojen.getir.service;

import com.enojen.getir.exception.RoleNotFoundException;
import com.enojen.getir.exception.UserAlreadyFoundException;
import com.enojen.getir.model.ERole;
import com.enojen.getir.model.Role;
import com.enojen.getir.model.User;
import com.enojen.getir.payload.request.LoginRequest;
import com.enojen.getir.payload.request.SignupRequest;
import com.enojen.getir.payload.response.BaseResponse;
import com.enojen.getir.payload.response.JwtResponse;
import com.enojen.getir.payload.response.MessageResponse;
import com.enojen.getir.repository.RoleRepository;
import com.enojen.getir.repository.UserRepository;
import com.enojen.getir.security.jwt.JwtUtils;
import com.enojen.getir.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder encoder;

    private final JwtUtils jwtUtils;

    public BaseResponse<JwtResponse> signIn(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        JwtResponse jwtResponse = new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);


        return new BaseResponse<>(jwtResponse);
    }

    public BaseResponse<?> registerUser(SignupRequest signUpRequest) {

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new UserAlreadyFoundException();
        }
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new UserAlreadyFoundException();
        }

        User user = User.builder()
                .username(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .password(encoder.encode(signUpRequest.getPassword()))
                .build();

        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_CUSTOMER)
                    .orElseThrow(RoleNotFoundException::new);
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                if ("admin".equals(role)) {
                    Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                            .orElseThrow(RoleNotFoundException::new);
                    roles.add(adminRole);
                } else {
                    Role userRole = roleRepository.findByName(ERole.ROLE_CUSTOMER)
                            .orElseThrow(RoleNotFoundException::new);
                    roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return new BaseResponse<>(new MessageResponse("User registered successfully!"));
    }
}
