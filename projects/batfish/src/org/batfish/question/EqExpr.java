package org.batfish.question;

public class EqExpr extends BaseBooleanExpr {

   private final IntExpr _lhs;

   private final IntExpr _rhs;

   public EqExpr(IntExpr lhs, IntExpr rhs) {
      _lhs = lhs;
      _rhs = rhs;
   }

   @Override
   public boolean evaluate(Environment env) {
      int lhs = _lhs.evaluate(env);
      int rhs = _rhs.evaluate(env);
      return lhs == rhs;
   }

}
