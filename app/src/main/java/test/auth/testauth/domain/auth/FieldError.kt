package test.auth.testauth.domain.auth

interface FieldError {
    val description: String
}

@JvmInline
value class Error(override val description: String) : FieldError

interface CompoundError<T : FieldError> : FieldError {
    val errors: List<T>
}
