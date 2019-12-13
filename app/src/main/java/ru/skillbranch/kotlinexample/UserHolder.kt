package ru.skillbranch.kotlinexample

object UserHolder {
    private val map = mutableMapOf<String, User>()

    fun registerUser(
        fullName: String,
        email: String,
        password: String
    ): User {
        return  User.makeUser(fullName, email, password)
            .also { user -> map[user.fullName] = user }
    }

    fun loginUser(login:String, password: String): String? {
        return map[login.trim()]?.run {
            if(checkPassword(password)) this.userInfo
            else null
        }
    }
}