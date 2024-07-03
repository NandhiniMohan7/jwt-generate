package jwtTokenDemo.Service;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jwtTokenDemo.Entity.Users;

@Service
public class JwtService {
	
	private final String SECRETKEY="xSbsWXfkUaaBFcAk0oPY3g==";

	public String extractUserName(String token)
	{
		return extractClaim(token,Claims::getSubject);
	}
	
	private Date extractExpiration(String token)
	{
		return extractClaim(token,Claims::getExpiration);
	}
	public Boolean isTokenExpired(String token)
	{
		return extractExpiration(token).before(new Date());
	}
	public Boolean isValid(String token,UserDetails user)
	{
		String username=extractUserName(token);
		return username.equals(user.getUsername()) && !isTokenExpired(token);
	}
	private <T> T extractClaim(String token,Function<Claims,T> resolver)
	{
		Claims claims=extractAllclaims(token);
		return resolver.apply(claims);
	}
	
	private Claims extractAllclaims(String token)
	{
		return Jwts.parser()
				.verifyWith(getSignKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
	
	public String generateToken(Users user)
	
	{
		return Jwts.builder()
				.subject(user.getUserName())
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis()))
				.signWith(getSignKey())
				.compact();
	}
	
	private SecretKey getSignKey()
	{
	 byte[] keyBytes=Decoders.BASE64.decode(SECRETKEY);
	 return Keys.hmacShaKeyFor(keyBytes);
	}

}
