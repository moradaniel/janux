package org.janux.adapt;

public interface PropertyVisitor
{
	void visit(Property property);
	void visit(CompositeProperty property);
	void visit(PrimitiveProperty property);
	void pushLevel();
	void popLevel();
}
