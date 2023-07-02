package ml.translation.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(reason = "Resource not found", value = HttpStatus.NOT_FOUND)
class ResourceNotFoundException(text: String): RuntimeException(text)