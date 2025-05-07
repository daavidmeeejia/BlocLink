package com.example.bloclink.model.classes

data class User(
    val userId: String = "",
    val name: String = "",
    val surname: String = "",
    val email: String = "",
    val avatar: String = ""
) {
    fun toMap(): MutableMap<String, Any> {
        return mutableMapOf(
            "userId" to this.userId,
            "name" to this.name,
            "surname" to this.surname,
            "email" to this.email,
            "avatar" to this.avatar
        )
    }
}