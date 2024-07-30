package projectlink.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import projectlink.models.AppUser;
import projectlink.repositories.AppUserRepository;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final AppUserRepository appUserRepository;
    private final UserDetailsService userDetailsService;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager,
                                      AppUserRepository appUserRepository,
                                      UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.appUserRepository = appUserRepository;
        this.userDetailsService = userDetailsService;
    }

    //  When user tries to log in
    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        TypeReference<Map<String, String>> typeReference = new TypeReference<>() {};
        Map<String, String> requestBody = new ObjectMapper().readValue(request.getInputStream(), typeReference);
        String username = requestBody.get("username");
        String password = requestBody.get("password");

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), password);
        return authenticationManager.authenticate(authenticationToken);
    }

    //  When login is successful
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication
    ) throws IOException {
        User user = (User) authentication.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        String accessToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);

        AppUser appUser = appUserRepository.findByUsername(user.getUsername());

        Map<String, String> sessionDetails = new HashMap<>();
        sessionDetails.put("access_token", accessToken);
        sessionDetails.put("firstName", appUser.getFirstName());
        sessionDetails.put("lastName", appUser.getLastName());
        sessionDetails.put("username", appUser.getUsername());
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), sessionDetails);
    }
}