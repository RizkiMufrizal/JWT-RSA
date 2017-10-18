# JWT-RSA

JWT-RSA is library for JWT encrypt and decrypt using RSA key. For encrypt, we use public key and for decrypt we use private key.

## How To Use

When you use this library, you can generate public and private key using command openssl like this.

for generate private key

```bash
openssl genrsa -passout pass:"rizkimufrizal" -des3 -out private.pem 2048
```

for generate public key

```bash
openssl rsa -in private.pem -outform PEM -pubout -out public.pem -passin pass:"rizkimufrizal"
```

convert private key to pkcs8 format in order to import it from kotlin

```bash
openssl pkcs8 -topk8 -in private.pem -inform pem -out private_key_pkcs8.pem -outform pem -nocrypt -passin pass:"rizkimufrizal"
```

or convert private key to unecnrypted format in order to import it from kotlin

```bash
openssl rsa -in private.pem -out private_unencrypted.pem -outform PEM -passin pass:"rizkimufrizal"
```

You can use one of the private keys. In this example, we use private key in pkcs8 format.

## Algorithm And Encryption Methods

You can use of this list algorithm :

* RSA_OAEP_256
* A128KW
* A128GCMKW
* A256KW
* A256GCMKW
* DIR
* ECDH_ES
* ECDH_ES_A128KW
* ECDH_ES_A192KW
* ECDH_ES_A256KW
* PBES2_HS256_A128KW
* PBES2_HS384_A192KW
* PBES2_HS512_A256KW

You can use of this list encryption methods :

* A128GCM
* A192GCM
* A256GCM
* A128CBC_HS256
* A192CBC_HS384
* A256CBC_HS512

## Example Using String

