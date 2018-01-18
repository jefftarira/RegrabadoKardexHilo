package JsonWebToken;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.io.UnsupportedEncodingException;

public class CreateToken {

  private static final String secret = "miclavesecreta";

  public String crearToken() {
    String token = "";
    try {

      Algorithm algorithm = Algorithm.HMAC512(secret);
      token = JWT.create()
              .withClaim("user", "jtarira")
              .sign(algorithm);

    } catch (UnsupportedEncodingException exception) {
      System.out.println("UTF-8 encoding not supported");
    } catch (JWTCreationException exception) {
      System.out.println("Invalid Signing configuration / Couldn't convert Claims.");
    }
    return token;
  }

  public boolean verificarToken(String token) {
    boolean valid = false;
    try {
      Algorithm algorithm = Algorithm.HMAC512(secret);
      JWTVerifier verifier = JWT.require(algorithm)
              .withClaim("user", "jtarira")
              .build();
      DecodedJWT jwt = verifier.verify(token);
      valid = true;
    } catch (UnsupportedEncodingException exception) {

    } catch (JWTVerificationException exception) {
      valid = false;
    }
    return valid;
  }

}
