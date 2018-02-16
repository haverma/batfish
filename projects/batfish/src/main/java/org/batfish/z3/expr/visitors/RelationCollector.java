package org.batfish.z3.expr.visitors;

import com.google.common.collect.ImmutableSet;
import java.util.Set;
import org.batfish.z3.SynthesizerInput;
import org.batfish.z3.expr.AndExpr;
import org.batfish.z3.expr.BitVecExpr;
import org.batfish.z3.expr.Comment;
import org.batfish.z3.expr.DeclareRelStatement;
import org.batfish.z3.expr.DeclareVarStatement;
import org.batfish.z3.expr.EqExpr;
import org.batfish.z3.expr.Expr;
import org.batfish.z3.expr.ExtractExpr;
import org.batfish.z3.expr.FalseExpr;
import org.batfish.z3.expr.HeaderSpaceMatchExpr;
import org.batfish.z3.expr.IdExpr;
import org.batfish.z3.expr.IfExpr;
import org.batfish.z3.expr.ListExpr;
import org.batfish.z3.expr.LitIntExpr;
import org.batfish.z3.expr.NotExpr;
import org.batfish.z3.expr.OrExpr;
import org.batfish.z3.expr.PrefixMatchExpr;
import org.batfish.z3.expr.QueryStatement;
import org.batfish.z3.expr.RangeMatchExpr;
import org.batfish.z3.expr.RuleStatement;
import org.batfish.z3.expr.SaneExpr;
import org.batfish.z3.expr.StateExpr;
import org.batfish.z3.expr.Statement;
import org.batfish.z3.expr.TrueExpr;
import org.batfish.z3.expr.VarIntExpr;
import org.batfish.z3.expr.VoidStatementVisitor;

public class RelationCollector implements ExprVisitor, VoidStatementVisitor {

  public static Set<String> collectRelations(SynthesizerInput input, Expr expr) {
    RelationCollector relationCollector = new RelationCollector(input);
    expr.accept(relationCollector);
    return relationCollector._relations.build();
  }

  public static Set<String> collectRelations(SynthesizerInput input, Statement statement) {
    RelationCollector relationCollector = new RelationCollector(input);
    statement.accept(relationCollector);
    return relationCollector._relations.build();
  }

  private final SynthesizerInput _input;

  private final ImmutableSet.Builder<String> _relations;

  private RelationCollector(SynthesizerInput input) {
    _input = input;
    _relations = ImmutableSet.builder();
  }

  @Override
  public void visitAndExpr(AndExpr andExpr) {
    andExpr.getConjuncts().forEach(expr -> expr.accept(this));
  }

  @Override
  public void visitBitVecExpr(BitVecExpr bitVecExpr) {}

  @Override
  public void visitComment(Comment comment) {}

  @Override
  public void visitDeclareRelStatement(DeclareRelStatement declareRelStatement) {}

  @Override
  public void visitDeclareVarStatement(DeclareVarStatement declareVarStatement) {}

  @Override
  public void visitEqExpr(EqExpr eqExpr) {
    eqExpr.getLhs().accept(this);
    eqExpr.getRhs().accept(this);
  }

  @Override
  public void visitExtractExpr(ExtractExpr extractExpr) {}

  @Override
  public void visitFalseExpr(FalseExpr falseExpr) {}

  @Override
  public void visitHeaderSpaceMatchExpr(HeaderSpaceMatchExpr headerSpaceMatchExpr) {
    headerSpaceMatchExpr.getExpr().accept(this);
  }

  @Override
  public void visitIdExpr(IdExpr idExpr) {}

  @Override
  public void visitIfExpr(IfExpr ifExpr) {
    ifExpr.getAntecedent().accept(this);
    ifExpr.getConsequent().accept(this);
  }

  @Override
  public void visitListExpr(ListExpr listExpr) {
    listExpr.getSubExpressions().forEach(expr -> expr.accept(this));
  }

  @Override
  public void visitLitIntExpr(LitIntExpr litIntExpr) {}

  @Override
  public void visitNotExpr(NotExpr notExpr) {
    notExpr.getArg().accept(this);
  }

  @Override
  public void visitOrExpr(OrExpr orExpr) {
    orExpr.getDisjuncts().forEach(expr -> expr.accept(this));
  }

  @Override
  public void visitPrefixMatchExpr(PrefixMatchExpr prefixMatchExpr) {
    prefixMatchExpr.getExpr().accept(this);
  }

  @Override
  public void visitQueryStatement(QueryStatement queryStatement) {
    queryStatement.getSubExpression().accept(this);
  }

  @Override
  public void visitRangeMatchExpr(RangeMatchExpr rangeMatchExpr) {
    rangeMatchExpr.getExpr().accept(this);
  }

  @Override
  public void visitRuleStatement(RuleStatement ruleStatement) {
    ruleStatement.getSubExpression().accept(this);
  }

  @Override
  public void visitSaneExpr(SaneExpr saneExpr) {
    saneExpr.getExpr().accept(this);
  }

  @Override
  public void visitStateExpr(StateExpr stateExpr) {
    _relations.add(BoolExprTransformer.getNodName(_input, stateExpr));
  }

  @Override
  public void visitTrueExpr(TrueExpr trueExpr) {}

  @Override
  public void visitVarIntExpr(VarIntExpr varIntExpr) {}
}