package com.example.agenda.app.common


interface Usecase<I,O> {
    suspend fun execute(input: I): O
}