this for example when using kotlin
```kotlin
import org.rizki.mufrizal.jwt.rsa.DecryptGenerator
import org.rizki.mufrizal.jwt.rsa.EncryptGenerator

class App {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val publicKey = "-----BEGIN PUBLIC KEY-----\n" +
                    "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzVNBIAdt3iUZppRmuLfz\n" +
                    "hobW10zcL/AoAwdFgGxBD7Lh3JWIAHAVsP+Yt0V521z3lQkULJQ6VzgOKL6HS/aa\n" +
                    "v+Aa770FaOBhJbycE93T7noe9chhKhA3wvUVboc8QzSxuHZsfmcOMtsCK59F9NJ1\n" +
                    "mzWCNUkwMcTxBssq5q21GTyynssQkiFBr1G7OH6JLWuFQLt14Zm3Wfrjnglj+O+H\n" +
                    "IOBevRjYixknW+z3wHoEqi+cE7k6fS3KIXFGX3PsWlPthjMLbepx3JiKTNFey69t\n" +
                    "a6KDeuY5gcJy3Ftr3nDCG5VmPIE1QZ13Z4KkHZ4mZcKTRiRsYmXlrZgBFPEEOpgV\n" +
                    "owIDAQAB\n" +
                    "-----END PUBLIC KEY-----"

            val privateKey = "-----BEGIN PRIVATE KEY-----\n" +
                    "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDNU0EgB23eJRmm\n" +
                    "lGa4t/OGhtbXTNwv8CgDB0WAbEEPsuHclYgAcBWw/5i3RXnbXPeVCRQslDpXOA4o\n" +
                    "vodL9pq/4BrvvQVo4GElvJwT3dPueh71yGEqEDfC9RVuhzxDNLG4dmx+Zw4y2wIr\n" +
                    "n0X00nWbNYI1STAxxPEGyyrmrbUZPLKeyxCSIUGvUbs4fokta4VAu3XhmbdZ+uOe\n" +
                    "CWP474cg4F69GNiLGSdb7PfAegSqL5wTuTp9LcohcUZfc+xaU+2GMwtt6nHcmIpM\n" +
                    "0V7Lr21rooN65jmBwnLcW2vecMIblWY8gTVBnXdngqQdniZlwpNGJGxiZeWtmAEU\n" +
                    "8QQ6mBWjAgMBAAECggEBAK9cjMGzNqXFH/xCwNzA1y+tWC54CZKz1SiI/FYrnwGu\n" +
                    "cPL5jzd4gz4xfpgAsYumAhp6r41HZ/B4ArfPyjQZwZ9g4wCges9Q3Afj55WcHtaN\n" +
                    "3IVkh3/qbAWJVq2YuOJZTfRSyGTI1bqfjGH/XTs0yJcwAy5JfOz03DpGKTTtZT/Q\n" +
                    "LKWMqGtD439w6a6E/wfbwQUTcjGokWgX6zhOJn5ox1Bxzwtke9QvzkKClIuAmNbP\n" +
                    "dbshzNFMFM04blEIwHS/Jm+Br58TTo7uFPtGw2a3Pmj9eKeOhEkTWVd1rQLkQ98g\n" +
                    "rZcdX4VDwpEWemyCnNsaph67G9dfWD9Rz9FXByWj1fECgYEA64+ZrIZUdQpl67Zi\n" +
                    "AVzRovNVw3Dovxa8Nay/deTwDeeQW02PfYBFH3uzSLhl+k5FVPh9tCZdYoTlRLwD\n" +
                    "KmFLtSTE1xACPUQ8lBumU9M/FTyursWflLoNDDOkpLF0RjAYT6QDVMICAVUZIGvv\n" +
                    "R57GyzAsWDZjH2te8wd6aL+/z3kCgYEA3yQK8L7Bd44JkOqFE6LVcBX0GuNGjy9b\n" +
                    "cQ1/TPb48GcRDhwQuG/5z78uemJTF0zxvCEVl13Gjqe9+bgndVpdi4ySsUKX+Mxi\n" +
                    "zeM3XsoM04F79k961F22ND18LIUxLZAFyCB09Ji/S+F/N/M+9yfyYuWiRAKKdSWW\n" +
                    "Stg6F2PoevsCgYEA3dsVhSPPD5yHeYUAsP/WgX5k2/nPe4nSIUtd14+Td8UMdLGL\n" +
                    "30ubzpcWt/rUMPbe3bRbz/wCH/PCawYYhSW1xBmpOlRdh45o76VK9dATrdDFRN3j\n" +
                    "+pNwDnnlKyfmtuQ9QWTbrkw6zz5yt9JwPigQWvY4DazLlp/tgT8dzuIpqSECgYBq\n" +
                    "u97P0S7RdQt2WfdVsSnO93FP+y6hBtICfaZKtkfVFje+PAZzcnxXtucQez+rgY6P\n" +
                    "onOld6GmUu44KLIXHCZqvc7dIzF2PK12Nh0iJhuEgAc/hj0Gn9yrmE1xLjSbyqw5\n" +
                    "Ue0fooC+Vxp3NM8FggIa9CRty5lW96ewHUWMMqndSQKBgQCROfob3N4qAI8CFXJK\n" +
                    "G+IEkNSYrdDj6Uu9WjM4A5otVDvzR2mqKg93JAsX1qRkwm2bWAg+mTPYq657VS2u\n" +
                    "XU3sraSATPp0Mqav/MtA7PhnLhOEYMDSRnz2wO0ZLRJafuMiFcYl9q73Zyd2Z/Ja\n" +
                    "QEikiQBuOsBXYRFQzOvkrzeriA==\n" +
                    "-----END PRIVATE KEY-----"

            val encryptedString = EncryptGenerator.generateEncrypt(plainText = "Hy Rizki Mufrizal", publicKey = publicKey, jWEAlgorithm = "RSA_OAEP_256", encryptionMethod = "A256CBC_HS512")

            println(encryptedString)

            val plainText = encryptedString?.let { DecryptGenerator.generateDecrypt(encryptText = it, privateKey = privateKey) }

            println(plainText)
        }
    }
}
```

this for example when using groovy

