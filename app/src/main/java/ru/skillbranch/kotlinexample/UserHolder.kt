package ru.skillbranch.kotlinexample

object UserHolder {
    private val map = mutableMapOf<String, User>()

    fun registerUser(
        fullName: String,
        email: String,
        password: String
    ): User {
        return if (!map.containsKey(fullName)) {
            User.makeUser(fullName, email, password)
                .also { user -> map[user.login] = user }
        } else {
            throw IllegalArgumentException("A user with this email already exists")
        }
    }

    fun loginUser(login: String, password: String): String? {

        var user = map[login.trim()]
        if (user == null) {
            val phone = login.trim().replace("[^+\\d]".toRegex(), "")
            user = map[phone]
        }
        return if (user != null && user.checkPassword(password)) user.userInfo
        else null
    }

    fun registerUserByPhone(fullName: String, rawPhone: String): User {

        val user = if (!map.containsKey(fullName)) {
            User.makeUser(fullName, phone = rawPhone)
        } else {
            throw IllegalArgumentException("A user with this email already exists")
        }
        if (user.phone?.first() != '+' && user.phone?.length != 12) IllegalArgumentException("Enter a valid phone number starting with a + and containing 11 digits")
        if (user.phone in map.map { it.value.phone }) throw IllegalArgumentException("A user with this phone already exists")
        map[user.phone!!] = user
        return user
    }

    fun requestAccessCode(login: String) {
        val user =
            map[login.trim().toLowerCase()] ?: map[login.trim().replace("[^+\\d]".toRegex(), "")]
            ?: return
        user.generateAccessCode()
    }

    fun clearHolder() {
        map.clear()
    }

    fun importUsers(list: List<String>): List<User> {
        val users = mutableListOf<User>()
        for (string in list) {
            val info = string.split(";")
            val (firstName, lastName) = User.fullNameToPair(info[0])
            if (info.size > 3) {
                val user = User(
                    firstName = firstName,
                    lastName = lastName,
                    email = info[1],
                    authData = info[2],
                    phone = info[3]
                )
                users+=user
                map[user.login] = user
            }
        }
        return users
    }
}