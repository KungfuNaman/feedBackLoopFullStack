package com.example.feedbackloop.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service    // This annotation is used to mark the class as a service
public class FeedbackService {
    static String checkstyleStringConfig="<?xml version=\"1.0\"?>\n" +
            "<!DOCTYPE module PUBLIC\n" +
            "          \"-//Checkstyle//DTD Checkstyle Configuration 1.3//EN\"\n" +
            "          \"https://checkstyle.org/dtds/configuration_1_3.dtd\">\n" +
            "\n" +
            "<!--\n" +
            "    Checkstyle configuration that checks the Google coding conventions from Google Java Style\n" +
            "    that can be found at https://google.github.io/styleguide/javaguide.html\n" +
            "\n" +
            "    Checkstyle is very configurable. Be sure to read the documentation at\n" +
            "    http://checkstyle.org (or in your downloaded distribution).\n" +
            "\n" +
            "    To completely disable a check, just comment it out or delete it from the file.\n" +
            "    To suppress certain violations please review suppression filters.\n" +
            "\n" +
            "    Authors: Max Vetrenko, Ruslan Diachenko, Roman Ivanov.\n" +
            " -->\n" +
            "\n" +
            "<module name = \"Checker\">\n" +
            "  <property name=\"charset\" value=\"UTF-8\"/>\n" +
            "\n" +
            "  <property name=\"severity\" value=\"warning\"/>\n" +
            "\n" +
            "  <property name=\"fileExtensions\" value=\"java, properties, xml\"/>\n" +
            "  <!-- Excludes all 'module-info.java' files              -->\n" +
            "  <!-- See https://checkstyle.org/config_filefilters.html -->\n" +
            "  <module name=\"BeforeExecutionExclusionFileFilter\">\n" +
            "    <property name=\"fileNamePattern\" value=\"module\\-info\\.java$\"/>\n" +
            "  </module>\n" +
            "  <!-- https://checkstyle.org/config_filters.html#SuppressionFilter -->\n" +
            "  <module name=\"SuppressionFilter\">\n" +
            "    <property name=\"file\" value=\"${org.checkstyle.google.suppressionfilter.config}\"\n" +
            "           default=\"checkstyle-suppressions.xml\" />\n" +
            "    <property name=\"optional\" value=\"true\"/>\n" +
            "  </module>\n" +
            "\n" +
            "  <!-- Checks for whitespace                               -->\n" +
            "  <!-- See http://checkstyle.org/config_whitespace.html -->\n" +
            "  <module name=\"FileTabCharacter\">\n" +
            "    <property name=\"eachLine\" value=\"true\"/>\n" +
            "  </module>\n" +
            "\n" +
            "  <module name=\"LineLength\">\n" +
            "    <property name=\"fileExtensions\" value=\"java\"/>\n" +
            "    <property name=\"max\" value=\"100\"/>\n" +
            "    <property name=\"ignorePattern\" value=\"^package.*|^import.*|a href|href|http://|https://|ftp://\"/>\n" +
            "  </module>\n" +
            "\n" +
            "  <module name=\"TreeWalker\">\n" +
            "    <module name=\"OuterTypeFilename\"/>\n" +
            "    <module name=\"IllegalTokenText\">\n" +
            "      <property name=\"tokens\" value=\"STRING_LITERAL, CHAR_LITERAL\"/>\n" +
            "      <property name=\"format\"\n" +
            "               value=\"\\\\u00(09|0(a|A)|0(c|C)|0(d|D)|22|27|5(C|c))|\\\\(0(10|11|12|14|15|42|47)|134)\"/>\n" +
            "      <property name=\"message\"\n" +
            "               value=\"Consider using special escape sequence instead of octal value or Unicode escaped value.\"/>\n" +
            "    </module>\n" +
            "    <module name=\"AvoidEscapedUnicodeCharacters\">\n" +
            "      <property name=\"allowEscapesForControlCharacters\" value=\"true\"/>\n" +
            "      <property name=\"allowByTailComment\" value=\"true\"/>\n" +
            "      <property name=\"allowNonPrintableEscapes\" value=\"true\"/>\n" +
            "    </module>\n" +
            "    <module name=\"AvoidStarImport\"/>\n" +
            "    <module name=\"OneTopLevelClass\"/>\n" +
            "    <module name=\"NoLineWrap\">\n" +
            "      <property name=\"tokens\" value=\"PACKAGE_DEF, IMPORT, STATIC_IMPORT\"/>\n" +
            "    </module>\n" +
            "    <module name=\"EmptyBlock\">\n" +
            "      <property name=\"option\" value=\"TEXT\"/>\n" +
            "      <property name=\"tokens\"\n" +
            "               value=\"LITERAL_TRY, LITERAL_FINALLY, LITERAL_IF, LITERAL_ELSE, LITERAL_SWITCH\"/>\n" +
            "    </module>\n" +
            "    <module name=\"NeedBraces\">\n" +
            "      <property name=\"tokens\"\n" +
            "               value=\"LITERAL_DO, LITERAL_ELSE, LITERAL_FOR, LITERAL_IF, LITERAL_WHILE\"/>\n" +
            "    </module>\n" +
            "    <module name=\"LeftCurly\">\n" +
            "      <property name=\"tokens\"\n" +
            "               value=\"ANNOTATION_DEF, CLASS_DEF, CTOR_DEF, ENUM_CONSTANT_DEF, ENUM_DEF,\n" +
            "                    INTERFACE_DEF, LAMBDA, LITERAL_CASE, LITERAL_CATCH, LITERAL_DEFAULT,\n" +
            "                    LITERAL_DO, LITERAL_ELSE, LITERAL_FINALLY, LITERAL_FOR, LITERAL_IF,\n" +
            "                    LITERAL_SWITCH, LITERAL_SYNCHRONIZED, LITERAL_TRY, LITERAL_WHILE, METHOD_DEF,\n" +
            "                    OBJBLOCK, STATIC_INIT, RECORD_DEF, COMPACT_CTOR_DEF\"/>\n" +
            "    </module>\n" +
            "    <module name=\"RightCurly\">\n" +
            "      <property name=\"id\" value=\"RightCurlySame\"/>\n" +
            "      <property name=\"tokens\"\n" +
            "               value=\"LITERAL_TRY, LITERAL_CATCH, LITERAL_FINALLY, LITERAL_IF, LITERAL_ELSE,\n" +
            "                    LITERAL_DO\"/>\n" +
            "    </module>\n" +
            "    <module name=\"RightCurly\">\n" +
            "      <property name=\"id\" value=\"RightCurlyAlone\"/>\n" +
            "      <property name=\"option\" value=\"alone\"/>\n" +
            "      <property name=\"tokens\"\n" +
            "               value=\"CLASS_DEF, METHOD_DEF, CTOR_DEF, LITERAL_FOR, LITERAL_WHILE, STATIC_INIT,\n" +
            "                    INSTANCE_INIT, ANNOTATION_DEF, ENUM_DEF, INTERFACE_DEF, RECORD_DEF,\n" +
            "                    COMPACT_CTOR_DEF\"/>\n" +
            "    </module>\n" +
            "    <module name=\"SuppressionXpathSingleFilter\">\n" +
            "      <!-- suppresion is required till https://github.com/checkstyle/checkstyle/issues/7541 -->\n" +
            "      <property name=\"id\" value=\"RightCurlyAlone\"/>\n" +
            "      <property name=\"query\" value=\"//RCURLY[parent::SLIST[count(./*)=1]\n" +
            "                                     or preceding-sibling::*[last()][self::LCURLY]]\"/>\n" +
            "    </module>\n" +
            "    <module name=\"WhitespaceAfter\">\n" +
            "      <property name=\"tokens\"\n" +
            "               value=\"COMMA, SEMI, TYPECAST, LITERAL_IF, LITERAL_ELSE,\n" +
            "                    LITERAL_WHILE, LITERAL_DO, LITERAL_FOR, DO_WHILE\"/>\n" +
            "    </module>\n" +
            "    <module name=\"WhitespaceAround\">\n" +
            "      <property name=\"allowEmptyConstructors\" value=\"true\"/>\n" +
            "      <property name=\"allowEmptyLambdas\" value=\"true\"/>\n" +
            "      <property name=\"allowEmptyMethods\" value=\"true\"/>\n" +
            "      <property name=\"allowEmptyTypes\" value=\"true\"/>\n" +
            "      <property name=\"allowEmptyLoops\" value=\"true\"/>\n" +
            "      <property name=\"ignoreEnhancedForColon\" value=\"false\"/>\n" +
            "      <property name=\"tokens\"\n" +
            "               value=\"ASSIGN, BAND, BAND_ASSIGN, BOR, BOR_ASSIGN, BSR, BSR_ASSIGN, BXOR,\n" +
            "                    BXOR_ASSIGN, COLON, DIV, DIV_ASSIGN, DO_WHILE, EQUAL, GE, GT, LAMBDA, LAND,\n" +
            "                    LCURLY, LE, LITERAL_CATCH, LITERAL_DO, LITERAL_ELSE, LITERAL_FINALLY,\n" +
            "                    LITERAL_FOR, LITERAL_IF, LITERAL_RETURN, LITERAL_SWITCH, LITERAL_SYNCHRONIZED,\n" +
            "                    LITERAL_TRY, LITERAL_WHILE, LOR, LT, MINUS, MINUS_ASSIGN, MOD, MOD_ASSIGN,\n" +
            "                    NOT_EQUAL, PLUS, PLUS_ASSIGN, QUESTION, RCURLY, SL, SLIST, SL_ASSIGN, SR,\n" +
            "                    SR_ASSIGN, STAR, STAR_ASSIGN, LITERAL_ASSERT, TYPE_EXTENSION_AND\"/>\n" +
            "      <message key=\"ws.notFollowed\"\n" +
            "              value=\"WhitespaceAround: ''{0}'' is not followed by whitespace. Empty blocks may only be represented as '{}' when not part of a multi-block statement (4.1.3)\"/>\n" +
            "      <message key=\"ws.notPreceded\"\n" +
            "              value=\"WhitespaceAround: ''{0}'' is not preceded with whitespace.\"/>\n" +
            "    </module>\n" +
            "    <module name=\"OneStatementPerLine\"/>\n" +
            "    <module name=\"MultipleVariableDeclarations\"/>\n" +
            "    <module name=\"ArrayTypeStyle\"/>\n" +
            "    <module name=\"MissingSwitchDefault\"/>\n" +
            "    <module name=\"FallThrough\"/>\n" +
            "    <module name=\"UpperEll\"/>\n" +
            "    <module name=\"ModifierOrder\"/>\n" +
            "    <module name=\"EmptyLineSeparator\">\n" +
            "      <property name=\"tokens\"\n" +
            "               value=\"PACKAGE_DEF, IMPORT, STATIC_IMPORT, CLASS_DEF, INTERFACE_DEF, ENUM_DEF,\n" +
            "                    STATIC_INIT, INSTANCE_INIT, METHOD_DEF, CTOR_DEF, VARIABLE_DEF, RECORD_DEF,\n" +
            "                    COMPACT_CTOR_DEF\"/>\n" +
            "      <property name=\"allowNoEmptyLineBetweenFields\" value=\"true\"/>\n" +
            "    </module>\n" +
            "    <module name=\"SeparatorWrap\">\n" +
            "      <property name=\"id\" value=\"SeparatorWrapDot\"/>\n" +
            "      <property name=\"tokens\" value=\"DOT\"/>\n" +
            "      <property name=\"option\" value=\"nl\"/>\n" +
            "    </module>\n" +
            "    <module name=\"SeparatorWrap\">\n" +
            "      <property name=\"id\" value=\"SeparatorWrapComma\"/>\n" +
            "      <property name=\"tokens\" value=\"COMMA\"/>\n" +
            "      <property name=\"option\" value=\"EOL\"/>\n" +
            "    </module>\n" +
            "    <module name=\"SeparatorWrap\">\n" +
            "      <!-- ELLIPSIS is EOL until https://github.com/google/styleguide/issues/258 -->\n" +
            "      <property name=\"id\" value=\"SeparatorWrapEllipsis\"/>\n" +
            "      <property name=\"tokens\" value=\"ELLIPSIS\"/>\n" +
            "      <property name=\"option\" value=\"EOL\"/>\n" +
            "    </module>\n" +
            "    <module name=\"SeparatorWrap\">\n" +
            "      <!-- ARRAY_DECLARATOR is EOL until https://github.com/google/styleguide/issues/259 -->\n" +
            "      <property name=\"id\" value=\"SeparatorWrapArrayDeclarator\"/>\n" +
            "      <property name=\"tokens\" value=\"ARRAY_DECLARATOR\"/>\n" +
            "      <property name=\"option\" value=\"EOL\"/>\n" +
            "    </module>\n" +
            "    <module name=\"SeparatorWrap\">\n" +
            "      <property name=\"id\" value=\"SeparatorWrapMethodRef\"/>\n" +
            "      <property name=\"tokens\" value=\"METHOD_REF\"/>\n" +
            "      <property name=\"option\" value=\"nl\"/>\n" +
            "    </module>\n" +
            "    <module name=\"PackageName\">\n" +
            "      <property name=\"format\" value=\"^[a-z]+(\\.[a-z][a-z0-9]*)*$\"/>\n" +
            "      <message key=\"name.invalidPattern\"\n" +
            "             value=\"Package name ''{0}'' must match pattern ''{1}''.\"/>\n" +
            "    </module>\n" +
            "    <module name=\"TypeName\">\n" +
            "      <property name=\"tokens\" value=\"CLASS_DEF, INTERFACE_DEF, ENUM_DEF,\n" +
            "                    ANNOTATION_DEF, RECORD_DEF\"/>\n" +
            "      <message key=\"name.invalidPattern\"\n" +
            "             value=\"Type name ''{0}'' must match pattern ''{1}''.\"/>\n" +
            "    </module>\n" +
            "    <module name=\"MemberName\">\n" +
            "      <property name=\"format\" value=\"^[a-z][a-z0-9][a-zA-Z0-9]*$\"/>\n" +
            "      <message key=\"name.invalidPattern\"\n" +
            "             value=\"Member name ''{0}'' must match pattern ''{1}''.\"/>\n" +
            "    </module>\n" +
            "    <module name=\"ParameterName\">\n" +
            "      <property name=\"format\" value=\"^[a-z]([a-z0-9][a-zA-Z0-9]*)?$\"/>\n" +
            "      <message key=\"name.invalidPattern\"\n" +
            "             value=\"Parameter name ''{0}'' must match pattern ''{1}''.\"/>\n" +
            "    </module>\n" +
            "    <module name=\"LambdaParameterName\">\n" +
            "      <property name=\"format\" value=\"^[a-z]([a-z0-9][a-zA-Z0-9]*)?$\"/>\n" +
            "      <message key=\"name.invalidPattern\"\n" +
            "             value=\"Lambda parameter name ''{0}'' must match pattern ''{1}''.\"/>\n" +
            "    </module>\n" +
            "    <module name=\"CatchParameterName\">\n" +
            "      <property name=\"format\" value=\"^[a-z]([a-z0-9][a-zA-Z0-9]*)?$\"/>\n" +
            "      <message key=\"name.invalidPattern\"\n" +
            "             value=\"Catch parameter name ''{0}'' must match pattern ''{1}''.\"/>\n" +
            "    </module>\n" +
            "    <module name=\"LocalVariableName\">\n" +
            "      <property name=\"format\" value=\"^[a-z]([a-z0-9][a-zA-Z0-9]*)?$\"/>\n" +
            "      <message key=\"name.invalidPattern\"\n" +
            "             value=\"Local variable name ''{0}'' must match pattern ''{1}''.\"/>\n" +
            "    </module>\n" +
            "    <module name=\"PatternVariableName\">\n" +
            "      <property name=\"format\" value=\"^[a-z]([a-z0-9][a-zA-Z0-9]*)?$\"/>\n" +
            "      <message key=\"name.invalidPattern\"\n" +
            "             value=\"Pattern variable name ''{0}'' must match pattern ''{1}''.\"/>\n" +
            "    </module>\n" +
            "    <module name=\"ClassTypeParameterName\">\n" +
            "      <property name=\"format\" value=\"(^[A-Z][0-9]?)$|([A-Z][a-zA-Z0-9]*[T]$)\"/>\n" +
            "      <message key=\"name.invalidPattern\"\n" +
            "             value=\"Class type name ''{0}'' must match pattern ''{1}''.\"/>\n" +
            "    </module>\n" +
            "    <module name=\"RecordComponentName\">\n" +
            "      <property name=\"format\" value=\"^[a-z]([a-z0-9][a-zA-Z0-9]*)?$\"/>\n" +
            "      <message key=\"name.invalidPattern\"\n" +
            "               value=\"Record component name ''{0}'' must match pattern ''{1}''.\"/>\n" +
            "    </module>\n" +
            "    <module name=\"RecordTypeParameterName\">\n" +
            "      <property name=\"format\" value=\"(^[A-Z][0-9]?)$|([A-Z][a-zA-Z0-9]*[T]$)\"/>\n" +
            "      <message key=\"name.invalidPattern\"\n" +
            "               value=\"Record type name ''{0}'' must match pattern ''{1}''.\"/>\n" +
            "    </module>\n" +
            "    <module name=\"MethodTypeParameterName\">\n" +
            "      <property name=\"format\" value=\"(^[A-Z][0-9]?)$|([A-Z][a-zA-Z0-9]*[T]$)\"/>\n" +
            "      <message key=\"name.invalidPattern\"\n" +
            "             value=\"Method type name ''{0}'' must match pattern ''{1}''.\"/>\n" +
            "    </module>\n" +
            "    <module name=\"InterfaceTypeParameterName\">\n" +
            "      <property name=\"format\" value=\"(^[A-Z][0-9]?)$|([A-Z][a-zA-Z0-9]*[T]$)\"/>\n" +
            "      <message key=\"name.invalidPattern\"\n" +
            "             value=\"Interface type name ''{0}'' must match pattern ''{1}''.\"/>\n" +
            "    </module>\n" +
            "    <module name=\"NoFinalizer\"/>\n" +
            "    <module name=\"GenericWhitespace\">\n" +
            "      <message key=\"ws.followed\"\n" +
            "             value=\"GenericWhitespace ''{0}'' is followed by whitespace.\"/>\n" +
            "      <message key=\"ws.preceded\"\n" +
            "             value=\"GenericWhitespace ''{0}'' is preceded with whitespace.\"/>\n" +
            "      <message key=\"ws.illegalFollow\"\n" +
            "             value=\"GenericWhitespace ''{0}'' should followed by whitespace.\"/>\n" +
            "      <message key=\"ws.notPreceded\"\n" +
            "             value=\"GenericWhitespace ''{0}'' is not preceded with whitespace.\"/>\n" +
            "    </module>\n" +
            "    <module name=\"Indentation\">\n" +
            "      <property name=\"basicOffset\" value=\"2\"/>\n" +
            "      <property name=\"braceAdjustment\" value=\"2\"/>\n" +
            "      <property name=\"caseIndent\" value=\"2\"/>\n" +
            "      <property name=\"throwsIndent\" value=\"4\"/>\n" +
            "      <property name=\"lineWrappingIndentation\" value=\"4\"/>\n" +
            "      <property name=\"arrayInitIndent\" value=\"2\"/>\n" +
            "    </module>\n" +
            "    <module name=\"AbbreviationAsWordInName\">\n" +
            "      <property name=\"ignoreFinal\" value=\"false\"/>\n" +
            "      <property name=\"allowedAbbreviationLength\" value=\"0\"/>\n" +
            "      <property name=\"tokens\"\n" +
            "               value=\"CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF,\n" +
            "                    PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF, PATTERN_VARIABLE_DEF, RECORD_DEF,\n" +
            "                    RECORD_COMPONENT_DEF\"/>\n" +
            "    </module>\n" +
            "    <module name=\"OverloadMethodsDeclarationOrder\"/>\n" +
            "    <module name=\"VariableDeclarationUsageDistance\"/>\n" +
            "    <module name=\"CustomImportOrder\">\n" +
            "      <property name=\"sortImportsInGroupAlphabetically\" value=\"true\"/>\n" +
            "      <property name=\"separateLineBetweenGroups\" value=\"true\"/>\n" +
            "      <property name=\"customImportOrderRules\" value=\"STATIC###THIRD_PARTY_PACKAGE\"/>\n" +
            "      <property name=\"tokens\" value=\"IMPORT, STATIC_IMPORT, PACKAGE_DEF\"/>\n" +
            "    </module>\n" +
            "    <module name=\"MethodParamPad\">\n" +
            "      <property name=\"tokens\"\n" +
            "               value=\"CTOR_DEF, LITERAL_NEW, METHOD_CALL, METHOD_DEF,\n" +
            "                    SUPER_CTOR_CALL, ENUM_CONSTANT_DEF, RECORD_DEF\"/>\n" +
            "    </module>\n" +
            "    <module name=\"NoWhitespaceBefore\">\n" +
            "      <property name=\"tokens\"\n" +
            "               value=\"COMMA, SEMI, POST_INC, POST_DEC, DOT,\n" +
            "                    LABELED_STAT, METHOD_REF\"/>\n" +
            "      <property name=\"allowLineBreaks\" value=\"true\"/>\n" +
            "    </module>\n" +
            "    <module name=\"ParenPad\">\n" +
            "      <property name=\"tokens\"\n" +
            "               value=\"ANNOTATION, ANNOTATION_FIELD_DEF, CTOR_CALL, CTOR_DEF, DOT, ENUM_CONSTANT_DEF,\n" +
            "                    EXPR, LITERAL_CATCH, LITERAL_DO, LITERAL_FOR, LITERAL_IF, LITERAL_NEW,\n" +
            "                    LITERAL_SWITCH, LITERAL_SYNCHRONIZED, LITERAL_WHILE, METHOD_CALL,\n" +
            "                    METHOD_DEF, QUESTION, RESOURCE_SPECIFICATION, SUPER_CTOR_CALL, LAMBDA,\n" +
            "                    RECORD_DEF\"/>\n" +
            "    </module>\n" +
            "    <module name=\"OperatorWrap\">\n" +
            "      <property name=\"option\" value=\"NL\"/>\n" +
            "      <property name=\"tokens\"\n" +
            "               value=\"BAND, BOR, BSR, BXOR, DIV, EQUAL, GE, GT, LAND, LE, LITERAL_INSTANCEOF, LOR,\n" +
            "                    LT, MINUS, MOD, NOT_EQUAL, PLUS, QUESTION, SL, SR, STAR, METHOD_REF \"/>\n" +
            "    </module>\n" +
            "    <module name=\"AnnotationLocation\">\n" +
            "      <property name=\"id\" value=\"AnnotationLocationMostCases\"/>\n" +
            "      <property name=\"tokens\"\n" +
            "               value=\"CLASS_DEF, INTERFACE_DEF, ENUM_DEF, METHOD_DEF, CTOR_DEF,\n" +
            "                      RECORD_DEF, COMPACT_CTOR_DEF\"/>\n" +
            "    </module>\n" +
            "    <module name=\"AnnotationLocation\">\n" +
            "      <property name=\"id\" value=\"AnnotationLocationVariables\"/>\n" +
            "      <property name=\"tokens\" value=\"VARIABLE_DEF\"/>\n" +
            "      <property name=\"allowSamelineMultipleAnnotations\" value=\"true\"/>\n" +
            "    </module>\n" +
            "    <module name=\"NonEmptyAtclauseDescription\"/>\n" +
            "    <module name=\"InvalidJavadocPosition\"/>\n" +
            "    <module name=\"JavadocTagContinuationIndentation\"/>\n" +
            "    <module name=\"SummaryJavadoc\">\n" +
            "      <property name=\"forbiddenSummaryFragments\"\n" +
            "               value=\"^@return the *|^This method returns |^A [{]@code [a-zA-Z0-9]+[}]( is a )\"/>\n" +
            "    </module>\n" +
            "    <module name=\"JavadocParagraph\"/>\n" +
            "    <module name=\"RequireEmptyLineBeforeBlockTagGroup\"/>\n" +
            "    <module name=\"AtclauseOrder\">\n" +
            "      <property name=\"tagOrder\" value=\"@param, @return, @throws, @deprecated\"/>\n" +
            "      <property name=\"target\"\n" +
            "               value=\"CLASS_DEF, INTERFACE_DEF, ENUM_DEF, METHOD_DEF, CTOR_DEF, VARIABLE_DEF\"/>\n" +
            "    </module>\n" +
            "    <module name=\"JavadocMethod\">\n" +
            "      <property name=\"scope\" value=\"public\"/>\n" +
            "      <property name=\"allowMissingParamTags\" value=\"true\"/>\n" +
            "      <property name=\"allowMissingReturnTag\" value=\"true\"/>\n" +
            "      <property name=\"allowedAnnotations\" value=\"Override, Test\"/>\n" +
            "      <property name=\"tokens\" value=\"METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF\"/>\n" +
            "    </module>\n" +
            "    <module name=\"MissingJavadocMethod\">\n" +
            "      <property name=\"scope\" value=\"public\"/>\n" +
            "      <property name=\"minLineCount\" value=\"2\"/>\n" +
            "      <property name=\"allowedAnnotations\" value=\"Override, Test\"/>\n" +
            "      <property name=\"tokens\" value=\"METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF,\n" +
            "                                   COMPACT_CTOR_DEF\"/>\n" +
            "    </module>\n" +
            "    <module name=\"MissingJavadocType\">\n" +
            "      <property name=\"scope\" value=\"protected\"/>\n" +
            "      <property name=\"tokens\"\n" +
            "                value=\"CLASS_DEF, INTERFACE_DEF, ENUM_DEF,\n" +
            "                      RECORD_DEF, ANNOTATION_DEF\"/>\n" +
            "      <property name=\"excludeScope\" value=\"nothing\"/>\n" +
            "    </module>\n" +
            "    <module name=\"MethodName\">\n" +
            "      <property name=\"format\" value=\"^[a-z][a-z0-9][a-zA-Z0-9_]*$\"/>\n" +
            "      <message key=\"name.invalidPattern\"\n" +
            "             value=\"Method name ''{0}'' must match pattern ''{1}''.\"/>\n" +
            "    </module>\n" +
            "    <module name=\"SingleLineJavadoc\">\n" +
            "      <property name=\"ignoreInlineTags\" value=\"false\"/>\n" +
            "    </module>\n" +
            "    <module name=\"EmptyCatchBlock\">\n" +
            "      <property name=\"exceptionVariableName\" value=\"expected\"/>\n" +
            "    </module>\n" +
            "    <module name=\"CommentsIndentation\">\n" +
            "      <property name=\"tokens\" value=\"SINGLE_LINE_COMMENT, BLOCK_COMMENT_BEGIN\"/>\n" +
            "    </module>\n" +
            "    <!-- https://checkstyle.org/config_filters.html#SuppressionXpathFilter -->\n" +
            "    <module name=\"SuppressionXpathFilter\">\n" +
            "      <property name=\"file\" value=\"${org.checkstyle.google.suppressionxpathfilter.config}\"\n" +
            "             default=\"checkstyle-xpath-suppressions.xml\" />\n" +
            "      <property name=\"optional\" value=\"true\"/>\n" +
            "    </module>\n" +
            "  </module>\n" +
            "</module>";