```groovy
import org.rizki.mufrizal.jwt.rsa.DecryptGenerator
import org.rizki.mufrizal.jwt.rsa.EncryptGenerator

class App {
    static void main(String[] args) {
        def publicKey = "-----BEGIN PUBLIC KEY-----\n" +
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzVNBIAdt3iUZppRmuLfz\n" +
                "hobW10zcL/AoAwdFgGxBD7Lh3JWIAHAVsP+Yt0V521z3lQkULJQ6VzgOKL6HS/aa\n" +
                "v+Aa770FaOBhJbycE93T7noe9chhKhA3wvUVboc8QzSxuHZsfmcOMtsCK59F9NJ1\n" +
                "mzWCNUkwMcTxBssq5q21GTyynssQkiFBr1G7OH6JLWuFQLt14Zm3Wfrjnglj+O+H\n" +
                "IOBevRjYixknW+z3wHoEqi+cE7k6fS3KIXFGX3PsWlPthjMLbepx3JiKTNFey69t\n" +
                "a6KDeuY5gcJy3Ftr3nDCG5VmPIE1QZ13Z4KkHZ4mZcKTRiRsYmXlrZgBFPEEOpgV\n" +
                "owIDAQAB\n" +
                "-----END PUBLIC KEY-----"

        def privateKey = "-----BEGIN PRIVATE KEY-----\n" +
                "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDNU0EgB23eJRmm\n" +
                "lGa4t/OGhtbXTNwv8CgDB0WAbEEPsuHclYgAcBWw/5i3RXnbXPeVCRQslDpXOA4o\n" +
                "vodL9pq/4BrvvQVo4GElvJwT3dPueh71yGEqEDfC9RVuhzxDNLG4dmx+Zw4y2wIr\n" +
                "n0X00nWbNYI1STAxxPEGyyrmrbUZPLKeyxCSIUGvUbs4fokta4VAu3XhmbdZ+uOe\n" +
                "CWP474cg4F69GNiLGSdb7PfAegSqL5wTuTp9LcohcUZfc+xaU+2GMwtt6nHcmIpM\n" +
                "0V7Lr21rooN65jmBwnLcW2vecMIblWY8gTVBnXdngqQdniZlwpNGJGxiZeWtmAEU\n" +
                "8QQ6mBWjAgMBAAECggEBAK9cjMGzNqXFH/xCwNzA1y+tWC54CZKz1SiI/FYrnwGu\n" +
                "cPL5jzd4gz4xfpgAsYumAhp6r41HZ/B4ArfPyjQZwZ9g4wCges9Q3Afj55WcHtaN\n" +
                "3IVkh3/qbAWJVq2YuOJZTfRSyGTI1bqfjGH/XTs0yJcwAy5JfOz03DpGKTTtZT/Q\n" +
                "LKWMqGtD439w6a6E/wfbwQUTcjGokWgX6zhOJn5ox1Bxzwtke9QvzkKClIuAmNbP\n" +
                "dbshzNFMFM04blEIwHS/Jm+Br58TTo7uFPtGw2a3Pmj9eKeOhEkTWVd1rQLkQ98g\n" +
                "rZcdX4VDwpEWemyCnNsaph67G9dfWD9Rz9FXByWj1fECgYEA64+ZrIZUdQpl67Zi\n" +
                "AVzRovNVw3Dovxa8Nay/deTwDeeQW02PfYBFH3uzSLhl+k5FVPh9tCZdYoTlRLwD\n" +
                "KmFLtSTE1xACPUQ8lBumU9M/FTyursWflLoNDDOkpLF0RjAYT6QDVMICAVUZIGvv\n" +
                "R57GyzAsWDZjH2te8wd6aL+/z3kCgYEA3yQK8L7Bd44JkOqFE6LVcBX0GuNGjy9b\n" +
                "cQ1/TPb48GcRDhwQuG/5z78uemJTF0zxvCEVl13Gjqe9+bgndVpdi4ySsUKX+Mxi\n" +
                "zeM3XsoM04F79k961F22ND18LIUxLZAFyCB09Ji/S+F/N/M+9yfyYuWiRAKKdSWW\n" +
                "Stg6F2PoevsCgYEA3dsVhSPPD5yHeYUAsP/WgX5k2/nPe4nSIUtd14+Td8UMdLGL\n" +
                "30ubzpcWt/rUMPbe3bRbz/wCH/PCawYYhSW1xBmpOlRdh45o76VK9dATrdDFRN3j\n" +
                "+pNwDnnlKyfmtuQ9QWTbrkw6zz5yt9JwPigQWvY4DazLlp/tgT8dzuIpqSECgYBq\n" +
                "u97P0S7RdQt2WfdVsSnO93FP+y6hBtICfaZKtkfVFje+PAZzcnxXtucQez+rgY6P\n" +
                "onOld6GmUu44KLIXHCZqvc7dIzF2PK12Nh0iJhuEgAc/hj0Gn9yrmE1xLjSbyqw5\n" +
                "Ue0fooC+Vxp3NM8FggIa9CRty5lW96ewHUWMMqndSQKBgQCROfob3N4qAI8CFXJK\n" +
                "G+IEkNSYrdDj6Uu9WjM4A5otVDvzR2mqKg93JAsX1qRkwm2bWAg+mTPYq657VS2u\n" +
                "XU3sraSATPp0Mqav/MtA7PhnLhOEYMDSRnz2wO0ZLRJafuMiFcYl9q73Zyd2Z/Ja\n" +
                "QEikiQBuOsBXYRFQzOvkrzeriA==\n" +
                "-----END PRIVATE KEY-----"

        def encryptedString = EncryptGenerator.generateEncrypt("Hy Rizki Mufrizal", publicKey, "RSA_OAEP_256", "A256CBC_HS512")

        println(encryptedString)

        def plainText = DecryptGenerator.generateDecrypt(encryptedString, privateKey)

        println(plainText)
    }
}
```

