package org.example.services

import org.example.data.models.User
import org.example.data.repositories.UserRepository

class AuthService(private val userRepository: UserRepository) {
    fun register(user: User): User? {
        if (userRepository.findUserById(user.username) != null) {
            return null
        }
        return userRepository.createUser(user)
    }

    fun login(username: String, password: String): User? {
        val user = userRepository.findUserById(username)
        return if (user != null && user.password == password) user else null
    }
}