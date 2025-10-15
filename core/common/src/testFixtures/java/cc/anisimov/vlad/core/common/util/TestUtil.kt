package cc.anisimov.vlad.core.common.util

fun readResource(name: String): String = object {}.javaClass.getResource("/$name")!!.readText()