this for example when using java

```java
import org.rizki.mufrizal.jwt.rsa.DecryptGenerator;
import org.rizki.mufrizal.jwt.rsa.EncryptGenerator;

public class App {
    public static void main(String[] args) {
        String publicKey = "-----BEGIN PUBLIC KEY-----\n" +
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzVNBIAdt3iUZppRmuLfz\n" +
                "hobW10zcL/AoAwdFgGxBD7Lh3JWIAHAVsP+Yt0V521z3lQkULJQ6VzgOKL6HS/aa\n" +
                "v+Aa770FaOBhJbycE93T7noe9chhKhA3wvUVboc8QzSxuHZsfmcOMtsCK59F9NJ1\n" +
                "mzWCNUkwMcTxBssq5q21GTyynssQkiFBr1G7OH6JLWuFQLt14Zm3Wfrjnglj+O+H\n" +
                "IOBevRjYixknW+z3wHoEqi+cE7k6fS3KIXFGX3PsWlPthjMLbepx3JiKTNFey69t\n" +
                "a6KDeuY5gcJy3Ftr3nDCG5VmPIE1QZ13Z4KkHZ4mZcKTRiRsYmXlrZgBFPEEOpgV\n" +
                "owIDAQAB\n" +
                "-----END PUBLIC KEY-----";

        String privateKey = "-----BEGIN PRIVATE KEY-----\n" +
                "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDNU0EgB23eJRmm\n" +
                "lGa4t/OGhtbXTNwv8CgDB0WAbEEPsuHclYgAcBWw/5i3RXnbXPeVCRQslDpXOA4o\n" +
                "vodL9pq/4BrvvQVo4GElvJwT3dPueh71yGEqEDfC9RVuhzxDNLG4dmx+Zw4y2wIr\n" +
                "n0X00nWbNYI1STAxxPEGyyrmrbUZPLKeyxCSIUGvUbs4fokta4VAu3XhmbdZ+uOe\n" +
                "CWP474cg4F69GNiLGSdb7PfAegSqL5wTuTp9LcohcUZfc+xaU+2GMwtt6nHcmIpM\n" +
                "0V7Lr21rooN65jmBwnLcW2vecMIblWY8gTVBnXdngqQdniZlwpNGJGxiZeWtmAEU\n" +
                "8QQ6mBWjAgMBAAECggEBAK9cjMGzNqXFH/xCwNzA1y+tWC54CZKz1SiI/FYrnwGu\n" +
                "cPL5jzd4gz4xfpgAsYumAhp6r41HZ/B4ArfPyjQZwZ9g4wCges9Q3Afj55WcHtaN\n" +
                "3IVkh3/qbAWJVq2YuOJZTfRSyGTI1bqfjGH/XTs0yJcwAy5JfOz03DpGKTTtZT/Q\n" +
                "LKWMqGtD439w6a6E/wfbwQUTcjGokWgX6zhOJn5ox1Bxzwtke9QvzkKClIuAmNbP\n" +
                "dbshzNFMFM04blEIwHS/Jm+Br58TTo7uFPtGw2a3Pmj9eKeOhEkTWVd1rQLkQ98g\n" +
                "rZcdX4VDwpEWemyCnNsaph67G9dfWD9Rz9FXByWj1fECgYEA64+ZrIZUdQpl67Zi\n" +
                "AVzRovNVw3Dovxa8Nay/deTwDeeQW02PfYBFH3uzSLhl+k5FVPh9tCZdYoTlRLwD\n" +
                "KmFLtSTE1xACPUQ8lBumU9M/FTyursWflLoNDDOkpLF0RjAYT6QDVMICAVUZIGvv\n" +
                "R57GyzAsWDZjH2te8wd6aL+/z3kCgYEA3yQK8L7Bd44JkOqFE6LVcBX0GuNGjy9b\n" +
                "cQ1/TPb48GcRDhwQuG/5z78uemJTF0zxvCEVl13Gjqe9+bgndVpdi4ySsUKX+Mxi\n" +
                "zeM3XsoM04F79k961F22ND18LIUxLZAFyCB09Ji/S+F/N/M+9yfyYuWiRAKKdSWW\n" +
                "Stg6F2PoevsCgYEA3dsVhSPPD5yHeYUAsP/WgX5k2/nPe4nSIUtd14+Td8UMdLGL\n" +
                "30ubzpcWt/rUMPbe3bRbz/wCH/PCawYYhSW1xBmpOlRdh45o76VK9dATrdDFRN3j\n" +
                "+pNwDnnlKyfmtuQ9QWTbrkw6zz5yt9JwPigQWvY4DazLlp/tgT8dzuIpqSECgYBq\n" +
                "u97P0S7RdQt2WfdVsSnO93FP+y6hBtICfaZKtkfVFje+PAZzcnxXtucQez+rgY6P\n" +
                "onOld6GmUu44KLIXHCZqvc7dIzF2PK12Nh0iJhuEgAc/hj0Gn9yrmE1xLjSbyqw5\n" +
                "Ue0fooC+Vxp3NM8FggIa9CRty5lW96ewHUWMMqndSQKBgQCROfob3N4qAI8CFXJK\n" +
                "G+IEkNSYrdDj6Uu9WjM4A5otVDvzR2mqKg93JAsX1qRkwm2bWAg+mTPYq657VS2u\n" +
                "XU3sraSATPp0Mqav/MtA7PhnLhOEYMDSRnz2wO0ZLRJafuMiFcYl9q73Zyd2Z/Ja\n" +
                "QEikiQBuOsBXYRFQzOvkrzeriA==\n" +
                "-----END PRIVATE KEY-----";

        String encryptedString = EncryptGenerator.generateEncrypt("Hy Rizki Mufrizal", publicKey, "RSA_OAEP_256", "A256CBC_HS512");

        System.out.println(encryptedString);

        String plainText = DecryptGenerator.generateDecrypt(encryptedString, privateKey);

        System.out.println(plainText);
    }
}

```

