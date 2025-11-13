# Goals and feature set
Start with practical subset that handles most common queries:
- `SELECT` with multiple columns and expressions: `SELECT a, b+1 AS x, COUNT(*)`
- `FROM` with table names and aliases, and subqueries in `FROM`: `FROM (SELECT ...) AS t`
- `WHERE` with full expressions, parentheses, `AND/OR/NOT`, comparison ops (`=, <>, <, <=, >, >=`), `IN`, `LIKE`, `IS NULL`, `BETWEEN`.
- Function calls and wildcards: `COUNT(*)`, `UPPER(name)`, `*`
- Produce an AST with location information (line/col or char index)

# High-level architecture:
Layers:
1. Lexer: turns raw SQL string -> tokens (IDENT, NUMBER, STRING, SYMBOL, KEYWORD, EOF).
2. Parser: consumes tokens -> AST. Uses 
 - recursive-descent for statement structure (SELECT, FROM, WHERE...)
 - Pratt (top-down operator precedence) for expressions.
3. AST: immutable node classes representing queries and expressions.
4. Binder: (next step) resolve identifiers to tables/columns, types
5. Tests: small SQL strings -> assert AST shape

# Tokenizer (lexer)
Key points:
- Support single/double-quoted strings (handle escapes)
- Recognize keywords case-insensitively
- Provide token position (index)
- Produce tokens for `(`, `)`, `,`, `*`, `;`

# Parser - structure + Pratt expression parser
Strategy:
- Implement `Parser` which consumes tokens with an index pointer.
- Implement `parseSelect()` (recursive-descent)
- For expressions, implement Pratt parser: `parseExpression(minPrecedence)` with prefix and infix parselets.

# Error handling & position info
- Tokens already have a `pos` index. When throwing parse errors, include `t().pos`
- Implement synchronization on parse errors when parsing long scripts (skip to next ;)