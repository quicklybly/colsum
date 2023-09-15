package translator.parser

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import translator.nodes.NumberNode
import translator.tokenization.TokenType.*
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class WhileSeparatorParserTest {
    private val tokenSequence = listOf(
        FUN_NAME to "rgb",
        PARENTHESIS_OPEN to "(",
        NUMBER to "1",
        COMMA_SEPARATOR to ", ",
        NUMBER to "2",
        COMMA_SEPARATOR to ", ",
        NUMBER to "3",
        PARENTHESIS_CLOSE to ")",
    )

    @Test
    fun consume_correct() {
        // given
        val tokens = tokenSequence
        val parser = Parser.WhileSeparatorParser(
            tokens,
            Parser.NumberParser(tokens),
            Parser.SingleTokenParser(tokens, COMMA_SEPARATOR)
        )
        for (tokenPair in tokens) {
            // when
            val parserResult = parser.consumeDelegate(2)
            // then
            assertAll({ assertEquals(5, parserResult.posOffset) }, {
                assertEquals(
                    listOf(
                        NumberNode.buildNumber(1.0).compute(),
                        NumberNode.buildNumber(2.0).compute(),
                        NumberNode.buildNumber(3.0).compute(),
                    ), parserResult.nodeList.map { it.compute() }
                )
            })
        }
    }

    @Test
    fun consume_incorrect() {
        // given
        val tokens = tokenSequence
        val parser = Parser.WhileSeparatorParser(tokens, Parser.EmptyParser, Parser.EmptyParser)
        // when + then
        assertFailsWith<ParseException> { parser.consume(-1) }
    }
}