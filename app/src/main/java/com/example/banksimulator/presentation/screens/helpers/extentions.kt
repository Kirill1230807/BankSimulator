package com.example.banksimulator.presentation.screens.helpers

fun String.toMaskedCardNumber() : String {
    return "**** **** **** ${takeLast(4)}"
}