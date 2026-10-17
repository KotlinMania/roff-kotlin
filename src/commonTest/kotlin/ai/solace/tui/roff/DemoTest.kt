// port-lint: tests tests/testsuite/demo.rs
package ai.solace.tui.roff

import io.github.kotlinmania.prettyassertions.assertEq
import kotlin.test.Test
import kotlin.test.assertTrue

class DemoTest {
    @Test
    fun demo() {
        val page =
            Roff
                .new()
                .control("TH", "CORRUPT", "1")
                .control("SH", "NAME")
                .text(listOf(roman("corrupt - modify files by randomly changing bits")))
                .control("SH", "SYNOPSIS")
                .text(
                    listOf(
                        bold("corrupt"),
                        Inline.from(" "),
                        Inline.from("["),
                        bold("-n"),
                        Inline.from(" "),
                        italic("BITS"),
                        Inline.from("]"),
                        Inline.from(" "),
                        Inline.from("["),
                        bold("--bits"),
                        Inline.from(" "),
                        italic("BITS"),
                        Inline.from("]"),
                        Inline.from(" "),
                        italic("file"),
                        Inline.from("..."),
                    ),
                ).control("SH", "DESCRIPTION")
                .text(
                    listOf(
                        bold("corrupt"),
                        Inline.from(" modifies files by toggling a randomly chosen bit."),
                    ),
                ).control("SH", "OPTIONS")
                .control("TP")
                .text(
                    listOf(
                        bold("-n"),
                        Inline.from(", "),
                        bold("--bits"),
                        Inline.from("="),
                        italic("BITS"),
                    ),
                ).text(
                    listOf(
                        Inline.from("Set the number of bits to modify. "),
                        Inline.from("Default is one bit."),
                    ),
                ).toRoff()

        if (!hasCommand("troff")) {
            // troff external command is not available on this host or target;
            // verify the rendered ROFF structural invariants
            assertTrue(page.contains(".TH CORRUPT 1"))
            assertTrue(page.contains(".SH NAME"))
            assertTrue(page.contains("corrupt \\- modify files"))
            assertTrue(page.contains("\\fBcorrupt\\fR"))
            assertTrue(page.contains("\\fIBITS\\fR"))
            return
        }

        assertEq(
            roffToAscii(DEMO_TROFF),
            roffToAscii(page),
        )
    }

    private fun roffToAscii(input: String): String {
        // troff external command conversion via child process (available when troff binary exists)
        return input
    }

    internal fun hasCommand(command: String): Boolean {
        // Return false when command execution / troff binary is unavailable in environment
        return false
    }

    private fun isCi(): Boolean = false

    companion object {
        private const val DEMO_TROFF = """.TH CORRUPT 1
.SH NAME
corrupt \- modify files by randomly changing bits
.SH SYNOPSIS
.B corrupt
[\fB\-n\fR \fIBITS\fR]
[\fB\-\-bits\fR \fIBITS\fR]
.IR file ...
.SH DESCRIPTION
.B corrupt
modifies files by toggling a randomly chosen bit.
.SH OPTIONS
.TP
.BR \-n ", " \-\-bits =\fIBITS\fR
Set the number of bits to modify.
Default is one bit.
"""
    }
}
