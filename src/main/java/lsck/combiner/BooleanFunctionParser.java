package lsck.combiner;

import java.util.BitSet;
import java.util.HashSet;
import java.util.Set;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import lsck.bitwise.BitVector;
import lsck.combiner.expand.BooleanExpressionBaseVisitor;
import lsck.combiner.expand.BooleanExpressionLexer;
import lsck.combiner.expand.BooleanExpressionParser;
import lsck.combiner.expand.BooleanExpressionParser.ConstantContext;
import lsck.combiner.expand.BooleanExpressionParser.ParensContext;
import lsck.combiner.expand.BooleanExpressionParser.ProductContext;
import lsck.combiner.expand.BooleanExpressionParser.SimpleTermContext;
import lsck.combiner.expand.BooleanExpressionParser.SumContext;
import lsck.common.Exceptions;

/**
 * Parses, expands, and simplifies a string representation of a Boolean function.
 *
 * <p>The function / expression is expanded and simplified into a set (representing a sum) of terms.
 * See {@link TermTable} for a description of the representation of terms using {@link BitVector}s.
 */
class BooleanFunctionParser {

  public static Set<BitVector> expand(int arity, String expression, boolean indexFromZero) {
    BooleanExpressionLexer lexer = new BooleanExpressionLexer(CharStreams.fromString(expression));
    lexer.removeErrorListeners();
    lexer.addErrorListener(new ExceptionErrorListener());

    BooleanExpressionParser parser = new BooleanExpressionParser(new CommonTokenStream(lexer));
    parser.removeErrorListeners();
    parser.addErrorListener(new ExceptionErrorListener());

    ParseTree tree = parser.expr();
    ExpressionVisitor visitor = new ExpressionVisitor(arity, indexFromZero);

    return visitor.visit(tree);
  }

  private static class ExpressionVisitor extends BooleanExpressionBaseVisitor<Set<BitVector>> {

    private final int arity;
    private final boolean indexFromZero;

    public ExpressionVisitor(int arity, boolean indexFromZero) {
      this.arity = arity;
      this.indexFromZero = indexFromZero;
    }

    @Override
    public Set<BitVector> visitParens(ParensContext ctx) {
      return visit(ctx.expr());
    }

    @Override
    public Set<BitVector> visitProduct(ProductContext ctx) {
      // Collect terms from child expressions
      Set<BitVector> leftTerms = visit(ctx.expr(0));
      Set<BitVector> rightTerms = visit(ctx.expr(1));

      Set<BitVector> expanded = new HashSet<>(leftTerms.size() * rightTerms.size());

      for (BitVector leftTerm : leftTerms) {
        for (BitVector rightTerm : rightTerms) {
          // In our context, a bitwise OR of a term vector is equivalent to
          // multiplication. This is because raising a Boolean variable to a positive
          // power leaves it unchanged.
          BitVector product = leftTerm.or(rightTerm);

          // In a Boolean context, any pair of equivalent terms cancel each other.
          if (expanded.contains(product)) {
            expanded.remove(product);
          } else {
            expanded.add(product);
          }
        }
      }

      return expanded;
    }

    @Override
    public Set<BitVector> visitSum(SumContext ctx) {
      // Start with terms from the left-hand expression, then add the terms from the
      // right-hand expression.
      Set<BitVector> expanded = visit(ctx.expr(0));

      // "Add up" all the SimpleTerms produced by simplifying our terms
      for (BitVector term : visit(ctx.expr(1))) {
        // In a Boolean context, any pair of equivalent terms cancel each other.
        if (expanded.contains(term)) {
          expanded.remove(term);
        } else {
          expanded.add(term);
        }
      }

      return expanded;
    }

    @Override
    public Set<BitVector> visitSimpleTerm(SimpleTermContext ctx) {
      BitSet term = new BitSet(arity);

      // Set the corresponding bit for each variable present in this term.
      // Multiplicity is unimportant since raising a Boolean variable to a positive
      // power leaves it unchanged.
      for (TerminalNode variable : ctx.VARIABLE()) {
        int index = Integer.parseInt(variable.getText().substring(1));

        if (!indexFromZero) {
          if (index == 0 || index > arity) {
            throw Exceptions.invalidIndexRangeException(1, arity, index);
          }

          index--; // Map all indices into [0, arity) range
        } else if (index >= arity) { // When indexing from zero
          throw Exceptions.invalidIndexRangeException(0, arity - 1, index);
        }

        term.set(arity - 1 - index);
      }

      // For compatibility with the rest of the tree, we need to wrap our single term
      // in a set.
      Set<BitVector> expanded = new HashSet<BitVector>(1);
      expanded.add(BitVector.fromBitSet(arity, term));

      return expanded;
    }

    @Override
    public Set<BitVector> visitConstant(ConstantContext ctx) {
      Set<BitVector> expanded = new HashSet<>(1);

      // '1' is represented by a vector of all zeros
      if (ctx.getText().equals("1")) expanded.add(BitVector.nullVector(arity));

      return expanded;
    }
  }

  // Error-handling approach inspired by https://stackoverflow.com/a/26573239
  private static class ExceptionErrorListener extends BaseErrorListener {

    @Override
    public void syntaxError(
        Recognizer<?, ?> recognizer,
        Object offendingSymbol,
        int line,
        int charPositionInLine,
        String msg,
        RecognitionException e) {
      throw new ParseCancellationException("Line " + line + ":" + charPositionInLine + " " + msg);
    }
  }
}