    public static void main(String[] args) {
        // This is the main method of the FeedbackService class
        // It is used to test the methods of the FeedbackService class
        FeedbackService feedbackService = new FeedbackService();
        String code ="import java.util.Scanner;\n" +
                "\n" +
                "public class Fibonacci {\n" +
                "    public static void main(String[] args) {\n" +
                "        Scanner scanner = new Scanner(System.in);\n" +
                "        System.out.print(\"Enter the number of terms: \");\n" +
                "        int n = scanner.nextInt();\n" +
                "\n" +
                "        int[] fibSequence = fibonacci(n);\n" +
                "        System.out.print(\"Fibonacci Sequence: \");\n" +
                "        for (int i = 0; i < n; i++) {\n" +
                "            System.out.print(fibSequence[i] + \" \");\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "    public static int[] fibonacci(int n) {\n" +
                "        int[] fibSequence = new int[n];\n" +
                "        fibSequence[0] = 0;\n" +
                "        fibSequence[1] = 1;\n" +
                "\n" +
                "        for (int i = 2; i < n; i++) {\n" +
                "            fibSequence[i] = fibSequence[i - 1] + fibSequence[i - 2];\n" +
                "        }\n" +
                "        return fibSequence;\n" +
                "    }\n" +
                "}\n";
//        feedbackService.generateAndVerify(code);
//        feedbackService.generateLlmOutput(code);
//        feedbackService.verifyOutput(code);
//          feedbackService.verifyWithInfer(code);                //DONE
//          feedbackService.verifyWithSymbolicExecution(code);      //DONE

        feedbackService.verifyWithCheckstyle(code,checkstyleStringConfig);
    }
    public ResponseEntity<?> generateAndVerify (String code) {
        // This method is used to generate and verify the feedback  for the given url

        HashMap<String, Object> response=new HashMap<>();
        response.put("status", "success");
        response.put("code", code);

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> generateLlmOutput (String code) {
        // This method is used to verify the feedback for the given url

        HashMap<String, Object> response=new HashMap<>();
        response.put("status", "success");
        response.put("code", code);

        return ResponseEntity.ok(response);
    }
    public ResponseEntity<?> verifyOutput (String code) {
        // This method is used to verify the feedback for the given url

        HashMap<String, Object> response=new HashMap<>();
        response.put("status", "success");
        response.put("code", code);

        return ResponseEntity.ok(response);
    }
    public ResponseEntity<?> verifyWithInfer(String code) {
        String filePath = "Fibonacci.java"; // Path where the code will be saved

        // Write the submitted code to a file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(code);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Failed to save the submitted code.");
        }

        // Compile and analyze the code
        try {
            Process compileProcess = Runtime.getRuntime().exec("javac " + filePath);
            int compileResult = compileProcess.waitFor();
            if (compileResult != 0) {
                String errors = readProcessOutput(compileProcess.getErrorStream());
                return ResponseEntity.internalServerError().body("Compilation failed: " + errors);
            }
            String compileLog = readProcessOutput(compileProcess.getInputStream());

            Process inferProcess = Runtime.getRuntime().exec("infer run -- javac " + filePath);
            int inferResult = inferProcess.waitFor();
            if (inferResult != 0) {
                String errors = readProcessOutput(inferProcess.getErrorStream());
                return ResponseEntity.internalServerError().body("Infer analysis failed: " + errors);
            }
            String analysisResult = readProcessOutput(inferProcess.getInputStream());

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Code analyzed successfully.");
            response.put("analysisResult", analysisResult); // Return analysis result
            return ResponseEntity.ok(response);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("An error occurred during compilation or analysis.");
        }
    }

    // Helper method to read process output into a String
    private String readProcessOutput(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }
        }
        return output.toString();
    }
    public ResponseEntity<?> verifyWithCheckstyle(String code, String configContent) {
        String filePath = "Fibonacci.java";  // Path where the code will be saved
        String configFilePath = "checkstyle_config.xml"; // Path where the Checkstyle configuration will be saved

        // Write the submitted code to a file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(code);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Failed to save the submitted code.");
        }

        // Write the configuration content to a file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(configFilePath))) {
            writer.write(configContent);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Failed to save the Checkstyle configuration.");
        }

        // Compile the code to check syntax errors
        try {
            Process compileProcess = Runtime.getRuntime().exec("javac " + filePath);
            int compileResult = compileProcess.waitFor();
            if (compileResult != 0) {
                String errors = readProcessOutput(compileProcess.getErrorStream());
                return ResponseEntity.internalServerError().body("Compilation failed: " + errors);
            }

            // Run Checkstyle on the code with the configuration file just created
            String checkstyleCommand = String.format("java -jar /Users/naman/Documents/Term2/verificationValidation/feedBackFullStack/checkstyle-8.41-all.jar -c %s %s", configFilePath, filePath);
            Process checkstyleProcess = Runtime.getRuntime().exec(checkstyleCommand);
            int checkstyleResult = checkstyleProcess.waitFor();
            String checkstyleOutput = readProcessOutput(checkstyleProcess.getInputStream());
            String checkstyleErrors = readProcessOutput(checkstyleProcess.getErrorStream());

            // Check if Checkstyle found any issues
            if (checkstyleResult != 0) {
                System.out.println("Checkstyle errors: " + checkstyleErrors);
                return ResponseEntity.internalServerError().body("Checkstyle analysis failed: " + checkstyleErrors);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Code analyzed successfully with Checkstyle.");
            response.put("analysisResult", checkstyleOutput); // Return Checkstyle output
            return ResponseEntity.ok(response);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("An error occurred during compilation or Checkstyle analysis.");
        }
    }
    public ResponseEntity<?> verifyWithSymbolicExecution(String code) {
        String filePath = "Fibonacci.java";  // Path where the code will be saved
        String className = "Fibonacci";  // Assuming class name is Fibonacci

        // Write the submitted code to a file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(code);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Failed to save the submitted code.");
        }

        // Compile the code to generate class file
        try {
            Process compileProcess = Runtime.getRuntime().exec("javac " + filePath);
            int compileResult = compileProcess.waitFor();
            if (compileResult != 0) {
                String errors = readProcessOutput(compileProcess.getErrorStream());
                return ResponseEntity.internalServerError().body("Compilation failed: " + errors);
            }

            // Create a .jpf file for Java Pathfinder
            String jpfFilePath = "verification.jpf";
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(jpfFilePath))) {
                writer.write("target=" + className);
                writer.newLine();
                writer.write("classpath=.;");
            }

            // Execute Java Pathfinder with the .jpf file
            Process jpfProcess = Runtime.getRuntime().exec("java -jar ./../../jpf-core/build/RunJPF.jar " + jpfFilePath);
            int jpfResult = jpfProcess.waitFor();
            if (jpfResult != 0) {
                String errors = readProcessOutput(jpfProcess.getErrorStream());
                return ResponseEntity.internalServerError().body("Java Pathfinder analysis failed: " + errors);
            }
            String analysisResult = readProcessOutput(jpfProcess.getInputStream());

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Code analyzed successfully with Java Pathfinder.");
            response.put("analysisResult", analysisResult);  // Return analysis result
            return ResponseEntity.ok(response);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("An error occurred during compilation or analysis.");
        }
    }

}
