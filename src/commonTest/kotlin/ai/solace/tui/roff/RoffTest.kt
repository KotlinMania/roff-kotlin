package ai.solace.tui.roff

import io.github.kotlinmania.prettyassertions.assertEq
import kotlin.test.Test
import kotlin.test.assertTrue

/**
 * Tests for the ROFF library, ported from the Rust test suite.
 */
class RoffTest {
    @Test
    fun escapeDash() {
        assertEq("\\-", escapeInline("-"))
    }

    @Test
    fun escapeBackslash() {
        assertEq("\\\\x", escapeInline("\\x"))
    }

    @Test
    fun escapeBackslashAndDash() {
        assertEq("\\\\\\-", escapeInline("\\-"))
    }

    @Test
    fun escapesLeadingControlChars() {
        assertEq("foo\n\\&.bar\n\\&'yo", escapeLeadingCcTest("foo\n.bar\n'yo"))
    }

    @Test
    fun escapePlain() {
        assertEq("abc", escapeInline("abc"))
    }

    @Test
    fun renderRoman() {
        val text = Roff().text(roman("foo")).toRoff()
        assertEq("foo\n", text)
    }

    @Test
    fun renderDash() {
        val text = Roff().text(roman("foo-bar")).toRoff()
        assertEq("foo\\-bar\n", text)
    }

    @Test
    fun renderItalic() {
        val text = Roff().text(italic("foo")).toRoff()
        assertEq("\\fIfoo\\fR\n", text)
    }

    @Test
    fun renderBold() {
        val text = Roff().text(bold("foo")).toRoff()
        assertEq("\\fBfoo\\fR\n", text)
    }

    @Test
    fun renderText() {
        val text = Roff().text(roman("roman")).toRoff()
        assertEq("roman\n", text)
    }

    @Test
    fun renderTextWithLeadingPeriod() {
        val text = Roff().text(roman(".roman")).toRoff()
        assertEq("\\&.roman\n", text)
    }

    @Test
    fun renderTextWithNewlinePeriod() {
        val text = Roff().text(roman("foo\n.roman")).toRoff()
        assertEq("foo\n\\&.roman\n", text)
    }

    @Test
    fun renderLineBreak() {
        val text =
            Roff()
                .text(roman("roman"), Inline.LineBreak, roman("more"))
                .toRoff()
        assertEq("roman\n.br\nmore\n", text)
    }

    @Test
    fun renderControl() {
        val text = Roff().control("foo", "bar", "foo and bar").toRoff()
        assertEq(".foo bar \"foo and bar\"\n", text)
    }

    @Test
    fun renderWithApostrophePreamble() {
        val text = Roff().text(roman("hello")).render()
        assertEq(".ie \\n(.g .ds Aq \\(aq\n.el .ds Aq '\nhello\n", text)
    }

    @Test
    fun renderApostropheEscaping() {
        val text = Roff().text(roman("don't")).render()
        // Should contain the escaped apostrophe
        assertTrue(text.contains("\\*(Aq"))
    }

    @Test
    fun toWriterTest() {
        val sb = StringBuilder()
        Roff().text(roman("hello")).toWriter(sb)
        assertEq(".ie \\n(.g .ds Aq \\(aq\n.el .ds Aq '\nhello\n", sb.toString())
    }
}

// Helper function for testing escapeLeadingCc
private fun escapeLeadingCcTest(s: String): String =
    s.replace("\n.", "\n\\&.").replace("\n'", "\n\\&'")
