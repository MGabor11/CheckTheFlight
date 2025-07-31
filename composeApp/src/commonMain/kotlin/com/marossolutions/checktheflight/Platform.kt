package com.marossolutions.checktheflight

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform