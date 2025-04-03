package com.echevarne.sap.cloud.facturacion.odata.processor.parser;

import org.apache.olingo.odata2.api.edm.EdmEntityType;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmLiteral;
import org.apache.olingo.odata2.api.edm.EdmSimpleType;
import org.apache.olingo.odata2.api.edm.EdmSimpleTypeKind;
import org.apache.olingo.odata2.api.uri.expression.CommonExpression;
import org.apache.olingo.odata2.api.uri.expression.ExpressionParserException;
import org.apache.olingo.odata2.api.uri.expression.FilterExpression;
import org.apache.olingo.odata2.api.uri.expression.LiteralExpression;
import org.apache.olingo.odata2.api.uri.expression.MemberExpression;
import org.apache.olingo.odata2.api.uri.expression.PropertyExpression;
import org.apache.olingo.odata2.core.edm.EdmBoolean;
import org.apache.olingo.odata2.core.uri.expression.ActualBinaryOperator;
import org.apache.olingo.odata2.core.uri.expression.ExpressionParserInternalError;
import org.apache.olingo.odata2.core.uri.expression.FilterExpressionImpl;
import org.apache.olingo.odata2.core.uri.expression.FilterParserExceptionImpl;
import org.apache.olingo.odata2.core.uri.expression.FilterParserImpl;
import org.apache.olingo.odata2.core.uri.expression.LiteralExpressionImpl;
import org.apache.olingo.odata2.core.uri.expression.Token;
import org.apache.olingo.odata2.core.uri.expression.Tokenizer;
import org.apache.olingo.odata2.core.uri.expression.TokenizerException;

import com.echevarne.sap.cloud.facturacion.util.ConversionUtils;
import com.echevarne.sap.cloud.facturacion.util.StringUtils;

public class SQLFilterParser extends FilterParserImpl {

  public SQLFilterParser(EdmEntityType resourceEntityType) {
    super(resourceEntityType);
  }

  @Override
  public FilterExpression parseFilterString(final String filterExpression)
      throws ExpressionParserException, ExpressionParserInternalError {
    return parseFilterString(filterExpression, false);
  }

  @Override
  public FilterExpression parseFilterString(final String filterExpression, final boolean allowOnlyBinary)
      throws ExpressionParserException, ExpressionParserInternalError {
    CommonExpression node = null;
    curExpression = filterExpression;
    try {
      // Throws TokenizerException and FilterParserException. FilterParserException is
      // caught somewhere above
      tokenList = new Tokenizer(filterExpression).tokenize();
      if (!tokenList.hasTokens()) {
        return new FilterExpressionImpl(filterExpression);
      }
    } catch (TokenizerException tokenizerException) {
      // Tested with TestParserExceptions.TestPMparseFilterString
      throw FilterParserExceptionImpl.createERROR_IN_TOKENIZER(tokenizerException, curExpression);
    }

    try {
      CommonExpression nodeLeft = readElement(null);
      node = readElements(nodeLeft, 0);
    } catch (ExpressionParserException filterParserException) {
      // Add empty filterTree to Exception
      // Tested for original throw point
      filterParserException.setFilterTree(new FilterExpressionImpl(filterExpression));
      throw filterParserException;
    }

    // Post check
    // if (tokenList.tokenCount() > tokenList.currentToken) // this indicates that
    // not all tokens have been read
    // {
    // // Tested with TestParserExceptions.TestPMparseFilterString
    // throw
    // FilterParserExceptionImpl.createINVALID_TRAILING_TOKEN_DETECTED_AFTER_PARSING(
    // tokenList.elementAt(tokenList.currentToken), filterExpression);
    // }

    // Create and return filterExpression node
    if ((allowOnlyBinary) && (node.getEdmType() != null)
        && (node.getEdmType() != EdmSimpleTypeKind.Boolean.getEdmSimpleTypeInstance())) {
      // Tested with TestParserExceptions.testAdditionalStuff CASE 9
      throw FilterParserExceptionImpl.createTYPE_EXPECTED_AT(EdmBoolean.getInstance(), node.getEdmType(), 1,
          curExpression);
    }

    return new FilterExpressionImpl(filterExpression, node);
  }

  @Override
  protected CommonExpression readElement(final CommonExpression leftExpression, final ActualBinaryOperator leftOperator)
      throws ExpressionParserException, ExpressionParserInternalError {
    CommonExpression expression = super.readElement(leftExpression, leftOperator);
    if (expression instanceof LiteralExpression && leftExpression instanceof PropertyExpression) {
      Token token = this.tokenList.lookPrevToken();

      try {
        String typeName = token.getEdmType().getName();
        if (!"Boolean".equals(typeName)) {
          EdmLiteral uriLiteral = EdmSimpleTypeKind.parseUriLiteral(token.getUriLiteral());
          String internalValue = StringUtils.QUOTE + ConversionUtils.applyAlphaConversionForField(leftExpression.getUriLiteral(),
                  uriLiteral.getLiteral()) + StringUtils.QUOTE;
          // TODO validar si hace falta colocar las comillas simples ''
          expression = new LiteralExpressionImpl(internalValue, token.getJavaLiteral());
        }

      } catch (EdmException e) {
        // Nothing to do
      }
    } else if (expression instanceof LiteralExpression && leftExpression instanceof MemberExpression) {
      Token token = this.tokenList.lookPrevToken();

      try {
        String typeName = token.getEdmType().getName();

        if (!"Boolean".equals(typeName)) {
          EdmLiteral uriLiteral = EdmSimpleTypeKind.parseUriLiteral(token.getUriLiteral());
          String internalValue = StringUtils.QUOTE + ConversionUtils.applyAlphaConversionForField(leftExpression.getUriLiteral(),
                  uriLiteral.getLiteral()) + StringUtils.QUOTE;
          MemberExpression mem = (MemberExpression)leftExpression;
          EdmLiteral edmLiteral = new EdmLiteral((EdmSimpleType) mem.getProperty().getEdmType(), token.getJavaLiteral().getLiteral());
          expression = new LiteralExpressionImpl(internalValue, edmLiteral);
        }
      } catch (EdmException e) {
        // Nothing to do
      }
    }
    return expression;
  }

}
