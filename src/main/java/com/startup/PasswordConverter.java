package com.startup;

import java.security.AlgorithmParameters;
import java.security.Key;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class PasswordConverter
{
	private static final String ALG_PLAINTEXT = "PLA";

	private static final String ALG_AES = "AES";

	private static final String ALGORITHM = "AES/CBC/PKCS5Padding";

	private static final byte[][] AES_SECRETS = {"SecretEduarteKey".getBytes(),
		Base64.getDecoder().decode("1dQptpunOoLV5yb7CIE/2JBlRo6z/thARyS5oQxGRPU=")};

	private static final int DEFAULT_KEY_VERSION = 1;
	
	public static void main(String[] args) {
		String encrypted = "";
		System.out.println(encrypted = convertToDatabaseColumn("Pass@123"));
		System.out.println(convertToEntityAttribute(encrypted));
		
		System.out.println(convertToEntityAttribute("sKrF/bim7BoPsFrZ7/VX0QtVYguvQbeB4oc6/KCDsGSVnK2ZGegnSBu5VQrgPE3hH8k9xuTgQLsRHnDGW58LdQ=="));
	}

	public static String convertToDatabaseColumn(String unencrypted)
	{
		if (unencrypted == null)
			return null;

		int keyVersion = DEFAULT_KEY_VERSION;
		Key key = new SecretKeySpec(AES_SECRETS[keyVersion], "AES");
		try
		{
			Cipher c = Cipher.getInstance(ALGORITHM);
			c.init(Cipher.ENCRYPT_MODE, key);
			AlgorithmParameters params = c.getParameters();
			byte[] ivBytes = params.getParameterSpec(IvParameterSpec.class).getIV();
			byte[] encryptedBytes = c.doFinal(unencrypted.getBytes());
			return ALG_AES + ":" + keyVersion + ":" + Base64.getEncoder().encodeToString(ivBytes)
				+ ":" + Base64.getEncoder().encodeToString(encryptedBytes);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	public static String convertToEntityAttribute(String base64)
	{
		if (base64 == null)
			return null;

		String[] splittedBase64 = base64.split(":");
		String alg = splittedBase64[0];

		if (ALG_PLAINTEXT.equals(alg))
			// niet splittedBase64[1] want er kunnen :'s in de plaintext staan
			return base64.substring(ALG_PLAINTEXT.length() + 1);

//		Asserts.equal("algoritme", alg, "AES");

//		Asserts.assertTrue("Key versie is niet numeriek", StringUtil.isNumeric(splittedBase64[1]));

		int keyVersion = Integer.parseInt(splittedBase64[1]);
//		Asserts.assertTrue("Onbekende key versie", keyVersion < AES_SECRETS.length);

		byte[] secret = AES_SECRETS[keyVersion];

		Key key = new SecretKeySpec(secret, "AES");
		try
		{
			Cipher c = Cipher.getInstance(ALGORITHM);
			byte[] ivBytes = Base64.getDecoder().decode(splittedBase64[2]);
			byte[] aesBytes = Base64.getDecoder().decode(splittedBase64[3]);
			c.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(ivBytes));
			return new String(c.doFinal(aesBytes));
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
}
