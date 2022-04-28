package com.example.mphasis.util


class NullResponseException(
    message: String = "Null response"
) : Exception(message)

class FailedResponseException(
    message: String = "Error: response failure"
) : Exception(message)
