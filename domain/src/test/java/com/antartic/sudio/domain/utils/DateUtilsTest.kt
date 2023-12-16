package com.antartic.sudio.domain.utils

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

class DateUtilsTest {

    @Test
    fun getBanksUseCase_test() {
        val timestamp = getDate(1701385200)
        val dateTimestamp = getDate("1701385200")
        val dateError2 = getDate("abcd")
        val dateError3 = getDate("17/02/1991")

        assertThat(timestamp).isEqualTo("01/12/2023")
        assertThat(dateTimestamp).isEqualTo("01/12/2023")
        assertThat(dateError2).isEqualTo("abcd")
        assertThat(dateError3).isEqualTo("17/02/1991")
    }
}
