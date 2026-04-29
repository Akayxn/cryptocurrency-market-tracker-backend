package com.akayxn.cryptocurrencymarkettracker.configuration;

import com.akayxn.cryptocurrencymarkettracker.model.User;
import com.akayxn.cryptocurrencymarkettracker.service.JwtService;
import com.akayxn.cryptocurrencymarkettracker.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

//      so basically we find the header which contains the name Authorization
        String authHeader = request.getHeader("Authorization");

//        checking if the header is either empty or if it doesn't start with "Bearer "
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
//           it just pases onto the next filter if it doesn't have the bearer in the authHeader
            filterChain.doFilter(request,response);
            return;
        }

//       we get the token from the authHeader where the actual token comes in the index 7 and ongoing.
        var token = authHeader.substring(7);
//        using the service we get the username by the token
        String username =jwtService.getUsernameByToken(token);

//        we loaded the whole userDetails p.s User model implements User details, and we got user details here.
        var loadedUser= userDetailsService.loadUserByUsername(username);

//        after that we checked if the token is valid or not, also we can type cast here be we implemented the UserDetails
        var isValid= jwtService.isValidToken((User) loadedUser,token);

//        checking the boolean value if its true it goes inside
        if(isValid){
//            this class basically a wrapper which just says that the user is authenticated with these roles
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loadedUser,null,loadedUser.getAuthorities());
//          this part is extra details like setting the ip address and session Id's
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//            this is where we store the authorized user data for the entirety of the session of the request.
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
//        this just passes it onto the next filter or the controller
        filterChain.doFilter(request,response);



    }
}
