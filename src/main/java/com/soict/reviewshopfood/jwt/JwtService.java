package com.soict.reviewshopfood.jwt;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

@Service
public class JwtService {

	
	public static final String EMAIL = "email";
	public static final String SECRET_KEY = "Laptrinhweb_Team_NguyenVanDung_DaoVietDuc_NguyenVietLong";
	public static final int EXPIRE_TIME = 68400000;
	
	
	//create token
	public String generateTokenLogin(String email) {
		
		String token = null;
		
		try {
			
			JWSSigner signer = new MACSigner(generateShareSecret());
			
			JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();
			builder.claim(EMAIL,email);
			builder.expirationTime(generateExpirationDate());
			
			JWTClaimsSet claimsSet = builder.build();
			SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256),claimsSet);
			
			signedJWT.sign(signer);
			token = signedJWT.serialize();
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
		return token;
	}
	
	//Lay cac thanh phan trong token
	private JWTClaimsSet getClaimsFromToken(String token) {
		
		JWTClaimsSet claims = null;
		
		try {
			
			SignedJWT signedJWT = SignedJWT.parse(token);
			JWSVerifier verifier = new MACVerifier(generateShareSecret());
			
			if(signedJWT.verify(verifier)) {
				
				claims = signedJWT.getJWTClaimsSet();
			}
		}catch(Exception e) {
			
			e.printStackTrace();
		}
		
		return claims;
	}
	
	//lay email trong token
	public String getEmailToken(String token) {
		
		String email=null;
		
		try {
			
			JWTClaimsSet claims = getClaimsFromToken(token);
			email = claims.getStringClaim(EMAIL);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return email;
	}


	private Date generateExpirationDate() {
		
		//chuyen kieu int sang Date
		return new Date(System.currentTimeMillis() + EXPIRE_TIME);
	}
	
	//lay ngay het han tu token
	private Date getExpirationDateFromToke(String token) {
		
		Date expiration = null;
		
		JWTClaimsSet claims = getClaimsFromToken(token);
		expiration = claims.getExpirationTime();
		
		return expiration;
	}


	//chuyen secret_key sang 32 bit
	private byte[] generateShareSecret() {
		
		byte[] sharedSecret = new byte[32];
		
		sharedSecret = SECRET_KEY.getBytes();
		
		return sharedSecret;
		
	}
	
	
	//kiem tra token con han khong
	public boolean isTokenExpired(String token) {
		
		Date expiration = getExpirationDateFromToke(token);
		return expiration.before(new Date());
	}
	
	//kiem tra token khi login co hop le khong
	public Boolean validateToken(String token) {
		
		if(token == null || token.trim().length() == 0) {
			return false;
		}
		
		String email = getEmailToken(token);
		if(email == null || email.isEmpty()) {
			return false;
		}
		
		if(isTokenExpired(token)) {
			return false;
		}
		
		return true;
	}
}
