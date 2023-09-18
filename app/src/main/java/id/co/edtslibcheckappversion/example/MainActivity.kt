package id.co.edtslibcheckappversion.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.co.edtslibcheckappversion.CheckAppVersion

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CheckAppVersion.privateKeyFileContent = "-----BEGIN PRIVATE KEY-----, MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCtB+skoOlXYT8o, lPYk3V43SZNmW9iHjdDuy4H0A9AFJ6Yv4Gzgz1ETO5W4bsm4jx1iHFm2vwL0a+Wh, xI8B47HHKA8m1tXK2pvG+WcOfX5BG34ccJq/TcqjaIEztLuwE4IBp/I14TNBY8Cx, bunMxyl/sh0YmBTOUmNAFRLl9YBCrOOk1XmDvsOQEJ7qe8CnBMNSeY6Kz+RHFEwC, 94Czw4OZi8X+g0rqH7Yg75NwyxE0XPhDEYJKHToPZjEtCyAP87pHsPNrMsfUi1Eg, z2Sqs2RJsLDzNouH7fq3LqVeN+4ZLxlWrrCBcGiMMlbrKh0kwtY/+VlOINEN4r9E, xIV/IJQPAgMBAAECggEAfuIBopiEw9jB8cZaQWo7PpurhTHwtGZgQdvl7cQl9MHA, cfdGoAwmzc2x2s8TCU2ZJGrZttKXHhlJCLkUjTQlF3/erIJ5wAlSljQpSEzqGmBI, uB4yu/oVkIJ/zCTKRTHo8cHwMWZ0qYy2ruZAeDPL0KISCAjtqOZtdX5+nzbJ2Rmu, D2KW3Qq0d3yEkpst4YS3a4OSwR81F2BMLIEAUntMfEnak5Omk2xuFU8Jy8gsHnPs, aUkqKJJWIRb1qzWkpYYsH3G7lDjCCvAKwpu5Nyo2kOswhPB8iKivvET57+Ici5uE, LrWbBXfWs4urzUn0d0iCtKikw0A/iPYJSd72PKqWwQKBgQDW9cIjx68rIhdTSc0D, VIdGJ2C311Q+FCQ60PUDamjS6HuKJ4XJ0vFRQFvU+Bno8XvphAVeQDQqiDED6TmL, S69888NRQ0HrgPgCUW5y9tVekdlo6vMsrRefHgLuQyryIBs26/W+WkjBzqmSlTiE, mEIA1MVttrBJ2o8VbZ/vLJerkQKBgQDOENz9E2G3rRegX2MFO0sv+EB4J9gMZdAY, 9+XOE7bkxQQjGBVujtFmU9sY5X/XZUeuVWrVq7ps3n0LE3RBMS0JmFZHuhPAGy83, khhyLsBMuDnMzJX+VgVJIVVGTHkxS/QFIurA+t+GNPuLMb5SC8zb9DbMxUx7bqj6, SaYHu+g1nwKBgAKjGTKfLIL29BpNeXpk+xPdP86BkcMBNECUd60HDSopN/rF6VpE, gDli9L10Hb1B2SdQO1CgsQMkkJVfsxkGYKzDwM2qaZCeEGzW2ugGJy/10hj7aOhP, Z07Ia3jI0ioOQsc1tdoT+XiHNIVb10mpJIYmFhX2e8ssJz04DxI8FdBBAoGAa/iU, Ahq8scQFRCeWt7AIxlq6VPrzjhNuxRZnF+Jmi1nbwBOnbyVv/MDlZRcjNBZCR8tD, zi9L4C13sW0BFgORSJzEYJLUNG1KAUEQhAuRAOHqjy+HTBuAa4AGsW3ydm0IsARK, otEQe76ZKqbmxRCfx4Qfjo22dFpYOcSLNTQ1CJsCgYBW/+GEueQcySamNj7rcjQd, neeVi92PlzqsB7MHH9yZWD8wxzztn8FTbw3eTJNBknra88V4lO2Lhrcz8UEY8lId, wPCec7hq5NZBHC8okMjgDYAi4Kdx6Q7UMpfa3fTNMxwUJtBG/Fgf6XmRdfC23hL7, ua+Q8Z3D2p5Tb8rEdJC4zw==, -----END PRIVATE KEY-----"
        CheckAppVersion.enableSignature = true
        CheckAppVersion.defaultPayload = "FqfDe5dYBza2fI29hVOSOK6t7Wjno098aJu9OE+ZgsyweBqiegPWX7MMcQtlG2VAw30ohEZN5m0R4Ejkc/BmMqyMA6AyBe9wqWdO3bp+3Bt0Dd+MUot3Cy0uP49BJZVVLiyrZYHAPu7srlqJ6gDTJUayclOHOXYbWJOinklCIa55ci6xCanIcLljEclZtyQN6XBQXl6JxaeFiJ+b6O6DSyZiVKSRAMVijyqmSIoPObmkxNPJyrB5U8nlkZPr2jiJ/0lXMcEPDuAIPJvys/shLTgluHQpZk6kzNx2ykIuPcQN9VKKL5Y1CJDkW0EHACOGX5LEDVYtq/OlhnhY+PWyHg=="

        CheckAppVersion.check(this, "1.0.0") {

        }
    }
}