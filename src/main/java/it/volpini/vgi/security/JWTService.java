package it.volpini.vgi.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import it.volpini.vgi.general.CostantiVgi;

@Service
public class JWTService {
	

	public String createToken (String claimValue, String claimKey, String issuer) {
		Algorithm algorithm = Algorithm.HMAC256(CostantiVgi.secret);
        return JWT.create()
                .withIssuer(issuer)
                .withClaim(claimKey, claimValue)
                .withExpiresAt(new Date(System.currentTimeMillis() + (120 * 60 * 1000)))
                .sign(algorithm);
	}
	
	public String verifyToken (String token, String issue, String claimKey) {
		DecodedJWT jwt = JWT.require(Algorithm.HMAC256(CostantiVgi.secret))
		.withIssuer(issue).build().verify(token);
		return jwt.getClaim(claimKey).asString();
	}
}
