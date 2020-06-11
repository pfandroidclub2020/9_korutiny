package net.pilsfree.korutiny

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

val uiContext = Dispatchers.Main
val bgContext = Dispatchers.Default
val ioContext = Dispatchers.IO

val bgScope = CoroutineScope(bgContext)
val uiScope = CoroutineScope(uiContext)
val ioScope = CoroutineScope(ioContext)