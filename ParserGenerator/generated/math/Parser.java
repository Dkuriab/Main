package math;

public class Parser {
    private final LexicalAnalyzer tokens;
    private Token token;

    private void nextToken() {
        tokens.nextToken();
        token = tokens.getToken();
    }

    public Parser(LexicalAnalyzer tokens) {
        this.tokens = tokens;
        nextToken();
    }

    public TermS termS(int acc) {
        TermS answer = new TermS("termS");
        switch(token.typeToken()) {
		case MUL -> {
                String MUL = token.text();
                answer.addChild(token.text());
                nextToken();
                Power power = power();
                answer.addChild(power);
                answer.val = acc * power.val;
                TermS termS = termS(answer.val);
                answer.addChild(termS);
                answer.val = termS.val;
            }
		case DIV -> {
                String DIV = token.text();
                answer.addChild(token.text());
                nextToken();
                Power power = power();
                answer.addChild(power);
                answer.val = acc / power.val;
                TermS termS = termS(answer.val);
                answer.addChild(termS);
                answer.val = termS.val;
            }
		case END, CLOSE, PLUS, MINUS -> {
                answer.addChild("eps");
                answer.val = acc;
            }
	        default ->
	            throw new ParseException("No valid token: " + token.text());
	    }
	    return answer;
	}
    public ExprS exprS(int acc) {
        ExprS answer = new ExprS("exprS");
        switch(token.typeToken()) {
		case PLUS -> {
                String PLUS = token.text();
                answer.addChild(token.text());
                nextToken();
                Term term = term();
                answer.addChild(term);
                answer.val = acc + term.val;
                ExprS exprS = exprS(answer.val);
                answer.addChild(exprS);
                answer.val = exprS.val;
            }
		case MINUS -> {
                String MINUS = token.text();
                answer.addChild(token.text());
                nextToken();
                Term term = term();
                answer.addChild(term);
                answer.val = acc - term.val;
                ExprS exprS = exprS(answer.val);
                answer.addChild(exprS);
                answer.val = exprS.val;
            }
		case END, CLOSE -> {
                answer.addChild("eps");
                answer.val = acc;
            }
	        default ->
	            throw new ParseException("No valid token: " + token.text());
	    }
	    return answer;
	}
    public Term term() {
        Term answer = new Term("term");
        switch(token.typeToken()) {
		case SQR, NUM, OPEN, MINUS -> {
                Power power = power();
                answer.addChild(power);
                TermS termS = termS(power.val);
                answer.addChild(termS);
                answer.val = termS.val;
            }
	        default ->
	            throw new ParseException("No valid token: " + token.text());
	    }
	    return answer;
	}
    public Expr expr() {
        Expr answer = new Expr("expr");
        switch(token.typeToken()) {
		case SQR, NUM, OPEN, MINUS -> {
                Term term = term();
                answer.addChild(term);
                ExprS exprS = exprS(term.val);
                answer.addChild(exprS);
                answer.val = exprS.val;
            }
	        default ->
	            throw new ParseException("No valid token: " + token.text());
	    }
	    return answer;
	}
    public Power power() {
        Power answer = new Power("power");
        switch(token.typeToken()) {
		case SQR, NUM, OPEN, MINUS -> {
                Factor factor = factor();
                answer.addChild(factor);
                PowerS powerS = powerS(factor.val);
                answer.addChild(powerS);
                answer.val = powerS.val;
            }
	        default ->
	            throw new ParseException("No valid token: " + token.text());
	    }
	    return answer;
	}
    public Factor factor() {
        Factor answer = new Factor("factor");
        switch(token.typeToken()) {
		case SQR -> {
                String SQR = token.text();
                answer.addChild(token.text());
                nextToken();
                Factor factor = factor();
                answer.addChild(factor);
                answer.val = factor.val * factor.val;
            }
		case NUM -> {
                String NUM = token.text();
                answer.addChild(token.text());
                nextToken();
                answer.val = Integer.parseInt(NUM);
            }
		case OPEN -> {
                String OPEN = token.text();
                answer.addChild(token.text());
                nextToken();
                Expr expr = expr();
                answer.addChild(expr);
                String CLOSE = token.text();
                answer.addChild(token.text());
                nextToken();
                answer.val = expr.val;
            }
		case MINUS -> {
                String MINUS = token.text();
                answer.addChild(token.text());
                nextToken();
                Factor factor = factor();
                answer.addChild(factor);
                answer.val = (-1) * factor.val;
            }
	        default ->
	            throw new ParseException("No valid token: " + token.text());
	    }
	    return answer;
	}
    public PowerS powerS(int acc) {
        PowerS answer = new PowerS("powerS");
        switch(token.typeToken()) {
		case POW -> {
                String POW = token.text();
                answer.addChild(token.text());
                nextToken();
                Power power = power();
                answer.addChild(power);
                answer.val = (int) Math.pow(acc, power.val);
            }
		case DIV, MUL, END, CLOSE, PLUS, MINUS -> {
                answer.addChild("eps");
                answer.val = acc;
            }
	        default ->
	            throw new ParseException("No valid token: " + token.text());
	    }
	    return answer;
	}

    public static class TermS extends Tree {
		public int val;
        public TermS(String node) {
            super(node);
        }
    }

    public static class ExprS extends Tree {
		public int val;
        public ExprS(String node) {
            super(node);
        }
    }

    public static class Term extends Tree {
		public int val;
        public Term(String node) {
            super(node);
        }
    }

    public static class Expr extends Tree {
		public int val;
        public Expr(String node) {
            super(node);
        }
    }

    public static class Power extends Tree {
		public int val;
        public Power(String node) {
            super(node);
        }
    }

    public static class Factor extends Tree {
		public int val;
        public Factor(String node) {
            super(node);
        }
    }

    public static class PowerS extends Tree {
		public int val;
        public PowerS(String node) {
            super(node);
        }
    }


 }