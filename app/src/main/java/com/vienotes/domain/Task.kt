package com.vienotes.domain

import java.io.Serializable

data class Task(val id: String, val name: String, val detail: String, val done: Boolean) : Serializable {
}