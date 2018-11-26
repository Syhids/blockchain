package com.github.mikephil.charting

import com.github.mikephil.charting.components.Description

fun descriptionOf(builder: Description.() -> Unit) = Description().also(builder)