## Example Using From File

this for example when using kotlin
```kotlin
import org.rizki.mufrizal.jwt.rsa.DecryptGenerator
import org.rizki.mufrizal.jwt.rsa.EncryptGenerator

class App {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val encryptedString = EncryptGenerator.generateEncrypt(plainText = "Hy Rizki Mufrizal", path = "/Users/rizkimufrizal/Documents/keys", fileName = "public.pem", jWEAlgorithm = "RSA_OAEP_256", encryptionMethod = "A256CBC_HS512")

            println(encryptedString)

            val plainText = encryptedString?.let { DecryptGenerator.generateDecrypt(encryptText = it, path = "/Users/rizkimufrizal/Documents/keys", fileName = "private_key_pkcs8.pem") }

            println(plainText)
        }
    }
}
```

this for example when using groovy

```groovy
import org.rizki.mufrizal.jwt.rsa.DecryptGenerator
import org.rizki.mufrizal.jwt.rsa.EncryptGenerator

class App {
    static void main(String[] args) {
        def encryptedString = EncryptGenerator.generateEncrypt("Hy Rizki Mufrizal", "/Users/rizkimufrizal/Documents/keys", "public.pem", "RSA_OAEP_256", "A256CBC_HS512")

        println(encryptedString)

        def plainText = DecryptGenerator.generateDecrypt(encryptedString, "/Users/rizkimufrizal/Documents/keys", "private_key_pkcs8.pem")

        println(plainText)
    }
}
```

this for example when using java

```java
import org.rizki.mufrizal.jwt.rsa.DecryptGenerator;
import org.rizki.mufrizal.jwt.rsa.EncryptGenerator;

public class App {
    public static void main(String[] args) {
        String encryptedString = EncryptGenerator.generateEncrypt("Hy Rizki Mufrizal", "/Users/rizkimufrizal/Documents/keys", "public.pem", "RSA_OAEP_256", "A256CBC_HS512");

        System.out.println(encryptedString);

        String plainText = DecryptGenerator.generateDecrypt(encryptedString, "/Users/rizkimufrizal/Documents/keys", "private_key_pkcs8.pem");

        System.out.println(plainText);
    }
}

```

## Author

* [Rizki Mufrizal](https://github.com/RizkiMufrizal)

## License

JWT-RSA is Open Source software released under the Apache 2.0 license.
