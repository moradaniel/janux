package org.janux.adapt;

import java.io.OutputStream;
import java.io.PrintStream;

public class PropertyPrinter implements PropertyVisitor
{
	private int level = 0;
	private PrintStream out;
	
	public PropertyPrinter()
	{
		out = System.out;
	}
	
	public PropertyPrinter(OutputStream os)
	{
		out = new PrintStream(os);
	}
	
	public void visit(Property property)
	{
		this.indent();
		PropertyMetadata metaData = property.getMetadata();
		out.println(metaData.getName() + " (" + metaData.getLabel() + ") " + " [" + metaData.getDataType() + "]: " + property.getValue());
	}

	public void visit(CompositeProperty composite)
	{
		this.indent();
		PropertyMetadata metaData = composite.getMetadata();
		out.println(metaData.getName() + " (" + metaData.getLabel() + ") " + " [" + composite.getCount() + " items]:");
	}

	public void visit(PrimitiveProperty primitive)
	{
		Property property = primitive;
		this.visit(property);
	}

	public void popLevel()
	{
		level--;
	}

	public void pushLevel()
	{
		level++;
	}

	private void indent()
	{
		for (int i = 0; i < level; i++)
		{
			out.print('\t');
		}
	}
}
