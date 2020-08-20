package ru.skillbranch.skillarticles.data.remote.err

import okio.IOException

class NoNetworkError(override val message: String = "Network not available") : IOException(message)