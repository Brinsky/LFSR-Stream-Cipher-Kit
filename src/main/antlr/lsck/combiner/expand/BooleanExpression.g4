grammar BooleanExpression;

@header{
package lsck.combiner.expand;
}

expr
	:	expr expr		# Product
	|	expr '+' expr	# Sum
	|	VARIABLE+		# SimpleTerm
	|	CONSTANT		# Constant
	|	'(' expr ')'	# Parens
	;

VARIABLE : 'x' [0-9]+;

CONSTANT : '0' | '1';

WHITESPACE : (' ' | '\t' | '\r' | '\n') -> skip ;