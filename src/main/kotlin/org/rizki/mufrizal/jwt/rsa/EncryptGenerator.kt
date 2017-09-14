package org.rizki.mufrizal.jwt.rsa

import com.nimbusds.jose.EncryptionMethod
import com.nimbusds.jose.JOSEException
import com.nimbusds.jose.JWEAlgorithm
import com.nimbusds.jose.JWEHeader
import com.nimbusds.jose.crypto.RSAEncrypter
import com.nimbusds.jwt.EncryptedJWT
import com.nimbusds.jwt.JWTClaimsSet
import org.rizki.mufrizal.jwt.rsa.reader.PublicKeyReader
import java.util.Date
import java.util.UUID

/**
 *
 * @Author Rizki Mufrizal <mufrizalrizki@gmail.com>
 * @Web <https://RizkiMufrizal.github.io>
 * @Since 14 September 2017
 * @Time 1:18 PM
 * @Project JWT-RSA
 * @Package org.rizki.mufrizal.jwt.rsa
 * @File EncryptGenerator
 *
 */
class EncryptGenerator {
    companion object {
        @JvmStatic
        fun generateEncrypt(plainText: String, publicKey: String, iss: String, sub: String): String? {
            try {
                val NOW = Date(Date().time / 1000 * 1000)
                val exp = Date(NOW.time + 1000 * 60 * 10)
                val jti = UUID.randomUUID().toString()

                val jwtClaims = JWTClaimsSet.Builder().issuer(iss).subject(sub).audience(plainText).expirationTime(exp).notBeforeTime(NOW).issueTime(NOW).jwtID(jti).build()

                val header = JWEHeader(JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A256CBC_HS512)

                val jwt = EncryptedJWT(header, jwtClaims)

                val encrypter = RSAEncrypter(PublicKeyReader.get(publicKey))

                jwt.encrypt(encrypter)

                return jwt.serialize()
            } catch (ex: JOSEException) {
                ex.printStackTrace()
            }

            return null
        }
    }
}