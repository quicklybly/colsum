package manual.translator.integrational

import org.junit.jupiter.api.assertAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import translator.parser.FunctionParser
import translator.tokenization.TokenType
import kotlin.test.assertEquals

class CalcFunctionParserTest {
    companion object {
        @JvmStatic
        fun tokenSequence() = listOf(
            Arguments.of(
                listOf(
                    Pair(TokenType.FUN_NAME, "calc"),
                    Pair(TokenType.PARENTHESIS_OPEN, "("),
                    Pair(TokenType.PARENTHESIS_OPEN, "("),
                    Pair(TokenType.NUMBER, "2"),
                    Pair(TokenType.OPERATOR_PLUS, "+"),
                    Pair(TokenType.NUMBER, "3"),
                    Pair(TokenType.OPERATOR_MUL, "*"),
                    Pair(TokenType.NUMBER, "4"),
                    Pair(TokenType.PARENTHESIS_CLOSE, ")"),
                    Pair(TokenType.OPERATOR_DIV, "/"),
                    Pair(TokenType.NUMBER, "2"),
                    Pair(TokenType.PARENTHESIS_CLOSE, ")"),
                ),
                7.0
            ),
            Arguments.of(
                listOf(
                    Pair(TokenType.FUN_NAME, "calc"),
                    Pair(TokenType.PARENTHESIS_OPEN, "("),
                    Pair(TokenType.PARENTHESIS_OPEN, "("),
                    Pair(TokenType.NUMBER_PERCENT, "2"),
                    Pair(TokenType.OPERATOR_PLUS, "+"),
                    Pair(TokenType.NUMBER_PERCENT, "3"),
                    Pair(TokenType.OPERATOR_MUL, "*"),
                    Pair(TokenType.NUMBER_PERCENT, "4"),
                    Pair(TokenType.PARENTHESIS_CLOSE, ")"),
                    Pair(TokenType.OPERATOR_DIV, "/"),
                    Pair(TokenType.NUMBER_PERCENT, "2"),
                    Pair(TokenType.PARENTHESIS_CLOSE, ")"),
                ),
                7.0
            ),
            Arguments.of(
                listOf(
                    Pair(TokenType.FUN_NAME, "calc"),
                    Pair(TokenType.PARENTHESIS_OPEN, "("),
                    Pair(TokenType.PARENTHESIS_OPEN, "("),
                    Pair(TokenType.PARENTHESIS_OPEN, "("),
                    Pair(TokenType.NUMBER, "1"),
                    Pair(TokenType.OPERATOR_MUL, "*"),
                    Pair(TokenType.NUMBER, "3"),
                    Pair(TokenType.OPERATOR_PLUS, "+"),
                    Pair(TokenType.NUMBER, "3"),
                    Pair(TokenType.OPERATOR_DIV, "/"),
                    Pair(TokenType.NUMBER, "1"),
                    Pair(TokenType.PARENTHESIS_CLOSE, ")"),
                    Pair(TokenType.OPERATOR_MUL, "*"),
                    Pair(TokenType.NUMBER, "8"),
                    Pair(TokenType.OPERATOR_MINUS, "-"),
                    Pair(TokenType.PARENTHESIS_OPEN, "("),
                    Pair(TokenType.NUMBER, "4"),
                    Pair(TokenType.OPERATOR_PLUS, "+"),
                    Pair(TokenType.NUMBER, "5"),
                    Pair(TokenType.OPERATOR_MUL, "*"),
                    Pair(TokenType.NUMBER, "7"),
                    Pair(TokenType.PARENTHESIS_CLOSE, ")"),
                    Pair(TokenType.PARENTHESIS_CLOSE, ")"),
                    Pair(TokenType.PARENTHESIS_CLOSE, ")"),
                ),
                9.0
            ),
            Arguments.of(
                listOf(
                    Pair(TokenType.FUN_NAME, "calc"),
                    Pair(TokenType.PARENTHESIS_OPEN, "("),
                    Pair(TokenType.PARENTHESIS_OPEN, "("),
                    Pair(TokenType.NUMBER, "12.1"),
                    Pair(TokenType.PARENTHESIS_CLOSE, ")"),
                    Pair(TokenType.OPERATOR_MUL, "*"),
                    Pair(TokenType.PARENTHESIS_OPEN, "("),
                    Pair(TokenType.NUMBER, "2"),
                    Pair(TokenType.OPERATOR_PLUS, "+"),
                    Pair(TokenType.NUMBER, "3"),
                    Pair(TokenType.OPERATOR_MUL, "*"),
                    Pair(TokenType.NUMBER, "4"),
                    Pair(TokenType.PARENTHESIS_CLOSE, ")"),
                    Pair(TokenType.OPERATOR_DIV, "/"),
                    Pair(TokenType.NUMBER, "2"),
                    Pair(TokenType.OPERATOR_PLUS, "+"),
                    Pair(TokenType.NUMBER_EXP, "e"),
                    Pair(TokenType.PARENTHESIS_CLOSE, ")"),
                ),
                84.7 + Math.E
            ),
            Arguments.of(
                listOf(
                    Pair(TokenType.FUN_NAME, "calc"),
                    Pair(TokenType.PARENTHESIS_OPEN, "("),
                    Pair(TokenType.FUN_NAME, "calc"),
                    Pair(TokenType.PARENTHESIS_OPEN, "("),
                    Pair(TokenType.PARENTHESIS_OPEN, "("),
                    Pair(TokenType.NUMBER, "2"),
                    Pair(TokenType.OPERATOR_PLUS, "+"),
                    Pair(TokenType.NUMBER, "3"),
                    Pair(TokenType.OPERATOR_MUL, "*"),
                    Pair(TokenType.NUMBER, "4"),
                    Pair(TokenType.PARENTHESIS_CLOSE, ")"),
                    Pair(TokenType.OPERATOR_DIV, "/"),
                    Pair(TokenType.NUMBER, "2"),
                    Pair(TokenType.PARENTHESIS_CLOSE, ")"),
                    Pair(TokenType.OPERATOR_PLUS, "+"),
                    Pair(TokenType.FUN_NAME, "calc"),
                    Pair(TokenType.PARENTHESIS_OPEN, "("),
                    Pair(TokenType.PARENTHESIS_OPEN, "("),
                    Pair(TokenType.NUMBER, "2"),
                    Pair(TokenType.OPERATOR_PLUS, "+"),
                    Pair(TokenType.NUMBER, "3"),
                    Pair(TokenType.OPERATOR_MUL, "*"),
                    Pair(TokenType.NUMBER, "4"),
                    Pair(TokenType.PARENTHESIS_CLOSE, ")"),
                    Pair(TokenType.OPERATOR_DIV, "/"),
                    Pair(TokenType.NUMBER, "2"),
                    Pair(TokenType.PARENTHESIS_CLOSE, ")"),
                    Pair(TokenType.PARENTHESIS_CLOSE, ")"),
                ),
                14.0
            ),
        )
    }

    @ParameterizedTest
    @MethodSource("tokenSequence")
    fun consume_correct(tokens: List<Pair<TokenType, String>>, res: Double) {
        // given
        val parser = FunctionParser.CalcFunctionParser(tokens)
        // when
        val parserResult = parser.consume(0)
        // then
        assertAll(
            { assertEquals(tokens.size, parserResult.posOffset) },
            { assertEquals(res, parserResult.nodeList[0].compute()) }
        )
    }
}
